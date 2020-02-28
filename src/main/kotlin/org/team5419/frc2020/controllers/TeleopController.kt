package org.team5419.frc2020.controllers

import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.subsystems.Storage.StorageMode
import org.team5419.frc2020.input.DriverControls
import org.team5419.frc2020.input.CodriverControls
import org.team5419.frc2020.input.driverXbox
import org.team5419.frc2020.input.codriverXbox
import org.team5419.frc2020.InputConstants
import org.team5419.frc2020.HoodConstants
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.fault.input.SpaceDriveHelper
import org.team5419.fault.input.DriveSignal
import org.team5419.fault.Controller
import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.GenericHID.RumbleType
import edu.wpi.first.wpilibj.XboxController

class TeleopController(val driver: DriverControls, val codriver: CodriverControls) : Controller {

    var isAligning = false

    var shotAngle: ShotSetpoint = Hood.HoodPosititions.RETRACT

    private val driveHelper = SpaceDriveHelper(
        { driver.getThrottle() },
        { driver.getTurn() },
        { driver.fastTurn() },
        { isAligning || driver.slowMove() },
        InputConstants.JoystickDeadband,
        InputConstants.SlowTurnMultiplier,
        InputConstants.SlowMoveMultiplier
    )

    override fun start() {
    }

    override fun update() {
        updateCodriver()
        updateDriver()
    }

    private fun updateDriver() {

        if( driver.togleAligning() ) {
            isAligning = !isAligning

            if(isAligning) {
                // turn limelight leds on
                Vision.on()

                // put the drive train in brake mode to make autoaligning easiers
                Drivetrain.brakeMode = true
            } else {
                // turn limelight leds off
                Vision.off()

                // put the drive train back in coast mode
                Drivetrain.brakeMode = false

                // turn of any rumbling from the controller
                driverXbox.setRumble(RumbleType.kLeftRumble, 0.0)
                driverXbox.setRumble(RumbleType.kRightRumble, 0.0)
            }
        }

        val output = driveHelper.output()

        if ( isAligning ) {
            if ( driver.adjustOffsetRight() >= InputConstants.TriggerDeadband ) {
                Vision.offset += driver.adjustOffsetRight() / 10
            }

            if ( driver.adjustOffsetLeft() >= InputConstants.TriggerDeadband ) {
                Vision.offset -= driver.adjustOffsetLeft() / 10
            }

            val alignOutput = Vision.autoAlign()

            if ( Vision.aligned ) {
                codriverXbox.setRumble(RumbleType.kLeftRumble, 0.1)
                codriverXbox.setRumble(RumbleType.kRightRumble, 0.1)
            }

            Drivetrain.setPercent(
                output.left + alignOutput.left,
                output.right + alignOutput.right
            )
        } else {
            Drivetrain.setPercent(output)
        }
    }

    @Suppress("ComplexMethod")
    private fun updateCodriver() {
        // intake

             if ( codriver.outtake() ) Intake.outtake()
        else if ( codriver.intake() ) Intake.intake()
        else Intake.store()

        // hood

        if ( codriver.deployHoodFar() ) {
            Hood.goto( Hood.HoodPosititions.FAR )
        } else if ( codriver.deployHoodTruss()) {
            Hood.goto( Hood.HoodPosititions.TRUSS )
        } else if ( codriver.deployHoodClose() ) {
            Hood.goto( Hood.HoodPosititions.CLOSE )
        } else if ( codriver.retractHood() || driver.retractHood() ){
            Hood.goto( Hood.HoodPosititions.RETRACT )
        }

        // rumble

        if ( Shooger.isSpedUp() ) {
            codriverXbox.setRumble(RumbleType.kLeftRumble, 0.3)
            codriverXbox.setRumble(RumbleType.kRightRumble, 0.3)
        } else {
            codriverXbox.setRumble(RumbleType.kLeftRumble, 0.0)
            codriverXbox.setRumble(RumbleType.kRightRumble, 0.0)
        }

        // shooger

             if ( codriver.shoog() ) Shooger.shoog( Hood.mode )
        else if ( codriver.spinUp() ) Shooger.spinUp( Hood.mode )
        else Shooger.stop()

        // storage

        if ( codriver.reverseStorage() ) {
            Storage.reverse()
        } else {
            Storage.resetReverse()

            if( Shooger.isHungry() && Storage.isLoadedBall){
                Storage.mode = StorageMode.LOAD
            } else if( Storage.mode == StorageMode.LOAD && !Storage.isLoadedBall){
                Storage.mode = StorageMode.PASSIVE
            } else if ( Intake.isActive() || Shooger.isActive() ) {
                Storage.mode = StorageMode.PASSIVE
            } else {
                Storage.mode = StorageMode.OFF
            }
        }

        // climber

        if ( driver.climb() ) {
            Climber.deploy()
        } else if ( driver.unclimb() ) {
            Climber.retract()
        } else {
            Climber.stop()
        }

        if( driver.winch() ){
            Climber.winch()
        } else {
            Climber.stopWinch()
        }
    }

    override fun reset() {
    }
}
