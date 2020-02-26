package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Intake
import org.team5419.fault.math.units.seconds
import org.team5419.fault.auto.Action

public class DeployIntakeAction(val deploy: Boolean) : Action() {
    init {
        finishCondition.set({ Intake.isAtSetPoint() })
    }

    override fun start() {
        if(deploy){
            Intake.deploy()
        } else {
            Intake.store()
        }
    }
}
