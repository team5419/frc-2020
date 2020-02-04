package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.fault.hardware.ctre.CTREBerkeliumEncoder
import org.team5419.fault.math.units.native.NativeUnitRotationModel
import org.team5419.frc2020.DriveConstants
import org.team5419.frc2020.SpinConstants
import org.team5419.frc2020.input.ColorSensor
import org.team5419.frc2020.input.ColorSensor.ColorValue

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import edu.wpi.first.wpilibj.DriverStation

object Spinner : Subsystem("Spinner") {

    private val mColorSensor = ColorSensor()
    private val spinnerMotor = BerkeliumSRX(SpinConstants.kSpinPort, NativeUnitRotationModel(SpinConstants.kEncoderTicksPerRotation))
    private var rotationFinished: Boolean


    init {
        spinnerMotor.talonSRX.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
        spinnerMotor.talonSRX.setSensorPhase(true)
        spinnerMotor.brakeMode = true
        rotationFinished = false
    }

    public fun spinColorMotor() {
        if (!rotationFinished) {
            rotationControl()
        } else {
            colorControl()
        }
    }

    public fun rotationControl() {
        var encoderTicks = SpinConstants.kEncoderTicksPerRotation * 3.5
        if (spinnerMotor.talonSRX.getSelectedSensorPosition(0) <= encoderTicks.value) {
            spinnerMotor.setVelocity(SpinConstants.kSpinSpeed)
        } else { /* > */
            rotationFinished = true
        }


    }



    public fun colorControl() {
        var gameData: String = DriverStation.getInstance().getGameSpecificMessage()
        var estColor: ColorValue = mColorSensor.getColor()
        var colorGoal: ColorValue
        when (gameData) {
            "R" -> colorGoal = ColorValue.BLUE // accounting for color offset
            "G" -> colorGoal = ColorValue.YELLOW
            "B" -> colorGoal = ColorValue.RED
            "Y" -> colorGoal = ColorValue.GREEN
            else -> colorGoal = ColorValue.UNKNOWN
        }

        if (estColor != colorGoal) {
            spinnerMotor.setVelocity(SpinConstants.kSpinSpeed)
        }
    }
}
