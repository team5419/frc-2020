package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Drivetrain
import com.ctre.phoenix.motion.*
import com.ctre.phoenix.motorcontrol.*
import org.team5419.fault.auto.Action
import org.team5419.frc2020.RobotConstants
import org.team5419.frc2020.DriveConstants
import org.team5419.fault.math.geometry.Pose2d
import jaci.pathfinder.Pathfinder
import jaci.pathfinder.Waypoint
import jaci.pathfinder.Trajectory
import jaci.pathfinder.modifiers.TankModifier

class FollowAction(
    waypoints: Array<Pose2d>,
    maxVelocity: Double = DriveConstants.MaxVelocity.value,
    maxAcceleration: Double = DriveConstants.MaxAcceleration.value,
    maxJerk: Double = 5.0 //Default value provided
) : Action() {
    val minPoints = 5

    val leftTrajectoryPoints = BufferedTrajectoryPointStream()
    val rightTrajectoryPoints = BufferedTrajectoryPointStream()

    init {

        if(waypoints.size < minPoints) println("Not enough points")
        val jaciArray: Array<Waypoint> = Array<Waypoint> (waypoints.size) { i ->
            Waypoint(
                waypoints.get(i).translation.x.value,
                waypoints.get(i).translation.y.value,
                waypoints.get(i).rotation.degree
            )
        }
        val config: Trajectory.Config = Trajectory.Config(
            Trajectory.FitMethod.HERMITE_QUINTIC,
            Trajectory.Config.SAMPLES_HIGH,
            0.01,
            maxVelocity,
            maxAcceleration,
            maxJerk
        )
        val source: Trajectory = Pathfinder.generate( jaciArray, config )
        val modifier: TankModifier = TankModifier( source )
        modifier.modify( RobotConstants.Width.value )
        val rightTrajectory: Trajectory = modifier.getRightTrajectory()
        val leftTrajectory: Trajectory = modifier.getLeftTrajectory()



        rightTrajectoryPoints.Write(trajectoryToPoints(rightTrajectory))
        leftTrajectoryPoints.Write(trajectoryToPoints(leftTrajectory))
    }

    private fun trajectoryToPoints(traj: Trajectory): Array<TrajectoryPoint> {

        return Array<TrajectoryPoint>(traj.segments.size) { i ->
            val segement = traj.segments[i]
            val point = TrajectoryPoint()
            point.position = segement.position
            point.velocity = segement.velocity
            point.headingDeg = segement.heading
            point.isLastPoint = i == traj.segments.size - 1
            point
        }

    }

    override fun start() {
        Drivetrain.leftMasterMotor.startMotionProfile(leftTrajectoryPoints, minPoints, ControlMode.MotionProfile)
        Drivetrain.rightMasterMotor.startMotionProfile(rightTrajectoryPoints, minPoints, ControlMode.MotionProfile)
    }

    override fun update() {

    }

    override fun next(): Boolean {
        return Drivetrain.leftMasterMotor.isMotionProfileFinished() ||
               Drivetrain.rightMasterMotor.isMotionProfileFinished()
    }
}
