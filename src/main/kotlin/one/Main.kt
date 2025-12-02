package one

fun main() {
    Dial(
        DialMovementListFromFile("input.txt"),
        ClackRecorder(),
        50
    )()
}