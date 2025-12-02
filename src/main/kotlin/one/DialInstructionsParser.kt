package one

interface DialInstructionsParser {
    operator fun invoke(): List<DialMovement>
}