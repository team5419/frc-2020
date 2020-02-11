package org.team5419.frc2020.controllers

import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.auto.generateRoutines
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.auto.*
import org.team5419.fault.Controller
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.networktables.NetworkTableEntry
import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.geometry.Pose2dWithCurvature
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.trajectory.DefaultTrajectoryGenerator
import org.team5419.fault.trajectory.constraints.TimingConstraint
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.frc2020.DriveConstants

public class AutoController() : Controller {
    public val autoSelector = SendableChooser<Routine>()

    public val routine = Routine(
        "Routine",
        Pose2d(),
        RamseteAction(
            Drivetrain,

            Pose2d(0.0.meters, 0.0.meters, 0.0.radians),
            arrayOf<Vector2<Meter>>(),
            Pose2d(1.0.meters, 0.0.meters, 0.0.radians),

            3.0.meters.velocity,
            3.0.meters.acceleration,
            12.volts,

            DriveConstants.TrackWidth,

            DriveConstants.Beta,
            DriveConstants.Zeta,

            DriveConstants.DriveKv,
            DriveConstants.DriveKa,
            DriveConstants.DriveKs
        )
    )

    init {
    }

    override fun start() {
        routine.start()
        println("Routine end at start: ${routine.next()}")
    }

    override fun update() {
        // println("Update")
        routine.update()
        if(routine.next()) {
            routine.finish()
            println("ROUTINE ENDED")
        }
    }

    override fun reset() {
        start()
    }
}
