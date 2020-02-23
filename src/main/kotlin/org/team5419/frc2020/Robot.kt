package org.team5419.frc2020

import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.input.*
import org.team5419.frc2020.controllers.*
import org.team5419.fault.math.units.seconds
import org.team5419.fault.BerkeliumRobot

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import edu.wpi.first.networktables.NetworkTableInstance
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.fault.hardware.ctre.*
import com.ctre.phoenix.motorcontrol.*

val tab: ShuffleboardTab = Shuffleboard.getTab("Master")

@SuppressWarnings("TooManyFunctions")
class Robot : BerkeliumRobot(0.02.seconds) {
    private val autoController: AutoController
    private val teleopController: TeleopController

    init {
        autoController = AutoController()
        teleopController = TeleopController(XboxDriver, XboxCodriver)

        // maximum update speed for Network tables, seconds

        NetworkTableInstance.getDefault().setUpdateRate(0.01)

        // add subsystems to manager

        +Drivetrain
        +Hood
        +Intake
        +Shooger
        +Storage
        +Vision
    }


    fun reset() {
        teleopController.reset()
        autoController.reset()

        // turn limelight off
        Vision.off()
    }

    override fun robotInit() {
    }

    override fun robotPeriodic() {
    }

    override fun disabledInit() {
        reset()
    }

    override fun disabledPeriodic() {
    }

    override fun autonomousInit() {
        reset()
        autoController.start()
    }

    override fun autonomousPeriodic() {
        autoController.update()
    }

    override fun teleopInit() {
        reset()
        teleopController.start()
    }

    override fun teleopPeriodic() {
        teleopController.update()
    }

    override fun testInit() {
    }

    override fun testPeriodic() {

    }
}
