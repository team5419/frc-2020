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

    enum class StorageMode() { LOAD, PASSIVE, REVERSE, OFF }

    public var mode = StorageMode.OFF
        set(mode: StorageMode) {
            if (mode == field) return

            when (mode) {
                StorageMode.LOAD -> {
                    hopper.set( ControlMode.PercentOutput, hopperPercent )
                    feeder.set( ControlMode.PercentOutput, feederPercent )
                }

                StorageMode.PASSIVE -> {
                    hopper.set( ControlMode.PercentOutput, hopperLazyPercent )
                    feeder.set( ControlMode.PercentOutput, 0.0 )
                }

                StorageMode.OFF -> {
                    hopper.set( ControlMode.PercentOutput, 0.0 )
                    feeder.set( ControlMode.PercentOutput, 0.0 )
                }

                StorageMode.REVERSE -> {
                    hopper.set( ControlMode.PercentOutput, -0.5 )
                    feeder.set( ControlMode.PercentOutput, -0.5 )
                }
            }

            lastMode = field

            field = mode
        }

    var lastMode = mode

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

    // reverse

    public fun reverse() {
        mode = StorageMode.REVERSE
    }

    public fun reset() {
        if (mode == StorageMode.REVERSE) {
            mode = lastMode
        }
    }

    // default settings

    private var hopperPercent = StorageConstants.HopperPercent
    private var feederPercent = StorageConstants.FeederPercent

    private var feederLazyPercent = StorageConstants.FeederLazyPercent
    private var hopperLazyPercent = StorageConstants.HopperLazyPercent

    // distance sensor to find balls

    private val isLoadedBall: Boolean
        get() = -feeder.getSelectedSensorPosition(0) >= StorageConstants.SensorThreshold

    init {
        tab.addNumber("feeder amperage", { feeder.getStatorCurrent() })
        tab.addNumber("IR pos", { -feeder.getSelectedSensorPosition(0).toDouble() })
        tab.addBoolean("IR Sensor", { isLoadedBall })
    }

    // subsystem functions

    @Suppress("ComplexMethod")
    override public fun periodic() {
        // if its reversed then we want to overide the any outher logic
        if (mode == StorageMode.REVERSE ) {
            return
        }

        // figure out what mode should we be in?
        if ( Shooger.isHungry() ) {
            mode = StorageMode.LOAD
        } else if ( Intake.isActive() || Shooger.isActive() ) {
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
