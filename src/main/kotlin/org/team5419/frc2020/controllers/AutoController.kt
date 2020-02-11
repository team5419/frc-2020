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


    // public val routines: Array<Routine> = generateRoutines( Drivetrain.robotPosition )

    init {
        public var routine: Action = NothingAction()

        try {
            private val json: String = "8ball.wpilib.json"
            private val path: Path = Filesystem.getDeployDirectory().toPath().resolve(json)
            private val trajectory: Trajectory = TrajectoryUtil.fromPathweaverJson(path)

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
        }
        catch(Exception e) {
            println("path failed to load")
            throw e
        }

        // routines.forEach({ autoSelector.addOption(it.name, it) })
    }

    override fun start() {
        // println("start action")


        routine.start()
        // println("Routine end at start: ${routine.next()}")
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
