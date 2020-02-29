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
import org.team5419.frc2020.auto.actions.*
import org.team5419.frc2020.auto.actions.RamseteAction
import org.team5419.frc2020.subsystems.Hood.HoodPosititions
import org.team5419.frc2020.subsystems.Setpoint
import org.team5419.frc2020.subsystems.Storage.StorageMode
import org.team5419.frc2020.DriveConstants
import edu.wpi.first.wpilibj.Filesystem
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil
import edu.wpi.first.wpilibj.trajectory.Trajectory
import java.nio.file.Path

fun generateRoutines (initalPose: Pose2d): Array<Routine>{
    return arrayOf<Routine> (
        Routine("Trech", initalPose,
            // shoog from starting position
            HoodAction(HoodPosititions.AUTO),
            IndexedShoogAction(3),

            // bring hood back down and turn intake on
            HoodAction(HoodPosititions.RETRACT),
            DeployIntakeAction(),

            // navigate to behind trench
            IntakeAction(),
            RamseteAction( arrayOf<Pose2d>(
                Pose2d(0.0.meters,   0.0.meters, 0.0.radians),
                Pose2d(2.0.meters, -1.55.meters, 0.0.radians),
                Pose2d(7.8.meters, -1.50.meters, 0.0.radians)
            ) ),
            DisableIntakeAction(),
            // RamseteAction(
            //     Pose(8.0.meters,-1.50.meters, 0.0.radians)
            // )

            // align and shoog
            AutoAlignAction(),
            HoodAction(HoodPosititions.FAR),
            IndexedShoogAction(5)
        ),
        Routine("Align", initalPose, AutoAlignAction()),
        Routine("Shoot 3", initalPose,
            HoodAction(HoodPosititions.AUTO),
            IndexedShoogAction(3)
        ),
        Routine("Pathfollowing", initalPose,
            RamseteAction( arrayOf<Pose2d>(
                Pose2d(0.0.meters,   0.0.meters, 0.0.radians),
                Pose2d(3.0.meters, -1.6.meters, 0.0.radians),
                Pose2d(3.7.meters, -1.5.meters, 0.0.radians)
                // Pose2d(7.0.meters, -1.55.meters, 0.0.radians),
                // Pose2d(9.0.meters, -1.50.meters, 0.0.radians)
            ) )
        )
    )
}
