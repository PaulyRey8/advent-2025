package one

import one.Dial.Movement

fun interface DialRotationStrategy {
    context(dial: Dial)
    fun rotate(dialMovement: Movement)

    object RecordOnZero : DialRotationStrategy {
        context(dial: Dial)
        override fun rotate(dialMovement: Movement) {
            with(dial) {
                position = when (dialMovement.direction) {
                    Movement.Direction.RIGHT -> (position + dialMovement.clicks).mod(Dial.MAX_DIAL_VALUE)
                    Movement.Direction.LEFT -> (position - dialMovement.clicks).mod(Dial.MAX_DIAL_VALUE)
                }

                println("MOVE position = $position")

                dial.notches[position]()
            }
        }
    }

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

                println("MOVE position = $position")
            }
        }
    }

}

