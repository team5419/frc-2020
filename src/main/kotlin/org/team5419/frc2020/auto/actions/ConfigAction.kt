package org.team5419.frc2020.auto.actions

import org.team5419.fault.auto.Action
import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.subsystems.Storage.StorageMode
import org.team5419.frc2020.subsystems.Intake.IntakeMode
import org.team5419.frc2020.subsystems.Intake.DeployMode

public class IntakeAction(config: IntakeMode): ConfigAction(intake = config)
public class DeployAction(config: DeployMode): ConfigAction(deploy = config)
public class StorageAction(config: StorageMode): ConfigAction(storage = config)

data class Config (
    val intake: IntakeMode? = null,
    val deploy: DeployMode? = null,
    val storage: StorageMode? = null
)

public class ConfigAction(
    val intake: IntakeMode?,
    val deploy: DeployMode?,
    val storage: StorageMode?
) : Action() {
    constructor(config: Config) : this(config.intake, config.deploy, config.storage)

    override fun start(){
        if(intake != null){
            Intake.intakeMode = intake
        }
        if(deploy != null){
            Intake.deployMode = deploy
        }
        if(storage != null){
            Storage.mode = storage
        }
    }

    override fun next(): Boolean = true

}
