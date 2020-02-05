package org.team5419.frc2020.input

import edu.wpi.first.wpilibj.GenericHID.Hand
import org.team5419.frc2020.input.DriverControls
import edu.wpi.first.wpilibj.XboxController

import org.team5419.frc2020.InputConstants

public val driverXbox = XboxController(InputConstants.XboxCodrivePort)

object XboxDriver : DriverControls {
    override public fun getThrottle() =
        driverXbox.getY( Hand.kLeft )

    override public fun getTurn() =
        driverXbox.getX( Hand.kRight )

    override public fun quickTurn() =
        driverXbox.getBumper( Hand.kLeft ) ||
        driverXbox.getBumper( Hand.kRight )

    override public fun slow() =
        driverXbox.getTriggerAxis(Hand.kLeft) >= InputConstants.TriggerDeadband ||
        driverXbox.getTriggerAxis(Hand.kRight) >= InputConstants.TriggerDeadband

    override public fun activateIntake() =
        driverXbox.getAButton()

    override public fun deployIntake() =
        driverXbox.getBButton()

    override public fun retractIntake() =
        driverXbox.getXButton()
}
