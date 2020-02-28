package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.tab
import org.team5419.fault.auto.Action
import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.subsystems.ShotSetpoint
import org.team5419.frc2020.subsystems.Storage
import org.team5419.frc2020.subsystems.Storage.StorageMode
import org.team5419.frc2020.subsystems.Hood

public class IndexedShoogAction(val balls: Int, val setpoint: ShotSetpoint = Hood.mode) : Action() {

    var lastSensorPosition: Int
    var loadedBalls: Int = 0

    init {
        finishCondition.set({ false })
        lastSensorPosition = Storage.sensorPosition
        tab.addNumber("Number of Changed States", { loadedBalls.toDouble() })
    }

    override fun start(){}

    override fun update(){
        Shooger.shoog(setpoint)

        if(Storage.sensorPosition < 300 && lastSensorPosition >= 300){
            println("loaded ball")
            loadedBalls++
        }

        lastSensorPosition = Storage.sensorPosition

        if( Shooger.isHungry() && Storage.isLoadedBall ){
            Storage.mode = StorageMode.LOAD
        } else if( Storage.mode == StorageMode.LOAD && !Storage.isLoadedBall){
            Storage.mode = StorageMode.PASSIVE
        } else {
            Storage.mode = StorageMode.PASSIVE
        }
    }

    override fun finish() {
        Storage.mode = StorageMode.OFF
    }
}
