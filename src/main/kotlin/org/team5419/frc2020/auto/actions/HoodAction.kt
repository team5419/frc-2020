package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Hood
import org.team5419.frc2020.subsystems.Hood.HoodPosititions
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.fault.auto.Action

public class FarHoodAction() : HoodAction(HoodPosititions.FAR)
public class TrussHoodAction() : HoodAction(HoodPosititions.TRUSS)
public class AutoHoodAction(): HoodAction(HoodPosititions.AUTO)
public class CloseHoodAction(): HoodAction(HoodPosititions.CLOSE)
public class RetractHoodAction(): HoodAction(HoodPosititions.CLOSE)

open class HoodAction(val pos: HoodPosititions) : Action() {
    override public fun start() {
        Hood.goto(pos)
    }

    override public fun next() = true
}
