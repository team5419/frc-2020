package org.team5419.frc2020.subsystems

import org.team5419.frc2020.subsystems.StorageMode
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

    val shoogerModel = NativeUnitRotationModel(ShoogerConstants.TicksPerRotation)

    val masterMotor = TalonSRX(ShoogerConstants.MasterPort)
        .apply {
            setNeutralMode(NeutralMode.Coast)
            setSensorPhase(false)
            setInverted(false)

            configPeakCurrentLimit(40)

            // primary PID constants
            config_kP(0, 0.3, 0)
            config_kI(0, 0.0, 0)
            config_kD(0, 0.5, 0)
            config_kF(0, 0.06, 0)

            selectProfileSlot(0, 0)

            configClosedLoopPeakOutput(0, 1.0, 0)
            configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
            config_IntegralZone(0, 0, 0)
            configClosedLoopPeriod(0, 1)

            setSelectedSensorPosition(0, 0, 0)

            // seconday PID slot
            config_kP(1, 0.0, 0)
            config_kI(1, 0.0, 0)
            config_kD(1, 0.0, 0)
            config_kF(1, 0.0, 0)

            selectProfileSlot(1, 1)

            configClosedLoopPeakOutput(1, 0.0, 0)
            configSelectedFeedbackSensor(FeedbackDevice.Analog, 1, 0)
            config_IntegralZone(1, 0, 0)
            configClosedLoopPeriod(1, 1)
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

    // hood

    private val hood = TalonSRX(HoodConstants.HoodPort)
        .apply {
            setNeutralMode(NeutralMode.Brake)

            config_kP(0, HoodConstants.PID.P, 0)
            config_kI(0, HoodConstants.PID.I, 0)
            config_kD(0, HoodConstants.PID.D, 0)
        }

    // vars

    private var lastVelocity = 0.0
    private var setpointVelocity = 0.0
    private var setpoint = 0.0
    private var bangBang = true

    var targetVelocity = ShoogerConstants.TargetVelocity.value

    // shuffleboard

    public val tabName = "Shooger"
    public val tab: ShuffleboardTab

    init {
        tab = Shuffleboard.getTab(tabName)

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

    public val amperage
        get() = masterMotor.getStatorCurrent()

    public val voltage
        get() = masterMotor.getMotorOutputVoltage()

    public val analogValue
        get() = masterMotor.getSelectedSensorPosition(1)

    // public api //

    public fun isHungry(): Boolean = flyWheelVelocity >= setpointVelocity - 150

    public fun toogleBrakeMode(isEnabled: Boolean) {
        masterMotor.setNeutralMode( if (isEnabled) NeutralMode.Brake else NeutralMode.Coast )
        slaveMotor1.setNeutralMode( if (isEnabled) NeutralMode.Brake else NeutralMode.Coast )
        slaveMotor2.setNeutralMode( if (isEnabled) NeutralMode.Brake else NeutralMode.Coast )
    }

    public fun shoog(shoogVelocity: Double = targetVelocity, useBangBang: Boolean = false) {
        if ( shoogVelocity == setpointVelocity ) return

        setpointVelocity = shoogVelocity

        setpoint = calculateSetpoint(shoogVelocity)

        bangBang = useBangBang

        if (!bangBang) {
            masterMotor.set(ControlMode.Velocity, setpoint)
        }
    }

    public fun stop() {
        setpoint = 0.0
        setpointVelocity = 0.0

        powerShooger(0.0)
        Storage.mode = StorageMode.OFF
    }

    // private api //

    private fun calculateSetpoint(velocity : Double) : Double {
        return velocity * 4096.0 / 600.0
    }

    private fun powerShooger(percent: Double){
        masterMotor.set(ControlMode.PercentOutput, percent)

    }

    // subsystem functions

    fun reset() {
        stop()
    }

    override fun autoReset() = reset()
    override fun teleopReset() = reset()

    override fun periodic() {
        if (setpoint == 0.0) return

        if ( isHungry() ) {
            Storage.mode = StorageMode.LOAD
        } else {
            Storage.mode = StorageMode.PASSIVE
        }

        if (bangBang) {
            if (setpointVelocity >= flyWheelVelocity) {
                masterMotor.set(ControlMode.PercentOutput, 1.0)
            } else {
                masterMotor.set(ControlMode.PercentOutput, 0.0)
            }
        }
    }
}
