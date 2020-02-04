package org.team5419.frc2020.input

public interface DriverControls {
    public fun getThrottle() : Double

    public fun getTurn() : Double

    public fun quickTurn() : Boolean

    public fun slow() : Boolean

    public fun deployIntake() : Boolean

    public fun retractIntake() : Boolean

    public fun activateIntake() : Boolean
}
