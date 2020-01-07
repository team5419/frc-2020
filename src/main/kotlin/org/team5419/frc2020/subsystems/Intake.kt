package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.SIUnit
import org.team5419.fault.math.units.seconds
import org.team5419.fault.math.units.Second
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import edu.wpi.first.wpilibj.Timer
import org.team5419.frc2020.IntakeConstants
import org.team5419.frc2020.DriveConstants


object Intake : Subsystem("Intake") {


    private val intakeMotor = BerkeliumSRX(IntakeConstants.kIntakePort, DriveConstants.kNativeGearboxConversion)
    private val conveyerMotor1 = BerkeliumSRX(IntakeConstants.kIntakePort, DriveConstants.kNativeGearboxConversion)
    private val conveyerMotor2 = BerkeliumSRX(IntakeConstants.kIntakePort, DriveConstants.kNativeGearboxConversion)
    private val loadMotor = BerkeliumSRX(IntakeConstants.kIntakePort, DriveConstants.kNativeGearboxConversion)
    private val timer = Timer()

    public var numberOfLoadedBalls: Int;

    init{
        numberOfLoadedBalls = 3;
    }

    public fun intake(timer: SIUnit<Second>) {

        intakeMotor.setPercent(1.0)
        intakeMotor.setPercent(1.0)
    }

    override public fun periodic() {

    }
}
