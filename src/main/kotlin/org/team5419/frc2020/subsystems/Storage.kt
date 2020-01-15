package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.DriveConstants

import org.team5419.frc2020.StorageConstants

object Storage : Subsystem("Storage") {
    //check second parameter
    private val loadMotor = BerkeliumSRX(StorageConstants.kLoaderPort, DriveConstants.kNativeGearboxConversion)
    private val ballInputMotor = BerkeliumSRX(StorageConstants.kBallInputPort, DriveConstants.kNativeGearboxConversion)



    public fun storagerotation() {


    }
    public fun takeIn(){
        loadMotor.setPercent(50.0);

    }
    public fun ballInput(){ //takes ball from storage and puts it in shooger


    }



}
