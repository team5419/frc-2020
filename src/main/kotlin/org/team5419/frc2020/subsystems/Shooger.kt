package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.derived.AngularVelocity
import org.team5419.fault.math.units.derived.Volt
import org.team5419.fault.math.units.derived.volts
import org.team5419.fault.math.units.native.NativeUnitRotationModel
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.ShoogerConstants
import org.team5419.frc2020.DriveConstants

object Shooger : Subsystem("Shooger") {

    private val shoogerMotor = BerkeliumSRX(ShoogerConstants.kMotorPort, NativeUnitRotationModel(DriveConstants.kTicksPerRotation))

    private fun calculateFeedforward(velocity : SIUnit<AngularVelocity>) : SIUnit<Volt> {
        return  0.0.volts * ShoogerConstants.kV
    }

    public fun shoog(shoogVelocity : SIUnit<AngularVelocity) {
        shoogerMotor.setVelocity(shoogVelocity, calculateFeedforward(shoogVelocity))
    }
}
