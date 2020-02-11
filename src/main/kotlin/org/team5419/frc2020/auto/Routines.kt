package org.team5419.frc2020.auto

import org.team5419.fault.auto.*
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.geometry.Pose2dWithCurvature
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.trajectory.DefaultTrajectoryGenerator
import org.team5419.fault.trajectory.constraints.TimingConstraint
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.frc2020.DriveConstants


object Routines{
    // fun generateRoutines (initalPose: Pose2d) : Array<Routine> {
    //     val path: Path = Filesystem.getDeployDirectory().toPath().resolve(json)
    //     val trajectory: Trajectory = TrajectoryUtil.fromPathweaverJson(path)

    //     return arrayOf(
    //         Routine("Auto Align", initalPose, AutoAlignAction()),
    //         Routine("Path following", initalPose,
    //             RamseteAction(
    //                 Drivetrain,
    //                 trajectory,
    //                 DriveConstants.MaxVelocity,
    //                 DriveConstants.MaxAcceleration,
    //                 12.volts,
    //                 DriveConstants.TrackWidth,
    //                 DriveConstants.Beta,
    //                 DriveConstants.Zeta,
    //                 DriveConstants.DriveKv,
    //                 DriveConstants.DriveKa,
    //                 DriveConstants.DriveKs
    //             )
    //         )
    //     )
    // }
}
