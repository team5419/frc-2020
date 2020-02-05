package org.team5419.frc2020.controllers

import org.team5419.frc2020.InputConstants
import org.team5419.frc2020.subsystems.*
import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.XboxController
import org.team5419.fault.Controller
import org.team5419.fault.input.SpaceDriveHelper
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.frc2020.HoodConstants
import org.team5419.frc2020.subsystems.Shooger

import org.team5419.frc2020.input.DriverControls
import org.team5419.frc2020.input.CodriverControls

class TeleopController(val driver: DriverControls, val codriver: CodriverControls) : Controller {

    private val driveHelper = SpaceDriveHelper(
        { driver.getThrottle() },
        { driver.getTurn() },
        { driver.quickTurn() },
        { driver.slow() },
        InputConstants.JoystickDeadband,
        InputConstants.QuickTurnMultiplier,
        InputConstants.SlowMoveMult
    )

    override fun start() {
        println("update")
    }

    override fun update() {
        updateDriver()
        updateCodriver()
    }

    fun updateDriver() {
        Drivetrain.setPercent(driveHelper.output())

        if ( Vision.aligned ) {
            Shooger.shoog()
        } else {
            Shooger.shoog(0)
        }

        Intake.setIntake(if (driver.activateIntake()) 1.0 else 0.0)
        Intake.setDeploy(if (driver.deployIntake()) 1.0 else if (driver.retractIntake()) -1.0 else 0.0)
    }

    fun updateCodriver() {

    }

    override fun reset() {
    }
}
