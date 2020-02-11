package org.team5419.frc2020.auto

import org.team5419.frc2020.VisionConstants
import org.team5419.frc2020.VisionConstants.PID
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.fault.auto.*
import org.team5419.fault.hardware.Limelight
import edu.wpi.first.wpilibj.controller.PIDController

class AutoAlignAction(val limelight: Limelight) : Action() {

    private val positionControl: PIDController
    private var error = 0.0
    private var output = 0.0

    init{
        positionControl = PIDController(PID.P, PID.I, PID.D)
        positionControl.setTolerance( VisionConstants.Tolerance )
        Drivetrain.isBraking = true
        //finishCondition is or not and
        finishCondition += { limelight.targetFound && positionControl.atSetpoint() }
    }

    override public fun update(){
        error = limelight.horizontalOffset
        if(error == 0.0) {
            output = 0.0
        } else {
            output = positionControl.calculate(error)
        }
        Drivetrain.setPercent(output, -output)
    }

    override public fun finish(){
        Drivetrain.isBraking = false
    }
}
