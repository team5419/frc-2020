package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Vision
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.fault.auto.Action

class AutoAlignAction() : Action() {
    init {
        finishCondition += { Vision.aligned }
    }

    override public fun start() {
        // turn limelight leds on
        Vision.on()

        // put the drive train in brake mode to make autoaligning easiers
        Drivetrain.brakeMode = true
    }

    override public fun update() {
        Drivetrain.setPercent(Vision.autoAlign())
    }

    override public fun finish() {
        // turn limelight leds on
        Vision.off()

        // put the drive train back in coast mode
        Drivetrain.brakeMode = false

        // make sure the drive train has stoped moving
        Drivetrain.setPercent(0.0, 0.0)
    }
}
