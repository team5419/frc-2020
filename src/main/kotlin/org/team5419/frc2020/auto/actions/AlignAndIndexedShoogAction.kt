package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.ShoogerConstants
import org.team5419.frc2020.fault.auto.SerialAction
import org.team5419.frc2020.fault.math.units.seconds

class AlignAndIndexedShoogAction(numBalls: Int) : SerialAction(
    AutoAlignAction(),
    IndexedShoogAction(numBalls)
)
