package org.team5419.frc2020.subsystems

import org.team5419.frc2020.tab
import org.team5419.frc2020.VisionConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.*
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

    public var offset = VisionConstants.TargetOffset

    // PID loop
    public val controller: PIDController =
        PIDController(
            VisionConstants.PID.P,
            VisionConstants.PID.I,
            VisionConstants.PID.D
        ).apply {
            setTolerance( VisionConstants.Tolerance )
        }

    // add the pid controllet to shuffleboard
    init {
        tab.add("Vision PID", controller).withWidget(BuiltInWidgets.kPIDCommand)
    }

    // auto alignment

    public val aligned
        get() = limelight.targetFound && controller.atSetpoint()

    public fun autoAlign(plus: Double=0.0) {
        // do we need to allign?
        if ( !limelight.targetFound || aligned ) return

        // get the pid loop output
        var output = controller.calculate(limelight.horizontalOffset + offset)

        // limit the output
        if (output > VisionConstants.MaxAutoAlignSpeed)
            output = VisionConstants.MaxAutoAlignSpeed
        if (output < -VisionConstants.MaxAutoAlignSpeed)
            output = -VisionConstants.MaxAutoAlignSpeed

        // lets drive baby
        var velocity = (output + plus).meters.velocity
        Drivetrain.setVelocity(velocity, -velocity, 0.0.volts, 0.0.volts)
    }
}
