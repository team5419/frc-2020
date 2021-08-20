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
    var isDeployed = false

    var shotSetpoint: ShotSetpoint = Hood.HoodPosititions.RETRACT

    data class Setpoint ( override val angle: Double, override val velocity: Double  ) : ShotSetpoint

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
        Vision.zoomOut()
    }

    override fun update() {
        updateDrivetrain()
        updateIntake()
        updateHood()
        updateShooger()
        updateStorage()
        updateClimber()
    }

    private fun updateDrivetrain() {
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
            // if ( driver.adjustOffsetRight() >= InputConstants.TriggerDeadband ) {
            //     Vision.offset += driver.adjustOffsetRight() / 10
            // }

            // if ( driver.adjustOffsetLeft() >= InputConstants.TriggerDeadband ) {
            //     Vision.offset -= driver.adjustOffsetLeft() / 10
            // }

            val alignOutput = Vision.autoAlign()

            if ( Vision.aligned() ) {
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

    private fun updateIntake() {
        if( codriver.storeIntake() ){
            isDeployed = !isDeployed
            if(isDeployed) {
                Intake.deploy()
                println("deploy intake")
            } else {
                Intake.store()
                println("store intake")
            }
        }

             if ( codriver.outtake() ) Intake.outtake()
        else if ( codriver.intake() ) Intake.intake()
        else Intake.stopIntake()
    }

    private fun updateHood() {
        if ( codriver.deployHoodFar() ){
            println("[Debug] hood far")
            shotSetpoint = Hood.HoodPosititions.FAR
        }
        else if ( codriver.deployHoodTruss()){
            println("[Debug] hood truss")
            shotSetpoint = Hood.HoodPosititions.TRUSS
        }
        else if ( codriver.deployHoodClose() ){
            shotSetpoint = Hood.HoodPosititions.CLOSE
            println("[Debug] hood close")
        }
        else if ( codriver.retractHood() || driver.retractHood() ){
            println("[Debug] hood retract")
            shotSetpoint = Hood.HoodPosititions.RETRACT
        }

        if ( false ) {
            // if the shot setpoint is not null, then set the shotSetpoint to it.
            Vision.getShotSetpoint()?.let { shotSetpoint = it }
        }

        if ( driver.adjustHoodUp() )
            shotSetpoint = Setpoint( shotSetpoint.angle + 1.0, shotSetpoint.velocity )
        else if ( driver.adjustHoodDown() )
            shotSetpoint = Setpoint( shotSetpoint.angle - 1.0, shotSetpoint.velocity )

        Hood.goto( shotSetpoint )
    }

    private fun updateShooger() {
             if ( codriver.shoog() ) Shooger.shoog( shotSetpoint )
        else if ( codriver.spinUp() ) Shooger.spinUp( shotSetpoint )
        else Shooger.stop()

        if ( Shooger.isSpedUp() ) {
            codriverXbox.setRumble(RumbleType.kLeftRumble, 0.3)
            codriverXbox.setRumble(RumbleType.kRightRumble, 0.3)
        } else {
            codriverXbox.setRumble(RumbleType.kLeftRumble, 0.0)
            codriverXbox.setRumble(RumbleType.kRightRumble, 0.0)
        }
    }

    private fun updateStorage() {
        if ( codriver.reverseStorage() ) {
            Storage.reverse()
        } else {
            Storage.resetReverse()

            if( Shooger.isHungry() ){
                Storage.mode = StorageMode.LOAD
            } else if( Intake.isActive() || Shooger.isActive() ) {
                Storage.mode = StorageMode.PASSIVE
            } else {
                Storage.mode = StorageMode.OFF
            }

            // if( Shooger.isHungry() ) {
            //     Storage.mode = StorageMode.LOAD
            // } else if ( Storage.mode == StorageMode.LOAD && !Storage.isLoadedBall ) {
            //     Storage.mode = StorageMode.PASSIVE
            // } else if ( Intake.isActive() || Shooger.isActive() ) {
            //     Storage.mode = StorageMode.PASSIVE
            // } else {
            //     Storage.mode = StorageMode.OFF
            // }
        }
    }

    private fun updateClimber() {
        if ( driver.climb() ) {
            Climber.deploy()
        } else if ( driver.unclimb() ) {
            Climber.retract()
        } else {
            Climber.stop()
        }

        if ( driver.winch() ) {
            Climber.winch()
        } else if (driver.unwinch() ){
            Climber.retractWinch()
        } else {
            Climber.stopWinch()
        }
    }

    override fun reset() {
        Vision.zoomOut()
    }
}
