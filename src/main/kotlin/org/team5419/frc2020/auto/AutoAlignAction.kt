package org.team5419.frc2020.auto

import org.team5419.frc2020.tab
import org.team5419.frc2020.VisionConstants
import org.team5419.frc2020.VisionConstants.PID
import org.team5419.frc2020.subsystems.*
import org.team5419.fault.auto.*
import org.team5419.fault.hardware.Limelight
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets

class AutoAlignAction() : Action() {

    private val positionControl: PIDController
    private var error = 0.0
    private var output = 0.0

    init{
        positionControl = PIDController(PID.P, PID.I, PID.D)
        positionControl.setTolerance( VisionConstants.Tolerance )
        Drivetrain.isBraking = true
        //finishCondition is or not and
        finishCondition += { Vision.targetFound && positionControl.atSetpoint() }

        tab.add("Vision PID", positionControl).withWidget(BuiltInWidgets.kPIDCommand)
        tab.addNumber("PID Output", { output })
    }

    override public fun update(){
        error = Vision.horizontalOffset
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
