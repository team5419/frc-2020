package org.team5419.frc2020.auto.actions

import org.team5419.fault.auto.Action
import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.subsystems.ShotSetpoint
import org.team5419.frc2020.subsystems.Storage
import org.team5419.frc2020.subsystems.Hood

public class IndexedShoogAction(val balls: Int, val setpoint: ShotSetpoint = Hood.mode) : Action() {

    var lastIsLoadedBall: Boolean = false
    var numberOfChangedStates: Int = 0

    override fun start(){
        lastIsLoadedBall = Storage.isLoadedBall
    }

    override fun update(){
        Shooger.shoog(setpoint)

        if(lastIsLoadedBall != Storage.isLoadedBall){
            lastIsLoadedBall = !lastIsLoadedBall
            println("index: ${lastIsLoadedBall}")
            numberOfChangedStates++
        }
    }

    override fun next() = numberOfChangedStates >= balls * 2

}
