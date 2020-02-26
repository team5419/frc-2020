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

    const val LeftMasterPort = 2
    const val LeftSlavePort = 3

    const val RightMasterPort = 5
    const val RightSlavePort = 4

    const val GyroPort = 20

    // configuration

    const val EncoderPhase = true

    val GearRatio = (10.3333 / 1.0)

    val TicksPerRotation = (2048.0 * GearRatio)
    val PigeonConversion = (3600.0 / 8192.0).nativeUnits

    // path following parameters

    val MaxVelocity = 4.0.meters.velocity
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
        public const val P = 0.2
        public const val I = 0.0
        public const val D = 0.0
        public const val F = 0.05
    }
}

object InputConstants {
    // controller ports
    public const val XboxDrivePort = 0
    public const val XboxCodrivePort = 1

    // slow movments multipliers
    public const val SlowTurnMultiplier = 0.4
    public const val SlowMoveMultiplier = 0.4

    // deadbands
    public const val TriggerDeadband = 0.1
    public const val JoystickDeadband = 0.07
}

object ShoogerConstants {
    public const val MasterPort = 7
    public const val SlavePort1 = 17
    public const val SlavePort2 = 18

    public const val BangBangTolerance = 50

    public val TargetVelocity = 4800.0
    public val TicksPerRotation = 4092.0.nativeUnits

    public val ShoogTime = 3.0.seconds
}

object HoodConstants {
    public const val HoodPort = 12

    public const val FarHoodAngle = 14.8
    public const val TrussHoodAngle = 14.8
    public const val CloseHoodAngle = 3.0

    object PID {
        public const val P = 3.0
        public const val I = 0.0
        public const val D = 45.0
    }

    public val MaxSpeed = 0.7
    public val MaxAngle = 18.0

    public val TicksPerRotation = 4092.0
    public val GearRatio = 4.0/1.0
}

object StorageConstants {
    public const val FeederPort = 10
    public const val HopperPort = 9

    public const val FeederPercent = 1.0
    public const val HopperPercent = 1.0

    public const val FeederLazyPercent = 0.3
    public const val HopperLazyPercent = 0.3

    public const val SensorThreshold = 500 //3500
}

object IntakeConstants {
    public val DeployTicksPerRotation = (4096).nativeUnits
    public val IntakeTicksPerRotation = (4096 * 10).nativeUnits
    public val StorePosition = 3.037282.radians
    public val DeployPosition = 0.radians

    public const val IntakePort = 8
    public const val DeployPort = 15

    // public const val RollerPort = 30

    public const val DeployStrength = 0.4
}

object ClimberConstants {
    public val WenchPort = 11
    public val DeployPort = 13
}

object VisionConstants {
    public val CameraAngle = 0.0.radians
    public val CameraHeight = 0.0.meters

    public val TargetHeight = 0.0.meters

    public val Tolerance = 0.1

    public val MaxAutoAlignSpeed = 0.2

    public val TargetOffset = 0.0

    object PID {
        public const val P = 0.016
        public const val I = 0.0
        public const val D = 0.0005
    }
}
