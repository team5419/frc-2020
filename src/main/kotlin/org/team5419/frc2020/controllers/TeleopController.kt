package org.team5419.frc2020.controllers

import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.subsystems.StorageMode
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

    private var isPassiveIntake: Boolean = false
    private var passiveState: StorageMode = StorageMode.OFF

    private val controller = XboxController(1)


    private val driveHelper = SpaceDriveHelper(
        { driver.getThrottle() },
        { driver.getTurn() },
        { driver.quickTurn() },
        { driver.slow() },
        InputConstants.JoystickDeadband,
        InputConstants.SlowTurnMultiplier,
        InputConstants.SlowMoveMultiplier
    )

    override fun start() { }

    override fun update() {
        // Storage.mode = StorageMode.OFF

        updateDriver()
        updateCodriver()
    }

    fun updateDriver() {
        Drivetrain.setPercent(driveHelper.output())

        if (driver.align()) {
            Vision.autoAlign()
        }
    }

    @Suppress("ComplexMethod")
    fun updateCodriver() {
        // if(codriver.tooglePassiveStorage()) {
        //     passiveState =
        //         if (passiveState == StorageMode.PASSIVE) StorageMode.OFF
        //         else StorageMode.PASSIVE
        // }

        if(codriver.intake() > 0.3){
            Intake.setIntake(codriver.intake())
            Storage.mode = StorageMode.PASSIVE
        } else if(codriver.outtake() > 0) {
            Intake.setIntake(-codriver.outtake())
            Storage.mode = StorageMode.PASSIVE
        } else{
            Intake.setIntake(0.0)
            Storage.mode = StorageMode.OFF
        }

        if(codriver.deployIntake()) { Intake.deploy() }
        else if(codriver.retractIntake()) { Intake.retract() }
        else { Intake.setDeploy(0.0) }

        if ( codriver.shoog() ) {
            Shooger.shoog()
        } else {
            Shooger.stop()
        }

        if ( codriver.deployHoodFar() ) Hood.goto( Hood.HoodPosititions.FAR )

        if (codriver.deployHoodClose() ) Hood.goto( Hood.HoodPosititions.CLOSE )

        if (codriver.retractHood() ) Hood.goto( Hood.HoodPosititions.RETRACT )
    }

    override fun reset() {
    }
}
