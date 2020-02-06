package org.team5419.frc2020.subsystems

import edu.wpi.first.wpilibj.Timer
import org.team5419.fault.hardware.Limelight
import org.team5419.fault.math.units.*
import org.team5419.fault.subsystems.Subsystem
import org.team5419.frc2020.VisionConstants
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.wpilibj.controller.PIDController
import org.team5419.fault.math.geometry.Rotation2d

object Vision : Limelight (
    networkTableName = "limelight",
    inverted = false,
    mTargetHeight = VisionConstants.TargetHeight,
    mCameraHeight = VisionConstants.CameraHeight,
    mCameraAngle = Rotation2d( VisionConstants.CameraAngle )
) {

    // PID

    const val kP: Double = VisionConstants.PID.P
    const val kI: Double = VisionConstants.PID.I
    const val kD: Double = VisionConstants.PID.D

    public val controller: PIDController = PIDController(kP, kI, kI)
    public var output: Double = 0.0

    init {
        controller.setTolerance( VisionConstants.Tolerance )
    }

    // Shuffleboard initilization

    val tabName = "Vision"

    var tab: ShuffleboardTab

    init{
        tab = Shuffleboard.getTab( tabName )

        tab.add("Vision PID", controller).withWidget(BuiltInWidgets.kPIDCommand)

        tab.addBoolean("Found Target", { targetFound })
        tab.addNumber("Horizontal Offset", { horizontalOffset })
        tab.addNumber("Vertical Offset", { verticalOffset })
        tab.addNumber("Target Area", { targetArea })
        tab.addNumber("Target Skew", { targetSkew })
        tab.addNumber("PID Output", { output })
    }

    // getters

    public val aligned
        get() = targetFound && controller.atSetpoint()

    fun periodic() {
        output = controller.calculate(horizontalOffset)

        // Drivetrain.setPercent(output, -output)
    }
}
