import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.time.measureTimedValue

fun main() {

    data class Point(val x: Int, val y: Int)

    fun List<String>.getGalaxies(): List<Point> = mapIndexed { y, line ->
        line.flatMapIndexed { x, c -> if (c == '#') listOf(Point(x, y)) else emptyList() }
    }.flatten()

    fun List<Point>.emptyRows(): List<Int> = (0..maxOf { it.y }).filter { y -> !any { it.y == y } }

    fun List<Point>.emptyColumns(): List<Int> = (0..maxOf { it.x }).filter { x -> !any { it.x == x } }

    fun List<Point>.sumOfLengths(multiplier: Long): Long {
        val emptyRows = emptyRows()
        val emptyColumns = emptyColumns()

        fun Point.shortestPathTo(point: Point): Long {
            val extraX = emptyColumns.count { (min(x, point.x)..max(x, point.x)).contains(it) } * (multiplier - 1)
            val xDiff = abs(x - point.x) + extraX
            val extraY = emptyRows.count { (min(y, point.y)..max(y, point.y)).contains(it) } * (multiplier - 1)
            val yDiff = abs(y - point.y).toLong() + extraY
            return xDiff + yDiff
        }

        fun IndexedValue<Point>.shortestPaths(): Long = takeLast(size - index).sumOf { value.shortestPathTo(it) }

        return withIndex().sumOf { it.shortestPaths() }
    }

    fun part1(input: List<String>): Long = input.getGalaxies().sumOfLengths(2)

    fun part2(input: List<String>): Long = input.getGalaxies().sumOfLengths(1000000)

    val testInput = readInput("Day11_test")
    val input = readInput("Day11")
    printOutput(
        day = 11,
        part1 = Results(
            expectedTestResult = 374L,
            testResult = measureTimedValue { part1(testInput) },
            actualResult = measureTimedValue { part1(input) }
        ),
        part2 = Results(
            expectedTestResult = 8410L,
            testResult = measureTimedValue { part2(testInput) },
            actualResult = measureTimedValue { part2(input) }
        ),
    )
}
