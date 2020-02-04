package org.team5419.frc2020

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.networktables.NetworkTableEntry
import org.team5419.frc2020.controllers.TeleopController
import org.team5419.frc2020.controllers.AutoController
import org.team5419.frc2020.subsystems.*
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
        tab = Shuffleboard.getTab("Auto")

        // subsystem manager
        // +Drivetrain
        // +Climber
        // +Intake
        // +Shooger
        // +Spinner

        tab.addBoolean("Found Target", { Vision.targetFound })
        tab.addNumber("Horizontal Offset", { Vision.horizontalOffset })
        tab.addNumber("Vertical Offset", { Vision.verticalOffset })
        tab.addNumber("Target Area", { Vision.targetArea })
        tab.addNumber("Target Skew", { Vision.targetSkew })
    }

    override fun robotInit() {

    }

    override fun robotPeriodic() {
        // Drivetrain.periodic()
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
