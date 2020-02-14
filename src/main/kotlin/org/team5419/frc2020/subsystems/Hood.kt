package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.HoodConstants
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.ControlMode
import org.team5419.fault.math.units.native.NativeUnitRotationModel

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import kotlin.math.PI
import edu.wpi.first.wpilibj.Timer
import com.ctre.phoenix.motorcontrol.NeutralMode

object Hood : Subsystem("Hood") {
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

            configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)

            setSensorPhase(true)
            setInverted(true)

            setNeutralMode(NeutralMode.Brake)
        }

    // gettes

    private val hoodPos
        get() = masterMotor.getSelectedSensorPosition(0) / HoodConstants.TicksPerRotation

    public val hoodAngle
        get(): Double = hoodPos * HoodConstants.GearRatio * (2 * PI)

    fun setAngle(angle: Double) {
        val ticks = angle / (2 * PI) / HoodConstants.GearRatio * HoodConstants.TicksPerRotation

        masterMotor.set(ControlMode.Position, ticks)
    }
}
