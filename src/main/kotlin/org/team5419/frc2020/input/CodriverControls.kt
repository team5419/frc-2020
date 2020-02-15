package org.team5419.frc2020.input

public interface CodriverControls {
    public fun shoog() : Boolean

    // intake function

    public fun intake(): Boolean

    public fun outtake(): Boolean

    public fun storeIntake(): Boolean

    public fun reverseIntake(): Boolean

    // hood functions

    public fun deployHoodFar(): Boolean

    public fun deployHoodClose(): Boolean

    public fun retractHood() : Boolean
}
