package org.team5419.frc2020.auto

import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.subsystems.Storage.State
import org.team5419.fault.auto.*
import org.team5419.fault.math.units.*
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets

public class HopperAction() : Action() {

    init{
        // finishCondition =+ { Intake.}
    }

    override fun start() {
        Intake.deploy()
    }

}
