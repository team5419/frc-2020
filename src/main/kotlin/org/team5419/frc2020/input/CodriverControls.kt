package org.team5419.frc2020.input

public interface CodriverControls {
    public fun shoog() : Boolean

    public fun deployIntake() : Boolean

    public fun retractIntake() : Boolean

    public fun intake() : Double

    public fun outtake(): Double

    public fun hood() : Boolean

    public fun deployHoodFar(): Boolean = codriverXbox.getPOV() == 270

    public fun deployHoodClose(): Boolean = codriverXbox.getPOV() == 270

    public fun retractHood() : Boolean

    public fun tooglePassiveStorage(): Boolean
}
