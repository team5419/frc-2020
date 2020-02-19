package org.team5419.frc2020.controllers

import org.team5419.fault.auto.Action
import org.team5419.fault.auto.NothingAction
import org.team5419.frc2020.auto.generateRoutines
import org.team5419.frc2020.auto.*
import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.DriveConstants
import org.team5419.frc2020.auto.actions.*
import org.team5419.frc2020.tab
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.wpilibj.Filesystem
import org.team5419.fault.Controller
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.geometry.Vector2

public class AutoController()  : Controller {

    //(val baseline: Routine = Routine("Baseline", Pose2d(), NothingAction())) : Controller {
    // public var autoSelector = SendableChooser<Routine>()
    // public var routine: Action

    // init {
    //     routine = baseline
    //     tab.add("Auto Selector", autoSelector)
    //     tab.add("Zero Robot Position",  { refreshRoutines() })
    //     autoSelector.setDefaultOption("Baseline", baseline)
    //     refreshRoutines()
    // }

    // private fun refreshRoutines() {
    //     //clear old routines
    //     autoSelector = SendableChooser<Routine>()

    //     // added the baseline as the default action
    //     autoSelector.setDefaultOption("Baseline", baseline)

    //     // add all the routies
    //     generateRoutines(Drivetrain.position.robotPosition).iterator().forEach({
    //         autoSelector.addOption(it.name, it)
    //     })
    // }

    var routine : Action = RamseteAction(
        Pose2d(0.0.meters, 0.0.meters, 0.0.radians),
        arrayOf<Vector2<Meter>>(),
        Pose2d(2.0.meters, 1.0.meters, 0.0.degrees),

        DriveConstants.MaxVelocity,
        DriveConstants.MaxAcceleration,
        12.volts,
        DriveConstants.TrackWidth,
        DriveConstants.Beta,
        DriveConstants.Zeta,
        DriveConstants.DriveKs,
        DriveConstants.DriveKv,
        DriveConstants.DriveKa
    )


    override fun start() {
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
