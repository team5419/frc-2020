package org.team5419.frc2020.subsystems

import org.team5419.frc2020.tab
import org.team5419.frc2020.ClimberConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.native.NativeUnitRotationModel
import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import kotlin.math.PI
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.networktables.EntryListenerFlags
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.ControlMode

object Climber : Subsystem("Climber") {
    // motor

    private val deployMotor = TalonSRX(ClimberConstants.DeployPort)
        .apply {
            // reset
            configFactoryDefault(100)
        }

    private val winchMotor = TalonSRX(ClimberConstants.WinchPort)
        .apply{
            configFactoryDefault(100)
        }

    // public api

    fun deploy() {
        deployMotor.set(ControlMode.PercentOutput, -0.5)
    }

    fun retract() {
        deployMotor.set(ControlMode.PercentOutput, 0.25)
    }

    fun stop() {
        deployMotor.set(ControlMode.PercentOutput, 0.0)
    }

    fun winch() {
        winchMotor.set(ControlMode.PercentOutput, 1.0)
    }

    fun stopWinch(){
        winchMotor.set(ControlMode.PercentOutput, 0.0)

    }

    // subsystem functions

    fun reset() = stop()

    override fun autoReset() = reset()
    override fun teleopReset() = reset()
}
