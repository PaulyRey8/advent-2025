package one

sealed interface DialSound {

    operator fun invoke() = Unit

    object Click : DialSound

    class Clack(
        private val clackRecorder: ClackRecorder
    ) : DialSound {
        override fun invoke() {
            clackRecorder()
        }
    }
}