package org.team5419.frc2020.subsystems

import org.team5419.frc2020.tab
import org.team5419.frc2020.IntakeConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.native.NativeUnitRotationModel
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import com.ctre.phoenix.motorcontrol.FeedbackDevice

@Suppress("TooManyFunctions")
object Intake : Subsystem("Intake") {
    // motors

    val intakeModel = NativeUnitRotationModel(IntakeConstants.IntakeTicksPerRotation)
    val deployModel = NativeUnitRotationModel(IntakeConstants.DeployTicksPerRotation)

    val intakeMotor = BerkeliumSRX(IntakeConstants.IntakePort, intakeModel)
    val deployMotor = BerkeliumSRX(100, deployModel).apply {
        talonSRX.configFactoryDefault()
        talonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder)
        talonSRX.setSelectedSensorPosition(0,0,100)
        talonSRX.setSensorPhase(false)
        talonSRX.setInverted(true)
        talonSRX.configClosedLoopPeakOutput(0, .6)
        talonSRX.config_kD(0, 10.0)
        talonSRX.config_kP(0, 1.0)
        talonSRX.configForwardSoftLimitThreshold(
            radiansToNativeUnits(IntakeConstants.DeployPosition.value), 100
        )
        talonSRX.configForwardSoftLimitEnable(true)
    }

    init{
        // tab.addNumber("Intake Pos", { deployMotor.talonSRX.getClosedLoopError(0).toDouble() })
    }

    // intake modes

    public enum class IntakeMode {
        INTAKE,
        OUTTAKE,
        OFF
    }

    public var intakeMode = IntakeMode.OFF
        set(mode: IntakeMode) {
            if ( mode == field ) return

            when (mode) {
                IntakeMode.INTAKE  -> { intakeMotor.setPercent( 0.7 ) }
                IntakeMode.OUTTAKE -> { intakeMotor.setPercent(  -1.0 ) }
                IntakeMode.OFF     -> { intakeMotor.setPercent(  0.0 ) }
            }

            field = mode
        }

    // deploy modes

    public enum class DeployMode {
        DEPLOY,
        STORE,
        OFF
    }

    public var deployMode = DeployMode.OFF
        set(mode: DeployMode) {
            if ( mode == field ) return

            when (mode) {
                DeployMode.DEPLOY -> {
                    deployMotor.setPosition(IntakeConstants.DeployPosition)
                }
                DeployMode.STORE -> {
                    deployMotor.setPosition(IntakeConstants.StorePosition)
                }
                DeployMode.OFF -> {
                    deployMotor.setPercent(  0.0 )
                }
            }

            field = mode
        }

    // deploy functions
    fun radiansToNativeUnits(radians: Double): Int = (radians / Math.PI / 2 * 4096).toInt()

    fun nativeUnitsToRadians(ticks: Int): Double = ticks / 4096 * 2 * Math.PI

    public fun store() {
        deployMode = DeployMode.STORE

        // turn off the intake, were storing it
        intakeMode = IntakeMode.OFF
    }

    public fun deploy() {
        deployMode = DeployMode.DEPLOY
    }

    public fun stopDeploy() {
        if( deployMode != DeployMode.STORE ){
            deployMode = DeployMode.OFF
        }
    }

    // intake functions

    public fun intake() {
        intakeMode = IntakeMode.INTAKE

        // cant intake if were down
        deployMode = DeployMode.DEPLOY
    }

    public fun outtake() {
        intakeMode = IntakeMode.OUTTAKE

        // cant intake if were down
        deployMode = DeployMode.DEPLOY
    }

    public fun stopIntake() {
        intakeMode = IntakeMode.OFF
    }

    public fun isActive() = intakeMode == IntakeMode.INTAKE

    public fun isAtSetPoint() =
        deployMotor.talonSRX.getClosedLoopError() < 30

    // combined functions

    public fun stop() {
        stopDeploy()
        stopIntake()
    }

    // subsystem functions
    fun reset() {
        deployMode = DeployMode.OFF
        intakeMode = IntakeMode.OFF
    }

    override fun autoReset() = reset()
    override fun teleopReset() = reset()

    override fun periodic() {
    }
}
