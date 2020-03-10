package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Vision
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.fault.auto.Action
import org.team5419.fault.math.units.*

class AutoAlignAction() : Action() {
    init {
        finishCondition += {  Vision.calculate(); Vision.aligned() }

        this.withTimeout( 3.0.seconds )
    }

    override public fun start() {
        println("start auto align")
        // turn limelight leds on
        Vision.on()

        // put the drive train in brake mode to make autoaligning easiers
        Drivetrain.brakeMode = true
    }

    override public fun update(dt: SIUnit<Second>) {
        Drivetrain.setPercent(Vision.autoAlign())
    }

    override public fun finish() {
        println("finish auto algin")
        // turn limelight leds on
        Vision.off()

        // put the drive train back in coast mode
        Drivetrain.brakeMode = false

        // make sure the drive train has stoped moving
        Drivetrain.setPercent(0.0, 0.0)
    }
}
