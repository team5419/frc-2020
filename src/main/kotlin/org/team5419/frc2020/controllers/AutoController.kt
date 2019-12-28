package org.team5419.frc2020.controllers

import org.team5419.fault.Controller
import org.team5419.fault.auto.Routine
import org.team5419.fault.auto.ParallelAction
import org.team5419.fault.auto.NothingAction
import org.team5419.fault.auto.DriveTrajectoryAction
import org.team5419.fault.auto.Action
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.trajectory.DefaultTrajectoryGenerator
import org.team5419.frc2020.subsystems.Drivetrain


public class AutoController(subsystems : SubsystemManager, ): Controller {
    public val routines : Array<Action> = arrayOf(
        redLeft, redCenter, redRight,
        blueLeft, blueCenter, blueRight
    )
}
