package org.team5419.frc2020.auto

import org.team5419.fault.auto.Action
import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.ShoogerConstants

class ShoogAction : Action() {
    init {
        withTimeout( ShoogerConstants.ShoogTime )
    }

    override public fun start() = Shooger.shoog()

    override public fun finish() = Shooger.stop()
}
