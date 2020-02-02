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
import org.team5419.fault.math.units.derived.radians
import org.team5419.fault.math.units.derived.velocity
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.auto.Routine
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.RobotController

@SuppressWarnings("MagicNumber")
class Robot : BerkeliumRobot(0.01.seconds) {
    private val mDriver: XboxController
    private val mCodriver: XboxController
    private val teleopController: TeleopController
    private val tab: ShuffleboardTab
    private var shooterVelocity : NetworkTableEntry
    private var hopperPercentEntry : NetworkTableEntry
    // private var hopperEnableEntry : NetworkTableEntry
    private var feederPercentEntry : NetworkTableEntry
    private var isEnabled: Boolean = false
    private var enabledTimer: Timer = Timer()
    // private var feederEnableEntry : NetworkTableEntry


    init {
        mDriver = XboxController(0)
        mCodriver = XboxController(1)
        teleopController = TeleopController(mDriver, mCodriver)
        tab = Shuffleboard.getTab("Shooger")

        NetworkTableInstance.getDefault().setUpdateRate(0.01) // maximum update speed, seconds

        // subsystem manager

        +Shooger

        shooterVelocity = tab.add("Target Velocity", 0.0).getEntry()
        hopperPercentEntry = tab.add("Hopper Percent", 0.0).getEntry()
        feederPercentEntry = tab.add("Feeder Hopper", 0.0).getEntry()
        // feederEnableEntry = tab.add("Toogle Feeder", false).withWidget(BuiltInWidgets.kBooleanBox).getEntry()
        // hopperEnableEntry = tab.add("Toogle Hopper", false).withWidget(BuiltInWidgets.kBooleanBox).getEntry()

        // tab.add("Current Velocity Graph", { Shooger.flyWheelVelocity }).withWidget(BuiltInWidgets.kGraph)
        tab.addNumber("Current Velocity", { Shooger.flyWheelVelocity })
        tab.addNumber("Current Acceleration", { Shooger.flyWheelAcceleration })
        tab.addNumber("Current Time", { enabledTimer.get() })
        tab.addBoolean("Enabled", { isEnabled })
        tab.addNumber("Motor Amperage", { Shooger.amperage })
        tab.addNumber("Motor Voltage", { Shooger.voltage })
        tab.addNumber("Battery Voltage", { RobotController.getBatteryVoltage() })

        // tab.addNumber("Hood Angle", { Shooger.hoodAngle.value })
    }

    override fun robotInit() {
    }

    override fun robotPeriodic() {
        Shuffleboard.update()
        Shooger.periodic()
        Shooger.shoog(shooterVelocity.getDouble(0.0))
        // Shooger.shoogPower(1.0)
        // Shooger.powerFeeder(feederPercentEntry.getDouble(0.0))
        Shooger.powerHopper(hopperPercentEntry.getDouble(0.0))

        // Shooger.enableFeeder = feederEnableEntry.getBoolean(false)
        // Shooger.enableHopper = hopperEnableEntry.getBoolean(false)
    }

    override fun disabledInit() {
        isEnabled = false
        // enabledTimer.stop()
        // enabledTimer.reset()
    }

    override fun disabledPeriodic() {
    }

    override fun autonomousInit() {
        isEnabled = true
        enabledTimer.start()
    }

    override fun autonomousPeriodic() {

    }

    override fun teleopInit() {
        isEnabled = true
        enabledTimer.start()
        teleopController.start()
    }

    override fun teleopPeriodic() {
        teleopController.update()
    }
}
