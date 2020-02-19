package org.team5419.frc2020.input

import org.team5419.frc2020.input.DriverControls
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

import org.team5419.frc2020.InputConstants

private val driverXbox = XboxController(InputConstants.XboxDrivePort)
@Suppress("TooManyFunctions")
object XboxDriver : DriverControls {
    override public fun getThrottle() =
        driverXbox.getY( Hand.kLeft )

    override public fun getTurn() =
        driverXbox.getX( Hand.kRight )

    override public fun slowMove() =
        driverXbox.getBumper( Hand.kLeft ) ||
        driverXbox.getBumper( Hand.kRight )

    override public fun slowTurn() =
        driverXbox.getTriggerAxis(Hand.kLeft) >= InputConstants.TriggerDeadband ||
        driverXbox.getTriggerAxis(Hand.kRight) >= InputConstants.TriggerDeadband

    // alignment functions

    override public fun align(): Boolean = driverXbox.getAButton()

    override public fun adjustOffsetLeft(): Boolean = driverXbox.getBumper(Hand.kLeft)

    override public fun adjustOffsetRight(): Boolean = driverXbox.getBumper(Hand.kRight)

    // hood functions

    override public fun retractHood(): Boolean = driverXbox.getYButton()

    // climber functions

    override public fun exend(): Boolean = driverXbox.getStartButton()
    override public fun retract(): Boolean = driverXbox.getBackButton()
    override public fun climb(): Boolean = driverXbox.getXButton()
}
