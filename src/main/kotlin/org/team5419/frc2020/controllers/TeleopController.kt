package org.team5419.frc2020.controllers

import org.team5419.frc2020.InputConstants
import org.team5419.frc2020.subsystems.*


import org.team5419.fault.Controller

import org.team5419.fault.input.SpaceDriveHelper

import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.XboxController

class TeleopController(val driver: XboxController, codriver: XboxController) : Controller {
    private val driveHelper = SpaceDriveHelper(
        { driver.getY(Hand.kLeft) },
        { driver.getX(Hand.kRight) },
        { driver.getBumper(Hand.kRight) },
        { driver.getBumper(Hand.kLeft) },
        InputConstants.JoystickDeadband,
        InputConstants.QuickTurnMultiplier,
        InputConstants.SlowMoveMult
    )

    override fun start() {
    }

    override fun update() {
        updateDriver()
        updateCodriver()
    }

    fun updateDriver() {
        Drivetrain.setPercent(driveHelper.output())
        Intake.setPercent(driver.getTriggerAxis(Hand.kLeft))

    }

    fun updateCodriver() {
    }

    override fun reset() {
    }
}
