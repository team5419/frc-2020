package tests

import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.auto.Action
import edu.wpi.first.wpilibj.controller.RamseteController
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward
import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.geometry.Translation2d
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry
import kotlin.math.PI

// refrences:
// https://github.com/wpilibsuite/allwpilib/blob/master/wpilibNewCommands/src/main/java/edu/wpi/first/wpilibj2/command/RamseteCommand.java
// https://docs.wpilib.org/en/latest/docs/software/examples-tutorials/trajectory-tutorial/index.html

public class MockRamseteAction(
    val startingPose: org.team5419.fault.math.geometry.Pose2d,

    val intermidatePose: Array<Vector2<Meter>>,

    val finalPose: org.team5419.fault.math.geometry.Pose2d,

    val maxVelocity: SIUnit<LinearVelocity>,
    val maxAcceleration: SIUnit<LinearAcceleration>,
    val maxVoltage: SIUnit<Volt>,

    val trackWidth: SIUnit<Meter>,

    val beta: Double,
    val zeta: Double,

    val kS: Double,
    val kV: Double,
    val kA: Double
) : Action() {

    val driveKinematics = DifferentialDriveKinematics(trackWidth.inMeters())

    val feedforward = SimpleMotorFeedforward(kS, kV, kA)

    val voltageConstraint = DifferentialDriveVoltageConstraint(
        feedforward,
        driveKinematics,
        maxVoltage.value
    )

    val driveKinematicsConstraint = DifferentialDriveKinematicsConstraint(
        driveKinematics,
        maxVelocity.value
    )

    val config = TrajectoryConfig(
        maxVelocity.value,
        maxAcceleration.value
    )

    val odometry = DifferentialDriveOdometry(Rotation2d.fromDegrees(Drivetrain.angle));

    init {
        config.setKinematics(driveKinematics)
        config.addConstraint(voltageConstraint)
        config.addConstraint(driveKinematicsConstraint)
    }

    val trajectory = TrajectoryGenerator.generateTrajectory(
        // inital pose
        Pose2d(
            startingPose.translation.x.inMeters(),
            startingPose.translation.y.inMeters(),
            Rotation2d(
                startingPose.rotation.radian.value
            )
        ),

        // list of intermidate points
        intermidatePose.map({ Translation2d(it.x.inMeters(), it.y.inMeters()) }),

        // final pose
        Pose2d(
            finalPose.translation.x.inMeters(),
            finalPose.translation.y.inMeters(),
            Rotation2d(
                finalPose.rotation.radian.value
            )
        ),

        // the trajectory configuration
        config
    )

    val controller = RamseteController(beta, zeta)

    var prevTime = 0.0.seconds
    var prevSpeed = DifferentialDriveWheelSpeeds(0.0, 0.0)

    init {
        finishCondition += { Drivetrain.time > trajectory.getTotalTimeSeconds() }
    }

    override fun update() {
        val dt = Drivetrain.dt

        odometry.update(
            Rotation2d.fromDegrees(Drivetrain.angle),
            Drivetrain.rightDistance.value,
            Drivetrain.leftDistance.value
        )

        val chassisSpeed = controller.calculate(
            odometry.getPoseMeters(),

            trajectory.sample(Drivetrain.time)
        )

        val setSpeed = driveKinematics.toWheelSpeeds(chassisSpeed)

        val leftFeedForward = feedforward.calculate(
            setSpeed.leftMetersPerSecond,
            (setSpeed.leftMetersPerSecond - prevSpeed.leftMetersPerSecond) / dt
        )

        val rightFeedForward = feedforward.calculate(
            setSpeed.rightMetersPerSecond,
            (setSpeed.rightMetersPerSecond - prevSpeed.rightMetersPerSecond) / dt
        )

        prevSpeed = setSpeed

        Drivetrain.setVelocity(
            setSpeed.leftMetersPerSecond.meters.velocity,
            setSpeed.rightMetersPerSecond.meters.velocity,
            leftFeedForward.volts,
            rightFeedForward.volts
        )
    }
}
