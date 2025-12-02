package one

data class DialMovement(
    val clicks: Int,
    val direction: Direction,
) {
    init {
        require(clicks > 0)
    }
}
