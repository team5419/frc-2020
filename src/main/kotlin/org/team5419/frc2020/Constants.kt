package org.team5419.frc2020

import org.team5419.fault.math.physics.DCMotorTransmission
import org.team5419.fault.math.physics.DifferentialDrive
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.units.derived.acceleration
import org.team5419.fault.math.units.derived.velocity
import org.team5419.fault.math.units.derived.radians
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.kEpsilon
import kotlin.math.PI
import kotlin.math.pow

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

    const val leftMasterPort = 10
    const val leftSlavePort = 11

    const val rightMasterPort = 8
    const val rightSlavePort = 6

    const val gyroPort = 7


    // configuration

    const val encoderPhase = true

    // path following parameters

    const val kBeta = 1.0 // m^-2
    const val kZeta = 1.0 // unitless

    val maxVelocity = 130.inches.velocity
    val maxAcceleration = 50.inches.acceleration

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
    public const val XboxCodrivePort = 0
    public const val XboxDrivePort = 0

    public const val SlowTurnMult = 0.4
    public const val SlowMoveMult = 0.4

    public const val DriverStowTimeout = 2.0

    public const val TriggerDeadband = 0.1
    public const val JoystickDeadband = 0.07
}

object ShoogerConstants {
    public const val kMasterPort = 3
    public const val kSlavePort1 = 15
    public const val kSlavePort2 = 16

    public const val kFeederPort = 13
    public const val kHopperPort = 9

    public val kMaxVelocity = 6000.0.radians.velocity
    public val kMinVelocity = 0.0.radians.velocity
    public val kV = SIUnit<Frac<Volt, AngularVelocity>>(12.0/6000.0/2/PI)
    public val kTicksPerRotation = (4092.0 / 3.5).nativeUnits
    public val flywheel = NativeUnitRotationModel(kTicksPerRotation)
}

object HoodConstants{
    public const val kPort = 12

    public const val kP = 1.0
    public const val kI = 0.0
    public const val kD = 0.0

    public val kTicksPerRotation = (4092 / 3).nativeUnits
    public val hood = NativeUnitRotationModel(kTicksPerRotation)
}

object IntakeConstants {
    public val kDeployTicksPerRotation = (4096 * 81).nativeUnits
    public val kDeployModel = NativeUnitRotationModel(kDeployTicksPerRotation)
    public val kIntakeTicksPerRotation = (4096 * 10).nativeUnits
    public val kIntakeModel = NativeUnitRotationModel(kIntakeTicksPerRotation)
    public const val kIntakePort = 2
    public const val kDeployPort = 4
}


object VisionConstants {
    public val kCameraHeight = 0.0.meters
    public val kTargetHeight = 0.0.meters
    public val kCameraAngle = Rotation2d( 0.0.radians )

    public val tolerance = 10.0
}
