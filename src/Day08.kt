import kotlin.time.measureTimedValue

fun main() {
    fun <T> List<T>.forEachWhile(predicate: () -> Boolean, onEach: (T) -> Unit) {
        var index = 0
        if (isNotEmpty()) {
            while (predicate()) {
                onEach(get(index))
                index++
                if (index == count()) index = 0
            }
        }
    }

    data class Node(val value: String, val left: String, val right: String)

    data class Input(val commands: String, val nodes: List<Node>)

    fun List<String>.toNodes(): List<Node> = map {
        val split = it.split(" = ")
        val connectedNodes = split.last().trim('(', ')').split(", ")
        Node(
            value = split[0],
            left = connectedNodes.first(),
            right = connectedNodes.last()
        )
    }

    fun parseInput(input: List<String>): Input = Input(
        commands = input.first(),
        nodes = input.drop(2).toNodes()
    )

    fun part1(input: List<String>): Int {
        val parsed = parseInput(input)
        val nodeMap = parsed.nodes.associate { it.value to (it.left to it.right) }
        var count = 0
        var current = "AAA"
        parsed.commands.toList().forEachWhile(predicate = { current != "ZZZ" }) { command ->
            count++
            current = nodeMap.getValue(current).let { if (command == 'L') it.first else it.second }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day08_test")
    val input = readInput("Day08")
    printOutput(
        day = 8,
        part1 = Results(
            expectedTestResult = 6,
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
