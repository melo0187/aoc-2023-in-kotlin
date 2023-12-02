enum class SpelledOutDigits(val digit: String) {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9");
}

fun main() {
    fun part1(input: List<String>): Int = input.sumOf { amendedCalibrationValue ->
        amendedCalibrationValue
                .mapNotNull(Char::digitToIntOrNull)
                .let {
                    it.first() * 10 + it.last()
                }
    }

    fun part2(input: List<String>): Int = input.sumOf { amendedCalibrationValue ->
        fun replaceSpelledOutDigits(amendedCalibrationValue: String): String = SpelledOutDigits.entries
                .fold(amendedCalibrationValue) { amendedValue, spelledOutDigit ->
                    amendedValue.replace(
                            oldValue = spelledOutDigit.name,
                            newValue = spelledOutDigit.digit,
                            ignoreCase = true
                    )
                }

        val translatedLeftToRight = amendedCalibrationValue
                .fold("") { acc, curr ->
                    replaceSpelledOutDigits(acc + curr)
                }

        val translatedRightToLeft = amendedCalibrationValue
                .foldRight("") { acc, curr ->
                    replaceSpelledOutDigits(acc + curr)
                }

        val firstDigit: Int = translatedLeftToRight
                .find(Char::isDigit)?.digitToInt()
                ?: error("")
        val lastDigit: Int = translatedRightToLeft
                .findLast(Char::isDigit)?.digitToInt()
                ?: error("")

        firstDigit * 10 + lastDigit
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)
    check(part2(listOf("twoone")) == 21)
    check(part2(listOf("twone")) == 21)
    check(part2(listOf("oneeight")) == 18)
    check(part2(listOf("oneight")) == 18)
    check(part2(listOf("nineeight")) == 98)
    check(part2(listOf("nineight")) == 98)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
