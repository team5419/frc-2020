package org.team5419.frc2020.input

import com.revrobotics.ColorSensorV3
import com.revrobotics.ColorSensorV3.RawColor
import com.revrobotics.ColorMatchResult
import com.revrobotics.ColorMatch
import com.revrobotics.*
import edu.wpi.first.wpilibj.I2C
import edu.wpi.first.wpilibj.util.Color
import edu.wpi.first.wpilibj.util.ColorShim
import org.team5419.frc2020.InputConstants

public class ColorSensor() {

    public enum class ColorValue {
        RED, GREEN, BLUE, YELLOW, UNKNOWN
    }

    val mColorSensorV3: ColorSensorV3 = ColorSensorV3(I2C.Port.kOnboard)
    val mColorMatcher: ColorMatch = ColorMatch()

    val kBlue: Color = ColorMatch.makeColor(0.122, 0.420, 0.458)
    val kGreen: Color = ColorMatch.makeColor(0.173, 0.571, 0.257)
    val kRed: Color = ColorMatch.makeColor(0.524, 0.345, 0.131)
    val kYellow: Color = ColorMatch.makeColor(0.321, 0.558, 0.120)

    public fun getRawColor(): MutableList<Int> {
        var rawColor: RawColor = mColorSensorV3.getRawColor()
        return mutableListOf(rawColor.red, rawColor.green, rawColor.blue, rawColor.ir)
    }

    public fun getColor(): ColorValue {
        var input: Color = mColorSensorV3.getColor()
        var result: ColorValue
        var colorMatchResult: Color

        mColorMatcher.addColorMatch(kRed)
        mColorMatcher.addColorMatch(kGreen)
        mColorMatcher.addColorMatch(kBlue)
        mColorMatcher.addColorMatch(kYellow)

        mColorMatcher.setConfidenceThreshold(InputConstants.kConfidence)
        colorMatchResult = mColorMatcher.matchClosestColor(input).color

        when (colorMatchResult) {
            kRed -> result = ColorValue.RED
            kGreen -> result = ColorValue.GREEN
            kBlue -> result = ColorValue.BLUE
            kYellow -> result = ColorValue.YELLOW
            else -> result = ColorValue.UNKNOWN
        }

        return result
    }

    fun getSensorValue() {
        var input: Color = mColorSensorV3.getColor()
        var colorMatchResult: Color

        mColorMatcher.addColorMatch(kRed)
        mColorMatcher.addColorMatch(kGreen)
        mColorMatcher.addColorMatch(kBlue)
        mColorMatcher.addColorMatch(kYellow)

        mColorMatcher.setConfidenceThreshold(InputConstants.kConfidence)
        colorMatchResult = mColorMatcher.matchClosestColor(input).color

        println("Code: ")
        when (colorMatchResult) {
            kRed -> println("red")
            kGreen -> println("green")
            kBlue -> println("blue")
            kYellow -> println("yellow")
            else -> println("unknown")
        }
    }
}
