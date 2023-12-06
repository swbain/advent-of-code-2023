import kotlin.time.measureTimedValue

fun main() {
    data class Race(val time: Long, val recordDistance: Long)

    fun Race.winCount(): Int = (1..<time).count { it * (time - it) > recordDistance }

    fun String.trimmedToValues(): List<String> = split("\\s+".toRegex()).drop(1)

    fun String.raceValues(): List<Long> = trimmedToValues().map { it.toLong() }

    fun String.combinedNumbers(): Long = trimmedToValues().reduce { acc, s -> acc + s }.toLong()

    fun List<String>.races(): List<Race> = first().raceValues().zip(last().raceValues(), ::Race)

    fun List<String>.partTwoRace(): Race = Race(first().combinedNumbers(), last().combinedNumbers())

    fun part1(input: List<String>): Int {
        return input.races().map { it.winCount() }.reduce(Int::times)
    }

    fun part2(input: List<String>): Int {
        return input.partTwoRace().winCount()
    }

    val testInput = readInput("Day06_test")
    val input = readInput("Day06")
    printOutput(
        day = 6,
        part1 = Results(
            expectedTestResult = 288,
            testResult = measureTimedValue { part1(testInput) },
            actualResult = measureTimedValue { part1(input) }
        ),
        part2 = Results(
            expectedTestResult = 71503,
            testResult = measureTimedValue { part2(testInput) },
            actualResult = measureTimedValue { part2(input) }
        ),
    )
}
