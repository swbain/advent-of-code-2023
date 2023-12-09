import kotlin.time.measureTimedValue

fun main() {
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

    fun Map<String, Pair<String, String>>.getCount(startingNode: String, commands: List<Char>): Long {
        var count = 0L
        var current = startingNode
        while (current.last() != 'Z') {
            for (command in commands) {
                count++
                current = getValue(current).let { if (command == 'L') it.first else it.second }
                if (current.last() == 'Z') return count
            }
        }
        return count
    }

    fun Input.nodeMap(): Map<String, Pair<String, String>> = nodes.associate { it.value to (it.left to it.right) }

    fun part1(input: List<String>): Long {
        val parsed = parseInput(input)
        return parsed.nodeMap().getCount(startingNode = "AAA", commands = parsed.commands.toList())
    }

    fun findGcd(x: Long, y: Long): Long {
        val mod = x % y
        return if (mod == 0L) y
        else findGcd(y, mod)
    }

    fun findLcm(x: Long, y: Long): Long {
        return x / findGcd(x, y) * y
    }

    fun part2(input: List<String>): Long {
        val parsed = parseInput(input)
        val starts = parsed.nodes.map { it.value }.filter { it.last() == 'A' }
        val actions = starts.map {
            parsed.nodeMap().getCount(startingNode = it, commands = parsed.commands.toList())
        }
        return actions.reduce { acc, i -> findLcm(acc, i) }
    }

    val testInput = readInput("Day08_test")
    val testInput2 = readInput("Day08_test2")
    val input = readInput("Day08")
    printOutput(
        day = 8,
        part1 = Results(
            expectedTestResult = 6,
            testResult = measureTimedValue { part1(testInput) },
            actualResult = measureTimedValue { part1(input) }
        ),
        part2 = Results(
            expectedTestResult = 6L,
            testResult = measureTimedValue { part2(testInput2) },
            actualResult = measureTimedValue { part2(input) }
        ),
    )
}
