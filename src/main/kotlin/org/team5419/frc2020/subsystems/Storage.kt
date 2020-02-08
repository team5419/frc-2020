package org.team5419.frc2020.subsystems


import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.fault.math.units.native.NativeUnitRotationModel
import org.team5419.fault.math.units.native.nativeUnits
import org.team5419.frc2020.StorageConstants

import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.seconds
import org.team5419.fault.math.units.Second

import edu.wpi.first.wpilibj.Timer
import com.ctre.phoenix.motorcontrol.NeutralMode
import edu.wpi.first.wpilibj.AnalogInput

//import com.revrobotics.Rev2mDistanceSensor.Port;

@SuppressWarnings("TooManyFunctions")
object Storage : Subsystem("Storage") {
    //check second parameter
    private val feederModel = NativeUnitRotationModel(4096.nativeUnits)
    private val hopperModel = NativeUnitRotationModel(4096.nativeUnits)
    private val feeder = BerkeliumSRX(StorageConstants.FeederPort, feederModel)
    private val hopper = BerkeliumSRX(StorageConstants.HopperPort, hopperModel)

    private var distanceSensor: AnalogInput = AnalogInput(1)
    // private var range: Double = 0.0
    private fun hasBall(): Boolean = false
    private val timer = Timer()

    init {
        feeder.talonSRX.setInverted(true)
        feeder.talonSRX.setNeutralMode(NeutralMode.Brake)
        hopper.talonSRX.setInverted(true)
        timer.start()
    }

    public var state: State = State.DISABLED
        set(target: State){
            target.config()
            field = target
        }

    public enum class State(val config: () -> Unit) {
        DISABLED({
            powerHopper(0.0)
            powerFeeder(0.0)
        }),
        PASSIVE({
            powerHopper(StorageConstants.HopperLazyPercent)
            periodic()
        }),
        ENABLED({
            powerHopper(StorageConstants.HopperPercent)
            periodic()
        })
    }

    fun powerHopper(percent: Double) = hopper.setPercent(percent)
    fun powerFeeder(percent: Double) = feeder.setPercent(percent)

    @SuppressWarnings("ComplexMethod")
    override public fun periodic(){
        println("feeder amperage: ${feeder.talonSRX.getOutputCurrent()}")
        when(state){
            State.PASSIVE -> {
                powerFeeder(0.0)
                println("disabled")

                // powerFeeder(if (hasBall()) 0.0 else StorageConstants.FeederLazyPercent)
            }
            State.ENABLED -> {
                if(Shooger.isHungry()){
                    println("enabled")
                    powerFeeder(1.0)
                }
                else {
                    powerFeeder(0.0)
                    println("disabled")

                }
                // if(timer.get() < 0.3 || !Shooger.isHungry()){
                //     if(!hasBall()) { powerFeeder(StorageConstants.FeederLazyPercent) }
                //     else { powerFeeder(0.0) }
                // }
                // else if(Shooger.isHungry()){
                //     powerFeeder(1.0)
                //     timer.reset()
                // }
            }
            State.DISABLED -> {
                powerFeeder(0.0)
            }
        }
    }
}
