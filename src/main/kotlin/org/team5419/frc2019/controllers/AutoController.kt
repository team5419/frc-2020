package org.team5419.frc2019.controllers


import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.units.Meter
import org.team5419.fault.math.units.inFeet
import org.team5419.fault.math.units.inches
import org.team5419.fault.math.units.feet
import org.team5419.fault.math.units.seconds
import org.team5419.fault.math.units.milliseconds
import org.team5419.fault.math.units.inSeconds
import org.team5419.fault.math.units.derived.velocity

import org.team5419.fault.trajectory.DefaultTrajectoryGenerator
import org.team5419.fault.math.units.derived.degrees
import org.team5419.fault.trajectory.followers.RamseteFollower
import org.team5419.fault.Controller

import org.team5419.frc2019.subsystems.Drivetrain
import org.team5419.frc2019.TrajectoryConstants

class AutoController: Controller() {

    
    val ramseteTracker = Drivetrain.trajectoryFollower

    val drive = Drivetrain.differentialDrive

    val trajectory = DefaultTrajectoryGenerator.generateTrajectory(
        listOf(
            //waypoints
        ),
        listOf(
            //constraints
            // CentripetalAccelerationConstraint(kMaxCentripetalAcceleration),
            // DifferentialDriveDynamicsConstraint(drive, 9.0.volts),
            // AngularAccelerationConstraint(kMaxAngularAcceleration)
        ),
        0.0.feet.velocity,
        0.0.feet.velocity,
        TrajectoryConstants.kMaxVelocity,
        TrajectoryConstants.kMaxAcceleration,
        true
    )

    var currentTime = 0.seconds
    val deltaTime = 20.milliseconds

    val xList = arrayListOf<Double>()
    val yList = arrayListOf<Double>()

    // val refXList = arrayListOf<Double>()
    // val refYList = arrayListOf<Double>()

    ramseteTracker.reset(trajectory)

    drive.robotPosition = ramseteTracker.referencePoint!!.state.state.pose
            .transformBy(Pose2d(1.feet, 50.inches, 5.degrees))

    while (!ramseteTracker.isFinished) {
        currentTime += deltaTime
        drive.setOutput(ramseteTracker.nextState(drive.robotPosition, currentTime))
        drive.update(deltaTime)

        xList += drive.robotPosition.translation.x.inFeet() // Nick: This is the part that needs to be replaced by the actual outputs instead of simulation
        yList += drive.robotPosition.translation.y.inFeet()

        val referenceTranslation = ramseteTracker.referencePoint!!.state.state.pose.translation
        refXList += referenceTranslation.x.inFeet()
        refYList += referenceTranslation.y.inFeet()


    }
    
}
