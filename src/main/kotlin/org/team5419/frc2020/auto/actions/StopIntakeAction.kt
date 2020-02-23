package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Intake
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.fault.auto.Action

class StopIntakeAction() : Action() {
    init {
        finishCondition.set({ true })
    }

    override public fun start() {
        Intake.stop()
    }
}
