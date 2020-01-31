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

object Shooger : Subsystem("Shooger") {

    private val masterMotor = TalonSRX(ShoogerConstants.kMasterPort)
    private val slaveMotor1 = VictorSPX(ShoogerConstants.kSlavePort1)
    private val slaveMotor2 = VictorSPX(ShoogerConstants.kSlavePort2)
    private val feeder = TalonSRX(5)
    private val hopper = TalonSRX(3)

    public var enableFeeder = true
    public var feederPercent = 0.0
    public var enableHopper = true
    public var hopperPercent = 0.0



    public val flyWheelVelocity
        get() = masterMotor.getSelectedSensorVelocity(0) / 4096.0 * 10.0 * 60
        //  get() = ShoogerConstants.flywheel.fromNativeUnitVelocity(masterMotor.encoder.rawVelocity)

    init{
        masterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
        masterMotor.setSensorPhase(true)

        slaveMotor1.follow(masterMotor)
        slaveMotor2.follow(masterMotor)

        masterMotor.setInverted(true)
        slaveMotor1.setInverted(true)
        slaveMotor2.setInverted(true)

        masterMotor.apply {
            config_kP(0, 0.2, 0)
            config_kI(0, 0.0, 0)
            config_kD(0, 0.0, 0)
            config_kF(0, 0.07, 0)
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

        hopper.setInverted(true)
        feeder.setInverted(true)
    }

    private fun calculateSetpoint(velocity : Double) : Double {
        return velocity * 4096.0 / 600.0
    }

    public fun shoog (shoogVelocity : Double) {
        val setpoint = calculateSetpoint(shoogVelocity)
        masterMotor.set(ControlMode.Velocity, setpoint)
        println(masterMotor.getSelectedSensorVelocity(0).toDouble() * 600.0 / 4096.0) /* * 10.0 / 4096.0) */
    }

    public fun powerFeeder(percent : Double){
        feeder.set(ControlMode.PercentOutput, percent)
    }
    public fun powerHopper(percent: Double){
        hopper.set(ControlMode.PercentOutput, percent)
    }

    override fun periodic(){
        // hopper.set(ControlMode.PercentOutput, if (enableHopper) hopperPercent else 0.0)
        // feeder.set(ControlMode.PercentOutput, if (enableFeeder) feederPercent else 0.0)
    }

    // public fun setPercent (percent: Double) {
    //     val velocity = (ShoogerConstants.kMaxVelocity - ShoogerConstants.kMinVelocity) * percent + ShoogerConstants.kMinVelocity
    //     shoog(velocity)
    // }
}
