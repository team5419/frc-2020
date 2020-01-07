package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.ClimberConstants
import org.team5419.frc2020.DriveConstants

object Climber : Subsystem("Climber") {

    private val climbMaster = BerkeliumSRX(ClimberConstants.kMasterPort, DriveConstants.kNativeGearboxConversion)
    private val climbSlave = BerkeliumSRX(ClimberConstants.kSlavePort, DriveConstants.kNativeGearboxConversion)

    public fun climb () {
        climbMaster.setPercent(1.0)
    }
}
