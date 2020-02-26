package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Hood
import org.team5419.frc2020.subsystems.Hood.HoodPosititions
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.fault.auto.Action

class HoodAction(val pos: HoodPosititions) : Action() {

    override public fun start() {
        Hood.goto(pos)
    }
}
