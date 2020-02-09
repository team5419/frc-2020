package org.team5419.frc2020.controllers

import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.auto.generateRoutines
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.auto.*
import org.team5419.fault.Controller
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.networktables.NetworkTableEntry

public class AutoController() : Controller {
    public val autoSelector = SendableChooser<Routine>()

    public var routine: Action = NothingAction()

    public val routines: Array<Routine> = generateRoutines( Drivetrain.robotPosition )

    init {
        routines.forEach({ autoSelector.addOption(it.name, it) })
    }

    override fun start() {
        // routine = routines[1]

        routine.start()
    }

    override fun update() {
        routine.update()

        Vision.autoAlign()
    }

    override fun reset() {
        start()
    }
}
