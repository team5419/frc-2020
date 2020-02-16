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
import org.team5419.frc2020.subsystems.StorageMode
import org.team5419.frc2020.auto.actions.*
import org.team5419.frc2020.DriveConstants
import edu.wpi.first.wpilibj.Filesystem
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil
import edu.wpi.first.wpilibj.trajectory.Trajectory
import java.nio.file.Path

fun generateRoutines (initalPose: Pose2d): Array<Routine> = arrayOf<Routine> (
        Routine("Max Score", initalPose, SerialAction(
            AlignAndShoogAction(),
            ParallelAction(
                DeployIntakeAction(),
                TimedIntakeAction(10.seconds),
                HopperAction(StorageMode.PASSIVE)
                RamseteAction(
                    Drivetrain,
                    initalPose,
                    initalPose,
                    DriveConstants.MaxVelocity,
                    DriveConstants.MaxAcceleration,
                    12.volts,
                    DriveConstants.beta,
                    DriveConstants.zeta,
                    DriveConstants.kA,
                    DriveConstants.kV,
                    DriveConstants.kS,
                )
            )
        )),
        Routine("Auto Align", initalPose, AutoAlignAction()),
        Routine("Align and Shoot", initalPose, AlignAndShoogAction())
    )
