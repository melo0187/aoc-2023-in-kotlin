sealed interface SchematicElement
data class SchematicNumber(
    val number: Int,
    val lineIndex: Int,
    val charIndices: IntRange,
) : SchematicElement {
    class Builder(private val lineIndex: Int, digit: Int, private val charIndex: Int) : SchematicElement {
        private val digits = mutableListOf<Int>()

        init {
            digits.add(digit)
        }

        fun append(digit: Int): Builder {
            digits.add(digit)
            return this
        }

        fun build(): SchematicNumber = SchematicNumber(
            number = digits.joinToString("").toInt(), lineIndex = lineIndex, charIndices = charIndex..<charIndex + digits.size
        )
    }

}

data class SchematicSymbol(val lineIndex: Int, val charIndex: Int) : SchematicElement
data class SchematicEmpty(val lineIndex: Int, val charIndex: Int) : SchematicElement

private fun String.mapLineToSchematicsElements() = foldIndexed(emptyList<SchematicElement>()) { index, acc, char ->
    when {
        char.isDigit() -> {
            (acc.lastOrNull() as? SchematicNumber.Builder)
                ?.apply {
                    append(char.digitToInt())
                }
                ?.let { acc }
                ?: SchematicNumber.Builder(0, char.digitToInt(), index)
                    .let { acc + it }
        }

        char == '.' -> {
            acc + SchematicEmpty(0, index)
        }

        else -> {
            acc + SchematicSymbol(0, index)
        }
    }
}.map { (it as? SchematicNumber.Builder)?.build() ?: it }

fun main() {
    fun part1(input: List<String>): Int = input.sumOf {
        it.toInt()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val schematicsNumber = SchematicNumber.Builder(0, 4, 0)
        .append(6)
        .append(7)
        .build()
    check(schematicsNumber.lineIndex == 0)
    check(schematicsNumber.number == 467)
    check(schematicsNumber.charIndices == 0..2)

    val schematicElements = "467..114..".mapLineToSchematicsElements()
    check(
        schematicElements == listOf(
            SchematicNumber(467, 0, 0..2),
            SchematicEmpty(0, 3),
            SchematicEmpty(0, 4),
            SchematicNumber(114, 0, 5..7),
            SchematicEmpty(0, 8),
            SchematicEmpty(0, 9),
        )
    )

    val schematicElements2 = "...*......".mapLineToSchematicsElements()
    check(
        schematicElements2 == listOf(
            SchematicEmpty(0, 0),
            SchematicEmpty(0, 1),
            SchematicEmpty(0, 2),
            SchematicSymbol(0, 3),
            SchematicEmpty(0, 4),
            SchematicEmpty(0, 5),
            SchematicEmpty(0, 6),
            SchematicEmpty(0, 7),
            SchematicEmpty(0, 8),
            SchematicEmpty(0, 9),
        )
    )

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
