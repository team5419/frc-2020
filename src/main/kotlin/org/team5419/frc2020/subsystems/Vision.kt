package org.team5419.frc2020.subsystems

import org.team5419.frc2020.VisionConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.*
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.hardware.Limelight
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.wpilibj.controller.PIDController

object Vision : Subsystem("Vision") {
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
    )

    public var output: Double = 0.0

    init {
        controller.setTolerance( VisionConstants.Tolerance )
    }

    // shuffleboard

    val tabName = "Vision"

    val tab: ShuffleboardTab

    init {
        tab = Shuffleboard.getTab( tabName )

        tab.add("Vision PID", controller).withWidget(BuiltInWidgets.kPIDCommand)

        tab.addBoolean("Found Target", { limelight.targetFound })
        tab.addNumber("Horizontal Offset", { limelight.horizontalOffset })
        tab.addNumber("Vertical Offset", { limelight.verticalOffset })
        tab.addNumber("Target Area", { limelight.targetArea })
        tab.addNumber("Target Skew", { limelight.targetSkew })
        tab.addNumber("PID Output", { output })
    }

    // auto alignment

    public val aligned
        get() = limelight.targetFound && controller.atSetpoint()

    public fun autoAlign() {
        output = controller.calculate(limelight.horizontalOffset)

        if (limelight.horizontalOffset == 0.0) {
            return
        }

        Drivetrain.setPercent(output, -output)
    }
}
