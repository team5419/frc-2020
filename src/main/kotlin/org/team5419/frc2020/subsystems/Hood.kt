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

    val hoodModel = HoodConstants.hood;

    private val masterMotor = TalonSRX(HoodConstants.HoodPort)

    init{
        masterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
        masterMotor.setSensorPhase(true)

        masterMotor.setInverted(true)

        masterMotor.setNeutralMode(NeutralMode.Coast)
        masterMotor.apply {
            // primary PID constants
            config_kP(0, 0.3, 0)
            config_kI(0, 0.0, 0)
            config_kD(0, 0.5, 0)
            config_kF(0, 0.06, 0)

            // seconday PID slot, not used
            config_kP(1, 0.0, 0)
            config_kI(1, 0.0, 0)
            config_kD(1, 0.0, 0)
            config_kF(1, 0.0, 0)

            config_IntegralZone(0, 0, 0)
            configClosedLoopPeakOutput(0, 1.0, 0)
            config_IntegralZone(1, 0, 0)
            configClosedLoopPeakOutput(1, 0.0, 0)
            selectProfileSlot(0, 0)
            selectProfileSlot(1, 1)
            configClosedLoopPeriod(0, 1)
            configClosedLoopPeriod(1, 1)
            setSelectedSensorPosition(0, 0, 0)

            // limit the current to not brown out
            configPeakCurrentLimit(40)
        }

    }
    // gettes

    public val hoodPos
        get() = masterMotor.getSelectedSensorPosition(0) / 4096.0

    public val hoodDeg
        get() = ((2 * PI)/ (hoodPos * (HoodConstants.gearRatio))) * (180/PI)


    public val amperage
        get() = masterMotor.getStatorCurrent()

    public val voltage
        get() = masterMotor.getMotorOutputVoltage()

    private val accelTimer = Timer()


    private fun firstpos(angle: Double){ // in radians

        val ticks = ( (8 * PI) * (180/ (hoodDeg * PI)) ) ;

        masterMotor.set(ControlMode.Position, ticks)
    }

    private fun secondpos(){

    }


    override fun periodic() {


    }


}
