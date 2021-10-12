package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.tab

import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.subsystems.Hood
import org.team5419.frc2020.ShoogerConstants.ShoogTime
import org.team5419.frc2020.fault.math.units.Second
import org.team5419.frc2020.fault.math.units.SIUnit
import org.team5419.frc2020.fault.auto.Action

public class SpinUpAction(timeout: SIUnit<Second> = ShoogTime) : Action() {
    init {
        withTimeout(timeout)
    }
    override fun start() = Shooger.spinUp(Hood.mode)

    //override fun next() = true
}
