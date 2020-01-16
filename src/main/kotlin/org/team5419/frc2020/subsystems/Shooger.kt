package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.ShoogerConstants
import org.team5419.frc2020.HoodConstants

object Shooger : Subsystem("Shooger") {

    private val masterMotor = BerkeliumSRX(ShoogerConstants.kMasterPort, ShoogerConstants.flywheel)
    private val slaveMotor1 = BerkeliumSRX(ShoogerConstants.kSlavePort1, ShoogerConstants.flywheel)
    private val slaveMotor2 = BerkeliumSRX(ShoogerConstants.kSlavePort2, ShoogerConstants.flywheel)
    private val slaveMotor3 = BerkeliumSRX(ShoogerConstants.kSlavePort3, ShoogerConstants.flywheel)

    private val hoodMotor = BerkeliumSRX(ShoogerConstants.kHoodPort, ShoogerConstants.flywheel)

    init{
        hoodMotor.talonSRX.apply{
            configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
            config_kP(0, HoodConstants.kP)
            config_kI(0, HoodConstants.kI)
            config_kD(0, HoodConstants.kD)
        }

        masterMotor.talonSRX.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
        slaveMotor1.follow(masterMotor)
        slaveMotor2.follow(masterMotor)
        slaveMotor3.follow(masterMotor)
    }

    private fun calculateFeedforward(velocity : SIUnit<AngularVelocity>) : SIUnit<Volt> {
        return  0.0.volts * ShoogerConstants.kV
    }

    public fun shoog (shoogVelocity : SIUnit<AngularVelocity>) {
        masterMotor.setVelocity(shoogVelocity, calculateFeedforward(shoogVelocity))
    }
}
