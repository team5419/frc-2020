package org.team5419.frc2020.input

import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.GenericHID
import org.team5419.frc2020.input.CodriverControls

import org.team5419.frc2020.InputConstants
import edu.wpi.first.wpilibj.XboxController

public val codriverXbox = XboxController(InputConstants.XboxCodrivePort)

object XboxCodriver : CodriverControls {

    override public fun shoog() =
        codriverXbox.getBumper( Hand.kLeft ) ||
        codriverXbox.getBumper( Hand.kRight );

    override public fun hood() =
        if (codriverXbox.getTriggerAxis(Hand.kLeft)>200) true else false;

    override public fun enableFeeding() =
        codriverXbox.getYButton()

    override public fun activateIntake() =
        codriverXbox.getAButton()

    override public fun deployIntake() =
        codriverXbox.getBButton()

    override public fun retractIntake() =
        codriverXbox.getXButton()
}
