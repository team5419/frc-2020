package org.team5419.frc2020.auto.actions

import org.team5419.fault.auto.Action
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.Second
import org.team5419.fault.math.units.derived.Radian
import edu.wpi.first.wpilibj.controller.PIDController
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.frc2020.DriveConstants.TurnPID

public class TurnAction(theta: Double): Action() {

    private val pid: PIDController
    private var output: Double

    init{
        pid = PIDController(TurnPID.P, TurnPID.I, TurnPID.D).apply{
            enableContinuousInput(0.0, 360.0)
            setSetpoint(theta)
            setTolerance(1.0)
        }
        output = 0.0

        finishCondition.set({ pid.atSetpoint() })
    }

    override fun update(dt: SIUnit<Second>) {
        output = pid.calculate(Drivetrain.angle)
        Drivetrain.setPercent(output, -output)
    }

    override fun finish() {
        Drivetrain.setPercent(0.0, 0.0)
    }
}
