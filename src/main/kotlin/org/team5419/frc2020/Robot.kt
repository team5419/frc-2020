package org.team5419.frc2020

import org.team5419.frc2020.fault.math.units.seconds
import org.team5419.frc2020.fault.BerkeliumRobot

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import edu.wpi.first.networktables.NetworkTableInstance
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import org.team5419.frc2020.fault.math.units.native.*
import org.team5419.frc2020.fault.math.units.derived.*
import org.team5419.frc2020.fault.math.units.*
import org.team5419.frc2020.fault.math.geometry.Pose2d
import org.team5419.frc2020.fault.hardware.ctre.*
import org.team5419.frc2020.fault.auto.Routine
import com.ctre.phoenix.motorcontrol.*
import org.team5419.frc2020.fault.subsystems.SubsystemManager

import com.ctre.phoenix.motorcontrol.can.TalonFX
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.GenericHID.Hand

val tab: ShuffleboardTab = Shuffleboard.getTab("Master")

@SuppressWarnings("TooManyFunctions")
class Robot : BerkeliumRobot(0.02.seconds) {

    object DriverControls {
        public val driverXbox = XboxController(0)
        public fun getPower() = driverXbox.getY( Hand.kLeft )
        public fun getDirection() = driverXbox.getX( Hand.kRight )
        public fun getSlowMode() = driverXbox.getAButtonPressed()
        public fun getReverse() = driverXbox.getYButtonPressed()
        public fun getSpinLeft() = driverXbox.getXButtonPressed()
    }

    var front=1

    var slow=false

    val leftMasterMotor = TalonFX(1)

    private val leftSlave = TalonFX(2)

    val rightMasterMotor = TalonFX(4)

    private val rightSlave = TalonFX(3)

    init  {
        leftSlave.apply {
            configFactoryDefault(100)
            configSupplyCurrentLimit(SupplyCurrentLimitConfiguration(true, 40.0, 0.0, 0.0), 100)

            // fallow the master
            follow(leftMasterMotor)
            setInverted(InvertType.FollowMaster)

            configVoltageCompSaturation(12.0, 100)
            enableVoltageCompensation(true)

            configClosedLoopPeakOutput(0, 0.1, 100)
        }

        rightSlave.apply {
            configFactoryDefault(100)
            configSupplyCurrentLimit(SupplyCurrentLimitConfiguration(true, 40.0, 0.0, 0.0), 100)

            // fallow the master
            follow(rightMasterMotor)
            setInverted(InvertType.FollowMaster)

            configVoltageCompSaturation(12.0, 100)
            enableVoltageCompensation(true)

            configClosedLoopPeakOutput(0, 0.1, 100)
        }

        leftMasterMotor.apply {
            configFactoryDefault(100)
            configSupplyCurrentLimit(SupplyCurrentLimitConfiguration(true, 40.0, 0.0, 0.0), 100)

            setSensorPhase(false)
            setInverted(false)

            config_kP( 0, 0.0 , 100 )
            config_kI( 0, 0.0 , 100 )
            config_kD( 0, 0.0 , 100 )
            config_kF( 0, 0.0 , 100 )

            setSelectedSensorPosition(0, 0, 100)

            configVoltageCompSaturation(12.0, 100)
            enableVoltageCompensation(true)
            setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10, 100)

            setNeutralMode(NeutralMode.Coast)

            configClosedLoopPeakOutput(0, 0.1, 100)
        }

        rightMasterMotor.apply {
            configFactoryDefault(100)
            configSupplyCurrentLimit(SupplyCurrentLimitConfiguration(true, 40.0, 0.0, 0.0), 100)

            setSensorPhase(true)
            setInverted(true)

            config_kP( 0, 0.0 , 100 )
            config_kI( 0, 0.0 , 100 )
            config_kD( 0, 0.0 , 100 )
            config_kF( 0, 0.0 , 100 )

            setSelectedSensorPosition(0, 0, 100)

            configVoltageCompSaturation(12.0, 100)
            enableVoltageCompensation(true)
            setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10, 100)

            setNeutralMode(NeutralMode.Coast)

            configClosedLoopPeakOutput(0, 0.1, 100)
        }
    }

    override fun teleopInit() {
    }

    override fun teleopPeriodic() {
        if (DriverControls.getSlowMode()==true){
            slow = !slow
        }

        if (DriverControls.getReverse()==true){
            front *= -1
        }

        if (slow==true){
            leftMasterMotor.set(ControlMode.PercentOutput,
            (DriverControls.getPower() - DriverControls.getDirection())*-0.05 * front)

            rightMasterMotor.set(ControlMode.PercentOutput,
            (DriverControls.getPower() + DriverControls.getDirection())*-0.05 * front)
        }

        else{
            leftMasterMotor.set(ControlMode.PercentOutput,
            (DriverControls.getPower() - DriverControls.getDirection())*-0.1 * front)

            rightMasterMotor.set(ControlMode.PercentOutput,
            (DriverControls.getPower() + DriverControls.getDirection())*-0.1 * front)
        }
        if (DriverControls.getSpinLeft){
            leftMasterMotor.set(ControlMode.PercentOutput,
            (DriverControls.getPower() - DriverControls.getDirection())*-0.1 * front)

            rightMasterMotor.set(ControlMode.PercentOutput,
            (DriverControls.getPower() + DriverControls.getDirection())*-0.1 * front)
        }

    }

}
