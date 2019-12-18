package org.team5419.frc2019

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.XboxController

import org.team5419.fault.auto.Routine
import org.team5419.frc2019.subsystems.Drivetrain
import org.team5419.frc2019.controllers.AutoController
import org.team5419.frc2019.auto.DriveTrajectoryAction

@SuppressWarnings("MagicNumber")
class Robot : TimedRobot() {
    private val drivetrain: Drivetrain
    private val mXboxController: XboxController
    private val autoController: AutoController
    val trajectoryGenerator: TrajectoryGenerator = DefaultTrajectoryGenerator(maxDx, maxDy, maxDTheta)
    val defaultTrajectory: Trajectory = trajectoryGenerator.generateTrajectory(
        listOf(
            Pose2d(0, 0),
            Pose2d(10.feet, 5.feet),
            Pose2d(10.feet, 10.feet)
        ),
        List<TimingConstraint<Pose2dWithCurvature>>(),
        0.0.feet.velocity,
        0.0.feet.velocity,
        TrajectoryConstants.kMaxVelocity,
        TrajectoryConstants.kMaxAcceleration,
        true
    )
    val defaultRoutine: Routine = Routine(
        "default",
        Pose2d(Vector2(0.0.meters, 0.0.meters), 0.degrees),
        mutableListOf(DriveTrajectoryAction(drivetrain, drivetrain.trajectoryFollower, defaultTrajectory))
    )

    init {
        drivetrain = Drivetrain
        mXboxController = XboxController(0)
    }

    override fun robotInit() {
    }

    override fun robotPeriodic() {
    }

    override fun disabledInit() {
    }

    override fun disabledPeriodic() {
    }

    override fun teleopInit() {
    }

    override fun teleopPeriodic() {
        val leftHand: Double = mXboxController.getY(Hand.kLeft) / 1
        val rightHand: Double = mXboxController.getY(Hand.kRight) / -1
        drivetrain.setPercent(leftHand, rightHand)
        drivetrain.setOutput(//Nick: Output from controller?)
    }
}
