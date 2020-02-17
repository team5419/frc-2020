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
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.input.DriveSignal
import org.team5419.fault.hardware.ctre.*
import edu.wpi.first.wpilibj.Notifier
import com.ctre.phoenix.sensors.PigeonIMU
import com.ctre.phoenix.motorcontrol.*

object Drivetrain : Subsystem("DriveTrain") {

    private const val kPositionSlot = 0
    private const val kVelocitySlot = 1
    private const val kTurnSlot = 2

    val leftDriveGearbox = DCMotorTransmission(
        1 / DriveConstants.DriveKv,
        DriveConstants.WheelRadius.value *
        DriveConstants.WheelRadius.value *
        RobotConstants.Mass.value / (2.0 * DriveConstants.DriveKa),
        DriveConstants.DriveKs
    )

    val rightDriveGearbox = DCMotorTransmission(
        1 / DriveConstants.DriveKv,
        DriveConstants.WheelRadius.value *
        DriveConstants.WheelRadius.value *
        RobotConstants.Mass.value / (2.0 * DriveConstants.DriveKa),
        DriveConstants.DriveKs
    )

    val nativeGearboxConversion = NativeUnitLengthModel(
        DriveConstants.TicksPerRotation,
        DriveConstants.WheelRadius
    )

    private val periodicIO = PeriodicIO()
    private var currentState = State.Nothing
    private var wantedState = State.Nothing

    val differentialDrive = DifferentialDrive(
        RobotConstants.Mass.value,
        DriveConstants.Moi,
        DriveConstants.AngularDrag,
        DriveConstants.WheelRadius.value,
        DriveConstants.EffectiveWheelbaseRadius.value,
        leftDriveGearbox, rightDriveGearbox
    )

    val trajectoryFollower = RamseteFollower(DriveConstants.Beta, DriveConstants.Zeta)

    val localization = TankPositionTracker(
        { angle },
        { leftDistance.value },
        { rightDistance.value }
    )

    val robotPosition: Pose2d
        get() = localization.robotPosition

    // hardware

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

    val leftDistance: SIUnit<Meter>
        get() = -nativeGearboxConversion.fromNativeUnitPosition(periodicIO.leftRawSensorPosition)

    val rightDistance: SIUnit<Meter>
        get() = nativeGearboxConversion.fromNativeUnitPosition(periodicIO.rightRawSensorPosition)

    val leftDistanceError: SIUnit<Meter>
        get() = -nativeGearboxConversion.fromNativeUnitPosition(periodicIO.leftRawDistanceError)

    val rightDistanceError: SIUnit<Meter>
        get() = nativeGearboxConversion.fromNativeUnitPosition(periodicIO.rightRawDistanceError)

    val leftVelocity: SIUnit<LinearVelocity>
        get() = nativeGearboxConversion.fromNativeUnitVelocity(periodicIO.leftRawSensorVelocity)

    val rightVelocity: SIUnit<LinearVelocity>
        get() = nativeGearboxConversion.fromNativeUnitVelocity(periodicIO.rightRawSensorVelocity)

    val angle: Rotation2d
        get() = -periodicIO.gyroAngle.toRotation2d()

    val angularVelocity: SIUnit<AngularVelocity>
        get() = periodicIO.angularVelocity

    val turnError: SIUnit<Radian>
        get() = periodicIO.turnError

    fun stop() = setOpenLoop(0.0, 0.0)

    fun setPercent(left: Double, right: Double) = setOpenLoop(left, right)

    fun setPercent(signal: DriveSignal) = setOpenLoop(signal.left, signal.right)

    fun setOpenLoop(left: Double, right: Double) {
        wantedState = State.OpenLoop

        periodicIO.leftPercent = left
        periodicIO.rightPercent = right

        periodicIO.leftFeedforward = 0.0.volts
        periodicIO.rightFeedforward = 0.0.volts
    }

    fun setPosition(distance: SIUnit<Meter>) {
        wantedState = State.Position

        leftMasterMotor.talonSRX.selectProfileSlot(kPositionSlot, 0)
        rightMasterMotor.talonSRX.selectProfileSlot(kPositionSlot, 0)

        periodicIO.leftPosition = distance
        periodicIO.rightPosition = distance

        periodicIO.leftFeedforward = 0.0.volts
        periodicIO.rightFeedforward = 0.0.volts
    }

    // fun setTurn(angle: Rotation2d, type: TurnType) {
    //     wantedState = State.Turning
    //     zeroOutputs()

    //     rightMasterMotor.talonSRX.selectProfileSlot(kPositionSlot, 0)
    //     rightMasterMotor.talonSRX.selectProfileSlot(kTurnSlot, 1)

    //     leftMasterMotor.talonSRX.follow(rightMasterMotor.talonSRX, FollowerType.AuxOutput1)

    //     periodicIO.angleTarget = angle
    // }

    fun setVelocity(
        leftVelocity: SIUnit<LinearVelocity>,
        rightVelocity: SIUnit<LinearVelocity>,
        leftFF: SIUnit<Volt>,
        rightFF: SIUnit<Volt>
    ) {
        wantedState = State.Velocity

        leftMasterMotor.talonSRX.selectProfileSlot(kVelocitySlot, 0)
        rightMasterMotor.talonSRX.selectProfileSlot(kVelocitySlot, 0)

        periodicIO.leftVelocity = leftVelocity
        periodicIO.rightVelocity = rightVelocity

        periodicIO.leftFeedforward = leftFF
        periodicIO.rightFeedforward = rightFF
    }

