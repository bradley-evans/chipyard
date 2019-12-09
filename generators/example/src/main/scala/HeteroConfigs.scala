package example

import chisel3._

import freechips.rocketchip.config.{Config}

// ---------------------
// Heterogenous Configs
// ---------------------

class LargeBoomAndRocketConfig extends Config(
  new WithTSI ++                                           // default top
  new WithNoGPIO ++                                        // no GPIO pins
  new WithBootROM ++                                       // default bootrom
  new freechips.rocketchip.subsystem.WithInclusiveCache ++ // use SiFive l2
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++     // no mmio master port
  new freechips.rocketchip.subsystem.WithNoSlavePort ++    // no mmio slave port
  new boom.common.WithRenumberHarts ++                     // avoid hartid overlap
  new boom.common.WithLargeBooms ++                        // 3-wide boom
  new boom.common.WithNBoomCores(1) ++                     // single-core boom
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++   // single-core rocket
  new freechips.rocketchip.system.BaseConfig)              // "base" rocketchip system

class SmallBoomAndRocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new boom.common.WithRenumberHarts ++
  new boom.common.WithSmallBooms ++                        // 1-wide boom
  new boom.common.WithNBoomCores(1) ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)

// DOC include start: BoomAndRocketWithHwacha
class HwachaLargeBoomAndHwachaRocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithBootROM ++
  new hwacha.DefaultHwachaConfig ++                      // add hwacha to all harts
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new boom.common.WithRenumberHarts ++
  new boom.common.WithLargeBooms ++
  new boom.common.WithNBoomCores(1) ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)
// DOC include end: BoomAndRocketWithHwacha

class RoccLargeBoomAndRoccRocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithRoccExample ++  // add example rocc accelerator to all harts
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new boom.common.WithRenumberHarts ++
  new boom.common.WithLargeBooms ++
  new boom.common.WithNBoomCores(1) ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)

class DualLargeBoomAndRocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new boom.common.WithRenumberHarts ++
  new boom.common.WithLargeBooms ++
  new boom.common.WithNBoomCores(2) ++                  // 2-boom cores
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)

// DOC include start: DualBoomAndRocketOneHwacha
class DualLargeBoomAndHwachaRocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new WithMultiRoCC ++                                  // support heterogeneous rocc
  new WithMultiRoCCHwacha(2) ++                         // put hwacha on hart-2 (rocket)
  new boom.common.WithRenumberHarts ++
  new boom.common.WithLargeBooms ++
  new boom.common.WithNBoomCores(2) ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)
// DOC include end: DualBoomAndRocketOneHwacha

class LargeBoomAndRV32RocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new boom.common.WithRenumberHarts ++
  new boom.common.WithLargeBooms ++
  new boom.common.WithNBoomCores(1) ++
  new freechips.rocketchip.subsystem.WithRV32 ++        // use 32-bit rocket
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)

// DOC include start: DualBoomAndRocket
class DualLargeBoomAndDualRocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new boom.common.WithRenumberHarts ++
  new boom.common.WithLargeBooms ++
  new boom.common.WithNBoomCores(2) ++                   // 2 boom cores
  new freechips.rocketchip.subsystem.WithNBigCores(2) ++ // 2 rocket cores
  new freechips.rocketchip.system.BaseConfig)
// DOC include end: DualBoomAndRocket
