package org.team5419.frc2020.input

public interface DriverControls {
    public fun getThrottle() : Double

    public fun getTurn() : Double

    public fun slowTurn() : Boolean

    public fun slowMove() : Boolean

    public fun align(): Boolean
}
