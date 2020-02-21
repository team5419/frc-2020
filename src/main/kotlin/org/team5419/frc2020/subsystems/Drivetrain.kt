package org.team5419.frc2020.subsystems

import org.team5419.frc2020.tab
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
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.geometry.Rotation2d as WPILibRotation2d
import edu.wpi.first.wpilibj.Notifier
import com.ctre.phoenix.sensors.PigeonIMU
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.ctre.phoenix.motorcontrol.*

@Suppress("TooManyFunctions")
object Drivetrain : Subsystem("DriveTrain") {
    // hardware

    val leftMasterMotor = TalonFX(DriveConstants.LeftMasterPort)

    private val leftSlave = TalonFX(DriveConstants.LeftSlavePort)

    val rightMasterMotor = TalonFX(DriveConstants.RightMasterPort)

    private val rightSlave = TalonFX(DriveConstants.RightSlavePort)

    public val gyro = PigeonIMU(DriveConstants.GyroPort)

    public val position = TankPositionTracker(
        { angle.degrees.toRotation2d() },
        { leftDistance.value },
        { rightDistance.value }
    )

    val robotPosition
        get() = position.robotPosition

    private var inverted = 1;

    init {
        leftSlave.apply {
            configFactoryDefault(100)

            // fallow the master
            follow(leftMasterMotor)
            setInverted(InvertType.FollowMaster)

            configVoltageCompSaturation(12.0, 100)
            enableVoltageCompensation(true)
        }

        rightSlave.apply {
            configFactoryDefault(100)

            // fallow the master
            follow(rightMasterMotor)
            setInverted(InvertType.FollowMaster)

            configVoltageCompSaturation(12.0, 100)
            enableVoltageCompensation(true)
        }

        leftMasterMotor.apply {
            configFactoryDefault(100)

            setSensorPhase(false)
            setInverted(false)

            config_kP( 0, DriveConstants.PID.P , 100 )
            config_kI( 0, DriveConstants.PID.I , 100 )
            config_kD( 0, DriveConstants.PID.D , 100 )
            config_kF( 0, DriveConstants.PID.F , 100 )

            setSelectedSensorPosition(0, 0, 100)

            configVoltageCompSaturation(12.0, 100)
            enableVoltageCompensation(true)
        }

        rightMasterMotor.apply {
            configFactoryDefault(100)

            setSensorPhase(true)
            setInverted(true)

            config_kP( 0, DriveConstants.PID.P , 100 )
            config_kI( 0, DriveConstants.PID.I , 100 )
            config_kD( 0, DriveConstants.PID.D , 100 )
            config_kF( 0, DriveConstants.PID.F , 100 )

            setSelectedSensorPosition(0, 0, 100)

            configVoltageCompSaturation(12.0, 100)
            enableVoltageCompensation(true)
        }

        gyro.apply {
            configFactoryDefault(100)

            setFusedHeading(0.0, 100)
        }

        tab.addNumber("drive angle", { angle })
    }

    // odometry

    val odometry = DifferentialDriveOdometry(WPILibRotation2d.fromDegrees(angle))

    val pose
        get() = odometry.getPoseMeters()

    // getters

    fun nativeUnitsToMeters(units: Int) =
        (DriveConstants.WheelCircumference * units.toDouble() / DriveConstants.TicksPerRotation)

    fun nativeUnitsToMetersPerSecond(units: Int) =
        (units.toDouble() * 10.0
            / DriveConstants.TicksPerRotation
            * DriveConstants.WheelCircumference.inMeters()).meters.velocity

    fun metersToNativeUnits(units: Double)
        = (units / DriveConstants.WheelCircumference.inMeters() * DriveConstants.TicksPerRotation)

    fun metersPerSecondToNativeUnits(units: Double)
        = (units / DriveConstants.WheelCircumference.inMeters() * DriveConstants.TicksPerRotation / 10)

    val leftDistance: SIUnit<Meter>
        get() = nativeUnitsToMeters(leftMasterMotor.getSelectedSensorPosition(0))

    val rightDistance: SIUnit<Meter>
        get() = nativeUnitsToMeters(rightMasterMotor.getSelectedSensorPosition(0))

    val leftVelocity: SIUnit<LinearVelocity>
        get() = nativeUnitsToMetersPerSecond(leftMasterMotor.getSelectedSensorVelocity(0))

    val rightVelocity: SIUnit<LinearVelocity>
        get() = nativeUnitsToMetersPerSecond(rightMasterMotor.getSelectedSensorVelocity(0))

    val angle: Double
        get() = -gyro.getFusedHeading()

    fun stop() = setOpenLoop(0.0, 0.0)

    fun invert() { inverted *= -1 }

    fun setPercent(left: Double, right: Double) = setOpenLoop(left, right)

    fun setPercent(signal: DriveSignal) = setOpenLoop(signal.left, signal.right)

    fun setOpenLoop(left: Double, right: Double) {
        leftMasterMotor.set(ControlMode.PercentOutput, left * inverted)
        rightMasterMotor.set(ControlMode.PercentOutput, right * inverted)
    }

    fun setVelocity(leftVelocity: SIUnit<LinearVelocity>, rightVelocity: SIUnit<LinearVelocity>) {
        leftMasterMotor.set(ControlMode.Velocity, metersPerSecondToNativeUnits(leftVelocity.value))
        rightMasterMotor.set(ControlMode.Velocity, metersPerSecondToNativeUnits(rightVelocity.value))
    }

    fun setVelocity(
        leftVelocity: SIUnit<LinearVelocity>,
        rightVelocity: SIUnit<LinearVelocity>,
        leftFF: SIUnit<Volt>,
        rightFF: SIUnit<Volt>
    ) {
        setVelocity(leftVelocity, rightVelocity)
        // leftMasterMotor.set(
        //     ControlMode.Velocity, metersPerSecondToNativeUnits(leftVelocity.value),
        //     DemandType.ArbitraryFeedForward, leftFF.value
        // )
        // rightMasterMotor.set(
        //     ControlMode.Velocity, metersPerSecondToNativeUnits(rightVelocity.value),
        //     DemandType.ArbitraryFeedForward, leftFF.value
        // )
    }

    override fun periodic() {
        odometry.update(
            WPILibRotation2d.fromDegrees(Drivetrain.angle),
            Drivetrain.leftDistance.inMeters(),
            Drivetrain.rightDistance.inMeters()
        )
    }
}
