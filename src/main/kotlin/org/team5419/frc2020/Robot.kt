package org.team5419.frc2020

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.XboxController

import org.team5419.frc2020.controllers.TeleopController

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {
    private val mDriver: XboxController
    private val mCodriver: XboxController
    private val teleopController: TeleopController

    init {
        mDriver = XboxController(0)
        mCodriver = XboxController(1)
        teleopController = TeleopController(mDriver, mCodriver)
    }

    override fun robotInit() {
    }

    override fun robotPeriodic() {
    }

    override fun disabledInit() {
    }

    override fun disabledPeriodic() {
    }

    override fun teleopInit() {
        teleopController.start()
    }

    override fun teleopPeriodic() {
        teleopController.update()
    }
}
