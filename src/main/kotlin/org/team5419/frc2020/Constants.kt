package org.team5419.frc2020

import org.team5419.fault.math.physics.DCMotorTransmission
import org.team5419.fault.math.physics.DifferentialDrive
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.fault.math.kEpsilon

import org.team5419.fault.math.units.native.NativeUnitRotationModel
import org.team5419.fault.math.units.native.nativeUnits
import kotlin.math.PI
import kotlin.math.pow

@SuppressWarnings("MaxLineLength, WildcardImport")

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
    public const val kMotorPort1 = 2
    public const val kMotorPort2 = 3
    public const val kMotorPort3 = 4
    public const val kMotorPort4 = 5
    public const val kMotorPort5 = 12

    public var kV = SIUnit<Frac<Volt, AngularVelocity>>(12.0/6000.0/2/Math.PI)
    private const val kTicksPerRotation = 4096 / 3
    public var gearbox = NativeUnitRotationModel(kTicksPerRotation)
}