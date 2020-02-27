package org.team5419.frc2020.auto.actions

import org.team5419.fault.auto.Action
import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.subsystems.Storage
import org.team5419.frc2020.subsystems.Hood

public class IndexedShoogAction(val balls: Int) : Action() {

    var lastIsLoadedBall: Boolean = false
    var numberOfChangedStates: Int = 0

    override fun start(){
        lastIsLoadedBall = Storage.isLoadedBall
    }

    override fun update(){
        Shooger.shoog(Hood.mode)

        if(lastIsLoadedBall != Storage.isLoadedBall){
            lastIsLoadedBall = !lastIsLoadedBall
            numberOfChangedStates++
        }
    }

    override fun next() = numberOfChangedStates * 2 >= balls

}
