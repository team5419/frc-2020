package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.ShoogerConstants
import org.team5419.fault.auto.SerialAction

class AlignAndShoogAction : SerialAction(
    AutoAlignAction(),
    TimedShoogAction( ShoogerConstants.ShoogTime )
) {}
