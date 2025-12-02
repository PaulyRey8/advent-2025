package one

class ClackRecorder {
    var count = 0

    operator fun invoke() {
        count++
        println("++ RECORD clacks = $count")
    }
}