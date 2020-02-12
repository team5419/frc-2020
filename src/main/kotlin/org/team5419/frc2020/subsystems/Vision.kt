package org.team5419.frc2020.subsystems

import org.team5419.frc2020.VisionConstants
import org.team5419.frc2020.tab
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.*
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.hardware.Limelight
import edu.wpi.first.wpilibj.controller.PIDController

object Vision : Subsystem("Vision") {

    val limelight = Limelight (
        networkTableName = "limelight",
        inverted = false,
        mTargetHeight = VisionConstants.TargetHeight,
        mCameraHeight = VisionConstants.CameraHeight,
        mCameraAngle = Rotation2d( VisionConstants.CameraAngle )
    )

    val horizontalOffset = limelight.horizontalOffset
    val targetFound = limelight.targetFound

    public var output: Double = 0.0

    // shuffleboard

    init {
        // tab.addBoolean("Found Target", { limelight.targetFound })
        // tab.addNumber("Horizontal Offset", { limelight.horizontalOffset })
        // tab.addNumber("Vertical Offset", { limelight.verticalOffset })
        // tab.addNumber("Target Area", { limelight.targetArea })
        // tab.addNumber("Target Skew", { limelight.targetSkew })
    }
}
