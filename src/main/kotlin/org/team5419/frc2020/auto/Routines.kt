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

fun generateRoutines (initalPose: Pose2d) : Array<Routine> {
    return arrayOf(
        // // Routine("Characterization", initalPose, CharacterizationAction(Drivetrain)),
        // RamseteAction(
        //     Drivetrain,

        //     Pose2d(0.0.meters, 0.0.meters, 0.0.radians),
        //     arrayOf<Vector2<Meter>>(),
        //     Pose2d(2.0.meters, 0.0.meters, 0.0.radians),

        //     3.0.meters.velocity,
        //     3.0.meters.acceleration,
        //     12.volts,

        //     DriveConstants.TrackWidth,

        //     DriveConstants.Beta,
        //     DriveConstants.Zeta,

        //     DriveConstants.DriveKv,
        //     DriveConstants.DriveKa,
        //     DriveConstants.DriveKs
        // ).toRoutine("Action", initalPose)
    )
}
