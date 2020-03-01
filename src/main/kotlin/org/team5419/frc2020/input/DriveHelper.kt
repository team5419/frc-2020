package org.team5419.frc2020.input

import org.team5419.fault.input.*
import org.team5419.fault.util.BooleanSource
import org.team5419.fault.util.DoubleSource
import kotlin.math.absoluteValue

abstract class DriveHelper : DriveSignalSource {

    abstract fun output(): DriveSignal

    override fun invoke() = output()

    protected fun handleDeadband(value: Double, deadband: Double) = when {
        value.absoluteValue < deadband.absoluteValue -> 0.0
        else -> (1+deadband) * value - deadband
    }
}

class TankHelper(
    private val left: DoubleSource,
    private val right: DoubleSource,
    private val slow: BooleanSource = { false },
    private val deadband: Double = kDefaultDeaband,
    private val slowMultiplier: Double = kDefaultSlowMultiplier
) : DriveHelper() {

    override fun output(): DriveSignal {
        val currentSlow = if (slow()) slowMultiplier else 1.0
        var currentLeft = left()
        var currentRight = right()
        currentLeft = handleDeadband(currentLeft, deadband)
        currentRight = handleDeadband(currentRight, deadband)
        return DriveSignal(currentLeft * currentSlow, currentRight)
    }

    companion object {
        const val kDefaultDeaband = 0.02
        const val kDefaultSlowMultiplier = 0.4
    }
}

class SpaceDriveHelper(
    private val throttle: DoubleSource,
    private val wheel: DoubleSource,
    private val quickTurn: BooleanSource,
    private val slow: BooleanSource = { false },
    private val deadband: Double = kDefaultDeadband,
    private val quickTurnMultiplier: Double = kDefaultQuickTurnMultiplier,
    private val slowMultiplier: Double = kDefaultSlowMultiplier
) : DriveHelper() {

    override fun output(): DriveSignal {
        var currentThrottle = throttle()
        var currentTurn = wheel() * if (quickTurn()) 1.0 else quickTurnMultiplier
        currentThrottle = handleDeadband(currentThrottle, deadband)
        currentTurn = handleDeadband(currentTurn, deadband)
        val currentSlow = if (slow()) slowMultiplier else 1.0
        val left = currentSlow * (currentThrottle - currentTurn)
        val right = currentSlow * (currentThrottle + currentTurn)
        return DriveSignal(left, right)
    }

    companion object {
        const val kDefaultDeadband = 0.02
        const val kDefaultQuickTurnMultiplier = 0.4
        const val kDefaultSlowMultiplier = 0.4
    }
}

class CheesyDriveHelper(
    private val throttle: DoubleSource,
    private val wheel: DoubleSource,
    private val quickTurn: BooleanSource
) : DriveHelper() {

    override fun output(): DriveSignal {
        return DriveSignal()
    }
}

class BerkeleyDriveHelper(
    private val throttle: DoubleSource,
    private val wheel: DoubleSource,
    private val quickTurn: BooleanSource,
    private val slow: BooleanSource = { false },
    private val deadband: Double = kDefaultDeadband,
    private val quickTurnMultiplier: Double = kDefaultQuickTurnMultiplier,
    private val slowMultiplier: Double = kDefaultSlowMultiplier
): DriveHelper() {

    private fun normalizeDeadband(value: Double, deadband: Double): Double {
        return if (value.absoluteValue >= deadband) (1 + deadband) * value - deadband else 0.0
    }

    override fun output(): DriveSignal {
        var currentThrottle = throttle()
        var currentTurn = wheel() * if (quickTurn()) 1.0 else quickTurnMultiplier
        currentThrottle = normalizeDeadband(currentThrottle, deadband)
        currentTurn = normalizeDeadband(currentTurn, deadband)
        val currentSlow = if (slow()) slowMultiplier else 1.0
        val left = currentSlow * (currentThrottle - currentTurn)
        val right = currentSlow * (currentThrottle + currentTurn)
        return DriveSignal(left, right)
    }

    companion object {
        const val kDefaultDeadband = 0.02
        const val kDefaultQuickTurnMultiplier = 0.4
        const val kDefaultSlowMultiplier = 0.4
    }




}
