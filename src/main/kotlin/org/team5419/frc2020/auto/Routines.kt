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

fun generateRoutines (initalPose: Pose2d) : Array<Routine>{

    return arrayOf(
        Routine("Alliance Side Trech", initalPose,
            ParallelAction(
                SerialAction(),
                DriveTrajectoryAction(
                    Drivetrain,
                    Drivetrain.trajectoryFollower,
                    DefaultTrajectoryGenerator.generateTrajectory(
                        listOf<Pose2d>(initalPose),
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
        ),
        Routine("Characterization", initalPose, CharacterizationAction(Drivetrain))
    )
}
