data class Bag(val redsCount: Int, val greensCount: Int, val bluesCount: Int)
data class GameRecord(val id: Int, val revealedCubeSubsets: List<RevealedCubeSubset>) {
    data class RevealedCubeSubset(
        val revealedRed: Int = 0,
        val revealedGreens: Int = 0,
        val revealedBlues: Int = 0,
    )

    companion object {
        fun fromInputLine(inputLine: String): GameRecord {
            val (gameIdString, revealedCubeSubsetsAsString) = inputLine.split(':')
            val id = gameIdString.filter(Char::isDigit).toInt()
            val listOfSubsetStrings = revealedCubeSubsetsAsString.split(';')
            val revealedCubeSubsets = listOfSubsetStrings.map { revealedCubeSubsetString ->
                val reveals = revealedCubeSubsetString.split(',')

                val revealedRed: Int = reveals.find { it.contains("red") }?.filter(Char::isDigit)?.toIntOrNull() ?: 0
                val revealedGreens: Int = reveals.find { it.contains("green") }?.filter(Char::isDigit)?.toIntOrNull() ?: 0
                val revealedBlues: Int = reveals.find { it.contains("blue") }?.filter(Char::isDigit)?.toIntOrNull() ?: 0

                RevealedCubeSubset(revealedRed, revealedGreens, revealedBlues)
            }

            return GameRecord(id, revealedCubeSubsets)
        }
    }
}

val expectedDay02testGameRecords = listOf(
    GameRecord(
        1, listOf(
            GameRecord.RevealedCubeSubset(revealedBlues = 3, revealedRed = 4),
            GameRecord.RevealedCubeSubset(revealedRed = 1, revealedGreens = 2, revealedBlues = 6),
            GameRecord.RevealedCubeSubset(revealedGreens = 2)
        )
    ),
    GameRecord(
        2, listOf(
            GameRecord.RevealedCubeSubset(revealedBlues = 1, revealedGreens = 2),
            GameRecord.RevealedCubeSubset(revealedGreens = 3, revealedBlues = 4, revealedRed = 1),
            GameRecord.RevealedCubeSubset(revealedGreens = 1, revealedBlues = 1)
        )
    ),
    GameRecord(
        3, listOf(
            GameRecord.RevealedCubeSubset(revealedGreens = 8, revealedBlues = 6, revealedRed = 20),
            GameRecord.RevealedCubeSubset(revealedBlues = 5, revealedRed = 4, revealedGreens = 13),
            GameRecord.RevealedCubeSubset(revealedGreens = 5, revealedRed = 1)
        )
    ),
    GameRecord(
        4, listOf(
            GameRecord.RevealedCubeSubset(revealedGreens = 1, revealedRed = 3, revealedBlues = 6),
            GameRecord.RevealedCubeSubset(revealedGreens = 3, revealedRed = 6),
            GameRecord.RevealedCubeSubset(revealedGreens = 3, revealedBlues = 15, revealedRed = 14)
        )
    ),
    GameRecord(
        5, listOf(
            GameRecord.RevealedCubeSubset(revealedRed = 6, revealedBlues = 1, revealedGreens = 3),
            GameRecord.RevealedCubeSubset(revealedBlues = 2, revealedRed = 1, revealedGreens = 2)
        )
    )
)

fun main() {
    /**
     * Determine which games would have been possible if the bag had been loaded with only
     * 12 red cubes,
     * 13 green cubes and
     * 14 blue cubes.
     * What is the sum of the IDs of those games?
     */
    fun part1(gameRecords: List<GameRecord>): Int {
        val loadedBag = Bag(
            redsCount = 12,
            greensCount = 13,
            bluesCount = 14
        )
        val (recordsOfPossibleGames) = gameRecords.partition { record ->
            record.revealedCubeSubsets.all {
                it.revealedRed <= loadedBag.redsCount &&
                        it.revealedGreens <= loadedBag.greensCount &&
                        it.revealedBlues <= loadedBag.bluesCount
            }
        }
        return recordsOfPossibleGames.sumOf(GameRecord::id)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val parsedDay02GameRecords = testInput.map(GameRecord::fromInputLine)
    check(parsedDay02GameRecords == expectedDay02testGameRecords).also { "Passed parsing Day02_test.txt test".println() }
    check(part1(parsedDay02GameRecords) == 8).also { "Passed calculating result for Day02 test".println() }

    val input = readInput("Day02")
    part1(input.map(GameRecord::fromInputLine)).println()
    part2(input).println()
}
