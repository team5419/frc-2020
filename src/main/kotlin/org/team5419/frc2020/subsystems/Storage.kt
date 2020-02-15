package org.team5419.frc2020.subsystems

import org.team5419.frc2020.tab
import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.StorageConstants
import org.team5419.frc2020.ShoogerConstants
import org.team5419.fault.subsystems.Subsystem
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.AnalogInput
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.ControlMode

object Storage : Subsystem("Storage") {
    // storage mode

    enum class StorageMode() { LOAD, PASSIVE, OFF }

    var mode = StorageMode.OFF
        set(mode: StorageMode) {
            if (mode == field) return

            if (mode == StorageMode.LOAD) {
                hopper.set( ControlMode.PercentOutput, hopperPercent )
                feeder.set( ControlMode.PercentOutput, feederPercent )
            }

            if (mode == StorageMode.PASSIVE) {
                hopper.set( ControlMode.PercentOutput, hopperLazyPercent )
                feeder.set( ControlMode.PercentOutput, 0.0 )
            }

            if (mode == StorageMode.OFF) {
                hopper.set( ControlMode.PercentOutput, 0.0 )
                feeder.set( ControlMode.PercentOutput, 0.0 )
            }

            field = mode
        }

    // motors

    private val feeder = TalonSRX(StorageConstants.FeederPort)
        .apply {
            setInverted(true)
            setNeutralMode(NeutralMode.Brake)
            configSelectedFeedbackSensor(FeedbackDevice.Analog)
        }

    private val hopper = TalonSRX(StorageConstants.HopperPort)
        .apply {
            setInverted(true)
        }

    // default settings

    private var hopperPercent = StorageConstants.HopperPercent
    private var feederPercent = StorageConstants.FeederPercent

    private var feederLazyPercent = feederPercent
    private var hopperLazyPercent = StorageConstants.HopperLazyPercent

    // distance sensor to find balls

    private val isLoadedBall: Boolean
        get() = feeder.getSelectedSensorPosition(0) >= StorageConstants.SensorThreshold

    init{
        tab.addBoolean("IR Sensor", { isLoadedBall })
    }

    // reverse

    private var reverse = false

    public fun revers() = { reverse = true }

    // subsystem functions

    override public fun periodic() {
        // if its reversed then make it go backwards
        if (reverse) {
            // make it go backwards
            feeder.set(ControlMode.PercentOutput, -1.0)
            hopper.set(ControlMode.PercentOutput, -1.0)

            // reset the reverse flag
            reverse = false

            // we done need to do the rest of the function
            return
        }

        // figure out what mode should we be in?
        if ( Shooger.isHungry() ) {
            mode = StorageMode.LOAD
        } else if ( Shooger.isActive() || Intake.isActive() ) {
            mode = StorageMode.PASSIVE
        } else {
            mode = StorageMode.OFF
        }

        // do we need to partally load?
        if (mode == StorageMode.PASSIVE) {
            feeder.set(
                ControlMode.PercentOutput,
                if ( isLoadedBall ) feederLazyPercent else 0.0
            )
        }
    }
}
