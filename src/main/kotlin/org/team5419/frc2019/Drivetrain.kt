// package org.team5419.frc2019

// import com.ctre.phoenix.motorcontrol.ControlMode
// import org.team5419.fault.subsystems.drivetrain.AbstractTankDrive
// import org.team5419.fault.subsystems.drivetrain.AbstractTankDrive.TurnType
// import org.team5419.fault.hardware.ctre.BerkeliumSPX
// import org.team5419.fault.hardware.ctre.BerkeliumSRX
// import org.team5419.fault.math.units.*
// import org.team5419.fault.math.units.derived.*
// import org.team5419.fault.math.geometry.Rotation2d
// import org.team5419.fault.math.physics.DifferentialDrive
// import org.team5419.fault.math.physics.DCMotorTransmission

// object Drivetrain : AbstractTankDrive() {

//     private val leftMaster = BerkeliumSRX(12, Constants.Drivetrain.NATIVE_UNIT_LENGTH_MODEL)
//     private val leftSlave1 = BerkeliumSPX(2, Constants.Drivetrain.NATIVE_UNIT_LENGTH_MODEL)
//     private val leftSlave2 = BerkeliumSPX(3, Constants.Drivetrain.NATIVE_UNIT_LENGTH_MODEL)

//     private val rightMaster = BerkeliumSRX(6, Constants.Drivetrain.NATIVE_UNIT_LENGTH_MODEL)
//     private val rightSlave1 = BerkeliumSPX(7, Constants.Drivetrain.NATIVE_UNIT_LENGTH_MODEL)
//     private val rightSlave2 = BerkeliumSPX(8, Constants.Drivetrain.NATIVE_UNIT_LENGTH_MODEL)

//     override val leftDistance: SIUnit<Meter> get() = 0.0.meters
//     override val leftDistanceError: SIUnit<Meter> get() = 0.0.meters

//     override val rightDistance: SIUnit<Meter> get() = 0.0.meters
//     override val rightDistanceError: SIUnit<Meter> get() = 0.0.meters

//     override val angularVelocity: SIUnit<AngularVelocity> get() = SIUnit<AngularVelocity>(0.0)
//     override val turnError: SIUnit<Radian> get() = SIUnit<Radian>(0.0)

//     val generlizedDCMotorTransmission = DCMotorTransmission(
//         Constants.Drivetrain.SPEED_PER_VOLT,
//         Constants.Drivetrain.TORQUE_PER_VOLT,
//         Constants.Drivetrain.FRICTION_VOLTAGE
//     )

//     override val differentialDrive = DifferentialDrive(
//         Constants.Drivetrain.MASS,
//         Constants.Drivetrain.MOMENT_OF_INITERIA,
//         Constants.Drivetrain.ANGULAR_DRAG,
//         Constants.Drivetrain.WHEEL_RADIUS.toDouble(),
//         Constants.Drivetrain.EFFECTIVE_WHEEL_BASE_RADIUS,
//         generlizedDCMotorTransmission,
//         generlizedDCMotorTransmission
//     )

//     init {
//         leftSlave1.victorSPX.apply {
//             follow(leftMaster)
//         }
//         leftSlave2.apply {
//             follow(leftMaster)
//         }
//         rightSlave1.apply {
//             follow(rightMaster)
//         }
//         rightSlave2.apply {
//             follow(rightMaster)
//         }
//     }

//     override fun setPercent(left: Double, right: Double) {
//         rightMaster.talonSRX.set(ControlMode.PercentOutput, right)
//         leftMaster.talonSRX.set(ControlMode.PercentOutput, left)
//     }

//     override fun setPosition(distance: SIUnit<Meter>) {
//     }

//     override fun setTurn(angle: Rotation2d, turnType: TurnType) {
//     }

//     override fun setVelocity(rightVelocity: LinearVelocity, leftVelocity: LinearVelocity, leftFF: Volt, rightFF: Volt) {
//     }
// }
