package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.tab

import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.subsystems.Hood
import org.team5419.frc2020.ShoogerConstants.ShoogTime
import org.team5419.fault.math.units.Second
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.auto.Action

public class TimedShoogAction(timeout: SIUnit<Second> = ShoogTime) : Action() {
    init {
        withTimeout(timeout)
    }

    override fun update() = Shooger.shoog(null, Hood.mode.velocity)
    override fun finish() = Shooger.stop()
}
