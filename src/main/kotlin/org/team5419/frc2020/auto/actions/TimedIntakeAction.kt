package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.tab

import org.team5419.frc2020.subsystems.*
import org.team5419.fault.math.units.*
import org.team5419.fault.auto.*
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import edu.wpi.first.wpilibj.controller.PIDController

public class TimedIntakeAction(timeout: SIUnit<Second>) : Action() {

    init {
        withTimeout(timeout)
    }

    override fun update() = Intake.intake()
    override fun finish() = Intake.stopIntake()
}
