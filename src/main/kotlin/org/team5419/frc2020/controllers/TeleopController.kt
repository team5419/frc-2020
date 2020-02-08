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
import org.team5419.frc2020.input.codriverXbox

class TeleopController(val driver: DriverControls, val codriver: CodriverControls) : Controller {

    private var isPassiveIntake: Boolean = false
    private var passiveState: Storage.State = Storage.State.DISABLED


    private val driveHelper = SpaceDriveHelper(
        { driver.getThrottle() },
        { driver.getTurn() },
        { driver.quickTurn() },
        { driver.slow() },
        InputConstants.JoystickDeadband,
        InputConstants.SlowTurnMultiplier,
        InputConstants.SlowMoveMultiplier
    )

    override fun start() {
    }

    override fun update() {
        updateDriver()
        updateCodriver()
    }

    @Suppress("ComplexMethod")
    fun updateDriver() {
        Drivetrain.setPercent(driveHelper.output())
    }

    @Suppress("ComplexMethod")
    fun updateCodriver() {
        if(codriver.tooglePassiveStorage()) {
            if(passiveState == Storage.State.PASSIVE){
                passiveState = Storage.State.DISABLED
            }
            else {
                passiveState = Storage.State.PASSIVE
            }
        }


        if(codriver.intake() > 0.3){
            Intake.setIntake(codriver.intake())
            Storage.state = Storage.State.ENABLED
        } else if(codriver.outtake() > 0) {
            Intake.setIntake(-codriver.outtake())
            Storage.state = passiveState
        } else{
            Intake.setIntake(0.0)
            Storage.state = passiveState
        }

        // val deployStength = 0.2

        // Intake.setDeploy(
        //     if (codriver.deployIntake()) deployStength
        //     else if (codriver.retractIntake()) -deployStength
        //     else    0.0
        // )

        if ( codriver.shoog() ) {
            Shooger.shoog()
            Storage.state = Storage.State.ENABLED
        } else {
            Shooger.stop()
            Storage.state = passiveState
        }
    }

    override fun reset() {
    }
}
