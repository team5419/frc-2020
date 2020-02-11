package org.team5419.frc2020

import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.fault.math.kEpsilon
import kotlin.math.PI

object RobotConstants {
    val Mass = 120.lbs
    val Length = 32.inches
    val Width = 27.5.inches
    val BumperThickness = 2.inches
}

object DriveConstants {
    // ports

    const val LeftMasterPort = 10
    const val LeftSlavePort = 11

    const val RightMasterPort = 8
    const val RightSlavePort = 6

    const val GyroPort = 7
    // configuration

    const val EncoderPhase = true

    val GearRatio = (3.0 / 32.0)

    val TicksPerRotation = (2048 * GearRatio).nativeUnits
    val PigeonConversion = (3600.0 / 8192.0).nativeUnits

    // path following parameters

    val MaxVelocity = 10.0.feet.velocity
    val MaxAcceleration = 4.0.feet.acceleration

    val MaxCentripetalAcceleration = 4.0.feet.acceleration
    val MaxAngularAcceleration = 2.0.radians.acceleration

    // dimensions

    val WheelRadius = 3.inches
    val WheelDiameter = WheelRadius * 2.0
    val WheelCircumference = WheelDiameter * PI

    // characterization

    const val Beta = 2.0 // m^-2
    const val Zeta = 0.7 // unitless

    val TrackWidth = 1.781.meters
    val EffectiveWheelbaseRadius = TrackWidth / 2.0

    val Moi = 0.0 // kg * m^2
    val AngularDrag = 10.0 // (N * m) / (rad / s)  TUNE ME

    const val DriveKv = 2.2
    const val DriveKa = 0.174
    const val DriveKs = 0.328

    object PID {
        public const val P = 1.0
        public const val I = 0.0
        public const val D = 0.0
    }
}

object InputConstants {
    // controller ports
    public const val XboxCodrivePort = 1
    public const val XboxDrivePort = 0

    // slow movments multipliers
    public const val SlowTurnMultiplier = 0.4
    public const val SlowMoveMultiplier = 0.4

    // deadbands
    public const val TriggerDeadband = 0.1
    public const val JoystickDeadband = 0.07
}

object ShoogerConstants {
    public const val MasterPort = 3
    public const val SlavePort1 = 15
    public const val SlavePort2 = 16

    public val TargetVelocity = 6000.0.radians.velocity
    public val TicksPerRotation = 4092.0.nativeUnits

    // WHAT AM I? ->
    // public val V = SIUnit<Frac<Volt, AngularVelocity>>(12.0/6000.0/2/PI)
}

object HoodConstants {
    public const val HoodPort = 12

    object PID {
        public const val P = 0.015
        public const val I = 0.00
        public const val D = 0.0
    }

    public val TicksPerRotation = (4092 / 3).nativeUnits
}
object StorageConstants {
    public const val FeederPort = 13
    public const val HopperPort = 9

    public const val storagePercent = 70.0
    public const val passiveStoragePercent = 9.0

    public const val FeederPercent = 1.0

    public const val HopperPercent = 1.0
    public const val HopperLazyPercent = 0.4
}

object IntakeConstants {
    public val DeployTicksPerRotation = (4096 * 81).nativeUnits
    public val IntakeTicksPerRotation = (4096 * 10).nativeUnits

    public const val IntakePort = 2
    public const val RollerPort = 17
    public const val DeployPort = 4
}

object VisionConstants {
    public val CameraAngle = 0.0.radians
    public val CameraHeight = 0.0.meters

    public val TargetHeight = 0.0.meters

    public val Tolerance = 0.1

    object PID {
        public const val P = 0.024
        public const val I = 0.00
        public const val D = 100.0
    }
}
