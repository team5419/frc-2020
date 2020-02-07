package org.team5419.frc2020

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import edu.wpi.first.networktables.NetworkTableEntry
import org.team5419.frc2020.controllers.TeleopController
import org.team5419.frc2020.controllers.AutoController
// import org.team5419.frc2020.input.XboxCodriver
// import org.team5419.frc2020.input.XboxDriver
import org.team5419.frc2020.subsystems.*
// import org.team5419.frc2020.auto.generateRoutines
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

val tab: ShuffleboardTab = Shuffleboard.getTab("Control")

@SuppressWarnings("MagicNumber")
class Robot : BerkeliumRobot(0.01.seconds) {
    private val teleopController: TeleopController

    init {
        // autoController = AutoController()
        teleopController = TeleopController(XboxDriver, XboxCodriver)

        // maximum update speed for Network tables, seconds

        NetworkTableInstance.getDefault().setUpdateRate(0.01)

        // add subsystems to manager

        +Drivetrain
        +Intake
        +Shooger
    }

    override fun robotInit() {
        Shooger.start()
    }

    override fun robotPeriodic() {
        Shuffleboard.update()
    }

    override fun disabledInit() {
        Shooger.toogleBrakeMode(true)
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
