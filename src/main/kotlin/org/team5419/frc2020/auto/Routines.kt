package org.team5419.frc2020.auto

import org.team5419.fault.auto.*
import org.team5419.frc2020.auto.actions.RamseteAction
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.geometry.Pose2dWithCurvature
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.trajectory.DefaultTrajectoryGenerator
import org.team5419.fault.trajectory.constraints.TimingConstraint
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.frc2020.auto.actions.*
import org.team5419.frc2020.DriveConstants
import edu.wpi.first.wpilibj.Filesystem
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil
import edu.wpi.first.wpilibj.trajectory.Trajectory
import java.nio.file.Path

fun generateRoutines (initalPose: Pose2d): Array<Routine>{
    return arrayOf<Routine> (
        Routine("Fallow path", initalPose, RamseteAction(
            Pose2d(0.0.meters, 0.0.meters, 0.0.radians),
            arrayOf<Vector2<Meter>>(),
            Pose2d(1.0.meters, 0.0.meters, 0.0.radians),

            DriveConstants.MaxVelocity,
            DriveConstants.MaxAcceleration,
            12.volts,
            DriveConstants.TrackWidth,
            DriveConstants.Beta,
            DriveConstants.Zeta,
            DriveConstants.DriveKs,
            DriveConstants.DriveKv,
            DriveConstants.DriveKa
        )),
        Routine("Max Score", initalPose,
            AlignAndShoogAction(),
            ParallelAction(
                TimedIntakeAction(10.seconds)
                // ,RamseteAction(
                //     initalPose,
                //     arrayOf<Vector2<Meter>>(),
                //     initalPose,
                //     DriveConstants.MaxVelocity,
                //     DriveConstants.MaxAcceleration,
                //     12.volts,
                //     DriveConstants.TrackWidth,
                //     DriveConstants.Beta,
                //     DriveConstants.Zeta,
                //     DriveConstants.DriveKs,
                //     DriveConstants.DriveKv,
                //     DriveConstants.DriveKa
                // )
            )
        ),
        Routine("Auto Align", initalPose, AutoAlignAction()),
        Routine("Align and Shoot", initalPose, AlignAndShoogAction())
    )
}
