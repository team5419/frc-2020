package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Hood
import org.team5419.frc2020.subsystems.Hood.HoodPosition
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.fault.auto.Action

class HoodAction(pos: HoodPosition) : Action() {
    init {
        finishCondition.set({ true })
    }

    override public fun start() {
        Hood.gotto(pos)
    }
}
