package org.team5419.frc2020.subsystems

import org.team5419.frc2020.tab
import org.team5419.frc2020.VisionConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.hardware.Limelight
import org.team5419.fault.hardware.Limelight.LightMode
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.wpilibj.controller.PIDController

object Vision : Subsystem("Vision") {
    // config limelight
    val limelight = Limelight(
        networkTableName = "limelight",
        inverted = false,

        mTargetHeight = VisionConstants.TargetHeight,
        mCameraHeight = VisionConstants.CameraHeight,
        mCameraAngle = Rotation2d( VisionConstants.CameraAngle )
    )

    // settings

    public var offset = VisionConstants.TargetOffset

    public val maxSpeed = VisionConstants.MaxAutoAlignSpeed//.value

    // PID loop controller
    public val controller: PIDController =
        PIDController(
            VisionConstants.PID.P,
            VisionConstants.PID.I,
            VisionConstants.PID.D
        ).apply {
            setTolerance( VisionConstants.Tolerance )
        }

    // add the pid controller to shuffleboard
    init {
        tab.addNumber("area", { limelight.targetArea })
        tab.addBoolean("Aligned", { aligned })
    }

    // auto alignment

    public fun calculate() = controller.calculate(limelight.horizontalOffset + offset)

    public val aligned
        get() = limelight.targetFound && controller.atSetpoint()

    public val horizontalOffset
        get() = limelight.horizontalOffset

    public fun autoAlign() {

        // turn lights on
        on()

        // get the pid loop output
        var output = calculate()

        // do we need to allign?
        if ( !limelight.targetFound || aligned ) return

        // limit the output
        if (output >  maxSpeed) output =  maxSpeed
        if (output < -maxSpeed) output = -maxSpeed

        val flip = 1

        // lets drive, baby
        Drivetrain.setPercent(
            (flip * output),
            (-flip * output)
        )
    }

    public fun on() {
        limelight.lightMode = LightMode.On
    }

    public fun off() {
        limelight.lightMode = LightMode.Off
    }
}
