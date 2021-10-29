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
    Routine("Alliance Trech - Target Start", Pose2d(),
        // shoog from starting position
        IntakeAction(),
        AutoHoodAction(),
        AlignShootAction(5.seconds),

        // bring hood back down and turn intake on
        RetractHoodAction(),
        DeployIntakeAction(),

        // // navigate to behind trench

        // DisableStorageAction(),
        RamseteAction( arrayOf<Pose2d>(
            Pose2d(0.0.meters, 0.0.meters, 0.0.degrees),
            Pose2d(3.5.meters, 0.0.meters, 0.0.degrees)
        ) ),
        RamseteAction( arrayOf<Pose2d>(
            Pose2d(3.5.meters, 0.0.meters, 0.0.degrees),
            Pose2d(0.0.meters, 0.0.meters, 0.0.degrees)
        ), reversed = true ),

        // align and shoog
        //SpinUpAction(),
        //AutoAlignAction(),
        AutoHoodAction(),
        AlignShootAction(6.seconds),
        DisableStorageAction(),
        DisableIntakeAction()

    ),

    Routine("Alliance Trech - Trench Start", Pose2d(),
        // shoog from starting position
        AutoHoodAction(),
        IndexedShoogAction(3),

        // bring hood back down and turn intake on
        RetractHoodAction(),
        DeployIntakeAction(),
        TurnAction(140.0, -1),

        // navigate to behind trench
        IntakeAction(),
        RamseteAction( arrayOf<Pose2d>(
            Pose2d(0.0.meters, 0.0.meters, 0.0.radians),
            Pose2d(258.9.inches, 0.0.meters, 0.0.radians)
        )),
        DisableIntakeAction(),

        // align and shoog
        AutoAlignAction(),
        FarHoodAction(),
        IndexedShoogAction(5)

    ),

    Routine("Enemy Trech", Pose2d(),
        // go into enemy trench and steal first balls
        DeployIntakeAction(),
        IntakeAction(),
        RamseteAction( arrayOf<Pose2d>(
            Pose2d(0.0.meters, 0.0.meters, 0.0.radians),
            Pose2d(130.36.inches, 0.0.meters, 0.0.radians)
        )),
        DisableIntakeAction(),
        //RetractIntakeAction(),

        // drive to shooting position
        RamseteAction( arrayOf<Pose2d>(
            Pose2d(130.36.inches, 0.0.meters, 0.0.radians),
            Pose2d(0.0.meters, 3.0.meters, 0.0.radians)
        ), reversed=true),

        // shoot the balls
        AlignAndIndexedShoogAction(5),

        // pick up more balls
        DeployIntakeAction(),
        IntakeAction(),

        RamseteAction(arrayOf<Pose2d>(
            Pose2d(130.36.inches, 0.0.meters, 0.0.radians),
            Pose2d(3.meters, 1.meters, 30.degrees)
        ))
    )
)
