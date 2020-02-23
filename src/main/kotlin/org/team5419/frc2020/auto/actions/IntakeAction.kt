package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Intake
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.fault.auto.Action

class IntakeAction(val intake: Boolean) : Action() {

    override public fun start() {
        if (intake) {
            Intake.intake()
        } else {
            Intake.stop()
        }
    }
}
