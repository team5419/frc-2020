package org.team5419.frc2020.fault.hardware.ctre

import com.ctre.phoenix.sensors.PigeonIMU
import org.team5419.frc2020.fault.math.geometry.Rotation2d
import org.team5419.frc2020.fault.math.units.derived.degrees
import org.team5419.frc2020.fault.util.Source

fun PigeonIMU.asSource(): Source<Rotation2d> = { Rotation2d(fusedHeading.degrees) }
