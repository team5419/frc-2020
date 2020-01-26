package org.team5419.frc2020.controllers

import org.team5419.fault.Controller
import org.team5419.fault.auto.Routine
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.frc2020.subsystems.Drivetrain
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser

public class AutoController(val baseline: Routine, var routines: Array<Routine>) : Controller {

    private val mRoutines: Array<out Routine>
    public val mAutoSelector = SendableChooser<Routine>()
    private var routine: Routine

    init {
        mRoutines = routines
        routine = baseline

        mAutoSelector.setDefaultOption("Baseline", baseline)
        mRoutines.forEach(
            { mAutoSelector.addOption(it.name, it) }
        )
    }

    override fun start() {
        routine = mAutoSelector.getSelected()
        routine.start()
        val initalPose: Pose2d = Pose2d()
    }

    override fun update() {
        routine.update()
    }
    override fun reset() {
        start()
    }
}
