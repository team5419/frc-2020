package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Intake
import org.team5419.fault.auto.Action
import org.team5419.fault.math.units.*

public class RetractIntakeAction () : Action() {

    init {
        withTimeout(1.0.seconds)
    }

    override fun start() = Intake.store()

    override fun finish() = Intake.stopDeploy()
}
