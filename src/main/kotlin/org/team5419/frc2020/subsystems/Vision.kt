package org.team5419.frc2020.subsystems

import edu.wpi.first.wpilibj.Timer
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


    // const val kI: Double = 1.0
    // const val kP: Double = 0.0
    // const val kD: Double = 0.0

    // var lastOffset: Double
    // var output: Double = 0.0
    // var integral: Double = 0.0
    // var lastTime: Double = 0.0
    // val timer: Timer = Timer()

    // fun sigmoid(x : Double) : Double = 1 / ( Math.exp(-x) + 1 )

    // init{
    //     Timer.start()
    //     lastOffset = horizontalOffset
    // }

    // fun periodic() {
    //     var e = horizontalOffset
    //     var time = timer.get()
    //     integral += e
    //     output = kP * e + kD * (e - lastOffset) / (time - lastTime) + kI * integral
    //     output = sigmoid(output)
    //     Drivetrain.setPercent(output, -output)
    // }
}
