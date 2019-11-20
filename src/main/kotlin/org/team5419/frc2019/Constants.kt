package org.team5419.frc2019

import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.Meter
import org.team5419.fault.math.units.inch
import org.team5419.fault.math.units.native.NativeUnitLengthModel
import org.team5419.fault.math.units.native.NativeUnit


object Constants {
    object Drivetrain{
        val WHEEL_RADIUS = 3.0.inch;
        val ENCODER_TICKS_PER_ROTATION = SIUnit <NativeUnit> (4092.0);
        val NATIVE_UNIT_LENGTH_MODEL = NativeUnitLengthModel (ENCODER_TICKS_PER_ROTATION, WHEEL_RADIUS)

        const val SPEED_PER_VOLT = 0.0
        const val TORQUE_PER_VOLT = 0.0
        const val FRICTION_VOLTAGE = 0.0
        const val MASS = 0.0
        const val MOMENT_OF_INERTIA = 0.0
        const val ANGULAR_DRAG = 0.0

    }
}
