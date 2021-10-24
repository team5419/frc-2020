package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Vision
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.subsystems.Hood
import org.team5419.frc2020.subsystems.Storage
import org.team5419.frc2020.subsystems.Storage.StorageMode
import org.team5419.frc2020.ShoogerConstants.ShoogTime
import org.team5419.frc2020.fault.math.units.Second
import org.team5419.frc2020.fault.math.units.SIUnit
import org.team5419.frc2020.fault.auto.Action

public class AlignShootAction(timeout: SIUnit<Second> = ShoogTime) : Action() {
    init {
        this.withTimeout(timeout)
    }

    override fun start() {
        Vision.on()
        Drivetrain.brakeMode = true
    }

    override fun update(dt: SIUnit<Second>) {
        Drivetrain.setPercent(Vision.autoAlign())
        Shooger.shoog(Hood.mode)

        if ( Shooger.isHungry() && Storage.isLoadedBall && { Vision.calculate(); Vision.aligned() }()) {
            //println("load (is aligned)")
            Storage.mode = StorageMode.LOAD
        } else if ( Storage.mode == StorageMode.LOAD && !Storage.isLoadedBall) {
            //println("done shooting")
            Storage.mode = StorageMode.PASSIVE
        } else {
            //println("waiting for ir sensor")
            Storage.mode = StorageMode.PASSIVE
        }
    }

    override fun finish() {
        Shooger.stop()
        Vision.off()
        // put the drive train back in coast mode
        Drivetrain.brakeMode = false

        // make sure the drive train has stoped moving
        Drivetrain.setPercent(0.0, 0.0)
    }
}
