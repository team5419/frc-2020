package org.team5419.frc2020.subsystems


import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.DriveConstants
import org.team5419.frc2020.StorageConstants


import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.seconds
import org.team5419.fault.math.units.Second
import edu.wpi.first.wpilibj.Timer

import edu.wpi.first.wpilibj.AnalogInput

//import com.revrobotics.Rev2mDistanceSensor.Port;

@SuppressWarnings("TooManyFunctions")
object Storage : Subsystem("Storage") {
    //check second parameter
    private val loadMotor = BerkeliumSRX(StorageConstants.kLoaderPort, DriveConstants.kNativeGearboxConversion)
    private val ballInputMotor = BerkeliumSRX(StorageConstants.kBallInputPort, DriveConstants.kNativeGearboxConversion)

    private var mRotateS: Boolean = false;
    private var mRotateH: Boolean = false;

    private val kPassiveStorgPerc = StorageConstants.passiveStoragePercent;
    private val kstoragePercent = StorageConstants.storagePercent;
    private val kHandPercent = StorageConstants.handPercent;


    private var distanceSensor: AnalogInput = AnalogInput(1)
    private var range: Double  = 0.0



    public enum class StorageMode(val num : Number) {
        ROTATION(kstoragePercent),
        PASSIVEROTATION(kPassiveStorgPerc),
        NOROTATION(0.0)
    }

   public enum class HandMode(val num : Number) {
        ROTATION(kHandPercent),
        NOROTATION(0.0)
        PASSIVELOADING(0)
    }

    init{
      private var handMode: HandMode = NOROTATION;
      private var storageMode: StorageMode = NOROTATION;

    }

    //Storage
    public fun storagerotation() {

    }

    public fun turnOff(){
        mRotateS = false
        loadMotor.setPercent(0.0);

    }
    public fun turnOn(){
        mRotateS = true
        loadMotor.setPercent(kstoragePercent);

    }
    public fun turnOnPassive(){
        mRotateS = true
        loadMotor.setPercent(kPassiveStorgPerc);

    }
    public fun toggleStorage(){
        if (mRotateS){
            turnOff();
        }
        else{
            turnOn();
        }
    }


//Hand
    public fun startLoading(){
        mRotateH = true
        ballInputMotor.setPercent(0.0);

    }
    public fun stopLoading(){
        mRotateH = false
        ballInputMotor.setPercent(kHandPercent);

    }

    public fun toggleLoading(){
        if (mRotateH){
            stopLoading();

        }
        else{
            startLoading();
        }

    }

    public fun passiveLoading(){ //maintains one ball near the shooter to store a max of 5 balls
        mRotateH = true
        range = (5.0*(distanceSensor.getVoltage()/0.004883))
        if(range <= 152.0 ){ /* > */ //152.0 is in mm !!find the actual length in mm
            ballInputMotor.setPercent(0.0);

        }
        else{
            ballInputMotor.setPercent(10.0);
        }


    }

    public fun ballInput(){ //takes ball from storage and puts it in shooger (1 at a time)
    }


    /////
    startLoading()
    stopLoading()
    /////

    /////////////////
    public fun sTurnOn(){storageMode = StorageMode.NOROTATION;}
    public fun sTurnPassive(){storageMode = StorageMode.PASSIVEROTATION;}
    public fun sTurnOff(){storageMode = StorageMode.ROTATION;}
    public fun hTurnOn(){handMode = HandMode.ROTATION};
    public fun hTurnPassive(){handMode = HandMode.PASSIVELOADING};
    public fun hTurnOff(){handMode = HandMode.NOROTATION};
    //public fun hToggle(){i}

    @SuppressWarnings("ComplexMethod")
    override public fun periodic(){


        if (handMode == HandMode.NOROTATION){
            stopLoading();
        }
        else if (handMode == HandMode.ROTATION){
           startLoading();
        }
        else if (handMode == HandMode.PASSIVELOADING){
           passiveLoading();
        }

        if (storageMode == StorageMode.PASSIVEROTATION){
            turnOnPassive()
        }
        else if (storageMode == StorageMode.NOROTATION){
            turnOff();
        }
        else if (storageMode == StorageMode.ROTATION){
            turnOn();
        }

    }



}
