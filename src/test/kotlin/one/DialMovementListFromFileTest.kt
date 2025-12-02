package one

import io.kotest.matchers.shouldBe
import one.Dial.Movement
import one.Dial.Movement.Direction.LEFT
import one.Dial.Movement.Direction.RIGHT
import org.junit.jupiter.api.Test

class DialMovementListFromFileTest {

    @Test
    fun `Dial instructions from empty file`() {
        DialMovementsSupplier.FromFile("emptyFile.txt")() shouldBe emptyList()
    }

    @Test
    fun `Dial instructions from file with single line`() {
        DialMovementsSupplier.FromFile("oneMovement.txt")() shouldBe listOf(
            Movement(68, LEFT)
        )
    }

    @Test
    fun `Dial instructions from file with multiple lines`() {
        DialMovementsSupplier.FromFile("multipleMovements.txt")() shouldBe listOf(
            Movement(68, LEFT),
            Movement(30, LEFT),
            Movement(48, RIGHT),
            Movement(5, LEFT),
            Movement(60, RIGHT),
            Movement(55, LEFT),
            Movement(1, LEFT),
            Movement(99, LEFT),
            Movement(14, RIGHT),
            Movement(82, LEFT),
        )
    }
}