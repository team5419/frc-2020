package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.subsystems.StorageMode
import org.team5419.fault.auto.*
import org.team5419.fault.math.units.*
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets

public class HopperAction(val mode: StorageMode) : Action() {

    override fun start() {
        Storage.mode = mode
        finishCondition.set({true})
    }

}
