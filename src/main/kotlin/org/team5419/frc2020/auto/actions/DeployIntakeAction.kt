package org.team5419.frc2020.auto.actions

import org.team5419.frc2020.subsystems.Intake.DeployMode
import org.team5419.frc2020.subsystems.Intake
import org.team5419.frc2020.fault.auto.Action

public class DeployIntakeAction(): ConfigDeployAction(DeployMode.DEPLOY)
public class RetractIntakeAction(): ConfigDeployAction(DeployMode.STORE)
public class DisableIntakeDeployAction(): ConfigDeployAction(DeployMode.OFF)

open class ConfigDeployAction(val deploy: DeployMode): Action() {
    override fun start() {
        Intake.deployMode = deploy
    }

    override fun next() = true

}
