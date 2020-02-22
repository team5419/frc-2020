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
        Routine("Follow path", initalPose, RamseteAction(
            Pose2d(0.0.meters, 0.0.meters, 0.0.radians),
            arrayOf<Vector2<Meter>>(
                // Vector2<Meter>(2.0.meters, 1.0.meters)
            ),
            Pose2d(4.0.meters, 1.0.meters, 0.0.degrees)
        ))
    )
}
