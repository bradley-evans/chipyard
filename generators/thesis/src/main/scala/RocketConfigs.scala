// -------------
// Rocket Core Configurations
// Set up for thesis research into basic Rocket Cores.
// This file was set up by Bradley Evans
// -------------
package thesis

import chisel3._

import freechips.rocketchip.config.{Config}

// --------------
// Rocket Configs
// --------------

class RocketConfig extends Config(
  new WithTop ++                                           // use default top
  new WithBootROM ++                                       // use default bootrom
  new freechips.rocketchip.subsystem.WithInclusiveCache ++ // use Sifive L2 cache
  new freechips.rocketchip.subsystem.WithNBigCores(1) ++   // single rocket-core
  new freechips.rocketchip.system.BaseConfig)              // "base" rocketchip system
