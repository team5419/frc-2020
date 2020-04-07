package org.team5419.frc2020.input

@Suppress("TooManyFunctions")
public interface DriverControls {
    // driver helper functions

    public fun getThrottle() : Double

    public fun getTurn() : Double

    public fun slowMove() : Boolean

    public fun fastTurn() : Boolean

    public fun invertDrivetrain() : Boolean

    // auto alignment functions

    public fun togleAligning(): Boolean

    public fun adjustOffsetLeft(): Double?

    public fun adjustOffsetRight(): Double?

    // hood functions

    public fun retractHood(): Boolean

    public fun adjustHoodUp(): Boolean

    public fun adjustHoodDown(): Boolean

    // climb

    public fun climb(): Boolean

    public fun unclimb(): Boolean

    public fun winch(): Boolean

    public fun unwinch(): Boolean

    // intake

    public fun intake(): Boolean

    public fun outtake(): Boolean
}
