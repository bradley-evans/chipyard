package example

import chisel3._

import freechips.rocketchip.config.{Config}

// --------------
// Rocket Configs
// --------------

class RocketConfig extends Config(
  new WithTSI ++                                           // use TSI bringup
  new WithNoGPIO ++                                        // no GPIO pins
  new WithBootROM ++                                       // use default bootrom
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++     // no MMIO master port
  new freechips.rocketchip.subsystem.WithNoSlavePort ++    // no MMIO slave port
  new freechips.rocketchip.subsystem.WithInclusiveCache ++ // use Sifive L2 cache
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++   // single rocket-core
  new freechips.rocketchip.system.BaseConfig)              // "base" rocketchip system

class HwachaRocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new hwacha.DefaultHwachaConfig ++                        // use Hwacha vector accelerator
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)

class RoccRocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithRoccExample ++    // use example RoCC-based accelerator
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)

// DOC include start: JtagRocket
class jtagRocketConfig extends Config(
  new WithDTM ++                                           // use top with dtm
  new WithNoGPIO ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithJtagDTM ++        // add jtag+DTM module to coreplex
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)
// DOC include end: JtagRocket

// DOC include start: DmiRocket
class dmiRocketConfig extends Config(
  new WithDTM ++                                        // use top with dtm
  new WithNoGPIO ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)
// DOC include end: DmiRocket

// DOC include start: PWMRocketConfig
class PWMRocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithPWMTL ++                                        // use top with tilelink-controlled PWM
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)
// DOC include end: PWMRocketConfig

class PWMAXI4RocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithPWMAXI4 ++                                    // use top with axi4-controlled PWM
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)

class GCDRocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithGCD ++                                           // add MMIO GCD module
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)

class SimBlockDeviceRocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new testchipip.WithBlockDevice ++                        // add block-device module to peripherybus
  new WithSimBlockDevice ++                                // use top with block-device IOs and connect to simblockdevice
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)

class BlockDeviceModelRocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new testchipip.WithBlockDevice ++                        // add block-device module to periphery bus
  new WithBlockDeviceModel ++                              // use top with block-device IOs and connect to a blockdevicemodel
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)

// DOC include start: GPIORocketConfig
class GPIORocketConfig extends Config(
  new WithTSI ++
  new WithGPIO ++                                          // add GPIOs
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)
// DOC include end: GPIORocketConfig

class DualCoreRocketConfig extends Config(
  new WithTSI ++
  new WithBootROM ++
  new WithNoGPIO ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNBigCores(2) ++   // dual-core (2 RocketTiles)
  new freechips.rocketchip.system.BaseConfig)

class RV32RocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithRV32 ++           // set RocketTiles to be 32-bit
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)

class GB1MemoryRocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithExtMemSize((1<<30) * 1L) ++ // use 2GB simulated external memory
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)

// DOC include start: Sha3Rocket
class Sha3RocketConfig extends Config(
  new WithTSI ++
  new WithNoGPIO ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new sha3.WithSha3Accel ++                                // add SHA3 rocc accelerator
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)
// DOC include end: Sha3Rocket

// DOC include start: InitZeroRocketConfig
class InitZeroRocketConfig extends Config(
  new WithInitZero(0x88000000L, 0x1000L) ++
  new WithNoGPIO ++
  new WithTSI ++
  new WithBootROM ++
  new freechips.rocketchip.subsystem.WithNoMMIOPort ++
  new freechips.rocketchip.subsystem.WithNoSlavePort ++
  new freechips.rocketchip.subsystem.WithInclusiveCache ++
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++
  new freechips.rocketchip.system.BaseConfig)
// DOC include end: InitZeroRocketConfig
