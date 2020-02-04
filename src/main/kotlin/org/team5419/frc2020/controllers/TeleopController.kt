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

class TeleopController(val driver: XboxController, val codriver: XboxController) : Controller {

    private val driveHelper = SpaceDriveHelper(
        { driver.getY(Hand.kLeft) },
        { driver.getX(Hand.kRight) },
        { driver.getBumper(Hand.kRight) },
        { driver.getBumper(Hand.kLeft) },
        InputConstants.JoystickDeadband,
        InputConstants.QuickTurnMultiplier,
        InputConstants.SlowMoveMult
    )

    // private var hoodMotorPercent : Double = 0.0

    // private var hoodPosition = 0.0.radians

    override fun start() {
    }

    override fun update() {
        updateDriver()
        updateCodriver()
        // hoodPosition = HoodConstants.hood.fromNativeUnitPosition(Shooger.hoodMotor.encoder.rawPosition)
    }

    fun updateDriver() {
        Drivetrain.setPercent(driveHelper.output())
        Intake.setIntake(if (driver.getAButton()) 1.0 else 0.0)
        Intake.setDeploy(if (driver.getBButton()) 1.0 else if (driver.getXButton()) -1.0 else 0.0)
        // if (driver.getTriggerAxis(Hand.kRight) > 0.3 && hoodPosition < 90.0) { //>
        //     Shooger.setLaunchPercent(hoodMotorPercent + driver.getTriggerAxis(Hand.kRight))
        // }
        // if (driver.getTriggerAxis(Hand.kLeft) > 0.3 && hoodPosition > -90.0) {
        //     Shooger.setLaunchPercent(hoodMotorPercent - driver.getTriggerAxis(Hand.kLeft))
        // }
    }

    fun updateCodriver() {

    }

    override fun reset() {
    }
}
