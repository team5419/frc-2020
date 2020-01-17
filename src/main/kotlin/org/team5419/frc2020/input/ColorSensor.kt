package org.team5419.frc2020.input

import com.revrobotics.ColorSensorV3
import com.revrobotics.ColorSensorV3.RawColor
import com.revrobotics.ColorMatchResult
import edu.wpi.first.wpilibj.I2C.Port
import edu.wpi.first.wpilibj.util.Color
import org.team5419.frc2020.InputConstants

public class ColorSensor() {
    
    public val mColorSensorV3: ColorSensorV3 = ColorSensorV3(I2C.Port.kOnboard)
    public val mColorMatcher: ColorMatch = ColorMatch()

    public enum class ColorOutput{
        RED, GREEN, BLUE, YELLOW, UNKNOWN
    }

    public fun getRawColor() {
        var rawColor: RawColor = mColorSensorV3.getRawColor()
        return rawColor
    }
    
    public fun getColor() {
        var input: Color = mColorSensorV3.getColor()
        var result: ColorOutput
        var colorMatchResult: Color

        colorMatcher.addColorMatch(InputConstants.kRed)
        colorMatcher.addColorMatch(InputConstants.kGreen)
        colorMatcher.addColorMatch(InputConstants.kBlue)
        colorMatcher.addColorMatch(InputConstants.kYellow)

        colorMatcher.setConfidenceThreshold(InputConstants.kConfidence)
        colorMatchResult = mColorMatcher.matchClosestColor(input).color

        when (colorMatchResult) {
            InputConstant.kRed -> res = ColorOutput.RED
            InputConstant.kGreen -> res = ColorOutput.GREEN
            InputConstant.kBlue -> res = ColorOutput.BLUE
            InputConstant.kYellow -> res = ColorOutput.YELLOW
            else -> res = ColorOutput.UNKNOWN
        }

        return result
    }
}