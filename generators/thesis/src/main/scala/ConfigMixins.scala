package thesis

import chisel3._

import freechips.rocketchip.config.{Field, Parameters, Config}
import freechips.rocketchip.subsystem.{RocketTilesKey, WithRoccExample, WithNMemoryChannels, WithNBigCores, WithRV32}
import freechips.rocketchip.diplomacy.{LazyModule, ValName}
import freechips.rocketchip.devices.tilelink.BootROMParams
import freechips.rocketchip.tile.{XLen, BuildRoCC, TileKey, LazyRoCC}

import boom.common.{BoomTilesKey}

import testchipip._

import sifive.blocks.devices.gpio._

object ConfigValName {
  implicit val valName = ValName("TestHarness")
}
import ConfigValName._


class WithTop extends Config((site, here, up) => {
  case BuildTop => (clock: Clock, reset: Bool, p: Parameters) => {
    Module(LazyModule(new Top()(p)).module)
  }
})


class WithBootROM extends Config((site, here, up) => {
  case BootROMParams => BootROMParams(
    contentFileName = s"./bootrom/bootrom.rv${site(XLen)}.img")
})


class WithDTMTop extends Config((site, here, up) => {
  case BuildTopWithDTM => (clock: Clock, reset: Bool, p: Parameters) => {
    Module(LazyModule(new TopWithDTM()(p)).module)
  }
})
