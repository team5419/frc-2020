package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem

object Climber : Subsystem {

    private val climbMaster = BerkeliumSRX(ClimberConstants.kMasterPort, DriveConstants.kNativeGearboxConversion)
    private val climbSlave = BerkeliumSRX(ShoogerConstants.kSlavePort, DriveConstants.kNativeGearboxConversion)

    public fun climb () {
        climbMaster.setPercent(1.0)
    }
}