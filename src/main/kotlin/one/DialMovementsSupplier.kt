package one

import one.Dial.Movement

typealias DialMovements = List<Movement>

sealed interface DialMovementsSupplier : () -> List<Movement> {

    class FromList(private val value: List<Movement>) : DialMovementsSupplier {
        override fun invoke(): DialMovements = value

        companion object {
            fun dialMovements(vararg movements: Movement): DialMovements =
                FromList(movements.toList())()
        }
    }

    class FromFile(private val path: String) : DialMovementsSupplier {
        override fun invoke(): List<Movement> =
            this::class.java.getResourceAsStream(path)?.bufferedReader()?.readLines()?.map { line ->
                Regex("([RL])(\\d+)").find(line)?.let {
                    Movement(
                        it.groupValues[2].toInt(),
                        it.groupValues[1].toDirection()
                    )
                } ?: throw IllegalArgumentException("Invalid instruction line: $line")
            } ?: throw IllegalArgumentException("File not found: $path")


        private fun String.toDirection(): Movement.Direction =
            when (this) {
                "R" -> Movement.Direction.RIGHT
                "L" -> Movement.Direction.LEFT
                else -> throw IllegalArgumentException("Invalid direction: $this")
            }
    }
}

