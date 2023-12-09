import kotlin.time.measureTimedValue

fun main() {

    fun List<Long>.getDifferences(): List<Long> = windowed(2) { it.last() - it.first() }

    fun List<Long>.getSteps(): List<List<Long>> {
        var diffs = this
        val steps = mutableListOf(this)
        while (!diffs.all { it == 0L }) {
            diffs = diffs.getDifferences()
            steps.add(diffs)
        }
        return steps
    }

    fun List<Long>.nextValue(): Long {
        val steps = getSteps().reversed()
        val newSteps = mutableListOf<List<Long>>()
        steps.forEachIndexed { index, longs ->
            if (index == 0) newSteps.add(longs)
            else newSteps.add(longs + (longs.last() + newSteps[index - 1].last()))
        }
        return newSteps.last().last()
    }

    fun getValues(input: String): List<Long> = input.split(" ").map { it.toLong() }

    fun part1(input: List<String>): Long = input.sumOf { getValues(it).nextValue() }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day09_test")
    val input = readInput("Day09")
    printOutput(
        day = 9,
        part1 = Results(
            expectedTestResult = 114L,
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
