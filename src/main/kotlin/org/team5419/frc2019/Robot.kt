package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot

import org.team5419.fault.hardware.ctre.BerkeliumSPX
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.fault.input.BerkeliumXbox
import org.team5419.fault.math.units.Meter

import edu.wpi.first.wpilibj.GenericHID.Hand

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {

    private val leftMaster: BerkeliumSRX<Meter>
    private val leftSlave1: BerkeliumSPX<Meter>
    private val leftSlave2: BerkeliumSPX<Meter>

    private val rightMaster: BerkeliumSRX<Meter>
    private val rightSlave1: BerkeliumSPX<Meter>
    private val rightSlave2: BerkeliumSPX<Meter>

    private val drivetrain: Drivetrain
    private val xboxController: BerkeliumXbox

    init {
        drivetrain = Drivetrain()
        xboxController = BerkeliumXbox(0)
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

        val leftHand: Double = xboxController.getY(Hand.kLeft) / 1
        val rightHand: Double = xboxController.getY(Hand.kRight) / -1
        drivetrain.setPercent(leftHand, rightHand)
    }
}
