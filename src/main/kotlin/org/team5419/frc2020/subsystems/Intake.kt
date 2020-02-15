package org.team5419.frc2020.subsystems

import org.team5419.frc2020.IntakeConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.native.NativeUnitRotationModel
import org.team5419.fault.hardware.ctre.BerkeliumSRX

public enum class IntakeMode {
    STORED,
    DEPLOYED
}

object Intake : Subsystem("Intake") {
    val intakeModel = NativeUnitRotationModel(IntakeConstants.IntakeTicksPerRotation)
    val deployModel = NativeUnitRotationModel(IntakeConstants.DeployTicksPerRotation)

    val intakeMotor = BerkeliumSRX(IntakeConstants.IntakePort, intakeModel)
    val deployMotor = BerkeliumSRX(IntakeConstants.DeployPort, deployModel)

    // deploy

    public var mode = IntakeMode.STORED
        set(mode: IntakeMode) {
            if (mode == field) return

            if ( mode == IntakeMode.DEPLOYED ) {
                intakeMotor.setPercent(  0.0 )
                deployMotor.setPercent( -0.2 )
            }

            if ( mode == IntakeMode.STORED ) {
                intakeMotor.setPercent( 1.0 )
                deployMotor.setPercent( 0.4 )
            }

            field = mode
        }

    public fun deploy() = { mode = IntakeMode.DEPLOYED }
    public fun retract() = { mode = IntakeMode.STORED }
    public fun isActive() = mode == IntakeMode.DEPLOYED

    // subsystem functions

    fun reset() {
        mode = IntakeMode.STORED
    }

    override fun autoReset() = reset()
    override fun teleopReset() = reset()

    override fun periodic() {
        if ( mode == IntakeMode.DEPLOYED ) {

        }

        if ( mode == IntakeMode.STORED ) {

        }
    }
}
