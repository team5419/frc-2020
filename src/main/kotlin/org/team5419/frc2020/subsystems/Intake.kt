package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.IntakeConstants
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.ControlMode

object Intake : Subsystem("Intake") {
    private var doesIntake = false
    private val intakeMotor = BerkeliumSRX(4, IntakeConstants.kIntakeModel)
    private val deployMotor = BerkeliumSRX(9, IntakeConstants.kIntakeModel)

    init{
        deployMotor.brakeMode = true
    }

    public var isIntake: Boolean = false
        set (value: Boolean) {
            if(value && !field){
                setPercent(1.0)
            }
            if(!value && field){
                setPercent(0.0)
            }
            field = value
        }

    public fun setIntake(percent: Double){
        intakeMotor.talonSRX.set(ControlMode.PercentOutput, percent)
    }

    public fun setDeploy(perent: Double){

    }

}
