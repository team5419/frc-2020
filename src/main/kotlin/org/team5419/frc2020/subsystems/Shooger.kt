package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.operations.*
import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.units.*
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.fault.hardware.ctre.BerkeliumSPX
import edu.wpi.first.networktables.NetworkTableEntry
import org.team5419.frc2020.ShoogerConstants
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.networktables.EntryListenerFlags
import org.team5419.frc2020.HoodConstants
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.AnalogInput
import com.ctre.phoenix.motorcontrol.NeutralMode

@Suppress("TooManyFunctions")
object Shooger : Subsystem("Shooger") {

    // fly wheel motors

    val shoogerModel = NativeUnitRotationModel(ShoogerConstants.TicksPerRotation)

    val masterMotor = TalonSRX(ShoogerConstants.MasterPort)
    val slaveMotor1 = VictorSPX(ShoogerConstants.SlavePort1)
    val slaveMotor2 = VictorSPX(ShoogerConstants.SlavePort2)

    init {
        masterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
        masterMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 1, 0)
        masterMotor.setSensorPhase(true)

        slaveMotor1.follow(masterMotor)
        slaveMotor2.follow(masterMotor)

        masterMotor.setInverted(true)
        slaveMotor1.setInverted(false)
        slaveMotor2.setInverted(false)

        masterMotor.setNeutralMode(NeutralMode.Coast)
        slaveMotor1.setNeutralMode(NeutralMode.Coast)
        slaveMotor2.setNeutralMode(NeutralMode.Coast)

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

    // feeder, hopper and hood

    private val feeder = TalonSRX(ShoogerConstants.kFeederPort)
    private val hopper = TalonSRX(ShoogerConstants.kHopperPort)
    private val hood = TalonSRX(HoodConstants.kPort)

    init {
        feeder.setInverted(true)
        feeder.setNeutralMode(NeutralMode.Brake)

        hopper.setInverted(true)

        // hood

        hood.apply {
            setNeutralMode(NeutralMode.Brake)
            config_kP( 0, HoodConstants.PID.P, 0)
        }
    }

    // default settings

    var targetVelocity = ShoogerConstants.TargetVelocity.value
    var hopperPercent = ShoogerConstants.HopperPercent
    var feederPercent = ShoogerConstants.FeederPercent
    var hopperLazyPercent = ShoogerConstants.HopperLazyPercent
    // Shuffleboard

    public val tabName = "Shooger"

    public val tab: ShuffleboardTab
    public val shooterVelocityEntry : NetworkTableEntry
    public val hopperPercentEntry : NetworkTableEntry
    public val hopperLazyPercentEntry : NetworkTableEntry
    public val feederPercentEntry : NetworkTableEntry
    public val feedingEnabledEntry : NetworkTableEntry
    // public val bangBangEntry : NetworkTableEntry

    init {
        tab = Shuffleboard.getTab(tabName)

        shooterVelocityEntry = tab.add("Target Velocity", targetVelocity).getEntry()
        shooterVelocityEntry.setPersistent()
        shooterVelocityEntry.addListener( { event ->
            targetVelocity = if(event.value.isDouble()) event.value.getDouble() else targetVelocity
            println("Updated Target Velocity: ${targetVelocity}")
        }, EntryListenerFlags.kUpdate)

        feederPercentEntry = tab.add("Feeder Percent", feederPercent).getEntry()
        hopperPercentEntry = tab.add("Hopper Percent", hopperPercent).getEntry()
        hopperLazyPercentEntry = tab.add("Hopper Lazy Percent", hopperLazyPercent).getEntry()
        feedingEnabledEntry = tab.add("Feeding Enabled", true).withWidget(BuiltInWidgets.kBooleanBox).getEntry()
        // bangBangEntry = tab.add("Bang Bang Toggle", true).withWidget(BuiltInWidgets.kBooleanBox).getEntry()

        feederPercentEntry.setPersistent()
        hopperPercentEntry.setPersistent()
        hopperLazyPercentEntry.setPersistent()

        feederPercentEntry.addListener(
            { event -> feederPercent = event.value.getDouble() },
            EntryListenerFlags.kUpdate
        )

        hopperPercentEntry.addListener(
            { event -> hopperPercent = event.value.getDouble() },
            EntryListenerFlags.kUpdate
        )

        hopperLazyPercentEntry.addListener(
            { event -> hopperLazyPercent = event.value.getDouble() },
            EntryListenerFlags.kUpdate
        )

        feedingEnabledEntry.addListener(
            { event -> feedingEnabled = event.value.getBoolean() },
            EntryListenerFlags.kUpdate
        )

        tab.addNumber("Real Velocity", { Shooger.flyWheelVelocity })
        tab.addNumber("Real Acceleration", { Shooger.flyWheelAcceleration })
    }

