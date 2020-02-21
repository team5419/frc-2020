package org.team5419.frc2020.controllers

import org.team5419.fault.auto.Action
import org.team5419.fault.auto.Routine
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

public class AutoController(val baseline: Routine = Routine("Baseline", Pose2d(), NothingAction())) : Controller {
    public var autoSelector = SendableChooser<Routine>()
    public var routine: Action

    init {
        // set the routine to be whatever the baseline is
        routine = baseline

        // added the baseline as the default action
        autoSelector.setDefaultOption("Baseline", baseline)

        // add all the routies
        generateRoutines(Drivetrain.position.robotPosition).iterator().forEach({
            autoSelector.addOption(it.name, it)
        })

        // add the auto selected and reset options to shuffleboard
        tab.add("Auto Selector", autoSelector)
    }

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
