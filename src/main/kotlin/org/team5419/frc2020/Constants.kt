package org.team5419.frc2020

import org.team5419.fault.math.physics.DCMotorTransmission
import org.team5419.fault.math.physics.DifferentialDrive
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.kEpsilon
import kotlin.math.PI
import kotlin.math.pow

@SuppressWarnings("MaxLineLength, WildcardImport")
object RobotConstants {
    val kRobotLength = 32.inches
    val kRobotWidth = 27.5.inches
    val kBumperThickness = 2.inches
}

object TrajectoryConstants {
    val kMaxCentripetalAcceleration = 4.0.feet.acceleration
    val kMaxAcceleration = 4.0.feet.acceleration
    val kMaxAngularAcceleration = 2.0.radians.acceleration
    val kMaxVelocity = 10.0.feet.velocity
}

object DriveConstants {
    // ports
    const val kLeftMasterPort = 1
    const val kLeftSlave1Port = 2
    const val kLeftSlave2Port = 3

    const val kRightMasterPort = 4
    const val kRightSlave1Port = 5
    const val kRightSlave2Port = 6

    const val kGyroPort = 7

    // Added Constants - Remove Later If Necessary
    const val kLeftEncoderPort1 = 1
    const val kLeftEncoderPort2 = 2
    const val kLeftEncoderReversed = true
    const val kRightEncoderPort1 = 4
    const val kRightEncoderPort2 = 7
    const val kRightEncoderReversed = false

    const val kEncoderDistancePerPulse = 1000.0

    const val kGyroReversed = false
    // End

    // misc
    const val kEncoderPhase = true

    // path following parameters
    const val kBeta = 1.0 // m^-2
    const val kZeta = 1.0 // unitless

    val kMotionMagicVelocity = 130.inches.velocity
    val kMotionMagicAcceleration = 50.inches.acceleration

    // dimensions and constants
    val kWheelRadius = 3.inches
    val kWheelDiameter = kWheelRadius * 2.0
    val kWheelCir = kWheelDiameter * PI

    val kTrackWidth = 20.inches
    val kEffectiveWheelbaseRadius = kTrackWidth / 2.0

    val kMass = 120.lbs
    val kMoi = 0.0 // kg * m^2
    val kAngularDrag = 10.0 // (N * m) / (rad / s)  TUNE ME

    val kTicksPerRotation = 4096.nativeUnits
    val kPigeonConversion = (3600.0 / 8192.0).nativeUnits

    const val kDriveKv = kEpsilon
    const val kDriveKa = kEpsilon
    const val kDriveKs = kEpsilon

    val kLeftDriveGearbox = DCMotorTransmission(
        1 / kDriveKv,
        kWheelRadius.value.pow(2) * kMass.value / (2.0 * kDriveKa),
        kDriveKs
    )

    val kRightDriveGearbox = DCMotorTransmission(
        1 / kDriveKv,
        kWheelRadius.value.pow(2) * kMass.value / (2.0 * kDriveKa),
        kDriveKs
    )

    val kDriveModel = DifferentialDrive(
        kMass.value,
        kMoi,
        kAngularDrag, // tune me
        kWheelRadius.value,
        kEffectiveWheelbaseRadius.value,
        kLeftDriveGearbox,
        kRightDriveGearbox
    )

    val kNativeGearboxConversion = NativeUnitLengthModel(
        kTicksPerRotation,
        kWheelRadius
    )
}

object InputConstants {
    public const val kDriverPort = 0
    public const val CODRIVER_BUTTON_BOARD_PORT = 1
    public const val CODRIVER_JOYSTICK_PORT = 2

    // driver constants
    public const val JOYSTICK_DEADBAND = 0.07
    public const val TRIGGER_DEADBAND = 0.1
    public const val TURN_MULT = 0.4
    public const val SLOW_MULT = 0.5
    public const val DRIVER_STOW_TIMEOUT = 2.0 // seconds

    // codriver constants
    public const val MANUAL_CONTROL_DEADBAND = 0.07
}

object ShoogerConstants {
    public const val kMasterPort = 6
    public const val kSlavePort1 = 7
    public const val kSlavePort2 = 8
    public const val kSlavePort3 = 9
    public val kMaxVelocity = 6000.0.radians.velocity
    public val kMinVelocity = 0.0.radians.velocity
    public val kV = SIUnit<Frac<Volt, AngularVelocity>>(12.0/6000.0/2/PI)
    public val kTicksPerRotation = (4092.0 / 3).nativeUnits
    public val flywheel = NativeUnitRotationModel(kTicksPerRotation)

    public const val kHoodP = 1
    public const val kHoodI = 0
    public const val kHoodD = 0
    public const val kHoodPort = 8
}

object HoodConstants{
    public const val kPort = 8
    public const val kP = 1.0
    public const val kI = 0.0
    public const val kD = 0.0

    public val kTicksPerRotation = (4092 / 3).nativeUnits
    public val hood = NativeUnitRotationModel(kTicksPerRotation)
}

object ClimberConstants {
    public const val kMasterPort = 9
    public const val kSlavePort = 10

}

object SpinConstants {
    public const val kMotorPort = 9
}

object IntakeConstants {
    public const val kIntakePort = 9
}
