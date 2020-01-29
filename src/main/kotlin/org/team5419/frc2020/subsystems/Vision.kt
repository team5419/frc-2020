package org.team5419.frc2020.subsystems

import org.team5419.fault.hardware.Limelight
import org.team5419.fault.math.units.*
import org.team5419.fault.subsystems.Subsystem
import org.team5419.frc2020.VisionConstants

object Vision: Limelight (
    networkTableName = "limelight",
    inverted = false,
    mTargetHeight = VisionConstants.kTargetHeight,
    mCameraHeight = VisionConstants.kCameraHeight,
    mCameraAngle = VisionConstants.kCameraAngle
) {

}
