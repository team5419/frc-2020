data class LookupEntry(val distance: Double, val angle: Double, val velocity: Double)

object Lookup {
    // largest entery at 0
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

                val prevEntery = table.get(i - 1)

                val percent = (distance - entry.distance) / (prevEntery.distance - entry.distance)

                val invertedPercent = percent - 1.0

                return LookupEntry(
                    distance,
                    entry.angle * percent + prevEntery.angle * invertedPercent,
                    entry.velocity * percent + prevEntery.velocity * invertedPercent
                )
            }
        }

        println("distance is too small")
        return null
    }
}