    fun setOutput(wheelVelocities: DifferentialDrive.WheelState, wheelVoltages: DifferentialDrive.WheelState) {
        wantedState = State.PathFollowing

        periodicIO.leftVelocity = (differentialDrive.wheelRadius * wheelVelocities.left).meters.velocity
        periodicIO.rightVelocity = (differentialDrive.wheelRadius * wheelVelocities.right).meters.velocity

        periodicIO.leftFeedforward = wheelVoltages.left.volts
        periodicIO.rightFeedforward = wheelVoltages.right.volts
    }

    override fun zeroOutputs() {
        wantedState = State.Nothing

        periodicIO.leftPercent = 0.0
        periodicIO.rightPercent = 0.0
        periodicIO.leftVelocity = 0.0.meters.velocity
        periodicIO.rightVelocity = 0.0.meters.velocity
        periodicIO.leftPosition = 0.0.meters
        periodicIO.rightPosition = 0.0.meters
        periodicIO.leftFeedforward = 0.0.volts
        periodicIO.rightFeedforward = 0.0.volts
    }

    override fun periodic() {
        periodicIO.leftVoltage = leftMasterMotor.voltageOutput
        periodicIO.rightVoltage = rightMasterMotor.voltageOutput

        periodicIO.leftCurrent = leftMasterMotor.talonSRX.getStatorCurrent().amps
        periodicIO.rightCurrent = rightMasterMotor.talonSRX.getStatorCurrent().amps

        periodicIO.leftRawSensorPosition = leftMasterMotor.encoder.rawPosition
        periodicIO.rightRawSensorPosition = rightMasterMotor.encoder.rawPosition

        periodicIO.leftRawDistanceError = leftMasterMotor.talonSRX.closedLoopError.nativeUnits
        periodicIO.rightRawDistanceError = rightMasterMotor.talonSRX.closedLoopError.nativeUnits
        periodicIO.leftRawSensorVelocity = leftMasterMotor.encoder.rawVelocity
        periodicIO.rightRawSensorVelocity = rightMasterMotor.encoder.rawVelocity


        periodicIO.gyroAngle = gyro.fusedHeading.degrees

        val xyz = DoubleArray(3)
        gyro.getRawGyro(xyz)
        periodicIO.angularVelocity = xyz[1].radians.velocity

        // println("Meters: Left: ${leftDistance.value}, Right: ${rightDistance.value}")
        // println("Angle: ${angle.degree}")

        when (wantedState) {
            State.Nothing -> {
                leftMasterMotor.setNeutral()
                rightMasterMotor.setNeutral()
            }
            State.OpenLoop -> {
                leftMasterMotor.setPercent(periodicIO.leftPercent)
                rightMasterMotor.setPercent(periodicIO.rightPercent)
            }
            State.PathFollowing, State.Velocity -> {
                leftMasterMotor.setVelocity(periodicIO.leftVelocity, periodicIO.leftFeedforward)
                rightMasterMotor.setVelocity(periodicIO.rightVelocity, periodicIO.rightFeedforward)
            }
            State.Position -> {
                leftMasterMotor.setPosition(periodicIO.leftPosition, 0.0.volts)
                rightMasterMotor.setPosition(periodicIO.rightPosition, 0.0.volts)
            }
            State.Turning -> {
                rightMasterMotor.talonSRX.set(
                    ControlMode.PercentOutput, 0.0,
                    DemandType.AuxPID, rightMasterMotor.talonSRX.getSelectedSensorPosition(1) +
                        periodicIO.angleTarget.value.value
                )
            }
        }

        if (wantedState != currentState) currentState = wantedState
    }

    class PeriodicIO {
        // input

        var leftVoltage: SIUnit<Volt> = 0.0.volts
        var rightVoltage: SIUnit<Volt> = 0.0.volts

        var leftCurrent: SIUnit<Ampere> = 0.0.amps
        var rightCurrent: SIUnit<Ampere> = 0.0.amps

        var leftRawSensorPosition = 0.0.nativeUnits
        var rightRawSensorPosition = 0.0.nativeUnits

        var leftRawDistanceError = 0.0.nativeUnits
        var rightRawDistanceError = 0.0.nativeUnits

        var leftRawSensorVelocity = 0.0.nativeUnits.velocity
        var rightRawSensorVelocity = 0.0.nativeUnits.velocity

        var gyroAngle: SIUnit<Radian> = 0.0.degrees
        var angularVelocity = 0.0.radians.velocity
        var turnError: SIUnit<Radian> = 0.0.degrees

        // output

        var leftPercent = 0.0
        var rightPercent = 0.0

        var leftVelocity: SIUnit<LinearVelocity> = 0.0.meters.velocity
        var rightVelocity: SIUnit<LinearVelocity> = 0.0.meters.velocity

        var leftPosition: SIUnit<Meter> = 0.0.meters
        var rightPosition: SIUnit<Meter> = 0.0.meters

        var leftFeedforward: SIUnit<Volt> = 0.0.volts
        var rightFeedforward: SIUnit<Volt> = 0.0.volts

        var angleTarget: Rotation2d = Rotation2d()
    }

    private enum class State { Nothing, Turning, Velocity, PathFollowing, OpenLoop, Position }
}
