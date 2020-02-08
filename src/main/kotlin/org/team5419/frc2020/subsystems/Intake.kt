package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.IntakeConstants
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.ControlMode
import org.team5419.fault.math.units.native.NativeUnitRotationModel

object Intake : Subsystem("Intake") {
    private var doesIntake = false

    public val deployModel = NativeUnitRotationModel(IntakeConstants.DeployTicksPerRotation)
    public val intakeModel = NativeUnitRotationModel(IntakeConstants.IntakeTicksPerRotation)

    private val intakeMotor = BerkeliumSRX(IntakeConstants.IntakePort, intakeModel)
    private val rollerMotor = BerkeliumSRX(IntakeConstants.RollerPort, intakeModel)

    private val deployMotor = BerkeliumSRX(IntakeConstants.DeployPort, deployModel)

    init{
        deployMotor.brakeMode = true
        rollerMotor.follow(intakeMotor)
    }

    public var isIntake: Boolean = false
        set (value: Boolean) {
            if(value && !field){
                setIntake(1.0)
            }
            if(!value && field){
                setIntake(0.0)
            }
            field = value
        }

    public fun setIntake(percent: Double){
        intakeMotor.talonSRX.set(ControlMode.PercentOutput, percent)
        println(percent)
    }

    public fun setDeploy(percent: Double){
        deployMotor.talonSRX.set(ControlMode.PercentOutput, percent)
    }

}
