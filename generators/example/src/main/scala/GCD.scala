package example

import chisel3._
import chisel3.util._
import chisel3.core.{IntParam, Reset}
import freechips.rocketchip.amba.axi4._
import freechips.rocketchip.subsystem.BaseSubsystem
import freechips.rocketchip.config.{Parameters, Field}
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.regmapper.{HasRegMap, RegField}
import freechips.rocketchip.tilelink._
import freechips.rocketchip.util.UIntIsOneOf

case class GCDParams(
  address: BigInt = 0x2000,
  width: Int = 32,
  useAXI4: Boolean = false,
  useBlackBox: Boolean = true)


case object GCDKey extends Field[Option[GCDParams]](None)

class GCDIO(w: Int) extends Bundle {
  val clock = Input(Clock())
  val reset = Input(Bool())
  val input_ready = Output(Bool())
  val input_valid = Input(Bool())
  val x = Input(UInt(w.W))
  val y = Input(UInt(w.W))
  val output_ready = Input(Bool())
  val output_valid = Output(Bool())
  val gcd = Output(UInt(w.W))
  val busy = Output(Bool())
}

trait GCDTopIO extends Bundle {
  val gcd_busy = Output(Bool())
}

// DOC include start: GCD blackbox
class GCDMMIOBlackBox(w: Int) extends BlackBox(Map("WIDTH" -> IntParam(w))) with HasBlackBoxResource
{
  val io = IO(new GCDIO(w))

  addResource("/vsrc/GCDMMIOBlackBox.v")
}
// DOC include end: GCD blackbox

class GCDMMIOChiselModule(w: Int) extends Module
{
  val io = IO(new GCDIO(w))

  val s_idle :: s_run :: s_done :: Nil = Enum(3)

  val state = RegInit(s_idle)
  val tmp   = Reg(UInt(w.W))
  val gcd   = Reg(UInt(w.W))

  io.input_ready := state === s_idle
  io.output_valid := state === s_done
  io.gcd := gcd

  when (state === s_idle && io.input_valid) {
    state := s_run
  } .elsewhen (state === s_run && tmp === 0.U) {
    state := s_done
  } .elsewhen (state === s_done && io.output_ready) {
    state := s_idle
  }

  when (state === s_idle && io.input_valid) {
    gcd := io.x
    tmp := io.y
  } .elsewhen (state === s_run) {
    when (gcd > tmp) {
      gcd := gcd - tmp
    } .otherwise {
      tmp := tmp - gcd
    }
  }

  io.busy := state =/= s_idle
}

// DOC include start: GCD instance regmap

trait GCDModule extends HasRegMap {
  val io: GCDTopIO

  implicit val p: Parameters
  def params: GCDParams
  val clock: Clock
  val reset: Reset


  // How many clock cycles in a PWM cycle?
  val x = Reg(UInt(params.width.W))
  val y = Wire(new DecoupledIO(UInt(params.width.W)))
  val gcd = Wire(new DecoupledIO(UInt(params.width.W)))
  val status = Wire(UInt(2.W))

  if (params.useBlackBox) {
    val impl = Module(new GCDMMIOBlackBox(params.width))

    impl.io.clock := clock
    impl.io.reset := reset.asBool
    impl.io.x := x
    impl.io.y := y.bits
    impl.io.input_valid := y.valid
    y.ready := impl.io.input_ready

    gcd.bits := impl.io.gcd
    gcd.valid := impl.io.output_valid
    impl.io.output_ready := gcd.ready

    status := Cat(impl.io.input_ready, impl.io.output_ready)
    io.gcd_busy := impl.io.busy
  } else {
    val impl = Module(new GCDMMIOChiselModule(params.width))

    impl.io.clock := clock
    impl.io.reset := reset.asBool

    impl.io.x := x
    impl.io.y := y.bits
    impl.io.input_valid := y.valid
    y.ready := impl.io.input_ready

    gcd.bits := impl.io.gcd
    gcd.valid := impl.io.output_valid
    impl.io.output_ready := gcd.ready

    status := Cat(impl.io.input_ready, impl.io.output_ready)
    io.gcd_busy := impl.io.busy
  }

  regmap(
    0x00 -> Seq(
      RegField.r(2, status)), // a read-only register capturing current status
    0x04 -> Seq(
      RegField.w(params.width, x)), // a plain, write-only register
    0x08 -> Seq(
      RegField.w(params.width, y)), // write-only, y.valid is set on write
    0x0C -> Seq(
      RegField.r(params.width, gcd))) // read-only, gcd.ready is set on read
}
// DOC include end: GCD instance regmap

// DOC include start: GCD cake
class GCDTL(params: GCDParams, beatBytes: Int)(implicit p: Parameters)
  extends TLRegisterRouter(
    params.address, "gcd", Seq("ucbbar,gcd"),
    beatBytes = beatBytes)(
      new TLRegBundle(params, _) with GCDTopIO)(
      new TLRegModule(params, _, _) with GCDModule)

class GCDAXI4(params: GCDParams, beatBytes: Int)(implicit p: Parameters)
  extends AXI4RegisterRouter(
    params.address,
    beatBytes=beatBytes)(
      new AXI4RegBundle(params, _) with GCDTopIO)(
      new AXI4RegModule(params, _, _) with GCDModule)

trait CanHavePeripheryGCD { this: BaseSubsystem =>
  private val portName = "gcd"

  // Only build if we are using the TL (nonAXI4) version
  val gcd = p(GCDKey) match {
    case Some(params) => {
      if (params.useAXI4) {
        val gcd = LazyModule(new GCDAXI4(params, pbus.beatBytes)(p))
        pbus.toSlave(Some(portName)) {
          gcd.node :=
          AXI4Buffer () :=
          TLToAXI4 () :=
          // toVariableWidthSlave doesn't use holdFirstDeny, which TLToAXI4() needsx
          TLFragmenter(pbus.beatBytes, pbus.blockBytes, holdFirstDeny = true)
        }
        Some(gcd)
      } else {
        val gcd = LazyModule(new GCDTL(params, pbus.beatBytes)(p))
        pbus.toVariableWidthSlave(Some(portName)) { gcd.node }
        Some(gcd)
      }
    }
    case None => None
  }
}

trait CanHavePeripheryGCDModuleImp extends LazyModuleImp {
  val outer: CanHavePeripheryGCD
  outer.gcd.map { g =>
    val gcd_busy = IO(Output(Bool()))
    gcd_busy := g.module.io.gcd_busy
  }
}

// DOC include end: GCD cake
