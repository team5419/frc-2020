package org.team5419.frc2020.subsystems

import edu.wpi.first.wpilibj.Timer
import org.team5419.fault.hardware.Limelight
import org.team5419.fault.math.units.*
import org.team5419.fault.subsystems.Subsystem
import org.team5419.frc2020.VisionConstants
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.wpilibj.controller.PIDController

object Vision : Limelight (
    networkTableName = "limelight",
    inverted = false,
    mTargetHeight = VisionConstants.kTargetHeight,
    mCameraHeight = VisionConstants.kCameraHeight,
    mCameraAngle = VisionConstants.kCameraAngle
) {
    const val kP: Double = 1.0/30.0
    const val kI: Double = 0.0
    const val kD: Double = 0.0

    // private val pidEntry : NetworkTableEntry
    // private val tab: ShuffleboardTab
    public val controller: PIDController = PIDController(kP, kI, kI)
    public var output: Double = 0.0

    init{

        // tab.add("Vision PID", controller).withWidget(BuiltInWidgets.kPIDCommand)
        // tab.addBoolean("Found Target", { targetFound })
        // tab.addNumber("Horizontal Offset", { horizontalOffset })
        // tab.addNumber("Vertical Offset", { verticalOffset })
        // tab.addNumber("Target Area", { targetArea })
        // tab.addNumber("Target Skew", { targetSkew })
        // tab.addNumber("PID Output", { output })

    }

    fun periodic() {
        output = controller.calculate(horizontalOffset)
        // Shuffleboard.update()
        // Drivetrain.setPercent(output, -output)
    }
}
