package org.team5419.frc2020.auto.actions

import org.team5419.fault.auto.Action
import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.subsystems.ShotSetpoint
import org.team5419.frc2020.subsystems.Storage
import org.team5419.frc2020.subsystems.Storage.StorageMode
import org.team5419.frc2020.subsystems.Hood

public class IndexedShoogAction(val balls: Int, val setpoint: ShotSetpoint = Hood.mode) : Action() {

    var lastIsLoadedBall: Boolean = false
    var numberOfChangedStates: Int = 0

    init {
        finishCondition += { numberOfChangedStates >= balls * 2 }
    }

    override fun start(){
        println("starting shoog action")
        lastIsLoadedBall = Storage.isLoadedBall
    }

    override fun update(){
        Shooger.shoog(setpoint)

        if(lastIsLoadedBall != Storage.isLoadedBall){
            lastIsLoadedBall = !lastIsLoadedBall
            println("index: ${lastIsLoadedBall}")
            numberOfChangedStates++
        }

        if( Shooger.isHungry() && Storage.isLoadedBall ){
            Storage.mode = StorageMode.LOAD
        } else if( Storage.mode == StorageMode.LOAD && !Storage.isLoadedBall){
            Storage.mode = StorageMode.PASSIVE
        } else {
            Storage.mode = StorageMode.PASSIVE
        }
    }

    override fun finish() {
        println("done shooting balls")

        Storage.mode = StorageMode.OFF
    }
}
