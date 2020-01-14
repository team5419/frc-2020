package org.team5419.frc2020.subsystems

import edu.wpi.first.wpilibj.ADXRS450_Gyro
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.PWMVictorSPX
import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.interfaces.Gyro
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj2.command.SubsystemBase

import org.team5419.frc2020.DriveConstants

class Trajectory() : SubsystemBase() {
    private val mLeftMotor1 : PWMVictorSPX = PWMVictorSPX(DriveConstants.kLeftSlave1Port)
    private val mLeftMotor2 : PWMVictorSPX = PWMVictorSPX(DriveConstants.kLeftSlave2Port)
    private val mRightMotor1 : PWMVictorSPX = PWMVictorSPX(DriveConstants.kRightSlave1Port)
    private val mRightMotor2 : PWMVictorSPX = PWMVictorSPX(DriveConstants.kRightSlave2Port)

    private val mLeftMotors : SpeedControllerGroup = SpeedControllerGroup(mLeftMotor1, mLeftMotor2)
    private val mRightMotors : SpeedControllerGroup = SpeedControllerGroup(mRightMotor1, mRightMotor2)
    private val mDrive : DifferentialDrive = DifferentialDrive(mLeftMotors, mRightMotors)

    private val mLeftEncoder : Encoder =
        Encoder(DriveConstants.kLeftEncoderPort1, DriveConstants.kLeftEncoderPort2,
                DriveConstants.kLeftEncoderReversed)

    private val mRightEncoder : Encoder =
        Encoder(DriveConstants.kRightEncoderPort1, DriveConstants.kRightEncoderPort2,
                DriveConstants.kRightEncoderReversed)

    private val mGyro : Gyro = ADXRS450_Gyro()
    private val mOdometry : DifferentialDriveOdometry

    init {
        mLeftEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse)
        mRightEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse)

        resetEncoders()
        mOdometry = DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()))
    }

    public override fun periodic() {
        mOdometry.update(Rotation2d.fromDegrees(getHeading()), mLeftEncoder.getDistance(),
                          mRightEncoder.getDistance())
    }

    public fun resetEncoders() {
        mLeftEncoder.reset()
        mRightEncoder.reset()
    }

    public fun getPose() : Pose2d {
        return mOdometry.getPoseMeters()
    }

    public fun getHeading() : Double {
        var heading : Double = 0.0
        if (DriveConstants.kGyroReversed) {
            heading = 1.0
        } else {
            heading = -1.0
        }
        return Math.IEEEremainder(mGyro.getAngle(), 360.0) * heading
    }

    public fun getWheelSpeeds() : DifferentialDriveWheelSpeeds {
        return DifferentialDriveWheelSpeeds(mLeftEncoder.getRate(), mRightEncoder.getRate())
    }
}
