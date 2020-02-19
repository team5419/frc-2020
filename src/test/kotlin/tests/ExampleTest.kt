package tests

import org.junit.jupiter.api.Test

import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.frc2020.subsystems.*
import org.team5419.frc2020.*
import kotlin.math.PI
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Vector2

public class ExampleTest {
    @Test
    fun example_test() {
        val ramsete = MockRamseteAction(
            Pose2d(0.0.meters, 0.0.meters, 0.0.radians),
            arrayOf<Vector2<Meter>>(),
            Pose2d(1.0.meters, 0.0.meters, 0.0.radians),

            DriveConstants.MaxVelocity,
            DriveConstants.MaxAcceleration,
            12.volts,
            DriveConstants.TrackWidth,
            DriveConstants.Beta,
            DriveConstants.Zeta,
            DriveConstants.DriveKs,
            DriveConstants.DriveKv,
            DriveConstants.DriveKa
        )

        ramsete.start()

        while ( !ramsete.next() ) {
            ramsete.update()
            Drivetrain.update()
        }

        ramsete.finish()
    }
}
