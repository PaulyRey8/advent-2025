package one

/**
 * Represents a dial that can be turned and records clacks.
 * @property clackRecorder The recorder for clacks.
 * @property position The current position of the dial.
 * @property dialRotationStrategy The strategy for dial rotation.
 */
class Dial(
    private val clackRecorder: ClackRecorder = ClackRecorder(),
    internal var position: Int = 0, // Internal for strategy access, encapsulated for external
    private val dialRotationStrategy: DialRotationStrategy = DialRotationStrategy.RecordOnZero
) {
    internal val notches: List<DialSound> = (0..MAX_DIAL_VALUE).map {
        when (it) {
            0 -> DialSound.Clack(clackRecorder)
            else -> DialSound.Click
        }
    }

    /**
     * The number of clacks recorded.
     */
    val clacks get() = clackRecorder.count

    /**
     * Turns the dial according to the given movements.
     * @param movements The movements to apply.
     */
    fun turn(movements: DialMovements) {
        movements.forEach {
            dialRotationStrategy.rotate(it)
        }
    }

    internal companion object {
        const val MAX_DIAL_VALUE = 100
    }

    /**
     * Represents a movement of the dial.
     * @property clicks The number of clicks to move.
     * @property direction The direction to move.
     * @throws IllegalArgumentException if clicks <= 0
     */
    data class Movement(
        val clicks: Int,
        val direction: Direction,
    ) {
        init {
            require(clicks > 0) { "Clicks must be greater than 0, but was $clicks" }
        }

        enum class Direction {
            RIGHT, LEFT
        }
    }

    /**
     * Returns the current position of the dial.
     */
    fun getPosition(): Int = position
}