    // gettes

    public val flyWheelVelocity
        get() = masterMotor.getSelectedSensorVelocity(0) / 4096.0 * 10.0 * 60

    public val amperage
        get() = masterMotor.getStatorCurrent()

    public val voltage
        get() = masterMotor.getMotorOutputVoltage()

    private val accelTimer = Timer()

    // vars

    private var feedingEnabled = true
    private var lastVelocity = 0.0
    public var flyWheelAcceleration = 0.0 // RPM/s
    private var setpointVelocity = 0.0
    var setpoint = 0.0
    var bangBang = false

    // funcs

    private fun calculateSetpoint(velocity : Double) : Double {
        return velocity * 4096.0 / 600.0
    }

    public fun toogleBrakeMode(isEnabled: Boolean){
        masterMotor.setNeutralMode(if (isEnabled) NeutralMode.Brake else NeutralMode.Coast)
        slaveMotor1.setNeutralMode(if (isEnabled) NeutralMode.Brake else NeutralMode.Coast)
        slaveMotor2.setNeutralMode(if (isEnabled) NeutralMode.Brake else NeutralMode.Coast)
    }

    public fun shoog(
        shoogVelocity: Double = shooterVelocityEntry.getDouble(targetVelocity),
        useBangBang: Boolean = false
    ) {
        setpointVelocity = shoogVelocity
        setpoint = calculateSetpoint(shoogVelocity)
        bangBang = useBangBang

        if (!bangBang) {
            masterMotor.set(ControlMode.Velocity, setpoint)
        }
    }

    public fun start() {
        powerHopper(1.0)
    }

    public fun stop() {
        setpoint = 0.0

        powerShooger(0.0)
        powerFeeder(0.0)
        powerHopper(0.0)
    }

    public fun powerShooger(percent: Double) {
        masterMotor.set(ControlMode.PercentOutput, percent)
    }

    public fun powerFeeder(percent : Double) {
        feeder.set(ControlMode.PercentOutput, percent)
    }

    public fun powerHopper(percent: Double) {
        hopper.set(ControlMode.PercentOutput, percent)
    }

    public fun powerHood(percent: Double){
        hood.set(ControlMode.PercentOutput, percent)
    }

    public fun enableFeeding(bool: Boolean) {
        feedingEnabled = bool
    }

    private fun recalculateAcceleration() {
        val time = accelTimer.get()
        accelTimer.stop()
        accelTimer.reset()
        val velocity = flyWheelVelocity
        if (time == 0.0) {
            lastVelocity = velocity
            accelTimer.start()
            return
        }
        flyWheelAcceleration = (velocity - lastVelocity) / time
        lastVelocity = velocity
    }

    override fun periodic() {
        feederPercent = feederPercentEntry.getDouble(feederPercent)
        hopperPercent = hopperPercentEntry.getDouble(hopperPercent)
        hopperLazyPercent = hopperLazyPercentEntry.getDouble(hopperLazyPercent)

        recalculateAcceleration()
        println()

        if (setpoint == 0.0) {
            return
        }

        if (bangBang) {
            if (setpointVelocity <= flyWheelVelocity + 10) {
                powerShooger(1.0)
            } else {
                powerShooger(0.0)
            }
        }

        if(feedingEnabled && flyWheelVelocity >= setpointVelocity - 150) {
            powerFeeder(feederPercent)
            powerHopper(hopperPercent)
        } else {
            powerFeeder(0.0)
            powerHopper(hopperLazyPercent)
        }

        println(masterMotor.getSelectedSensorPosition(1))
    }
}
