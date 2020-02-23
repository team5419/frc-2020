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
import org.team5419.frc2020.subsystems.Hood.HoodPosititions
import org.team5419.frc2020.DriveConstants
import edu.wpi.first.wpilibj.Filesystem
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil
import edu.wpi.first.wpilibj.trajectory.Trajectory
import java.nio.file.Path

fun generateRoutines (initalPose: Pose2d): Array<Routine>{
    return arrayOf<Routine> (

        Routine("Follow path", initalPose, RamseteAction(
            Pose2d(0.0.meters, 0.0.meters, 0.0.radians),
            arrayOf<Vector2<Meter>>(),
            Pose2d(4.0.meters, 1.0.meters, 0.0.degrees)
        )),
        Routine("Target", initalPose,
            HoodAction(HoodPosititions.AUTO),
            TimedShoogAction(5.seconds),
            HoodAction(HoodPosititions.RETRACT),
            RamseteAction(
                Pose2d(0.0.meters, 0.0.meters, 0.0.radians),
                arrayOf<Vector2<Meter>>(),
                Pose2d(2.0.meters, -1.55.meters, 0.0.degrees)
            ),
            StartIntakeAction(),
            RamseteAction(
                Pose2d(2.0.meters, -1.55.meters, 0.0.radians),
                arrayOf<Vector2<Meter>>(),
                Pose2d(8.0.meters, -1.50.meters, 0.0.degrees)
            ),
            StopIntakeAction(),
            HoodAction(HoodPosititions.FAR),
            AutoAlignAction(),
            TimedShoogAction(6.seconds)
        ),
        Routine("Align", initalPose, AutoAlignAction())
    )
}
