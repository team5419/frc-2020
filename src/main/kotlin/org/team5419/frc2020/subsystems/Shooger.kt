package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.operations.*
import org.team5419.fault.math.units.*
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.fault.hardware.ctre.BerkeliumSPX
import org.team5419.frc2020.ShoogerConstants
import org.team5419.frc2020.HoodConstants
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import edu.wpi.first.wpilibj.Timer
import com.ctre.phoenix.motorcontrol.NeutralMode

object Shooger : Subsystem("Shooger") {

    private val masterMotor = TalonSRX(ShoogerConstants.kMasterPort)
    private val slaveMotor1 = VictorSPX(ShoogerConstants.kSlavePort1)
    private val slaveMotor2 = VictorSPX(ShoogerConstants.kSlavePort2)
    private val feeder = TalonSRX(5)
    private val hopper = TalonSRX(3)

    private var enableFeeder = true

    private var setpointVelocity = 0.0



    public val flyWheelVelocity
        get() = masterMotor.getSelectedSensorVelocity(0) / 4096.0 * 10.0 * 60
        //  get() = ShoogerConstants.flywheel.fromNativeUnitVelocity(masterMotor.encoder.rawVelocity)
    private val accelTimer = Timer()
    private var lastVelocity = 0.0
    public var flyWheelAcceleration = 0.0 // RPM/s
    public val amperage
        get() = masterMotor.getStatorCurrent()
    public val voltage
        get() = masterMotor.getMotorOutputVoltage()

    init{
        masterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
        masterMotor.setSensorPhase(true)

        slaveMotor1.follow(masterMotor)
        slaveMotor2.follow(masterMotor)

        masterMotor.setInverted(true)
        slaveMotor1.setInverted(true)
        slaveMotor2.setInverted(true)

        masterMotor.setNeutralMode(NeutralMode.Brake)
        slaveMotor1.setNeutralMode(NeutralMode.Brake)
        slaveMotor2.setNeutralMode(NeutralMode.Brake)

        masterMotor.apply {
            config_kP(0, 0.3, 0)
            config_kI(0, 0.0, 0)
            config_kD(0, 0.7, 0)
            config_kF(0, 0.05, 0)
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
        }

        hopper.setInverted(false)
        feeder.setInverted(true)
        feeder.setNeutralMode(NeutralMode.Brake)
    }

    private fun calculateSetpoint(velocity : Double) : Double {
        return velocity * 4096.0 / 600.0
    }

    public fun shoog (shoogVelocity : Double) {
        setpointVelocity = shoogVelocity
        val setpoint = calculateSetpoint(shoogVelocity)
        masterMotor.set(ControlMode.Velocity, setpoint)
        // println(masterMotor.getSelectedSensorVelocity(0).toDouble() * 600.0 / 4096.0) /* * 10.0 / 4096.0) */
    }

    public fun shoogPower(percent: Double) {
        masterMotor.set(ControlMode.PercentOutput, percent)
        // println(masterMotor.getSelectedSensorVelocity(0).toDouble() * 600.0 / 4096.0)
    }

    public fun powerFeeder(percent : Double){
        feeder.set(ControlMode.PercentOutput, percent)
    }
    public fun powerHopper(percent: Double){
        hopper.set(ControlMode.PercentOutput, percent)
    }

    public fun enableFeeder(bool: Boolean){
        enableFeeder = bool
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
        recalculateAcceleration()
        // println(flyWheelAcceleration)
        println(setpointVelocity)
        if(enableFeeder && flyWheelVelocity >= setpointVelocity) {
            powerFeeder(1.0)
        } else {
            powerFeeder(0.0)
        }
    }
}
