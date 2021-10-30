package org.team5419.frc2020.auto

import org.team5419.frc2020.fault.auto.*
import org.team5419.frc2020.fault.math.geometry.Pose2d
import org.team5419.frc2020.fault.math.geometry.Vector2
import org.team5419.frc2020.fault.math.geometry.Pose2dWithCurvature
import org.team5419.frc2020.fault.math.units.*
import org.team5419.frc2020.fault.math.units.derived.*
import org.team5419.frc2020.fault.trajectory.DefaultTrajectoryGenerator
import org.team5419.frc2020.fault.trajectory.constraints.TimingConstraint
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.frc2020.auto.actions.*
import org.team5419.frc2020.auto.actions.RamseteAction
import org.team5419.frc2020.subsystems.Hood.HoodPosititions
import org.team5419.frc2020.subsystems.Setpoint
import org.team5419.frc2020.subsystems.Storage.StorageMode
import org.team5419.frc2020.DriveConstants
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil
import edu.wpi.first.wpilibj.trajectory.Trajectory


val routines = arrayOf<Routine>(
    Routine("In front of target", Pose2d(),
        // shoog from starting position
        IntakeAction(),
        //SpinUpAction(),
        AutoHoodAction(),
        AutoAlignAction(),
        TimedShoogAction(4.seconds),

        // bring hood back down and turn intake on

        RetractHoodAction(),
        DeployIntakeAction(),

        // // navigate to behind trench

        // DisableStorageAction(),
        RamseteAction( arrayOf<Pose2d>(
            Pose2d(0.0.meters, 0.0.meters, 0.0.degrees),
            Pose2d(3.3.meters, 0.0.meters, 0.0.degrees)
        ) ),
        RamseteAction( arrayOf<Pose2d>(
            Pose2d(3.3.meters, 0.0.meters, 0.0.degrees),
            Pose2d(0.0.meters, 0.5.meters, -15.0.degrees)
        ), reversed = true ),

        // align and shoog
        //SpinUpAction(),
        //AutoAlignAction(),
        //SpinUpAction(),
        AutoHoodAction(),
        AutoAlignAction(),
        TimedShoogAction(6.seconds),
        DisableStorageAction(),
        DisableIntakeAction()

    ),

    Routine("Anything else", Pose2d(),
        IntakeAction(),
        AutoHoodAction(),
        AutoAlignAction(),
        TimedShoogAction(7.seconds),

        RamseteAction( arrayOf<Pose2d>(
            Pose2d(0.0.meters, 0.0.meters, 0.0.degrees),
            Pose2d(1.0.meters, 0.0.meters, 0.0.degrees)
        ) )

    )
)
