package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.HoodConstants
import org.team5419.frc2020.tab
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.ControlMode
import org.team5419.fault.math.units.native.NativeUnitRotationModel
import edu.wpi.first.wpilibj.shuffleboard.*
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import kotlin.math.PI
import edu.wpi.first.wpilibj.Timer
import com.ctre.phoenix.motorcontrol.NeutralMode
import edu.wpi.first.networktables.EntryListenerFlags
import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*

object Hood : Subsystem("Hood") {
    public val angleOffset = 2253

    private val nativeUnitsToAngle = HoodConstants.GearRatio / HoodConstants.TicksPerRotation * (2 * PI)

    private val masterMotor = TalonSRX(HoodConstants.HoodPort)
        .apply {
            // primary PID constants
            config_kP(0, HoodConstants.PID.P, 0)
            config_kI(0, HoodConstants.PID.I, 0)
            config_kD(0, HoodConstants.PID.D, 0)
            // make sure it dosent go to fast
            configClosedLoopPeakOutput(0, HoodConstants.MaxSpeed, 0)
            // limit the current to not brown out
            configPeakCurrentLimit(40)
            configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute)
            setSensorPhase(true)
            setInverted(false)
            setNeutralMode(NeutralMode.Brake)
            configForwardSoftLimitThreshold( angleToNativeUnits( HoodConstants.MaxAngle ).toInt(), 0)
            configForwardSoftLimitEnable(true, 0)
            configReverseSoftLimitThreshold( angleToNativeUnits( 0.0 ).toInt(), 0)
            configReverseSoftLimitEnable(true, 0)
        }

    fun angleToNativeUnits(angle: Double) =
        (angle / nativeUnitsToAngle) - angleOffset

    public val hoodAngle
        get() = (masterMotor.getSelectedSensorPosition(0) + angleOffset) * nativeUnitsToAngle

    // shuffleboard

    public val tabName = "Hood"

    init {
        tab.addNumber("Angle", { hoodAngle })
        tab.addNumber("Error", { masterMotor.getClosedLoopError(0).toDouble() })
        tab.addNumber("Position", {masterMotor.getSelectedSensorPosition(0).toDouble()})
        val gotoentry = tab.add("Go to angle", 0.0).getEntry()

        gotoentry.addListener( { event ->
            if (event.value.isDouble()) println( angleToNativeUnits( event.value.getDouble() ) )
            // if (event.value.isDouble()) setAngle( event.value.getDouble() )
        }, EntryListenerFlags.kUpdate)
    }

    // gettes

    public enum class HoodPosititions(public val angle: Double) {
        FAR(15.0),
        CLOSE(5.0),
        RETRACT(0.0)
    }

    fun goto(angle: HoodPosititions) {
        goto( angle.angle )
    }

    fun goto(angle: Double) {
        if (angle < 0.0 || angle > HoodConstants.MaxAngle) return
        val ticks = angleToNativeUnits(angle)

        masterMotor.set(ControlMode.Position, ticks)
    }
}
