fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")
    printOutput(
        day = 4,
        part1 = Results(
            expectedTestResult = 0,
            testResult = runAndMeasure { part1(testInput) },
            actualResult = runAndMeasure { part1(input) }
        ),
        part2 = Results(
            expectedTestResult = 0,
            testResult = runAndMeasure { part2(testInput) },
            actualResult = runAndMeasure { part2(input) }
        ),
    )
}
