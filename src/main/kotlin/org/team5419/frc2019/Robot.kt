package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.GenericHID.Hand
import edu.wpi.first.wpilibj.XboxController

import org.team5419.fault.hardware.ctre.BerkeliumSPX
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.fault.input.xboxController
import org.team5419.fault.math.units.Meter
import org.team5419.frc2019.subsystems.Drivetrain


@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {
    private val drivetrain: Drivetrain
    private val mXboxController: XboxController

    init {
        drivetrain = Drivetrain
        mXboxController = XboxController(0)
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
    }

    override fun teleopPeriodic() {
        val leftHand: Double = mXboxController.getY(Hand.kLeft) / 1
        val rightHand: Double = mXboxController.getY(Hand.kRight) / -1
        drivetrain.setPercent(leftHand, rightHand)
    }
}
