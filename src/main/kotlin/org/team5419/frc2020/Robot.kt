package org.team5419.frc2020

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import edu.wpi.first.networktables.NetworkTableEntry
import org.team5419.frc2020.controllers.TeleopController
import org.team5419.frc2020.subsystems.*
import org.team5419.fault.BerkeliumRobot
import org.team5419.fault.math.units.seconds
import org.team5419.fault.math.units.derived.radians
import org.team5419.fault.math.units.derived.velocity
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.auto.Routine
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.RobotController
import org.team5419.frc2020.input.XboxDriver
import org.team5419.frc2020.input.XboxCodriver

@SuppressWarnings("MagicNumber")
class Robot : BerkeliumRobot(0.01.seconds) {
    private val teleopController: TeleopController

    init {
        teleopController = TeleopController(XboxDriver, XboxCodriver)

        NetworkTableInstance.getDefault().setUpdateRate(0.01) // maximum update speed, seconds

        +Drivetrain
        +Intake
        +Shooger
    }

    override fun robotInit() {
    }

    override fun robotPeriodic() {
        Shuffleboard.update()
    }

    override fun disabledInit() {
    }

    override fun disabledPeriodic() {
    }

    override fun autonomousInit() {
    }

    override fun autonomousPeriodic() {

    }

    override fun teleopInit() {
        teleopController.start()
    }

    override fun teleopPeriodic() {
        teleopController.update()
    }
}
