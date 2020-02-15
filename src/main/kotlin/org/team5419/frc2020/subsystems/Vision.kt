package org.team5419.frc2020.subsystems

import org.team5419.frc2020.tab
import org.team5419.frc2020.VisionConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.*
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.hardware.Limelight
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.wpilibj.controller.PIDController

object Vision : Subsystem("Vision") {
    // config limelight

    val limelight = Limelight (
        networkTableName = "limelight",
        inverted = false,
        mTargetHeight = VisionConstants.TargetHeight,
        mCameraHeight = VisionConstants.CameraHeight,
        mCameraAngle = Rotation2d( VisionConstants.CameraAngle )
    )

    // PID loop

    public val controller: PIDController = PIDController(
            VisionConstants.PID.P,
            VisionConstants.PID.I,
            VisionConstants.PID.D
    ).apply { setTolerance( VisionConstants.Tolerance ) }

    init{
        tab.add("Vision PID", controller).withWidget(BuiltInWidgets.kPIDCommand)
    }

    // auto alignment

    public val aligned
        get() = limelight.targetFound && controller.atSetpoint()

    public fun autoAlign() {
        var output = controller.calculate(limelight.horizontalOffset)

        if (limelight.horizontalOffset == 0.0) {
            return
        }

        var max = 0.4

        if (output > max) {
            output = max
        }

        if (output < -max) {
            output = -max
        }

        Drivetrain.setPercent(output, -output)
    }
}
