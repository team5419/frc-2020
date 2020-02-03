package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.ControlMode

object Intake : Subsystem("Intake") {
    private var doesIntake = false
    private val intakeMotor = TalonSRX(4)
    private val deployMotor = TalonSRX(9)

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

    public fun setPercent(percent: Double){
        intakeMotor.set(ControlMode.PercentOutput, percent)
    }

}
