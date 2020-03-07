package org.team5419.frc2020.subsystems

import org.team5419.frc2020.tab
import org.team5419.frc2020.subsystems.Storage
import org.team5419.frc2020.ShoogerConstants
import org.team5419.frc2020.HoodConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.fault.util.MovingAverageFilter
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.AnalogInput
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.networktables.EntryListenerFlags
import edu.wpi.first.networktables.EntryNotification
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration

public interface ShotSetpoint {
    public val angle: Double
    public val velocity: Double
}

public data class Setpoint(override val angle: Double, override val velocity: Double) : ShotSetpoint

@Suppress("TooManyFunctions")
object Shooger : Subsystem("Shooger") {
    // shooger motors

    val masterMotor = TalonFX(ShoogerConstants.MasterPort)
        .apply {
            configFactoryDefault(100)

            setNeutralMode(NeutralMode.Coast)
            setSensorPhase(false)
            setInverted(true)
            configSupplyCurrentLimit(SupplyCurrentLimitConfiguration(true, 40.0, 0.0, 0.0), 100)

            config_kP(0, 10000.0, 100)
            config_kI(0, 0.0, 100)
            config_kD(0, 0.0, 100)
            config_kF(0, 0.0, 100)


            config_kP(1, 0.5, 100)
            config_kI(1, 0.0, 100)
            config_kD(1, 0.0, 100)
            config_kF(1, 0.06, 100)

            selectProfileSlot(1, 0)

            // configClosedLoopPeakOutput(0, 1.0, 100)

            setSelectedSensorPosition(0, 0, 100)
            configClosedloopRamp(0.25, 100)

            configClosedLoopPeriod(0, 1, 100)
            configPeakOutputForward(1.0, 100)
            configPeakOutputReverse(1.0, 100)


        }

    val slaveMotor = TalonFX(ShoogerConstants.SlavePort)
        .apply {
            follow(masterMotor)
            setInverted(false)
            setNeutralMode(NeutralMode.Coast)
            configSupplyCurrentLimit(SupplyCurrentLimitConfiguration(true, 40.0, 0.0, 0.0), 100)
        }

    // shuffleboard

    init {
        tab.add("Target Velocity", 0)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(mapOf<String, Double>("min" to 0.0, "max" to 4800.0))
            .getEntry()
            .addListener({
                value: EntryNotification ->  if(value.value.isDouble()) setShoogerVelocity(value.value.getDouble())
                println("update target velocity: ${setpointVelocity}")
            }, EntryListenerFlags.kUpdate)
        tab.addNumber("Avg Real Velocity", { Shooger.averageVelocity })
        tab.addNumber("Real Velocity", { Shooger.flyWheelVelocity })
    }

    // state

    private var setpointVelocity = 0.0
    private var setpoint = 0.0
    private var active = false

    private val averageVelocityFilter: MovingAverageFilter

    public val averageVelocity: Double
        get() = averageVelocityFilter.average

    init{
        averageVelocityFilter = MovingAverageFilter(10)
    }

    // accesors
    private var isCap = true
    public val flyWheelVelocity
        get() = masterMotor.getSelectedSensorVelocity(0) / 2048.0 * 10.0 * 60.0 / 0.75

    public fun isHungry(): Boolean = isActive() && isSpedUp()

    public fun isSpedUp(): Boolean = setpointVelocity != 0.0 && flyWheelVelocity >= setpointVelocity - 50

    public fun shouldStopFeeding(): Boolean = setpointVelocity != 0.0 && flyWheelVelocity >= setpointVelocity - 100

    public fun isActive(): Boolean = active

    // mutators

    public fun shoog(shotSetpoint: ShotSetpoint) = shoog(shotSetpoint.velocity)

    public fun shoog(shoogVelocity: Double) {
        // full current baby, lets get this wheel spining
        // masterMotor.configSupplyCurrentLimit(SupplyCurrentLimitConfiguration(true, 40.0, 0.0, 0.0), 100)
        // its active, we want to shoot if at full speed
        active = true

        // tell it to go to target velocity
        // setShoogerVelocity(shoogVelocity)
    }

    public fun spinUp(shotSetpoint: ShotSetpoint) = spinUp(shotSetpoint.velocity)

    public fun spinUp(shoogVelocity: Double) {
        // limit the current so we dont brown out if were driving
        masterMotor.configSupplyCurrentLimit(SupplyCurrentLimitConfiguration(true, 20.0, 0.0, 0.0), 100)

        // its not active, we dont want to shoot even if at speed
        active = false

        // tell it to go to target velocity
        // setShoogerVelocity(shoogVelocity)
    }

    public fun stop() {
        // we dont want any balls to be loaded
        active = false

        // reset the setpoints, not that it matters
        // setpointVelocity = 0.0
        // setpoint = 0.0

        // turn off the flywheel
        masterMotor.set(ControlMode.PercentOutput, 0.0)
    }

    // private api

    private fun calculateSetpoint(velocity : Double) =
        velocity * 2048.0 / 600.0 * 0.75

    private fun setShoogerVelocity(shoogVelocity: Double) {
        if ( shoogVelocity == setpointVelocity ) return

        // set the setpoints to the given velocity
        setpointVelocity = shoogVelocity
        setpoint = calculateSetpoint(shoogVelocity)

        // tell the motor to go
        println(shoogVelocity)
        masterMotor.set(ControlMode.Velocity, setpoint)
    }

    // subsystem functions

    fun reset() = stop()

    override fun autoReset() = reset()
    override fun teleopReset() = reset()

    override fun periodic() {
        averageVelocityFilter += flyWheelVelocity

        if(flyWheelVelocity < 500 && !isCap) {
            masterMotor.configClosedloopRamp(0.1, 100)
            isCap = true
        } else if (flyWheelVelocity > 500 && isCap) {
            // masterMotor.configClosedloopRamp(0.0, 100)
            isCap = false
        }
        println(masterMotor.getBusVoltage())
    }
}
