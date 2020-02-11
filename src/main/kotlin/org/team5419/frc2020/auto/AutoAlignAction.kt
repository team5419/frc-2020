package org.team5419.frc2020.auto

import org.team5419.fault.auto.Action
import org.team5419.frc2020.subsystems.Vision
import org.team5419.frc2020.subsystems.Drivetrain

class AutoAlignAction : Action() {
    init {
        finishCondition += { Vision.aligned }
    }

    override public fun update() = Vision.autoAlign()

    override public fun finish() = Drivetrain.stop()
}
