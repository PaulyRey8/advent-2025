package one

import io.kotest.matchers.shouldBe
import one.Dial.Movement
import one.Dial.Movement.Direction
import one.DialMovementsSupplier.FromList.Companion.dialMovements
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class DialTest {

    @Test
    fun `dial records zero on no movement`() {
        with(Dial()) {
            turn(emptyList())
            clacks shouldBe 0
        }
    }

    @Test
    fun `dial position can be set on init`() {
        with(Dial(position = 25)) {
            turn(dialMovements())
            position shouldBe 25
        }
    }

    @ParameterizedTest(name = "{0}")
    @EnumSource(Direction::class)
    fun `dial records one clack on full rotation`(direction: Direction) {
        with(Dial()) {
            turn(dialMovements(Movement(100, direction)))
            clacks shouldBe 1
        }
    }

    @Nested
    inner class DialWithRecordOnZeroStrategyTest {

        @ParameterizedTest(name = "{0}")
        @EnumSource(Direction::class)
        fun `overlapping turn does not record one clack when it doesnt end on zero`(direction: Direction) {
            with(Dial()) {
                turn(dialMovements(Movement(150, direction)))
                clacks shouldBe 0
            }
        }

        @Test
        fun `overlapping turn does record one clack when it does stop on zero during movement`() {
            with(Dial()) {
                turn(
                    dialMovements(
                        Movement(150, Direction.RIGHT),
                        Movement(50, Direction.LEFT),
                        Movement(101, Direction.LEFT),
                    )
                )
                clacks shouldBe 1
            }
        }

        @Test
        fun `full movement instructions result in correct clacks`() {
            with(Dial(position = 50)) {
                turn(
                    dialMovements(
                        Movement(68, Direction.LEFT),
                        Movement(30, Direction.LEFT),
                        Movement(48, Direction.RIGHT),
                        Movement(5, Direction.LEFT),
                        Movement(60, Direction.RIGHT),
                        Movement(55, Direction.LEFT),
                        Movement(1, Direction.LEFT),
                        Movement(99, Direction.LEFT),
                        Movement(14, Direction.RIGHT),
                        Movement(82, Direction.LEFT),
                    )
                )
                clacks shouldBe 3
            }
        }
    }

    @Nested
    inner class DialWithRecordOnZeroCrossoverStrategyTest {

        @ParameterizedTest(name = "{0}")
        @EnumSource(Direction::class)
        fun `overlapping turn does record one clack when it doesnt end on zero`(direction: Direction) {
            with(Dial(dialRotationStrategy = DialRotationStrategy.RecordOnZeroCrossover)) {
                turn(dialMovements(Movement(50, direction)))
                clacks shouldBe 1
            }
        }

        @Test
        fun `overlapping turn does record one clack when it stops on zero during movement`() {
            with(Dial(dialRotationStrategy = DialRotationStrategy.RecordOnZeroCrossover)) {
                turn(dialMovements(Movement(50, Direction.LEFT)))
                clacks shouldBe 1
            }
        }

        @Test
        fun `full movement instructions result in correct clacks`() {
            with(Dial(position = 50, dialRotationStrategy = DialRotationStrategy.RecordOnZeroCrossover)) {
                turn(
                    dialMovements(
                        Movement(68, Direction.LEFT),
                        Movement(30, Direction.LEFT),
                        Movement(48, Direction.RIGHT),
                        Movement(5, Direction.LEFT),
                        Movement(60, Direction.RIGHT),
                        Movement(55, Direction.LEFT),
                        Movement(1, Direction.LEFT),
                        Movement(99, Direction.LEFT),
                        Movement(14, Direction.RIGHT),
                        Movement(82, Direction.LEFT),
                    )
                )
                clacks shouldBe 6
            }
        }
    }
}
