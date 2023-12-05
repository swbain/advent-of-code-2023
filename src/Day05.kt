import kotlin.time.measureTimedValue

fun main() {

    data class Mapping(val destination: IntRange, val source: IntRange)

    fun String.toMapping(): Mapping = split(' ').map { it.trim().toInt() }.let {
        Mapping(
            destination = it[0]..<it[0] + it[2],
            source = it[1]..<it[1] + it[2]
        )
    }

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day05_test")
    val input = readInput("Day05")
    printOutput(
        day = 5,
        part1 = Results(
            expectedTestResult = 0,
            testResult = measureTimedValue { part1(testInput) },
            actualResult = measureTimedValue { part1(input) }
        ),
        part2 = Results(
            expectedTestResult = 0,
            testResult = measureTimedValue { part2(testInput) },
            actualResult = measureTimedValue { part2(input) }
        ),
    )
}