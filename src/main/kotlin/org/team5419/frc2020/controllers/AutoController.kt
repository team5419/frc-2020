package org.team5419.frc2020.controllers

import org.team5419.fault.Controller
import org.team5419.fault.auto.Routine
import org.team5419.fault.math.units.seconds
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.frc2020.subsystems.Drivetrain
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.Timer



public class AutoController(val baseline: Routine, var routines: Array<Routine>) : Controller {

    private val mRoutines: Array<out Routine>
    public val mAutoSelector = SendableChooser<Routine>()
    private var routine: Routine
    private val timer: Timer = Timer()

    init {
        mRoutines = routines
        routine = baseline

        mAutoSelector.addDefault("Baseline", baseline)
        mRoutines.forEach(
            { mAutoSelector.addOption(it.name, it) }
        )
    }

    override fun start() {
        routine = mAutoSelector.getSelected()
        routine.start()
        val initalPose: Pose2d = Pose2d()
        timer.start()
    }

    override fun update() {

        routine.update(timer.get().seconds)
        timer.reset()

    }
    override fun reset() {
        start()
    }
}
