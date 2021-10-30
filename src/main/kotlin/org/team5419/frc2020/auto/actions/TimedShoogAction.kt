package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.subsystems.Hood
import org.team5419.frc2020.subsystems.Storage
import org.team5419.frc2020.subsystems.Storage.StorageMode
import org.team5419.frc2020.ShoogerConstants.ShoogTime
import org.team5419.frc2020.fault.math.units.Second
import org.team5419.frc2020.fault.math.units.SIUnit
import org.team5419.frc2020.fault.auto.Action

import org.team5419.frc2020.fault.math.units.seconds
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.frc2020.StorageConstants

public class TimedShoogAction(timeout: SIUnit<Second> = ShoogTime) : Action() {
    init {
        this.withTimeout(timeout)
    }

    override fun start() {
        timer.stop()
        timer.reset()
        timer.start()
        Drivetrain.brakeMode = true
    }

    override fun update(dt: SIUnit<Second>) {
        Shooger.shoog(Hood.mode)

         if ( Shooger.isHungry() && Storage.isLoadedBall) {
            //println("load (is aligned)")
            Storage.mode = StorageMode.LOAD
        } else if ( Storage.mode == StorageMode.LOAD && !Storage.isLoadedBall) {
            //println("done shooting")
            Storage.mode = StorageMode.PASSIVE
        } else {
            //println("waiting for ir sensor")
            if(timer.get().value.seconds.rem(StorageConstants.LoopTime) < StorageConstants.OffTime) {
                Storage.mode = StorageMode.REVERSE // off cycle
            } else {
                Storage.mode = StorageMode.PASSIVE
            }
        }
    }

    override fun finish() {
        Shooger.stop()
        Drivetrain.brakeMode = false
    }
}
