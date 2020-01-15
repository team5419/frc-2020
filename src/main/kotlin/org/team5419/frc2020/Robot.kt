package org.team5419.frc2020

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand
import org.team5419.frc2020.subsystems.*
import org.team5419.fault.BerkeliumRobot
import org.team5419.fault.math.units.seconds


@SuppressWarnings("MagicNumber")
class Robot : BerkeliumRobot(0.05.seconds) {
    private val mDriver: XboxController

    init {
        mDriver = XboxController(0)
        +Shooger
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
    }

    override fun teleopPeriodic() {
        Shooger.setPercent(mDriver.getX(Hand.kLeft))
    }
}
