package org.team5419.frc2020.input

import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.GenericHID
import org.team5419.frc2020.input.CodriverControls

import org.team5419.frc2020.InputConstants
import edu.wpi.first.wpilibj.XboxController

public val codriverXbox = XboxController(InputConstants.XboxCodrivePort)

object XboxCodriver: CodriverControls {
    override public fun shoog() = codriverXbox.getYButton()

    override public fun hood() = codriverXbox.getTriggerAxis(Hand.kLeft)>0.2

    override public fun enableFeeding(): Boolean = false //to change

    override public fun activateIntake(): Boolean = codriverXbox.getAButton()

    override public fun deployIntake(): Boolean = codriverXbox.getBButton()

    override public fun retractIntake(): Boolean = codriverXbox.getXButton()

    override public fun deployHood(): Boolean = codriverXbox.getPOV() == 270

    override public fun retractHood(): Boolean = codriverXbox.getPOV() == 90
}
