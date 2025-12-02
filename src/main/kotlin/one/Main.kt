package one

fun main() {
    Dial(
        position = 50,
        dialRotationStrategy = DialRotationStrategy.RecordOnZeroCrossover
    ).turn(DialMovementsSupplier.FromFile("input.txt")())
}