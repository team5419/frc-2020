package org.team5419.frc2020.input

import edu.wpi.first.wpilibj.GenericHID.Hand
import org.team5419.frc2020.input.DriverControls
import edu.wpi.first.wpilibj.XboxController

import org.team5419.frc2020.InputConstants

public val Xbox = XboxController(InputConstants.XboxCodrivePort)

object XboxDriver : DriverControls {
    override public fun getThrottle() = Xbox.getX( Hand.kLeft )

    override public fun getTurn() = Xbox.getY( Hand.kRight )

    override public fun quickTurn() =
        Xbox.getBumper( Hand.kLeft ) ||
        Xbox.getBumper( Hand.kRight )

    override public fun slow() =
        Xbox.getTriggerAxis(Hand.kLeft) >= InputConstants.TriggerDeadband ||
        Xbox.getTriggerAxis(Hand.kRight) >= InputConstants.TriggerDeadband

    override public fun activateIntake() =
        Xbox.getAButton()

    override public fun deployIntake() =
        Xbox.getBButton()

    override public fun retractIntake() =
        Xbox.getXButton()
}
