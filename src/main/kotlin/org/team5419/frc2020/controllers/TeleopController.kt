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

    var isAlign = false
    var output = DriveSignal()
    var alignOutput = DriveSignal()

    private val driveHelper = SpaceDriveHelper(
        { driver.getThrottle() },
        { driver.getTurn() },
        { driver.fastTurn() },
        { isAlign || driver.slowMove() },
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

        if( driver.align() ) {
            isAlign = !isAlign
            if(isAlign){
                Drivetrain.brakeMode = true
            } else {
                driverXbox.setRumble(RumbleType.kLeftRumble, 0.0)
                driverXbox.setRumble(RumbleType.kRightRumble, 0.0)
                Vision.off()
                Drivetrain.brakeMode = false
            }
        }

        // if(driver.invertDrivetrain())
        //     Drivetrain.invert()
        output = driveHelper.output()



        if ( isAlign ) {
            if ( driver.adjustOffsetRight() >= InputConstants.TriggerDeadband ) {
                Vision.offset += driver.adjustOffsetRight() / 10
            }

            if ( driver.adjustOffsetLeft() >= InputConstants.TriggerDeadband ) {
                Vision.offset -= driver.adjustOffsetLeft() / 10
            }

            alignOutput = Vision.autoAlign()



            if ( Vision.aligned ) {
                codriverXbox.setRumble(RumbleType.kLeftRumble, 0.1)
                codriverXbox.setRumble(RumbleType.kRightRumble, 0.1)
            }
        }

        Drivetrain.setPercent(
            output.right + alignOutput.right,
            output.left + alignOutput.left
        )
    }

    @Suppress("ComplexMethod")
    private fun updateCodriver() {
        // intake

             if ( codriver.outtake() ) Intake.outtake()
        else if ( codriver.intake() ) Intake.intake()
        else Intake.store()

        // hood

        if ( codriver.deployHoodFar() ) {
            println("goto far")
            Hood.goto( Hood.HoodPosititions.FAR )
        } else if ( codriver.deployHoodTruss()) {
            println("goto truss")
            Hood.goto( Hood.HoodPosititions.TRUSS )
        } else if ( codriver.deployHoodClose() ) {
            println("goto close")
            Hood.goto( Hood.HoodPosititions.CLOSE )
        } else if ( codriver.retractHood() || driver.retractHood() ){
            println("goto")
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

        if ( codriver.shoog() )
            // codriver.loadShooger() determins wether we should be just
            // spining up or if were shooging
            Shooger.shoog( codriver.loadShooger() )
        else Shooger.stop()

        // storage

        if ( codriver.reverseStorage() ) {
            Storage.reverse()
        } else {
            Storage.resetReverse()

            if ( Shooger.isHungry() ) {
                Storage.mode = StorageMode.LOAD
            } else if ( Intake.isActive() || Shooger.isActive() ) {
                Storage.mode = StorageMode.PASSIVE
            } else {
                Storage.mode = StorageMode.OFF
            }
        }
    }

    override fun reset() {
    }
}
