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

    const val LeftMasterPort = 3
    const val LeftSlavePort = 4

    const val RightMasterPort = 1
    const val RightSlavePort = 2

    const val GyroPort = 20

    // configuration

    const val EncoderPhase = true

    val GearRatio = (10.3333 / 1.0)

    val TicksPerRotation = (2048.0 * GearRatio)
    val PigeonConversion = (3600.0 / 8192.0).nativeUnits

    // path following parameters

    val MaxVelocity = 4.5.meters.velocity
    val MaxAcceleration = 6.0.feet.acceleration
    val MaxCentripetalAcceleration = 3.0.meters.acceleration

    // dimensions

    val WheelRadius = 3.inches
    val WheelDiameter = WheelRadius * 2.0
    val WheelCircumference = WheelDiameter * PI

    // characterization

    const val Beta = 2.0 // m^-2
    const val Zeta = 0.7 // unitless

    val TrackWidth = 1.781.meters
    val EffectiveWheelbaseRadius = TrackWidth / 2.0

    const val DriveKv = 2.3
    const val DriveKa = 0.463
    const val DriveKs = 0.191

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
    public const val JoystickDeadband = 0.05
}

object ShoogerConstants {
    public const val MasterPort = 6
    public const val SlavePort = 7

    public val TicksPerRotation = 2048.0
    public val GearRation = 0.75

    public val ShoogTime = 3.0.seconds
}

object HoodConstants {
    public const val HoodPort = 10

    public const val FarHoodAngle = 15.0
    public const val TrussHoodAngle = 14.8
    public const val CloseHoodAngle = 3.0

    object PID {
        public const val P = 0.5
        public const val I = 0.0
        public const val D = 45.0
    }

    public val MaxSpeed = 0.7
    public val MaxAngle = 18.0

    public val TicksPerRotation = 4092.0
    public val GearRatio = 4.0/1.0
}

object StorageConstants {
    public const val FeederPort = 12
    public const val HopperPort = 11

    public const val FeederPercent = 1.0
    public const val HopperPercent = 1.0

    public const val FeederLazyPercent = 0.3
    public const val HopperLazyPercent = 0.35

    public const val SensorThreshold = 500 //3500
}

object IntakeConstants {
    public val DeployTicksPerRotation = (4096).nativeUnits
    public val IntakeTicksPerRotation = (4096 * 10).nativeUnits
    public val StorePosition = 1300
    public val DeployPosition = 0

    public const val IntakePort = 9
    public const val DeployPort = 8 // make sure it dosent overlap with intake deploy

    // public const val RollerPort = 30

    public const val DeployStrength = 0.4
}

object ClimberConstants {
    public val WinchPort = 13
    public val DeployPort = 14 // make sure it dosent overlap with climber deploy
}

object VisionConstants {
    public val CameraAngle = 16.599.degrees
    public val CameraHeight = 7.inches

    public val TargetHeight = 2.28.meters

    public val Tolerance = 0.3

    public val MaxAutoAlignSpeed = 0.2

    public val TargetOffset = 0.0

    public const val MaxOffsetFor2XZoom = 10.0

    object PID {
        public const val P = 0.015
        public const val I = 0.003
        public const val D = 0.0005
    }

    object PID2 {
        public const val P = 0.015
        public const val I = 0.003
        public const val D = 0.0005
    }
}
