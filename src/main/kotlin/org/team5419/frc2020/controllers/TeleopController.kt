package org.team5419.frc2020.controllers

import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.input.DriverControls
import org.team5419.frc2020.input.CodriverControls
import org.team5419.frc2020.InputConstants
import org.team5419.frc2020.HoodConstants
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.fault.input.SpaceDriveHelper
import org.team5419.fault.Controller
import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.XboxController


class TeleopController(val driver: DriverControls, val codriver: CodriverControls) : Controller {
    private val driveHelper = SpaceDriveHelper(
        { driver.getThrottle() },
        { driver.getTurn() },
        { driver.quickTurn() },
        { driver.slow() },
        InputConstants.JoystickDeadband,
        InputConstants.SlowTurnMultiplier,
        InputConstants.SlowMoveMultiplier
    )

    override fun start() {}

    override fun update() {
        updateDriver()
        updateCodriver()
    }

    private fun updateDriver() {
        Drivetrain.setPercent(driveHelper.output())

        if (driver.align()) {
            Vision.autoAlign()
        }
    }

    private fun updateCodriver() {
        // intake

        if ( codriver.outtake() ) Intake.outtake()
        if ( codriver.intake() ) Intake.intake()

        if ( codriver.storeIntake() ) Intake.store()

        // shooger

        if ( codriver.shoog() ) Shooger.shoog() else Shooger.stop()

        // storage

        codriver.reverseIntake()

        // hood

        if ( codriver.deployHoodFar() )
            Hood.goto( Hood.HoodPosititions.FAR )

        if ( codriver.deployHoodClose() )
            Hood.goto( Hood.HoodPosititions.CLOSE )

        if (codriver.retractHood() )
            Hood.goto( Hood.HoodPosititions.RETRACT )
    }

    override fun reset() {
    }
}
