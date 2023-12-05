import kotlin.time.measureTimedValue

fun main() {

    data class Mapping(val destination: LongRange, val source: LongRange)

    data class Component(val mappings: List<Mapping>)

    infix fun Long.nextValueFrom(component: Component): Long {
        return component.mappings
            .firstOrNull { it.source.contains(this) }
            ?.let {
                val diff = this - it.source.first
                it.destination.first + diff
            }
            ?: this
    }

    infix fun Long.locationFrom(components: List<Component>): Long {
        return components.fold(this) { acc, component ->
            acc nextValueFrom component
        }
    }

    fun String.toMapping(): Mapping = split(' ').filter { it.isNotEmpty() }.map { it.trim().toLong() }.let {
        Mapping(
            destination = it[0]..<it[0] + it[2],
            source = it[1]..<it[1] + it[2]
        )
    }

    fun List<String>.seeds(): List<Long> {
        return first().split(": ").last().split(" ").map { it.toLong() }
    }

    fun List<String>.components(): List<Component> {
        val components = mutableListOf<Component>()
        var currentMappings = mutableListOf<Mapping>()
        for (line in drop(2)) {
            if (line.isEmpty()) {
                components.add(Component(currentMappings))
                currentMappings= mutableListOf()
            } else if (line.first().isDigit()) currentMappings.add(line.toMapping())
        }
        components.add(Component(currentMappings))
        return components
    }

    fun part1(input: List<String>): Long {
        return input.seeds().minOf { it locationFrom input.components() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day05_test")
    val input = readInput("Day05")
    printOutput(
        day = 5,
        part1 = Results(
            expectedTestResult = 35L,
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