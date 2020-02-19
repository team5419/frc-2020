package org.team5419.frc2020.subsystems

import org.team5419.frc2020.ClimbConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.fault.math.units.native.NativeUnitRotationModel
import org.team5419.fault.math.units.native.nativeUnits

object Climber : Subsystem("Climber") {

    private val exendModel = NativeUnitRotationModel(1.nativeUnits)
    private val climbModel = NativeUnitRotationModel(1.nativeUnits)

    private val exendMotor = BerkeliumSRX(ClimbConstants.exendPort, exendModel)
    private val climbMotor = BerkeliumSRX(ClimbConstants.climberPort, climbModel)

    init{
        exendMotor.talonSRX.configFactoryDefault(100)
        climbMotor.talonSRX.configFactoryDefault(100)

    }

    fun exend() = exendMotor.setPercent(1.0)
    fun retract() = exendMotor.setPercent(-1.0)
    fun climb() = climbMotor.setPercent(1.0)

    fun stopMoving() {
        climbMotor.setPercent(0.0)
        exendMotor.setPercent(0.0)
    }
}
