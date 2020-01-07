package org.team5419.frc2020

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import org.team5419.frc2020.controllers.TeleopController
import org.team5419.frc2020.controllers.AutoController
import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.auto.generateRoutines
import org.team5419.fault.BerkeliumRobot
import org.team5419.fault.math.units.seconds
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.auto.Routine

@SuppressWarnings("MagicNumber")
class Robot : BerkeliumRobot(0.05.seconds) {
    private val mDriver: XboxController
    private val mCodriver: XboxController
    private val teleopController: TeleopController
    private val autoController: AutoController
    private val smartDashboard: ShuffleboardTab

    init {
        mDriver = XboxController(0)
        mCodriver = XboxController(1)
        teleopController = TeleopController(mDriver, mCodriver)
        autoController = AutoController(Routine("", Pose2d()), *generateRoutines(Pose2d()))
        smartDashboard = Shuffleboard.getTab("SmartDashboard")

        // subsystem manager
        +Drivetrain
    }

    override fun robotInit() {
        smartDashboard.apply {
            add("Drivetrain", Drivetrain).withWidget(BuiltInWidgets.kDifferentialDrive)
            add("Drivetrain", Drivetrain).withWidget(BuiltInWidgets.kDifferentialDrive)
            add("Angle", Drivetrain.gyro).withWidget(BuiltInWidgets.kGyro)
            //add number of preoaded balls
            // add("Video Feed", Drivetrain).withWidget(BuiltInWidgets.kCameraStream)
            add("Auto Selector", autoController.mAutoSelector).withWidget(BuiltInWidgets.kComboBoxChooser)
        }
    }

    override fun robotPeriodic() {
        Shuffleboard.update()
    }

    override fun disabledInit() {
    }

    override fun disabledPeriodic() {
    }

    override fun autonomousInit() {
        autoController.start()
    }

    override fun autonomousPeriodic() {
        autoController.update()
    }

    override fun teleopInit() {
        teleopController.start()
    }

    override fun teleopPeriodic() {
        teleopController.update()
    }
}
