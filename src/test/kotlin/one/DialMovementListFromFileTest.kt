package one

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class DialMovementListFromFileTest {

    @Test
    fun `Dial instructions from empty file`() {
        DialMovementListFromFile("emptyFile.txt")() shouldBe emptyList()
    }

    @Test
    fun `Dial instructions from file with single line`() {
        DialMovementListFromFile("oneMovement.txt")() shouldBe listOf(
            DialMovement(68, Direction.LEFT)
        )
    }

    @Test
    fun `Dial instructions from file with multiple lines`() {
        DialMovementListFromFile("multipleMovements.txt")() shouldBe listOf(
            DialMovement(68, Direction.LEFT),
            DialMovement(30, Direction.LEFT),
            DialMovement(48, Direction.RIGHT),
            DialMovement(5, Direction.LEFT),
            DialMovement(60, Direction.RIGHT),
            DialMovement(55, Direction.LEFT),
            DialMovement(1, Direction.LEFT),
            DialMovement(99, Direction.LEFT),
            DialMovement(14, Direction.RIGHT),
            DialMovement(82, Direction.LEFT),
        )
    }
}