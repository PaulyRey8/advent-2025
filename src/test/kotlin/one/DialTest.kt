package one

import io.kotest.matchers.shouldBe
import one.DialMovementListFromList.Companion.dialMovementListOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class DialTest {

    @Test
    fun `dial records zero on no movement`() {
        val clackRecorder = ClackRecorder()
        Dial(dialMovementListOf(), clackRecorder)()

        clackRecorder.count shouldBe 0
    }

    @Test
    fun `dial position can be set on init`() {
        val clackRecorder = ClackRecorder()
        val dial = Dial(dialMovementListOf(), clackRecorder, 25)

        dial()

        dial.currentPosition() shouldBe 25
    }

    @ParameterizedTest(name = "{0}")
    @EnumSource(Direction::class)
    fun `dial records one clack on full rotation`(direction: Direction) {
        val clackRecorder = ClackRecorder()
        Dial(dialMovementListOf(DialMovement(100, direction)), clackRecorder)()

        clackRecorder.count shouldBe 1
    }

    @ParameterizedTest(name = "{0}")
    @EnumSource(Direction::class)
    fun `overlapping turn does not record one clack when it doesnt end on zero`(direction: Direction) {
        val clackRecorder = ClackRecorder()
        Dial(dialMovementListOf(DialMovement(150, direction)), clackRecorder)()

        clackRecorder.count shouldBe 0
    }

    @Test
    fun `overlapping turn does record one clack when it does stop on zero during movement`() {
        val clackRecorder = ClackRecorder()
        Dial(
            dialMovementListOf(
                DialMovement(150, Direction.RIGHT),
                DialMovement(50, Direction.LEFT),
                DialMovement(101, Direction.LEFT),
            ), clackRecorder
        )()

        clackRecorder.count shouldBe 1
    }

    @Test
    fun `full movement instructions result in correct clacks`() {
        val clackRecorder = ClackRecorder()
        Dial(
            dialMovementListOf(
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
            ),
            clackRecorder,
            50
        )()

        clackRecorder.count shouldBe 3
    }
}