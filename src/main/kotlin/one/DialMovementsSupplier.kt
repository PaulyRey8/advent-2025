package one

import one.Dial.Movement

/**
 * Supplies a list of [Movement]s for a dial, typically representing a sequence of dial instructions.
 *
 * Implementations:
 * - [FromList]: Supplies movements from a provided list.
 * - [FromFile]: Supplies movements by parsing a resource file.
 */
sealed interface DialMovementsSupplier : () -> List<Movement> {

    /**
     * Supplies dial movements from a given list.
     * @param value the list of [Movement]s to supply
     */
    class FromList(private val value: List<Movement>) : DialMovementsSupplier {
        /** Returns the supplied list of movements. */
        override fun invoke(): DialMovements = value

        companion object {
            /**
             * Creates a [DialMovements] list from the given [Movement]s.
             * @param movements the movements to include
             * @return the list of movements
             */
            fun dialMovements(vararg movements: Movement): DialMovements =
                FromList(movements.toList())()
        }
    }

    /**
     * Supplies dial movements by parsing a resource file.
     * The file must contain one movement per line, e.g. "R10" or "L5".
     * @param path the resource path to the file
     */
    class FromFile(private val path: String) : DialMovementsSupplier {
        /**
         * Parses the file and returns the list of movements.
         * @throws IllegalArgumentException if the file is not found or a line is invalid
         */
        override fun invoke(): List<Movement> =
            this::class.java.getResourceAsStream(path)?.bufferedReader()?.use { reader ->
                reader.lineSequence().map { line ->
                    Regex(FILE_LINE_REGEX).find(line)?.let {
                        Movement(
                            it.groupValues[2].toInt(),
                            it.groupValues[1].toDirection()
                        )
                    } ?: throw IllegalArgumentException("Invalid instruction line: $line")
                }.toList()
            } ?: throw IllegalArgumentException("File not found: $path")

        /** Converts a direction character to a [Movement.Direction]. */
        private fun String.toDirection(): Movement.Direction =
            when (this) {
                RIGHT_DIRECTION_CHAR -> Movement.Direction.RIGHT
                LEFT_DIRECTION_CHAR -> Movement.Direction.LEFT
                else -> throw IllegalArgumentException("Invalid direction: $this")
            }

        private companion object {
            const val LEFT_DIRECTION_CHAR = "L"
            const val RIGHT_DIRECTION_CHAR = "R"
            const val FILE_LINE_REGEX = "([${LEFT_DIRECTION_CHAR}$RIGHT_DIRECTION_CHAR])(\\d+)"
        }
    }
}

/**
 * Alias for a list of dial movements.
 */
typealias DialMovements = List<Movement>
