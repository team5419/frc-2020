package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.operations.*
import org.team5419.fault.math.units.*
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.fault.hardware.ctre.BerkeliumSPX
import edu.wpi.first.networktables.NetworkTableEntry
import org.team5419.frc2020.ShoogerConstants
import edu.wpi.first.wpilibj.shuffleboard.*
import org.team5419.frc2020.HoodConstants
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import edu.wpi.first.wpilibj.Timer
import com.ctre.phoenix.motorcontrol.NeutralMode

object Shooger : Subsystem("Shooger") {

    // fly wheel motors\
    private val masterMotor = TalonSRX(ShoogerConstants.kMasterPort)
    private val slaveMotor1 = VictorSPX(ShoogerConstants.kSlavePort1)
    private val slaveMotor2 = VictorSPX(ShoogerConstants.kSlavePort2)

    init {
        masterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
        masterMotor.setSensorPhase(true)

        slaveMotor1.follow(masterMotor)
        slaveMotor2.follow(masterMotor)

        masterMotor.setInverted(true)
        slaveMotor1.setInverted(true)
        slaveMotor2.setInverted(true)

        masterMotor.setNeutralMode(NeutralMode.Coast)
        slaveMotor1.setNeutralMode(NeutralMode.Coast)
        slaveMotor2.setNeutralMode(NeutralMode.Coast)

        masterMotor.apply {
            config_kP(0, 0.5, 0)
            config_kI(0, 0.0, 0)
            config_kD(0, 0.8, 0)
            config_kF(0, 0.06, 0)

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
            configPeakCurrentLimit(40)
        }
    }

    // feeder and hopper

    private val feeder = TalonSRX(ShoogerConstants.kFeederPort)
    private val hopper = TalonSRX(ShoogerConstants.kHopperPort)

    init {
        feeder.setInverted(true)
        feeder.setNeutralMode(NeutralMode.Brake)
        hopper.setInverted(false)
    }

    // hood



    // Shuffleboard

    public val tabName = "Shooger"

    public val tab: ShuffleboardTab
    public var shooterVelocityEntry : NetworkTableEntry
    public var hopperPercentEntry : NetworkTableEntry
    public var hopperLazyPercentEntry : NetworkTableEntry
    public var feederPercentEntry : NetworkTableEntry
    public var feedingEnabledEntry : NetworkTableEntry
    public var bangBangEntry : NetworkTableEntry

    init {
        tab = Shuffleboard.getTab(tabName)

        shooterVelocityEntry = tab.add("Target Velocity", 0.0).getEntry()

        feederPercentEntry = tab.add("Feeder Percent", 0.0).getEntry()
        hopperPercentEntry = tab.add("Hopper Percent", 0.0).getEntry()
        hopperLazyPercentEntry = tab.add("Hopper Lazy Percent", 0.0).getEntry()

        feedingEnabledEntry = tab.add("Feeding Enabled", true).withWidget(BuiltInWidgets.kBooleanBox).getEntry()
        bangBangEntry = tab.add("Bang Bang Toggle", true).withWidget(BuiltInWidgets.kBooleanBox).getEntry()

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
    var hopperPercent = 0.0
    var feederPercent = 0.0
    var hopperLazyPercent = 0.0

    // actaull funcs

    private fun calculateSetpoint(velocity : Double) : Double {
        return velocity * 4096.0 / 600.0
    }

    public fun shoog(shoogVelocity : Double=shooterVelocityEntry.getDouble(0.0), useBangBang: Boolean = bangBangEntry.getBoolean(false)) {
        setpointVelocity = shoogVelocity
        setpoint = calculateSetpoint(shoogVelocity)

        bangBang = useBangBang

        if (!bangBang) {
            masterMotor.set(ControlMode.Velocity, setpoint)
        }
    }

    public fun powerShooger(percent: Double) {
        masterMotor.set(ControlMode.PercentOutput, percent)
    }

    public fun powerFeeder(percent : Double){
        feeder.set(ControlMode.PercentOutput, percent)
    }

    public fun powerHopper(percent: Double){
        hopper.set(ControlMode.PercentOutput, percent)
    }

    public fun enableFeeding(bool: Boolean){
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

    override fun periodic(){
        hopperPercent = hopperPercentEntry.getDouble(0.0)
        feederPercent = hopperPercentEntry.getDouble(0.0)
        hopperLazyPercent = hopperLazyPercentEntry.getDouble(0.0)

        recalculateAcceleration()

        if (!bangBang) {
            if (setpointVelocity <= flyWheelVelocity + 10) {
                powerShooger(1.0)
            } else {
                powerShooger(0.0)
            }
        }

        if(feedingEnabled && flyWheelVelocity >= setpointVelocity - 150) {
            powerFeeder(hopperPercent)
            powerHopper(feederPercent)
        } else {
            powerFeeder(0.0)
            powerHopper(hopperLazyPercent)
        }
    }
}
