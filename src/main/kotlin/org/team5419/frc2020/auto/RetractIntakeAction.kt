package org.team5419.frc2020.auto

import org.team5419.frc2020.subsystems.Intake
import org.team5419.fault.auto.Action

public class RetractIntakeAction () : Action() {

    init {
        finishCondition.set({ Intake.isAtSetPoint() })
    }

    override fun start() = Intake.retract()

}
