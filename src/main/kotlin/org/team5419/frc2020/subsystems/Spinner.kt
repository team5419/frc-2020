package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.fault.hardware.ctre.CTREBerkeliumEncoder
import org.team5419.fault.math.units.native.NaitveUnitRotationModel
import org.team5419.frc2020.DriveConstants
import org.team5419.frc2020.SpinConstants
import org.team5419.frc2020.input.ColorSensor
import org.team5419.frc2020.input.ColorSensor.ColorOutput

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import edu.wpi.first.wpilibj.DriverStation

object Spinner() : Subsystem("Spinner") {

    private val mColorSensor = ColorSensor()
    private val spinnerMotor = BerkeliumSRX(SpinConstants.kMotorPort, NativeUnitRotationModel(DriveConstants.kTicksPerRotation))

    spinnerMotor.talonSRX.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0)
    spinnerMotor.talonSRX.setSensorPhase(true)
    spinnerMotor.brakeMode = true


    public fun rotationControl() {
        var encoderTicks = 3.5 * SpinConstants.kEncoderTicksPerRotation
        if (spinnerMotor.talonSRX.getSelectedSensorPosition(0) <=  encoderTicks) {
            spinnerMotor.setVelocity(SpinConstants.kSpinSpeed)
        }
    }

    public fun colorControl() {
        var gameData: String = DriverStation.getInstance().getGameSpecificMessage()
        var estColor: ColorOutput = mColorSensor.getColor()
        var colorGoal: ColorOutput
        when (gameData) {
            "R" -> colorGoal = ColorOutput.BLUE // accounting for color offset
            "G" -> colorGoal = ColorOutput.YELLOW
            "B" -> colorGoal = ColorOutput.RED
            "Y" -> colorGoal = ColorOutput.GREEN
            else -> colorGoal = ColorOutput.UNKNOWN
        }

        if (estColor != colorGoal) {
            spinnerMotor.setVelocity(SpinConstants.kSpinSpeed)
        } 
    }
}
