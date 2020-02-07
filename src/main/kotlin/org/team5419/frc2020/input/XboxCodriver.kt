package org.team5419.frc2020.input

import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.GenericHID
import org.team5419.frc2020.input.CodriverControls

import org.team5419.frc2020.InputConstants
import edu.wpi.first.wpilibj.XboxController

public val Xbox_co = XboxController(InputConstants.XboxCodrivePort)
object XboxCodriver : CodriverControls {

    override public fun shoog() = Xbox_co.getYButton()

    override public fun hood() = if (Xbox_co.getTriggerAxis(Hand.kLeft)>200)true else false;

    override public fun enableFeeding(): Boolean = false //to change

    override public fun activateIntake(): Boolean = Xbox.getAButton()

    override public fun deployIntake(): Boolean = Xbox.getBButton()

    override public fun retractIntake(): Boolean = Xbox.getXButton()

    override public fun deployHood(): Boolean = Xbox_co.getPOV() == 270

    override public fun retractHood(): Boolean = Xbox_co.getPOV() == 90
}
