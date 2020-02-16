data class LookupEntry(val distance: Double, val angle: Double, val velocity: Double)

object Lookup {
    private val table = mutableListOf<LookupEntry>()

    fun add(distance: Double, angle: Double, velocity: Double) {
        for (i in 0..table.size) {
            val entry = table.get(i)

            if (entry.distance < distance) {
                table.add(i, LookupEntry(distance, angle, velocity))

                break
            }
        }
    }

    fun get(distance: Double): LookupEntry? {
        for (i in 0..table.size) {
            val entry = table.get(i)

            if (entry.distance < distance) {
                if (i == 0) {
                    println("distance is too large")
                    return null
                }

                val prevEntery = table.get(i)

                return LookupEntry(
                    distance,
                    ( entry.angle + prevEntery.angle ) / 2,
                    ( entry.velocity + prevEntery.velocity ) / 2
                )
            }
        }

        println("distance is too small")
        return null
    }
}
