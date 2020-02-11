package org.team5419.frc2020.auto

import org.team5419.frc2020.tab

import org.team5419.frc2020.subsystems.*
import org.team5419.fault.auto.*
import org.team5419.fault.auto.math.units.*
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets

public class TimedShoogAction(timeout: SIUnit<Seconds>) : Action() {

    init{
        withTimeout(timeout)
    }

    override fun update() {
        Shooger.shoog()
    }

}
