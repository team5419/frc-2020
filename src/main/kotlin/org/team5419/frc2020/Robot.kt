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

    init {
        mDriver = XboxController(0)
        mCodriver = XboxController(1)
        teleopController = TeleopController(mDriver, mCodriver)

        NetworkTableInstance.getDefault().setUpdateRate(0.01) // maximum update speed, seconds

        // subsystem manager

        +Shooger
    }

    override fun robotInit() {
    }

    override fun robotPeriodic() {
        Shuffleboard.update()

        Shooger.periodic()

        Shooger.shoog( Shooger.shooterVelocityEntry.getDouble(0.0))
    }

    override fun disabledInit() {
        // enabledTimer.stop()
        // enabledTimer.reset()
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
