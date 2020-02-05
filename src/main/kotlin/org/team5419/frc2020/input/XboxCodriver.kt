package org.team5419.frc2020.input

import edu.wpi.first.wpilibj.GenericHID
import org.team5419.frc2020.input.CodriverControls
import org.team5419.frc2020.InputConstants
import edu.wpi.first.wpilibj.XboxController

public val Xbox_co = XboxController(InputConstants.XboxCodrivePort)
object XboxCodriver : CodriverControls {

    override public fun shoog() = Xbox.getYButton()

}
