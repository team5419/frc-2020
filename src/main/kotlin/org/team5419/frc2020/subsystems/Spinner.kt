package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.DriveConstants
import org.team5419.frc2020.SpinConstants

object Spinner : Subsystem("Spinner") {

    private val spinMotor = BerkeliumSRX(SpinConstants.kMotorPort, DriveConstants.kNativeGearboxConversion)

    public fun rotationControl() {

    }

    public fun colorControl() {

    }
}
