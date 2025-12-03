package one

import one.Dial.Movement

/**
 * Strategy for rotating a [Dial] according to a [Movement].
 *
 * Implementations:
 * - [RecordOnZero]: Triggers a clack only when the dial lands exactly on zero after a movement.
 * - [RecordOnZeroCrossover]: Triggers a clack every time the dial crosses zero during a movement.
 */
fun interface DialRotationStrategy {
    /**
     * Rotates the dial according to the given [dialMovement].
     * @param dialMovement the movement to apply
     */
    context(dial: Dial)
    fun rotate(dialMovement: Movement)

    /**
     * Strategy that triggers a clack only when the dial lands exactly on zero after a movement.
     */
    object RecordOnZero : DialRotationStrategy {
        context(dial: Dial)
        override fun rotate(dialMovement: Movement) {
            with(dial) {
                position = when (dialMovement.direction) {
                    Movement.Direction.RIGHT -> (position + dialMovement.clicks).mod(Dial.MAX_DIAL_VALUE)
                    Movement.Direction.LEFT -> (position - dialMovement.clicks).mod(Dial.MAX_DIAL_VALUE)
                }

                // For debugging: print the new position
                println("MOVE position = $position")

                dial.notches[position]()
            }
        }
    }

    /**
     * Strategy that triggers a clack every time the dial crosses zero during a movement.
     */
    object RecordOnZeroCrossover : DialRotationStrategy {
        context(dial: Dial)
        override fun rotate(dialMovement: Movement) {
            with(dial) {
                position = when (dialMovement.direction) {
                    Movement.Direction.RIGHT -> {
                        repeat(dialMovement.clicks) {
                            position = (position + 1).mod(Dial.MAX_DIAL_VALUE).apply {
                                notches[position]()
                            }
                        }
                        position
                    }

                    Movement.Direction.LEFT -> {
                        repeat(dialMovement.clicks) {
                            position = (position - 1).mod(Dial.MAX_DIAL_VALUE).apply {
                                notches[position]()
                            }
                        }
                        position
                    }
                }

                // For debugging: print the new position
                println("MOVE position = $position")
            }
        }
    }

}
