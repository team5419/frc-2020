package org.team5419.frc2020.subsystems

import org.team5419.frc2020.RobotConstants
import org.team5419.frc2020.DriveConstants
import org.team5419.fault.trajectory.followers.RamseteFollower
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.native.nativeUnits
import org.team5419.fault.math.units.native.NativeUnitLengthModel
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.fault.math.physics.DCMotorTransmission
import org.team5419.fault.math.localization.TankPositionTracker
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.input.DriveSignal
import org.team5419.fault.hardware.ctre.*
import edu.wpi.first.wpilibj.Notifier
import com.ctre.phoenix.sensors.PigeonIMU
import com.ctre.phoenix.motorcontrol.*

object Drivetrain : Subsystem("DriveTrain") {
    // hardware

    val nativeGearboxConversion = NativeUnitLengthModel(
        DriveConstants.TicksPerRotation,
        DriveConstants.WheelRadius
    )

    val leftMasterMotor = BerkeliumSRX(
        DriveConstants.LeftMasterPort, nativeGearboxConversion
    )

    private val leftSlave = BerkeliumSRX(DriveConstants.LeftSlavePort, nativeGearboxConversion)

    val rightMasterMotor = BerkeliumSRX(
        DriveConstants.RightMasterPort, nativeGearboxConversion
    )

    private val rightSlave = BerkeliumSRX(DriveConstants.RightSlavePort, nativeGearboxConversion)

    public val gyro = PigeonIMU(DriveConstants.GyroPort)

    public val position = TankPositionTracker(
        { angle },
        { leftDistance },
        { rightDistance }
    )

    init {
        leftSlave.talonSRX.apply {
            configFactoryDefault(100)

            // fallow the master
            follow(leftMasterMotor.talonSRX)
            setInverted(InvertType.FollowMaster)
        }

        rightSlave.talonSRX.apply {
            configFactoryDefault(100)

            // fallow the master
            follow(rightMasterMotor.talonSRX)
            setInverted(InvertType.FollowMaster)
        }

        leftMasterMotor.talonSRX.apply {
            configFactoryDefault(100)

            setSensorPhase(false)

            config_kP( 0, DriveConstants.PID.P , 100 )
            config_kI( 0, DriveConstants.PID.I , 100 )
            config_kD( 0, DriveConstants.PID.D , 100 )
        }

        rightMasterMotor.talonSRX.apply {
            configFactoryDefault(100)

            setSensorPhase(true)

            config_kP( 0, DriveConstants.PID.P , 100 )
            config_kI( 0, DriveConstants.PID.I , 100 )
            config_kD( 0, DriveConstants.PID.D , 100 )
        }
    }

    val leftDistance: SIUnit<Meter>
        get() = leftMasterMotor.encoder.position

    val rightDistance: SIUnit<Meter>
        get() = rightMasterMotor.encoder.position

    val leftVelocity: SIUnit<LinearVelocity>
        get() = leftMasterMotor.encoder.velocity

    val rightVelocity: SIUnit<LinearVelocity>
        get() = rightMasterMotor.encoder.velocity

    val angle: Rotation2d
        get() = -gyro.fusedHeading.degrees.toRotation2d()

    fun stop() = setOpenLoop(0.0, 0.0)

    fun setPercent(left: Double, right: Double) = setOpenLoop(left, right)

    fun setPercent(signal: DriveSignal) = setOpenLoop(signal.left, signal.right)

    fun setOpenLoop(left: Double, right: Double) {
        leftMasterMotor.setPercent(left)
        rightMasterMotor.setPercent(right)
    }

    fun setVelocity(leftVelocity: SIUnit<LinearVelocity>, rightVelocity: SIUnit<LinearVelocity>) {
        leftMasterMotor.setVelocity(leftVelocity)
        rightMasterMotor.setVelocity(rightVelocity)
    }

    fun setVelocity(
        leftVelocity: SIUnit<LinearVelocity>,
        rightVelocity: SIUnit<LinearVelocity>,
        leftFF: SIUnit<Volt>,
        rightFF: SIUnit<Volt>
    ) {
        leftMasterMotor.setVelocity(leftVelocity, leftFF)
        rightMasterMotor.setVelocity(rightVelocity, rightFF)
    }

    override fun periodic() {}
}
