package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Intake
import org.team5419.fault.math.units.seconds
import org.team5419.fault.auto.Action

public class DeployIntakeAction() : Action() {
    init {
        withTimeout(0.5.seconds)
    }

    override fun start() = Intake.deploy()

    override fun finish() = Intake.stop()
}
