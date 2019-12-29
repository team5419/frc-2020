package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem

class SubsystemManager(vararg subsystems: Subsystem) : Subsystem() {
    private val subsystems: List<Subsystem>

    init { this.subsystems = subsystems.toList() }

    override fun periodic() = subsystems.forEach { it.periodic() }

    override fun autoReset() = subsystems.forEach { it.autoReset() }

    override fun teleopReset() = subsystems.forEach { it.teleopReset() }

    override fun zeroOutputs() = subsystems.forEach { it.zeroOutputs() }
}
