package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.auto.Action
import kotlin.math.PI
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics
import edu.wpi.first.wpilibj.geometry.Translation2d as WPILibTranslation2d
import edu.wpi.first.wpilibj.geometry.Rotation2d as WPILibRotation2d
import edu.wpi.first.wpilibj.geometry.Pose2d as WPILibPose2d
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward
import edu.wpi.first.wpilibj.controller.RamseteController
import org.team5419.frc2020.DriveConstants

// refrences:
// https://github.com/wpilibsuite/allwpilib/blob/master/wpilibNewCommands/src/main/java/edu/wpi/first/wpilibj2/command/RamseteCommand.java
// https://docs.wpilib.org/en/latest/docs/software/examples-tutorials/trajectory-tutorial/index.html

public class RamseteAction(
    val startingPose: Pose2d,

    val intermidatePose: Array<Vector2<Meter>>,

    val finalPose: Pose2d,

    val maxVelocity: SIUnit<LinearVelocity> = DriveConstants.MaxVelocity,
    val maxAcceleration: SIUnit<LinearAcceleration> = DriveConstants.MaxAcceleration,
    val maxVoltage: SIUnit<Volt> = 12.volts,

    val trackWidth: SIUnit<Meter> = DriveConstants.TrackWidth,

    val beta: Double = DriveConstants.Beta,
    val zeta: Double = DriveConstants.Zeta,

    val kS: Double = DriveConstants.DriveKs,
    val kV: Double = DriveConstants.DriveKv,
    val kA: Double = DriveConstants.DriveKa
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

    init {
        config.setKinematics(driveKinematics)
        config.addConstraint(voltageConstraint)
        config.addConstraint(driveKinematicsConstraint)
    }

    val trajectory = TrajectoryGenerator.generateTrajectory(
        // inital pose
        WPILibPose2d(
            startingPose.translation.x.inMeters(),
            startingPose.translation.y.inMeters(),
            WPILibRotation2d(
                startingPose.rotation.radian.value
            )
        ),

        // list of intermidate points
        intermidatePose.map({ WPILibTranslation2d(it.x.inMeters(), it.y.inMeters()) }),

        // final pose
        WPILibPose2d(
            finalPose.translation.x.inMeters(),
            finalPose.translation.y.inMeters(),
            WPILibRotation2d(
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
        // finishCondition += { getTime() > trajectory.getTotalTimeSeconds() }
    }

    override fun update() {
        val time = getTime()
        val dt = time - prevTime

        val chassisSpeed = controller.calculate(
            Drivetrain.pose,

            trajectory.sample(time.inSeconds())
        )

        val setSpeed = driveKinematics.toWheelSpeeds(chassisSpeed)

        val leftFeedForward = feedforward.calculate(
            setSpeed.leftMetersPerSecond,
            (setSpeed.leftMetersPerSecond - prevSpeed.leftMetersPerSecond) / dt.value
        )

        val rightFeedForward = feedforward.calculate(
            setSpeed.rightMetersPerSecond,
            (setSpeed.rightMetersPerSecond - prevSpeed.rightMetersPerSecond) / dt.value
        )

        prevSpeed = setSpeed

        println(setSpeed.leftMetersPerSecond)
        println(setSpeed.rightMetersPerSecond)

        Drivetrain.setVelocity(
            setSpeed.leftMetersPerSecond.meters.velocity,
            setSpeed.rightMetersPerSecond.meters.velocity,
            leftFeedForward.volts / 12.0,
            rightFeedForward.volts / 12.0
        )
    }
}
