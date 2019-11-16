package org.team5419.frc2019

import com.ctre.phoenix.motorcontrol.ControlMode
import org.team5419.fault.subsystems.drivetrain.AbstractTankDrive
import org.team5419.fault.subsystems.drivetrain.AbstractTankDrive.TurnType
import org.team5419.fault.hardware.ctre.BerkeliumSPX
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.fault.math.units.Meter
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.derived.AngularVelocity
import org.team5419.fault.math.units.derived.LinearVelocity
import org.team5419.fault.math.units.derived.Radian
import org.team5419.fault.math.units.derived.Volt
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.physics.DifferentialDrive
import org.team5419.fault.math.physics.DCMotorTransmission

class Drivetrain(

    _leftMaster: BerkeliumSRX<Meter>,
    _leftSlave1: BerkeliumSPX<Meter>,
    _leftSlave2: BerkeliumSPX<Meter>,
    _rightMaster: BerkeliumSRX<Meter>,
    _rightSlave1: BerkeliumSPX<Meter>,
    _rightSlave2: BerkeliumSPX<Meter>

) : AbstractTankDrive() {

    private val leftMaster: BerkeliumSRX<Meter>
    private val leftSlave1: BerkeliumSPX<Meter>
    private val leftSlave2: BerkeliumSPX<Meter>
    private val rightMaster: BerkeliumSRX<Meter>
    private val rightSlave1: BerkeliumSPX<Meter>
    private val rightSlave2: BerkeliumSPX<Meter>

    override val leftDistance: SIUnit<Meter> get() = SIUnit<Meter>(0.0)
    override val leftDistanceError: SIUnit<Meter> get() = SIUnit<Meter>(0.0)

    override val rightDistance: SIUnit<Meter> get() = SIUnit<Meter>(0.0)
    override val rightDistanceError: SIUnit<Meter> get() = SIUnit<Meter>(0.0)

    override val angularVelocity: SIUnit<AngularVelocity> get() = SIUnit<AngularVelocity>(0.0)
    override val turnError: SIUnit<Radian> get() = SIUnit<Radian>(0.0)

    val generlizedDCMotorTransmission = DCMotorTransmission(
        Constants.Drivetrain.SPEED_PER_VOLT,
        Constants.Drivetrain.TORQUE_PER_VOLT,
        Constants.Drivetrain.FRICTION_VOLTAGE
    )

    override val differentialDrive = DifferentialDrive(
        Constants.Drivetrain.MASS,
        Constants.Drivetrain.MOMENT_OF_INITERIA,
        Constants.Drivetrain.ANGULAR_DRAG,
        Constants.Drivetrain.WHEEL_RADIUS.toDouble(),
        Constants.Drivetrain.EFFECTIVE_WHEEL_BASE_RADIUS,
        generlizedDCMotorTransmission,
        generlizedDCMotorTransmission
    )

    init {

        leftMaster = _leftMaster
        leftSlave1 = _leftSlave1
        leftSlave2 = _leftSlave2
        rightMaster = _rightMaster
        rightSlave1 = _rightSlave1
        rightSlave2 = _rightSlave2

        leftSlave1.victorSPX.follow(leftMaster)

        // leftMaster = _leftMaster.apply {
        //     setInverted(false)
        //     setSensorPhase(true)
        //     setStatusFramePeriod(
        //         StatusFrameEnhanced.Status_3_Quadrature,
        //         Constants.TALON_UPDATE_PERIOD_MS,
        //         0
        //     )
        // }
        // leftSlave1 = _leftSlave1.apply {
        //     follow(leftMaster)
        //     setInverted(false)
        // }
        // leftSlave1 = _leftSlave2.apply {
        //     follow(leftMaster)
        //     setInverted(false)
        // }
        // rightMaster = _rightMaster.apply {
        //     setInverted(true)
        //     setSensorPhase(true)
        //     setStatusFramePeriod(
        //         StatusFrameEnhanced.Status_3_Quadrature,
        //         Constants.TALON_UPDATE_PERIOD_MS,
        //         0
        //     )
        // }
        // rightSlave1 = _rightSlave1.apply {
        //     follow(rightMaster)
        //     setInverted(false)
        // }
        // rightSlave2 = _rightSlave2.apply {
        //     follow(rightMaster)
        //     setInverted(false)
        // }
    }

    override fun setPercent(left: Double, right: Double) {
        rightMaster.talonSRX.set(ControlMode.PercentOutput, right)
        leftMaster.talonSRX.set(ControlMode.PercentOutput, left)
    }

    override fun setPosition(distance: SIUnit<Meter>) {
    }

    override fun setTurn(angle: Rotation2d, turnType: TurnType) {
    }

    override fun setVelocity(rightVelocity: LinearVelocity, leftVelocity: LinearVelocity, leftFF: Volt, rightFF: Volt) {
    }
}
