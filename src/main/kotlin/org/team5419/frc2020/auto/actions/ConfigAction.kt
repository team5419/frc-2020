package org.team5419.frc2020.auto.actions

import org.team5419.fault.auto.Action
import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.subsystems.Storage.StorageMode
import org.team5419.frc2020.subsystems.Intake.IntakeMode
import org.team5419.frc2020.subsystems.Intake.DeployMode

// intake actions
public class IntakeAction(): ConfigAction(intake = IntakeMode.INTAKE)
public class OuttakeAction(): ConfigAction(intake = IntakeMode.OUTTAKE)
public class DisableIntakeAction(): ConfigAction(intake = IntakeMode.OFF)

// intake actions
public class DeployIntakeAction(): ConfigAction(deploy = DeployMode.DEPLOY)
public class RetractIntakeAction(): ConfigAction(deploy = DeployMode.STORE)

// storage actions
public class PassiveStorageAction(): ConfigAction(storage = StorageMode.PASSIVE)
public class UnajamStorageAction(): ConfigAction(storage = StorageMode.REVERSE)
public class LoadStorageAction(): ConfigAction(storage = StorageMode.LOAD)
public class DisableStorageAction(): ConfigAction(storage = StorageMode.OFF)


open public class ConfigAction(
    val intake: IntakeMode? = null,
    val deploy: DeployMode? = null,
    val storage: StorageMode? = null
) : Action() {

    override fun start() {
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
