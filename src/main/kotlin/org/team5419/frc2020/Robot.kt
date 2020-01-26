package org.team5419.frc2020

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.networktables.NetworkTableEntry
import org.team5419.frc2020.controllers.TeleopController
import org.team5419.frc2020.controllers.AutoController
import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.auto.generateRoutines
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
    private val autoController: AutoController
    private val tab: ShuffleboardTab

    private val bool: NetworkTableEntry
    private var balls : Double = 3.0
    private var addBall: Boolean = false
    // private val auto : NetworkTableEntry

    init {
        mDriver = XboxController(0)
        mCodriver = XboxController(1)
        teleopController = TeleopController(mDriver, mCodriver)
        autoController = AutoController(Routine("", Pose2d()), generateRoutines(Pose2d()))
        tab = Shuffleboard.getTab("Auto")

        // subsystem manager
        +Drivetrain
        +Climber
        +Intake
        +Shooger
        +Spinner

        bool = tab.add("bool", false).withWidget("Toggle Button").getEntry();
        tab.addNumber("Stored Cells", { balls })
        tab.addNumber("Heading", { Drivetrain.angle.degree.value }).withWidget(BuiltInWidgets.kGyro)
        tab.add("AutoSelector", autoController.mAutoSelector).withWidget(BuiltInWidgets.kComboBoxChooser)
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
