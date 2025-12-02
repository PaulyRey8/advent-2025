package one

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ClackRecorderTest {

    @Test
    fun `initial clack record is 0`() {
        ClackRecorder().count shouldBe 0
    }

    @Test
    fun `one clack record returns count of 1`() {
        ClackRecorder().apply {
            this()
        }.count shouldBe 1
    }

    @Test
    fun `multiple clacks are recorded`() {
        ClackRecorder().apply {
          repeat(10) {
              this()
          }
        }.count shouldBe 10
    }
}