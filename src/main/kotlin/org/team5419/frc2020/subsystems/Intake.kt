package org.team5419.frc2020.subsystems

import org.team5419.frc2020.IntakeConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.native.NativeUnitRotationModel
import org.team5419.fault.hardware.ctre.BerkeliumSRX

object Intake : Subsystem("Intake") {
    val intakeModel = NativeUnitRotationModel(IntakeConstants.IntakeTicksPerRotation)
    val deployModel = NativeUnitRotationModel(IntakeConstants.DeployTicksPerRotation)

    val intakeMotor = BerkeliumSRX(IntakeConstants.IntakePort, intakeModel)
    val rollerMotor = BerkeliumSRX(IntakeConstants.RollerPort, intakeModel)

    val deployMotor = BerkeliumSRX(IntakeConstants.DeployPort, deployModel)

    init {
        deployMotor.brakeMode = true
        rollerMotor.follow(intakeMotor)
    }

    // intake

    private var intakePercent = 0.0

    public fun setIntake(percent: Double){
        if (percent == intakePercent) return
        intakePercent = percent
        intakeMotor.setPercent(percent)
    }

    // deploy

    public fun deploy() = setDeploy(0.2)
    public fun retract() = setDeploy(-0.4)

    private var deployPercent = 0.0

    public fun setDeploy(percent: Double){
        println("set percent ${percent}")
        if (percent == deployPercent) return
        deployPercent = percent
        deployMotor.setPercent(percent)
    }

    // subsystem functions

    fun reset() {
        setIntake(0.0)
        setDeploy(0.0)
    }

    override fun autoReset() = reset()
    override fun teleopReset() = reset()
}
