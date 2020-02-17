package org.team5419.frc2020.subsystems

import org.team5419.frc2020.RobotConstants
import org.team5419.frc2020.DriveConstants
import org.team5419.fault.trajectory.followers.RamseteFollower
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.native.nativeUnits
import org.team5419.fault.math.units.native.NativeUnitLengthModel
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.fault.math.physics.DifferentialDrive
import org.team5419.fault.math.physics.DCMotorTransmission
import org.team5419.fault.math.localization.TankPositionTracker
import org.team5419.fault.math.geometry.Rotation2d
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

    private val leftSlave1 = BerkeliumSRX(
        DriveConstants.LeftSlavePort, nativeGearboxConversion
    )

    val rightMasterMotor = BerkeliumSRX(
        DriveConstants.RightMasterPort, nativeGearboxConversion
    )

    private val rightSlave1 = BerkeliumSRX(
        DriveConstants.RightSlavePort, nativeGearboxConversion
    )

    public val gyro = PigeonIMU(DriveConstants.GyroPort)

    init {
        leftSlave1.talonSRX.apply {
            configFactoryDefault(100)

            // fallow the master
            follow(leftMasterMotor.talonSRX)
            setInverted(InvertType.FollowMaster)
        }

        rightSlave1.talonSRX.apply {
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

    // val leftDistance: SIUnit<Meter>
    //     get() = -nativeGearboxConversion.fromNativeUnitPosition(periodicIO.leftRawSensorPosition)

    // val rightDistance: SIUnit<Meter>
    //     get() = nativeGearboxConversion.fromNativeUnitPosition(periodicIO.rightRawSensorPosition)

    // val leftDistanceError: SIUnit<Meter>
    //     get() = -nativeGearboxConversion.fromNativeUnitPosition(periodicIO.leftRawDistanceError)

    // val rightDistanceError: SIUnit<Meter>
    //     get() = nativeGearboxConversion.fromNativeUnitPosition(periodicIO.rightRawDistanceError)

    // val leftVelocity: SIUnit<LinearVelocity>
    //     get() = nativeGearboxConversion.fromNativeUnitVelocity(periodicIO.leftRawSensorVelocity)

    // val rightVelocity: SIUnit<LinearVelocity>
    //     get() = nativeGearboxConversion.fromNativeUnitVelocity(periodicIO.rightRawSensorVelocity)

    // val angle: Rotation2d
    //     get() = periodicIO.gyroAngle.toRotation2d()

    // val angularVelocity: SIUnit<AngularVelocity>
    //     get() = periodicIO.angularVelocity

    // val turnError: SIUnit<Radian>
    //     get() = periodicIO.turnError

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

    override fun periodic() {}
}
