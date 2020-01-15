package org.team5419.frc2020.controllers

import org.team5419.frc2020.InputConstants
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.frc2020.input.DriverControls
import org.team5419.frc2020.input.CodriverControls

import org.team5419.fault.Controller

import org.team5419.fault.input.SpaceDriveHelper

import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.XboxController

class TeleopController(driver: DriverControls, codriver: CodriverControls) : Controller {
    private val driveHelper = SpaceDriveHelper(
        { driver.getThrottle() },
        { driver.getTurn()     },
        { driver.quickTurn()   },
        { driver.slow()        },
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
    }

    fun updateCodriver() {
    }

    override fun reset() {
    }
}
