package org.team5419.frc2020.input

import edu.wpi.first.wpilibj.GenericHID.Hand
import org.team5419.fault.input.DriverControls
import org.team5419.fault.input.xboxController
import org.team5419.fault.input.XboxButton
import org.team5419.frc2020.InputConstants

public val Xbox = xboxController(InputConstants.kXboxCodrivePort)

object XboxDriver : DriverControls {
    public fun getThrottle() = Xbox.getX( Hand.kLeft )

    public fun getTurn() = Xbox.getY( Hand.kRight )

    public fun quickTurn() =
        Xbox.button( XboxButton.LeftBumper ) ||
        Xbox.button( XboxButton.RightBumper )

    public fun slow() =
        Xbox.getTriggerAxis(Hand.kLeft) >= InputConstants.TriggerDeadband ||
        Xbox.getTriggerAxis(Hand.kRight) >= InputConstants.TriggerDeadband
}
