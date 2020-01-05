package org.team5419.frc2020.controllers

import org.team5419.frc2020.InputConstants
import org.team5419.frc2020.subsystems.Drivetrain
import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.XboxController
import org.team5419.fault.Controller
import org.team5419.fault.input.SpaceDriveHelper

class TeleopController(driver: XboxController, codriver: XboxController) : Controller {
    private val driveHelper = SpaceDriveHelper(
        { driver.getY(Hand.kLeft) },
        { driver.getX(Hand.kRight) },
        { driver.getBumper(Hand.kLeft) || driver.getBumper(Hand.kLeft) },
        {
            driver.getTriggerAxis(Hand.kLeft) >= InputConstants.TRIGGER_DEADBAND ||
            driver.getTriggerAxis(Hand.kRight) >= InputConstants.TRIGGER_DEADBAND
        }
    )

    override fun start() {
    }

    override fun update() {
        updateDriver()
        updateCodriver()
    }

    fun updateDriver() {
        Drivetrain.setPercent(driveHelper.output())
    }

    fun updateCodriver() {
    }

    override fun reset() {
    }
}
