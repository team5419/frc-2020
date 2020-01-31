package org.team5419.frc2020

import edu.wpi.first.wpilibj.XboxController
import org.team5419.frc2020.controllers.TeleopController
import org.team5419.frc2020.controllers.AutoController
import org.team5419.frc2020.input.XboxCodriver
import org.team5419.frc2020.input.XboxDriver
import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.auto.generateRoutines
import org.team5419.fault.BerkeliumRobot
import org.team5419.fault.math.units.seconds
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.auto.Routine
import org.team5419.fault.input.SpaceDriveHelper
import edu.wpi.first.wpilibj.GenericHID.Hand


@SuppressWarnings("MagicNumber")
class Robot : BerkeliumRobot(0.05.seconds) {
    private val mDriver: XboxController = XboxController(InputConstants.XboxDrivePort)
    private val mCodriver: XboxController = XboxController(InputConstants.XboxCodrivePort)
    // private val teleopController: TeleopController
    private val driveHelper = SpaceDriveHelper(
        { -mDriver.getY(Hand.kLeft) },
        { -mDriver.getX(Hand.kRight) },
        { mDriver.getBumper(Hand.kRight) },
        { mDriver.getBumper(Hand.kLeft) },
        InputConstants.JoystickDeadband,
        InputConstants.QuickTurnMultiplier,
        InputConstants.SlowMoveMult
    )
    // private val autoController: AutoController

    init {
        // teleopController = TeleopController(mDriver, mCodriver)
        // autoController = AutoController(Routine("", Pose2d()), generateRoutines(Pose2d()))

        // subsystem manager
        +Drivetrain
    }

    override fun robotInit() {
    }

    override fun robotPeriodic() {
    }

    override fun disabledInit() {
    }

    override fun disabledPeriodic() {
    }

    override fun autonomousInit() {
        // autoController.start()
    }

    override fun autonomousPeriodic() {
        // autoController.update()
    }

    override fun teleopInit() {
        // teleopController.start()
    }

    override fun teleopPeriodic() {
        // teleopController.update()
        println(mDriver.getY(Hand.kLeft).toString())
        Drivetrain.setPercent(driveHelper.output())
    }
}
