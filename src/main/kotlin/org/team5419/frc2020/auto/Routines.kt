package org.team5419.frc2020.auto

import org.team5419.fault.auto.*
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Pose2dWithCurvature
import org.team5419.fault.math.units.meters
import org.team5419.fault.math.units.derived.velocity
import org.team5419.fault.trajectory.DefaultTrajectoryGenerator
import org.team5419.fault.trajectory.constraints.TimingConstraint
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.frc2020.TrajectoryConstants


val leftBlue: Routine = Routine("Left Blue", Pose2d(), 
    ParallelAction(
        SerialAction(),
        DriveTrajectoryAction(
            Drivetrain,
            Drivetrain.trajectoryFollower,
            DefaultTrajectoryGenerator.generateTrajectory(
                listOf<Pose2d>(),
                listOf<TimingConstraint<Pose2dWithCurvature>>(),
                0.0.meters.velocity,
                0.0.meters.velocity,
                TrajectoryConstants.kMaxVelocity,
                TrajectoryConstants.kMaxAcceleration,
                false,
                true
            )
        )
    )
)

val centerBlue: Routine = Routine("Center Blue", Pose2d(), 
    ParallelAction(
        SerialAction(),
        DriveTrajectoryAction(
            Drivetrain,
            Drivetrain.trajectoryFollower,
            DefaultTrajectoryGenerator.generateTrajectory(
                listOf<Pose2d>(),
                listOf<TimingConstraint<Pose2dWithCurvature>>(),
                0.0.meters.velocity,
                0.0.meters.velocity,
                TrajectoryConstants.kMaxVelocity,
                TrajectoryConstants.kMaxAcceleration,
                false,
                true
            )
        )
    )
)

val rightBlue: Routine = Routine("Right Blue", Pose2d(), 
    ParallelAction(
        SerialAction(),
        DriveTrajectoryAction(
            Drivetrain,
            Drivetrain.trajectoryFollower,
            DefaultTrajectoryGenerator.generateTrajectory(
                listOf<Pose2d>(),
                listOf<TimingConstraint<Pose2dWithCurvature>>(),
                0.0.meters.velocity,
                0.0.meters.velocity,
                TrajectoryConstants.kMaxVelocity,
                TrajectoryConstants.kMaxAcceleration,
                false,
                true
            )
        )
    )
)

val leftRed: Routine = Routine("Left Red", Pose2d(), 
    ParallelAction(
        SerialAction(),
        DriveTrajectoryAction(
            Drivetrain,
            Drivetrain.trajectoryFollower,
            DefaultTrajectoryGenerator.generateTrajectory(
                listOf<Pose2d>(),
                listOf<TimingConstraint<Pose2dWithCurvature>>(),
                0.0.meters.velocity,
                0.0.meters.velocity,
                TrajectoryConstants.kMaxVelocity,
                TrajectoryConstants.kMaxAcceleration,
                false,
                true
            )
        )
    )
)

val centerRed: Routine = Routine("Center Red", Pose2d(), 
    ParallelAction(
        SerialAction(),
        DriveTrajectoryAction(
            Drivetrain,
            Drivetrain.trajectoryFollower,
            DefaultTrajectoryGenerator.generateTrajectory(
                listOf<Pose2d>(),
                listOf<TimingConstraint<Pose2dWithCurvature>>(),
                0.0.meters.velocity,
                0.0.meters.velocity,
                TrajectoryConstants.kMaxVelocity,
                TrajectoryConstants.kMaxAcceleration,
                false,
                true
            )
        )
    )
)

val rightRed: Routine = Routine("Right Red", Pose2d(), 
    ParallelAction(
        SerialAction(),
        DriveTrajectoryAction(
            Drivetrain,
            Drivetrain.trajectoryFollower,
            DefaultTrajectoryGenerator.generateTrajectory(
                listOf<Pose2d>(),
                listOf<TimingConstraint<Pose2dWithCurvature>>(),
                0.0.meters.velocity,
                0.0.meters.velocity,
                TrajectoryConstants.kMaxVelocity,
                TrajectoryConstants.kMaxAcceleration,
                false,
                true
            )
        )
    )
)

val routineArray: Array<Routine> = arrayOf(leftBlue, centerBlue, rightBlue, leftRed, centerRed, rightRed)