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

    private var mode = StorageMode.OFF
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

    public val feeder = TalonSRX(StorageConstants.FeederPort)
        .apply {
            setInverted(true)
            setSensorPhase(false)
            setNeutralMode(NeutralMode.Brake)
            configSelectedFeedbackSensor(FeedbackDevice.Analog)
        }

    public val hopper = TalonSRX(StorageConstants.HopperPort)
        .apply {
            setInverted(true)
        }

    // default settings

    private var hopperPercent = StorageConstants.HopperPercent
    private var feederPercent = StorageConstants.FeederPercent

    private var feederLazyPercent = 0.3
    private var hopperLazyPercent = StorageConstants.HopperLazyPercent

    // distance sensor to find balls


    private val isLoadedBall: Boolean
        get() = -feeder.getSelectedSensorPosition(0) >= StorageConstants.SensorThreshold

    init {
        tab.addNumber("feeder amperage", { feeder.getStatorCurrent() })
        tab.addNumber("IR pos", { -feeder.getSelectedSensorPosition(0).toDouble() })
        tab.addBoolean("IR Sensor", { isLoadedBall })
    }

    // reverse

    private var reverse = 0

    public fun revers() { reverse = 2 }

    // subsystem functions

    @Suppress("ComplexMethod")
    override public fun periodic() {
        // if its reversed then make it go backwards
        if (reverse == 2) {
            // make it go backwards
            feeder.set(ControlMode.PercentOutput, -0.5)
            hopper.set(ControlMode.PercentOutput, -0.5)

            // reset the reverse flag
            reverse = 1

            // we dont want to do the rest of the function
            return
        }

        if (reverse == 1) {
            feeder.set(ControlMode.PercentOutput, 0.0)
            hopper.set(ControlMode.PercentOutput, 0.0)

            reverse = 0
        }

        // figure out what mode should we be in?
        if ( Shooger.isHungry() ) {
            mode = StorageMode.LOAD
        } else if ( Intake.isActive() ) {
            mode = StorageMode.PASSIVE
        } else {
            mode = StorageMode.OFF
        }

        // do we need to partally load?
        if (mode == StorageMode.PASSIVE) {
            feeder.set(
                ControlMode.PercentOutput,
                if ( !isLoadedBall ) feederLazyPercent else 0.0
            )
        }
    }
}
