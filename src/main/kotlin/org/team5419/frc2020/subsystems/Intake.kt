package org.team5419.frc2020.subsystems

import org.team5419.frc2020.IntakeConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.native.NativeUnitRotationModel
import org.team5419.fault.hardware.ctre.BerkeliumSRX

object Intake : Subsystem("Intake") {
    public val intakeModel = NativeUnitRotationModel(IntakeConstants.IntakeTicksPerRotation)
    public val deployModel = NativeUnitRotationModel(IntakeConstants.DeployTicksPerRotation)

    private val intakeMotor = BerkeliumSRX(IntakeConstants.IntakePort, intakeModel)
    private val deployMotor = BerkeliumSRX(IntakeConstants.DeployPort, deployModel)

    init {
        deployMotor.brakeMode = true
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
        intakeMotor.setPercent(percent)
    }

    public fun setDeploy(percent: Double){
        deployMotor.setPercent(percent)
    }

    // subsystem functions

    fun reset() {
        isIntake = false
    }

    override fun autoReset() = reset()
    override fun teleopReset() = reset()
}
