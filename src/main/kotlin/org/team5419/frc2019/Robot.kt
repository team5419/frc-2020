package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot

import org.team5419.fault.hardware.LazyTalonSRX
import org.team5419.fault.hardware.LazyVictorSPX
import com.ctre.phoenix.motorcontrol.ControlMode

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {

    private val mLeftMaster: LazyTalonSRX
    private val mLeftSlave1: LazyVictorSPX
    private val mLeftSlave2: LazyVictorSPX

    private val mRightMaster: LazyTalonSRX
    private val mRightSlave1: LazyVictorSPX
    private val mRightSlave2: LazyVictorSPX

    private val mXboxController: XboxController

    init {
        mLeftMaster = LazyTalonSRX(12)
        mLeftSlave1 = LazyVictorSPX(2)
        mLeftSlave2 = LazyVictorSPX(3)

        mRightMaster = LazyTalonSRX(6)
        mRightSlave1 = LazyVictorSPX(7)
        mRightSlave2 = LazyVictorSPX(8)

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

        // val chainDown: Double = mXboxController.getTriggerAxis(Hand.kLeft) / 1.0
        // val chainUp: Double = mXboxController.getTriggerAxis(Hand.kRight) / 1.0

        val leftHand: Double = mXboxController.getY(Hand.kLeft) / 1
        val rightHand: Double = mXboxController.getY(Hand.kRight) / -1

        mLeftMaster.set(ControlMode.PercentOutput, leftHand)
        mLeftSlave1.set(ControlMode.PercentOutput, leftHand)
        mLeftSlave2.set(ControlMode.PercentOutput, leftHand)

        mRightMaster.set(ControlMode.PercentOutput, rightHand)
        mRightSlave1.set(ControlMode.PercentOutput, rightHand)
        mRightSlave2.set(ControlMode.PercentOutput, rightHand)

        // mChainLift.set(ControlMode.PercentOutput, chainUp - chainDown)
        // mChainBottom.set(ControlMode.PercentOutput, chainUp - chainDown)
    }
}
