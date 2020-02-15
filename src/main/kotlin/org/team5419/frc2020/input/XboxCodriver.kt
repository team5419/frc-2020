package org.team5419.frc2020.input

import org.team5419.frc2020.input.CodriverControls
import org.team5419.frc2020.InputConstants
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

private val codriverXbox = XboxController(InputConstants.XboxCodrivePort)

object XboxCodriver: CodriverControls {
    override public fun shoog(): Boolean = codriverXbox.getBumper(Hand.kRight)

    override public fun intake(): Double = codriverXbox.getTriggerAxis(Hand.kLeft)

    override public fun outtake(): Double = codriverXbox.getTriggerAxis(Hand.kRight)

    override public fun deployIntake(): Boolean = codriverXbox.getBButton()

    override public fun retractIntake(): Boolean = codriverXbox.getXButton()

    override public fun deployHoodFar(): Boolean = codriverXbox.getPOV() == 0

    override public fun deployHoodClose(): Boolean = codriverXbox.getPOV() == 90

    override public fun retractHood(): Boolean = codriverXbox.getPOV() == 180

    override public fun tooglePassiveStorage(): Boolean = codriverXbox.getBumperPressed(Hand.kLeft)
}
