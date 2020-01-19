package org.team5419.frc2020.input

import com.revrobotics.ColorSensorV3
import com.revrobotics.ColorSensorV3.RawColor
import com.revrobotics.ColorMatchResult
import edu.wpi.first.wpilibj.I2C.Port
import edu.wpi.first.wpilibj.util.Color
import org.team5419.frc2020.InputConstants

public class ColorSensor() {

    val mColorSensorV3: ColorSensorV3
    val mColorMatcher: ColorMatch

    init { 
        val mColorSensorV3: ColorSensorV3 = ColorSensorV3(I2C.Port.kOnboard)
        val mColorMatcher: ColorMatch = ColorMatch()
    }


    public enum class ColorOutput{
        RED, GREEN, BLUE, YELLOW, UNKNOWN
    }

    val kBlue: Color = ColorMatch.makeColor(0, 255, 255)
    val kGreen: Color = ColorMatch.makeColor(0, 255,   0)
    val kRed: Color = ColorMatch.makeColor(255, 0, 0)
    val kYellow: Color = ColorMatch.makeColor(255, 255, 0)
    // public fun getRawColor() {
    //     var rawColor: RawColor = mColorSensorV3.getRawColor()
    //     return rawColor
    // }

    public fun getColor() {
        var input: Color = mColorSensorV3.getColor()
        var result: ColorOutput
        var colorMatchResult: Color

        mColorMatcher.addColorMatch(kRed)
        mColorMatcher.addColorMatch(kGreen)
        mColorMatcher.addColorMatch(kBlue)
        mColorMatcher.addColorMatch(kYellow)

        mColorMatcher.setConfidenceThreshold(InputConstants.kConfidence)
        colorMatchResult = mColorMatcher.matchClosestColor(input).color

        when (colorMatchResult) {
            kRed -> result = ColorOutput.RED
            kGreen -> result = ColorOutput.GREEN
            kBlue -> result = ColorOutput.BLUE
            kYellow -> result = ColorOutput.YELLOW
            else -> result = ColorOutput.UNKNOWN
        }

        return result
    }
}
