package one

class Dial(
    private val instructions: List<DialMovement>,
    private val clackRecorder: ClackRecorder,
    private var position: Int = 0
) {
    private val notches: List<DialSound> = (0..MAX_DIAL_VALUE).map {
        when (it) {
            0 -> DialSound.Clack(clackRecorder)
            else -> DialSound.Click
        }
    }.toList()

    init {
        println("START position = $position")
    }

    private fun DialMovement.rotate() {
        position = when (direction) {
            Direction.RIGHT -> (position + clicks).mod(MAX_DIAL_VALUE)
            Direction.LEFT -> (position - clicks).mod(MAX_DIAL_VALUE)
        }

        println("MOVE position = $position")

        notches[position]()
    }

    operator fun invoke() {
        instructions.forEach { instruction ->
            instruction.rotate()
        }
    }

    fun currentPosition() = position

    private companion object {
        private const val MAX_DIAL_VALUE = 100
    }
}