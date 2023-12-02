fun main() {

    fun String.isColorValid(): Boolean = split(" ").run {
        val blockCount = first().toInt()
        when (last()) {
            "red" -> blockCount <= 12
            "green" -> blockCount <= 13
            "blue" -> blockCount <= 14
            else -> false // illegal
        }
    }

    fun String.isSetValid(): Boolean = split(", ").all { it.isColorValid() }

    fun String.areAllSetsValid(): Boolean = split("; ").all { it.isSetValid() }

    fun String.idValue(): Int {
        // return game id if all sets pulled are valid, otherwise return zero
        val split = removePrefix("Game ").split(": ")
        return if (split.last().areAllSetsValid()) split.first().toInt() else 0
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { it.idValue() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}