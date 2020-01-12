package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.derived.AngularVelocity
import org.team5419.fault.math.units.derived.Volt
import org.team5419.fault.math.units.derived.volts
import org.team5419.frc2020.ShoogerConstants

object Shooger : Subsystem {

    private val shoogerMotor = BerkeliumSRX(ShoogerConstants.kMotorPort, DriveConstants.kNativeGearboxConversion)

    private fun calculateFeedforward(SIUnit<AngularVelocity>) : SIUnit<Volt> {
        return  0.0.volts * ShoogerConstants.kV
    }

    public fun shoog(shoogVelocity : SIUnit<AngularVelocity) {
        shoogerMotor.setVelocity(shoogVelocity, calculateFeedforward(shoogVelocity))
    }
}
