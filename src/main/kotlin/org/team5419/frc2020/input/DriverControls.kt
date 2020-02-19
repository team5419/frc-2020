package org.team5419.frc2020.input

@Suppress("TooManyFunctions")
public interface DriverControls {
    // driver helper functions

    public fun getThrottle() : Double

    public fun getTurn() : Double

    public fun slowTurn() : Boolean

    public fun slowMove() : Boolean

    // auto alignment functions

    public fun align(): Boolean

    public fun adjustOffsetLeft(): Boolean

    public fun adjustOffsetRight(): Boolean

    // hood functions

    public fun retractHood(): Boolean

    // climber functions

    public fun exend(): Boolean

    public fun retract(): Boolean

    public fun climb(): Boolean
}
