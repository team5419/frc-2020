package org.team5419.frc2020.controllers

import org.team5419.frc2020.subsystems.*
// import org.team5419.frc2020.auto.generateRoutines
// import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.auto.*
import org.team5419.fault.Controller
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil
import edu.wpi.first.wpilibj.trajectory.Trajectory
import edu.wpi.first.networktables.NetworkTableEntry
import java.nio.file.Path
import edu.wpi.first.wpilibj.Filesystem
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

public class AutoController() : Controller {
    public val autoSelector = SendableChooser<Routine>()

    private val json: String = "8ball.wpilib.json"


    public var routine: Action = NothingAction()

    // public val routines: Array<Routine> = generateRoutines( Drivetrain.robotPosition )

    init {
        // routines.forEach({ autoSelector.addOption(it.name, it) })
        try {
            val path: Path = Filesystem.getDeployDirectory().toPath().resolve(json)
            val trajectory: Trajectory = TrajectoryUtil.fromPathweaverJson(path)
            routine = RamseteAction(
                Drivetrain,
                trajectory,
                DriveConstants.MaxVelocity,
                DriveConstants.MaxAcceleration,
                12.volts,
                DriveConstants.TrackWidth,
                DriveConstants.Beta,
                DriveConstants.Zeta,
                DriveConstants.DriveKv,
                DriveConstants.DriveKa,
                DriveConstants.DriveKs
            )
        } catch(e: FileNotFoundException) {
            println("Path could not be loaded")
            throw e
        }
    }

    override fun start() {
        println("start action")
        routine.start()
    }

    override fun update() {
        // routine.update()

        // if (routine.next()) {
        //     routine.finish()

        //     routine = NothingAction()

        //     println("done with action")
        // }

        Vision.autoAlign()
    }

    override fun reset() {
        start()
    }
}
