package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.DriveConstants

import org.team5419.frc2020.StorageConstants

object Storage : Subsystem("Storage") {
    //check second parameter
    private val loadMotor = BerkeliumSRX(StorageConstants.kLoaderPort, DriveConstants.kNativeGearboxConversion)
    private val ballInputMotor = BerkeliumSRX(StorageConstants.kBallInputPort, DriveConstants.kNativeGearboxConversion)
    private var mRotateS: Boolean = false;
    private var mRotateH: Boolean = false;
    private val kstoragePercent = StorageConstants.storagePercent;
    private val kHandPercent = StorageConstants.handPercent;


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
    public fun toggleStorage(){
        if (mRotateS){
            turnOff();
        }
        else{
            turnOn();
        }
    }

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

    public fun ballInput(){ //takes ball from storage and puts it in shooger


    }



}
