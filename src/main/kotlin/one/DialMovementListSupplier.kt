package one

import java.io.File

interface DialMovementListSupplier {
    operator fun invoke(): List<DialMovement>
}

class DialMovementListFromFile(private val path: String) : DialMovementListSupplier {
    override fun invoke(): List<DialMovement> =
        this::class.java.getResourceAsStream(path)?.bufferedReader()?.readLines()?.map { line ->
            val regex = Regex("([RL])(\\d+)")

            val matchResult = regex.find(line)
            matchResult?.let {
                DialMovement(
                    it.groupValues[2].toInt(),
                    it.groupValues[1].toDirection()
                )
            } ?: throw IllegalArgumentException("Invalid instruction line: $line")
        } ?: throw IllegalArgumentException("File not found: $path")


    private fun String.toDirection(): Direction =
        when (this) {
            "R" -> Direction.RIGHT
            "L" -> Direction.LEFT
            else -> throw IllegalArgumentException("Invalid direction: $this")
        }
}

class DialMovementListFromList(private val value: List<DialMovement>) : DialMovementListSupplier {
    override fun invoke(): List<DialMovement> = value

    companion object {
        fun dialMovementListOf(vararg movements: DialMovement) =
            DialMovementListFromList(movements.toList())
    }
}