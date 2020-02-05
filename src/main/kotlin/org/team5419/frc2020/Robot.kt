package org.team5419.frc2020

import edu.wpi.first.wpilibj.XboxController

import org.team5419.frc2020.controllers.TeleopController
import org.team5419.frc2020.controllers.AutoController
import org.team5419.frc2020.subsystems.Vision
import edu.wpi.first.wpilibj.shuffleboard.*
import org.team5419.fault.BerkeliumRobot
import org.team5419.fault.math.units.seconds
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.auto.Routine
import org.team5419.fault.auto.NothingAction

@SuppressWarnings("MagicNumber")
class Robot : BerkeliumRobot(0.05.seconds) {
    private val mDriver: XboxController
    private val mCodriver: XboxController
    private val teleopController: TeleopController
    private val tab: ShuffleboardTab


    init {
        mDriver = XboxController(0)
        mCodriver = XboxController(1)
        teleopController = TeleopController(mDriver, mCodriver)
        tab = Shuffleboard.getTab("Vision")
        println("Init")

        tab.add("Vision PID", Vision.controller).withWidget(BuiltInWidgets.kPIDCommand)
        tab.addBoolean("Found Target", { Vision.targetFound })
        tab.addNumber("PID Output", { Vision.output })



        // subsystem manager
        // +Drivetrain
        // +Climber
        // +Intake
        // +Shooger
        // +Spinner
    }

    override fun robotInit() {

    }

    override fun robotPeriodic() {
        // Drivetrain.periodic()
        Vision.periodic()
        Shuffleboard.update()
    }

    override fun disabledInit() {
    }

    override fun disabledPeriodic() {
    }

    override fun autonomousInit() {
        // autoController.start()
    }

    override fun autonomousPeriodic() {
        // autoController.update()
    }

    override fun teleopInit() {
        // teleopController.start()
    }

    override fun teleopPeriodic() {
        // teleopController.update()
    }
}
