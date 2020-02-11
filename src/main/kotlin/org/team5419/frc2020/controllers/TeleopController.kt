package org.team5419.frc2020.controllers

import org.team5419.frc2020.subsystems.*
// import org.team5419.frc2020.subsystems.StorageMode
import org.team5419.frc2020.input.DriverControls
import org.team5419.frc2020.input.CodriverControls
import org.team5419.frc2020.InputConstants
import org.team5419.frc2020.HoodConstants
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.fault.input.SpaceDriveHelper
import org.team5419.fault.Controller
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

class TeleopController(val driver: DriverControls, val codriver: CodriverControls) : Controller {

    // private var isPassiveIntake: Boolean = false
    // private var passiveState: StorageMode = StorageMode.OFF


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
        updateDriver()
        // updateCodriver()
    }

    fun updateDriver() {
        Drivetrain.setPercent(driveHelper.output())
    }

    // @Suppress("ComplexMethod")
    // fun updateCodriver() {
    //     if(codriver.tooglePassiveStorage()) {
    //         passiveState =
    //             if (passiveState == StorageMode.PASSIVE) StorageMode.OFF
    //             else StorageMode.PASSIVE
    //     }


    //     if(codriver.intake() > 0.3){
    //         Intake.setIntake(codriver.intake())
    //     } else if(codriver.outtake() > 0) {
    //         Intake.setIntake(-codriver.outtake())
    //     } else{
    //         Intake.setIntake(0.0)
    //     }

    //     val deployStength = 0.2

    //     Intake.setDeploy(
    //              if (codriver.deployIntake()) deployStength
    //         else if (codriver.retractIntake()) -deployStength
    //         else    0.0
    //     )

    //     if ( codriver.shoog() ) {
    //         Shooger.shoog()
    //     } else {
    //         Shooger.stop()
    //     }
    // }

    override fun reset() {
    }
}
