package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.operations.*
import org.team5419.fault.math.units.native.*
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.ShoogerConstants
import com.ctre.phoenix.motorcontrol.FeedbackDevice

object Shooger : Subsystem("Shooger") {

    private val shoogerMotor1 = BerkeliumSRX(ShoogerConstants.kMotorPort1, ShoogerConstants.gearbox)
    private val shoogerMotor2 = BerkeliumSRX(ShoogerConstants.kMotorPort2, ShoogerConstants.gearbox)
    private val shoogerMotor3 = BerkeliumSRX(ShoogerConstants.kMotorPort3, ShoogerConstants.gearbox)
    private val shoogerMotor4 = BerkeliumSRX(ShoogerConstants.kMotorPort4, ShoogerConstants.gearbox)
    private val shoogerMotor5 = BerkeliumSRX(ShoogerConstants.kMotorPort5, ShoogerConstants.gearbox)

    private const val minVelocity = 0.0
    private const val maxVelocity = 6000 * 2 * Math.PI

    public val angularVelocity : Boolean
        get() = shoogerMotor5.talonSRX.getSelectedSensorVelocity(0).radians.velocity.fromNativeUnitVelocity(ShoogerConstants.gearbox)
    
    init{
        shoogerMotor2.follow(shoogerMotor1)
        shoogerMotor3.follow(shoogerMotor1)
        shoogerMotor4.follow(shoogerMotor1)
        shoogerMotor5.talonSRX.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
    }

    private fun calculateFeedforward(velocity : SIUnit<AngularVelocity>) : SIUnit<Volt> {
        return velocity * ShoogerConstants.kV
    }

    public fun shoog (shoogVelocity : SIUnit<AngularVelocity>) {
        shoogerMotor1.setVoltage(calculateFeedforward(shoogVelocity))
    }

    public fun setPercent (percent: Double) {
        val shoogVelocity = (percent * (maxVelocity - minVelocity) + minVelocity).radians.velocity
        shoog(shoogVelocity)
        println(angularVelocity)
    }
}
