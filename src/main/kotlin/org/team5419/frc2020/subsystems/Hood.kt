package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.HoodConstants
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.ControlMode
import org.team5419.fault.math.units.native.NativeUnitRotationModel

import com.ctre.phoenix.motorcontrol.FeedbackDevice

object Hood : Subsystem("Hood") {

    private val hood = TalonSRX(HoodConstants.HoodPort);

    private val masterMotor = TalonSRX(HoodConstants.HoodPort)

    init{
        masterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
        masterMotor.setSensorPhase(true)

    }


}
