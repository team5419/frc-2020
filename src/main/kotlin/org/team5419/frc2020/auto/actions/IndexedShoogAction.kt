package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.tab
import org.team5419.fault.auto.Action
import org.team5419.frc2020.StorageConstants
import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.subsystems.ShotSetpoint
import org.team5419.frc2020.subsystems.Storage
import org.team5419.frc2020.subsystems.Storage.StorageMode
import org.team5419.frc2020.subsystems.Hood
import org.team5419.fault.math.units.*

public class IndexedShoogAction(val balls: Int, val setpoint: ShotSetpoint = Hood.mode) : Action() {

    val sensorPos
        get() = Storage.sensorPosition

    var lastSensorPos = sensorPos
    var ballShot: Int = 0

    val ballShootSensorThreashold = StorageConstants.SensorThreshold

    init {
        finishCondition += { ballShot >= balls }

        this.withTimeout( (balls * 1.3).seconds )
    }

    override fun start() {
        ballShot = 0
    }

    override fun update(dt: SIUnit<Second>) {
        Shooger.shoog(setpoint)

        if ( sensorPos < ballShootSensorThreashold && lastSensorPos > ballShootSensorThreashold ) {
            ballShot++
            println("shot ball ${ballShot}")
        }

        lastSensorPos = sensorPos

        if ( Shooger.isHungry() && Storage.isLoadedBall ) {
            Storage.mode = StorageMode.LOAD
        } else if ( Storage.mode == StorageMode.LOAD && !Storage.isLoadedBall) {
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
