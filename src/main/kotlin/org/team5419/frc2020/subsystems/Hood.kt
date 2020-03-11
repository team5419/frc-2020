package org.team5419.frc2020.subsystems

import org.team5419.frc2020.tab
import org.team5419.frc2020.HoodConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.native.NativeUnitRotationModel
import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import kotlin.math.PI
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.networktables.EntryListenerFlags
import edu.wpi.first.networktables.EntryNotification
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.ControlMode

object Hood : Subsystem("Hood") {
    // motor

    private val hoodMotor = TalonSRX(HoodConstants.HoodPort)
        .apply {
            // reset
            configFactoryDefault(100)

            // config PID constants
            config_kP(0, HoodConstants.PID.P, 100)
            config_kI(0, HoodConstants.PID.I, 100)
            config_kD(0, HoodConstants.PID.D, 100)

            // make sure it dosent go to fast
            configClosedLoopPeakOutput(0, HoodConstants.MaxSpeed, 100)

            // limit the current to not brown out
            configPeakCurrentLimit(20, 100)

            // config the sensor and direction
            configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 100)
            setSensorPhase(false)
            setInverted(false)

            setNeutralMode(NeutralMode.Brake)

            // config the soft limits
            configForwardSoftLimitThreshold( angleToNativeUnits( HoodConstants.MaxAngle ).toInt(), 100)
            configForwardSoftLimitEnable(true, 100)
            configReverseSoftLimitThreshold( angleToNativeUnits( 0.0 ).toInt(), 100)
            configReverseSoftLimitEnable(true, 100)

            // configClosedLoopPeakOutput(0, 0.3, 100)

            // reset the sensor
            // setSelectedSensorPosition(0, 0, 100)
        }

    // hood positions

    public enum class HoodPosititions(override val angle: Double, override val velocity: Double) : ShotSetpoint {
        FAR(HoodConstants.FarHoodAngle, 5000.0),
        TRUSS(HoodConstants.TrussHoodAngle, 4700.0),
        CLOSE(HoodConstants.CloseHoodAngle, 3000.0),
        AUTO(12.5, 3000.0),
        RETRACT(0.0, 2500.0)
    }

    var mode: ShotSetpoint = HoodPosititions.RETRACT
        set(value: ShotSetpoint) {
            if (value == field) return
            if (field.angle == value.angle && field.velocity == value.velocity) return

            field = value

            goto(value.angle)
        }

    init {
        tab.addNumber("hood angle", {hoodAngle()})
        tab.addNumber("hood ticks", {hoodMotor.getSelectedSensorPosition(0).toDouble()})
        tab.addNumber("hood error", {hoodMotor.getClosedLoopError(0).toDouble()})
        // tab.add("Set Hood Pos", 0.0)
        //     .withWidget(BuiltInWidgets.kNumberSlider)
        //     .getEntry()
        //     .addListener({
        //         value: EntryNotification -> goto(value.value.getDouble())
        //     }, EntryListenerFlags.kUpdate)
    }

    // public api

    // private val nativeUnitsToAngle = 18.0 / 130.0
    fun angleToNativeUnits(angle: Double) = angle / 18.0  * (616.0 - 486.0) + 486.0

    fun hoodAngle() = (hoodMotor.getSelectedSensorPosition(0) - 486.0) / (616.0 - 486.0) * 18.0

    fun goto(angle: ShotSetpoint) {
        mode = angle
    }

    fun goto(angle: Double) {
        println("hood goto ${angle}")
        if (angle < 0.0 || angle > HoodConstants.MaxAngle) {
            println("angle out of range")

            return goto(angle.coerceIn(0.0, HoodConstants.MaxAngle))
        }

        val ticks = angleToNativeUnits(angle)

        hoodMotor.set(ControlMode.Position, ticks)
    }

    // subsystem functions

    fun reset() {
        println("hood reset")
        hoodMotor.set(ControlMode.PercentOutput, 0.0)
    }

    override fun autoReset() = reset()
    override fun teleopReset() = reset()
}
