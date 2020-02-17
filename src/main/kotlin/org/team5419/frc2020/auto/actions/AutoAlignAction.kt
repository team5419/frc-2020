package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.tab
import org.team5419.frc2020.VisionConstants.Tolerance
import org.team5419.frc2020.VisionConstants.PID
import org.team5419.frc2020.subsystems.*
import org.team5419.fault.auto.*
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets

class AutoAlignAction() : Action() {
    init {
        finishCondition += { Vision.aligned }
    }

    override public fun update() {
        Vision.autoAlign()
    }

    override public fun finish() {
        Vision.off()

        Drivetrain.setPercent(0.0, 0.0)
    }
}
