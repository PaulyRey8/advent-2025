package one

class Dial(
    private val clackRecorder: ClackRecorder = ClackRecorder(),
    internal var position: Int = 0,
    private val dialRotationStrategy: DialRotationStrategy = DialRotationStrategy.RecordOnZero
) {
    internal val notches: List<DialSound> = (0..MAX_DIAL_VALUE).map {
        when (it) {
            0 -> DialSound.Clack(clackRecorder)
            else -> DialSound.Click
        }
    }.toList()

    val clacks get() = clackRecorder.count

    init {
        println("START position = $position")
    }

    fun turn(movements: DialMovements) {
        movements.forEach {
            dialRotationStrategy.rotate(it)
        }
    }

    internal companion object {
        const val MAX_DIAL_VALUE = 100
    }

    class Movement(
        val clicks: Int,
        val direction: Direction,
    ) {
        init {
            require(clicks > 0)
        }

        enum class Direction {
            RIGHT, LEFT
        }
    }
}