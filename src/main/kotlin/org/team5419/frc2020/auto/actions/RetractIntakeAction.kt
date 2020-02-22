package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Intake
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.Second
import org.team5419.fault.math.units.seconds
import org.team5419.fault.auto.Action

public class RetractIntakeAction (timeout: SIUnit<Second> = 1.0.seconds) : Action() {

    init {
        withTimeout(timeout)
    }

    override fun start() = Intake.store()
    override fun finish() = Intake.stopDeploy()
}
