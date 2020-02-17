package org.team5419.frc2020.input

import org.team5419.frc2020.input.CodriverControls
import org.team5419.frc2020.InputConstants
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

private val codriverXbox = XboxController(InputConstants.XboxCodrivePort)

object XboxCodriver: CodriverControls {
    override public fun shoog(): Boolean = codriverXbox.getBumper(Hand.kRight)

    // intake functions

    override public fun intake(): Boolean = codriverXbox.getTriggerAxis(Hand.kLeft) > 0.3

    override public fun outtake(): Boolean = codriverXbox.getTriggerAxis(Hand.kRight) > 0.3

    override public fun storeIntake(): Boolean = codriverXbox.getXButton()

    override public fun reverseStorage(): Boolean = codriverXbox.getYButton()

    // hood functions

    override public fun deployHoodFar(): Boolean = codriverXbox.getPOV() == 0

    override public fun deployHoodClose(): Boolean = codriverXbox.getPOV() == 90

    override public fun retractHood(): Boolean = codriverXbox.getPOV() == 180

    override public fun toogleStorage(): Boolean = codriverXbox.getBumperPressed(Hand.kLeft)
}
