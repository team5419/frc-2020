package org.team5419.frc2020.input

public interface CodriverControls {
    // shooger functions

    public fun shoog() : Boolean

    public fun loadShooger() : Boolean

    // intake functions

    public fun intake(): Boolean

    public fun outtake(): Boolean

    public fun storeIntake(): Boolean

    // storage functions

    public fun reverseStorage(): Boolean

    // hood functions

    public fun deployHoodFar(): Boolean

    public fun deployHoodClose(): Boolean

    public fun retractHood() : Boolean

    public fun toogleStorage(): Boolean
}
