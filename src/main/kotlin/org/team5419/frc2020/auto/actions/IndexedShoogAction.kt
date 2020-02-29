package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.tab
import org.team5419.fault.auto.Action
import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.subsystems.ShotSetpoint
import org.team5419.frc2020.subsystems.Storage
import org.team5419.frc2020.subsystems.Storage.StorageMode
import org.team5419.frc2020.subsystems.Hood

public class IndexedShoogAction(val balls: Int, val setpoint: ShotSetpoint = Hood.mode) : Action() {

    val sensorPos
        get() = Shooger.averageVelocity

    var lastSensorPos = sensorPos
    var ballShot: Int = 0

    val ballShootThreashold = (setpoint.velocity - 200)

    init {
        finishCondition.set({ ballShot >= balls })
        lastSensorPos = Shooger.averageVelocity
    }

    override fun start() {
        ballShot = 0
    }

    override fun update() {
        Shooger.shoog(setpoint)

        if ( sensorPos < ballShootThreashold && lastSensorPos > ballShootThreashold ) {
            ballShot++
            println("shot ball ${ballShot}")
        }

        lastSensorPos = sensorPos

        if( Shooger.isHungry() && Storage.isLoadedBall ){
            Storage.mode = StorageMode.LOAD
        } else if( Storage.mode == StorageMode.LOAD && !Storage.isLoadedBall){
            Storage.mode = StorageMode.PASSIVE
        } else {
            Storage.mode = StorageMode.PASSIVE
        }
    }

    override fun finish() {
        println("done")

        Shooger.stop()

        Storage.mode = StorageMode.OFF
    }
}
