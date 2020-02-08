package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.DriveConstants
import org.team5419.frc2020.StorageConstants
import org.team5419.frc2020.ShoogerConstants
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.seconds
import org.team5419.fault.math.units.Second
import edu.wpi.first.wpilibj.Timer
import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.AnalogInput
import edu.wpi.first.networktables.EntryListenerFlags
import edu.wpi.first.wpilibj.shuffleboard.*
import com.ctre.phoenix.motorcontrol.NeutralMode

//import com.revrobotics.Rev2mDistanceSensor.Port

enum class StorageMode() { LOAD, PASSIVE, OFF }

object Storage : Subsystem("Storage") {

    var mode = StorageMode.OFF
        set(mode: StorageMode) {
            if (mode == StorageMode.LOAD) {
                hopper.set( ControlMode.PercentOutput, hopperPercent )
                feeder.set( ControlMode.PercentOutput, feederPercent )
            }

            if (mode == StorageMode.PASSIVE) {
                hopper.set( ControlMode.PercentOutput, 0.0 )
                feeder.set( ControlMode.PercentOutput, hopperLazyPercent )
            }

            if (mode == StorageMode.OFF) {
                hopper.set( ControlMode.PercentOutput, 0.0 )
                feeder.set( ControlMode.PercentOutput, 0.0 )
            }

            field = mode
        }

    // private var

    private val feeder = TalonSRX(StorageConstants.FeederPort)
    private val hopper = TalonSRX(StorageConstants.HopperPort)

    init {
        feeder.setInverted(true)
        feeder.setNeutralMode(NeutralMode.Brake)

        hopper.setInverted(true)
    }

    private var hopperPercent = StorageConstants.HopperPercent
    private var feederPercent = StorageConstants.FeederPercent

    private var feederLazyPercent = feederPercent
    private var hopperLazyPercent = StorageConstants.HopperLazyPercent

    private var distanceSensor: AnalogInput = AnalogInput(1)
    private var range: Double  = 0.0

    // subsystem functions

    fun reset() {
        mode = StorageMode.OFF
    }

    override fun autoReset() = reset()
    override fun teleopReset() = reset()

    override public fun periodic() {
        range = 5.0 * distanceSensor.getVoltage() / 0.004883

        if (mode == StorageMode.PASSIVE) {
            feeder.set(
                ControlMode.PercentOutput,
                if (range <= 152.0) feederLazyPercent else 0.0
            )
        }
    }
}
