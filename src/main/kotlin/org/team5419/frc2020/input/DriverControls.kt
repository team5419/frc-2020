package org.team5419.frc2020.input

public interface DriverControls {
    public fun getThrottle() : Double

    public fun getTurn() : Double

    public fun quickTurn() : Boolean

    public fun slow() : Boolean

    public fun align(): Boolean
}
