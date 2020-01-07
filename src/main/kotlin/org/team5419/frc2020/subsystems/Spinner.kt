package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem

object Spinner : Subsystem {

    private val spinMotor = BerkeliumSRX(SpinConstants.kMasterPort, DriveConstants.kNativeGearboxConversion)

    public fun rotationControl() {
        
    }

    public fun colorControl() {

    }
}