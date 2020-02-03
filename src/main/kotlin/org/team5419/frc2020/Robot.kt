package org.team5419.frc2020

import edu.wpi.first.wpilibj.XboxController
import org.team5419.frc2020.controllers.TeleopController
import org.team5419.frc2020.subsystems.*
import org.team5419.fault.BerkeliumRobot
import org.team5419.fault.math.units.seconds

@SuppressWarnings("MagicNumber")
class Robot : BerkeliumRobot(0.05.seconds) {
    private val mDriver: XboxController = XboxController(InputConstants.XboxDrivePort)
    private val mCodriver: XboxController = XboxController(InputConstants.XboxCodrivePort)
    private val teleopController: TeleopController

    init {
        teleopController = TeleopController(mDriver, mCodriver)

        +Drivetrain
        +Intake
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
    }

    override fun autonomousPeriodic() {
    }

    override fun teleopInit() {
        teleopController.start()
    }

    override fun teleopPeriodic() {
        teleopController.update()
    }
}
