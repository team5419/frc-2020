package tests

import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*

object Drivetrain {
    val angle = 0.0

    var leftDistance = 0.0.meters
    var rightDistance = 0.0.meters

    var leftVelocity = 0.0
    var rightVelocity = 0.0

    var time = 0.0
    var dt = 0.01

    fun setVelocity(
        leftVelocity: SIUnit<LinearVelocity>,
        rightVelocity: SIUnit<LinearVelocity>,
        leftFF: SIUnit<Volt>,
        rightFF: SIUnit<Volt>
    ) {
        this.leftVelocity = leftVelocity.value
        this.rightVelocity = rightVelocity.value

        println("")
        println("left velocity " + leftVelocity.value)
        println("right velocity " + rightVelocity.value)
        println("left distance " + leftDistance.value)
        println("right distance " + rightDistance.value)
    }

    fun update() {
        time += dt

        leftDistance += leftVelocity.meters * dt
        rightDistance += rightVelocity.meters * dt
    }
}
