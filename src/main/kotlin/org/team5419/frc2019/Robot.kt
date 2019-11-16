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
        leftMaster = BerkeliumSRX(12, Constants.Drivetrain.NATIVE_UNIT_LENGTH_MODEL)
        leftSlave1 = BerkeliumSPX(2, Constants.Drivetrain.NATIVE_UNIT_LENGTH_MODEL)
        leftSlave2 = BerkeliumSPX(3, Constants.Drivetrain.NATIVE_UNIT_LENGTH_MODEL)

        rightMaster = BerkeliumSRX(6, Constants.Drivetrain.NATIVE_UNIT_LENGTH_MODEL)
        rightSlave1 = BerkeliumSPX(7, Constants.Drivetrain.NATIVE_UNIT_LENGTH_MODEL)
        rightSlave2 = BerkeliumSPX(8, Constants.Drivetrain.NATIVE_UNIT_LENGTH_MODEL)

        drivetrain = Drivetrain(
            leftMaster,
            leftSlave1,
            leftSlave2,
            rightMaster,
            rightSlave1,
            rightSlave2
        )

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
