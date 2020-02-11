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
import org.team5419.frc2020.auto.*

public class AutoController(val baseline: Routine = Routine("Baseline", Pose2d(), NothingAction())) : Controller {
    public val autoSelector = SendableChooser<Routine>()
    private val json: String = "8ball.wpilib.json"
    public var routine: Action
    public val routines: Array<Routine>

    init {
        routine = baseline
        val path: Path = Filesystem.getDeployDirectory().toPath().resolve(json)
        val trajectory: Trajectory = TrajectoryUtil.fromPathweaverJson(path)
        val initalPose = Drivetrain.robotPosition
        routines = arrayOf(
            Routine("Auto Align", initalPose, AutoAlignAction()),
            Routine("Path following", initalPose,
                RamseteAction(
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
            )
        )
        routines.forEach({ autoSelector.addOption(it.name, it) })
    }

    override fun start() {
        routine = autoSelector.getSelected()
        println("start action")
        routine.start()

        println("routine start")
    }

    override fun update() {
        routine.update()

        if (routine.next()) {
            routine.finish()
            routine = NothingAction()
            println("done with action")
        }
    }

    override fun reset() {
        start()
    }
}
