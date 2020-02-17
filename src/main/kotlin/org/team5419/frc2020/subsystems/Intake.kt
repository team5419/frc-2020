package org.team5419.frc2020.subsystems

import org.team5419.frc2020.IntakeConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.native.NativeUnitRotationModel
import org.team5419.fault.hardware.ctre.BerkeliumSRX

object Intake : Subsystem("Intake") {
    // motors

    val intakeModel = NativeUnitRotationModel(IntakeConstants.IntakeTicksPerRotation)
    val deployModel = NativeUnitRotationModel(IntakeConstants.DeployTicksPerRotation)

    val intakeMotor = BerkeliumSRX(IntakeConstants.IntakePort, intakeModel)
    val deployMotor = BerkeliumSRX(IntakeConstants.DeployPort, deployModel)

    // intake modes

    public enum class IntakeMode {
        INTAKE,
        OUTTAKE,
        OFF
    }

    public var mode = IntakeMode.OFF
        set(mode: IntakeMode) {
            if ( mode == field ) return

            when (mode) {
                IntakeMode.INTAKE  -> { intakeMotor.setPercent(  1.0 ) }
                IntakeMode.OUTTAKE -> { intakeMotor.setPercent( -1.0 ) }
                IntakeMode.OFF     -> { intakeMotor.setPercent(  0.0 ) }
            }

            field = mode
        }

    // deploy functions

    public fun store()  = deployMotor.setPercent( -0.2 )
    public fun deploy() = deployMotor.setPercent(  0.1 )

    // intake functions

    public fun intake()  { mode = IntakeMode.INTAKE }
    public fun outtake() { mode = IntakeMode.OUTTAKE }
    public fun stop()    { mode = IntakeMode.OFF }

    public fun isActive() = mode == IntakeMode.INTAKE

    // subsystem functions

    fun reset() {
        stop()
    }

    override fun autoReset() = reset()
    override fun teleopReset() = reset()

    override fun periodic() {}
}
