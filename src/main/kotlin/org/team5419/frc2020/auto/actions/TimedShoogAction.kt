package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.tab

import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.subsystems.Hood
import org.team5419.frc2020.subsystems.Storage
import org.team5419.frc2020.subsystems.Storage.StorageMode
import org.team5419.frc2020.ShoogerConstants.ShoogTime
import org.team5419.fault.math.units.Second
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.auto.Action

public class TimedShoogAction(timeout: SIUnit<Second> = ShoogTime) : Action() {
    init {
        withTimeout(timeout)
    }

    override fun update() {
        Shooger.shoog(Hood.mode)

        if( Shooger.isHungry() && Storage.isLoadedBall ){
            Storage.mode = StorageMode.LOAD
        } else if( Storage.mode == StorageMode.LOAD && !Storage.isLoadedBall){
            Storage.mode = StorageMode.PASSIVE
        } else {
            Storage.mode = StorageMode.PASSIVE
        }
    }

    override fun finish() = Shooger.stop()
}
