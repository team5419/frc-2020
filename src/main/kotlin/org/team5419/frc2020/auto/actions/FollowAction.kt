package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Drivetrain
import com.ctre.phoenix.motion.*
import com.ctre.phoenix.motorcontrol.*
import org.team5419.fault.auto.Action

class FollowAction : Action() {
    val minPoints = 5

    val trajectoryPoints = BufferedTrajectoryPointStream()

    init {
        trajectoryPoints.Write(arrayOf<TrajectoryPoint>(

        ))
    }

    override fun start() {
        Drivetrain.leftMasterMotor.startMotionProfile(trajectoryPoints, minPoints, ControlMode.MotionProfile)
        Drivetrain.rightMasterMotor.startMotionProfile(trajectoryPoints, minPoints, ControlMode.MotionProfile)
    }

    override fun update() {

    }

    override fun next(): Boolean {
        return Drivetrain.leftMasterMotor.isMotionProfileFinished() ||
               Drivetrain.rightMasterMotor.isMotionProfileFinished()
    }
}
