package org.team5419.frc2020.subsystems

import org.team5419.frc2020.tab
import org.team5419.frc2020.subsystems.Storage
import org.team5419.frc2020.ShoogerConstants
import org.team5419.frc2020.HoodConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.AnalogInput
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.networktables.EntryListenerFlags
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.ControlMode

@Suppress("TooManyFunctions")
object Shooger : Subsystem("Shooger") {
    // shooger motors

    val masterMotor = TalonSRX(ShoogerConstants.MasterPort)
        .apply {
            configFactoryDefault(100)

            setNeutralMode(NeutralMode.Coast)
            setSensorPhase(false)
            setInverted(false)

            // configPeakCurrentLimit(40)

            config_kP(0, 0.3, 100)
            config_kI(0, 0.0, 100)
            config_kD(0, 0.5, 100)
            config_kF(0, 0.06, 100)

            selectProfileSlot(0, 0)

            configClosedLoopPeakOutput(0, 1.0, 100)
            configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100)

            setSelectedSensorPosition(0, 0, 100)
        }

    val slaveMotor1 = VictorSPX(ShoogerConstants.SlavePort1)
        .apply {
            follow(masterMotor)
            setInverted(true)
            setNeutralMode(NeutralMode.Coast)
        }

    val slaveMotor2 = VictorSPX(ShoogerConstants.SlavePort2)
        .apply {
            follow(masterMotor)
            setInverted(false)
            setNeutralMode(NeutralMode.Coast)
        }

    // settings

    private var targetVelocity = ShoogerConstants.TargetVelocity.value
    private var bangBang = true

    // state

    private var setpointVelocity = 0.0
    private var setpoint = 0.0
    private var active = false

    // shuffleboard

    init {
        val shooterVelocityEntry = tab.add("Target Velocity", targetVelocity).getEntry()

        targetVelocity = shooterVelocityEntry.getDouble(ShoogerConstants.TargetVelocity.value)

        shooterVelocityEntry.setPersistent()
        shooterVelocityEntry.addListener( { event ->
            targetVelocity = if (event.value.isDouble()) event.value.getDouble() else targetVelocity

            println("Updated Target Velocity: ${targetVelocity}")
        }, EntryListenerFlags.kUpdate)

        tab.addNumber("Real Velocity", { Shooger.flyWheelVelocity })
    }

    // gettes

    public val flyWheelVelocity
        get() = masterMotor.getSelectedSensorVelocity(0) / 4096.0 * 10.0 * 60

    // public api

    public fun isHungry(): Boolean = isActive() && flyWheelVelocity >= setpointVelocity - 150

    public fun isActive(): Boolean = active

    public fun shoog(active: Boolean? = null, shoogVelocity: Double = targetVelocity) {
        this.active = active ?: true

        if ( shoogVelocity == setpointVelocity ) return

        setpointVelocity = shoogVelocity

        setpoint = calculateSetpoint(shoogVelocity)

        if (!bangBang) {
            masterMotor.set(ControlMode.Velocity, setpoint)
        }
    }

    public fun stop() {
        setpoint = 0.0
        setpointVelocity = 0.0

        active = false

        powerShooger(0.0)
    }

    // private api

    private fun calculateSetpoint(velocity : Double) =
        velocity * 4096.0 / 600.0

    private fun powerShooger(percent: Double) =
        masterMotor.set(ControlMode.PercentOutput, percent)

    // subsystem functions

    fun reset() {
        stop()
    }

    override fun autoReset() = reset()
    override fun teleopReset() = reset()

    override fun periodic() {
        if (setpoint != 0.0 && bangBang) {
            if (setpointVelocity >= flyWheelVelocity) {
                masterMotor.set(ControlMode.PercentOutput, 1.0)
            } else {
                masterMotor.set(ControlMode.PercentOutput, 0.0)
            }
        }
    }
}
