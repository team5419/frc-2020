package org.team5419.frc2020.input

import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.GenericHID
import org.team5419.frc2020.input.CodriverControls

import org.team5419.frc2020.InputConstants
import edu.wpi.first.wpilibj.XboxController

public val Xbox_co = XboxController(InputConstants.XboxCodrivePort)
object XboxCodriver : CodriverControls {

    override public fun shoog() =
        Xbox_co.getBumper( Hand.kLeft ) ||
        Xbox_co.getBumper( Hand.kRight );

    override public fun hood() = if (Xbox_co.getTriggerAxis(Hand.kLeft)>200)true else false;

    override public fun enableFeeding() =
    Xbox_co.getYButton()

    override public fun activateIntake() =
        Xbox.getAButton()

    override public fun deployIntake() =
        Xbox.getBButton()

    override public fun retractIntake() =
        Xbox.getXButton()



}
