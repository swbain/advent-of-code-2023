import kotlin.time.measureTimedValue

fun main() {

    data class Card(val yours: Set<Int>, val winning: Set<Int>, val id: Int)

    fun Card.value(): Int = (winning intersect yours).fold(0) { acc, _ -> if (acc == 0) 1 else acc * 2 }

    fun Card.winners(): Int = (winning intersect yours).count()

    fun String.toCardValues(): Set<Int> = split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()

    fun String.toCard(): Card = split(": ").run {
        last().split("|").let {
            Card(
                id = first().split(" ").last().toInt(),
                yours = it.first().toCardValues(),
                winning = it.last().toCardValues()
            )
        }
    }

    fun Card.idsToUpdate(): IntRange = winners().takeIf { it > 0 }?.let { id + 1..id + it } ?: IntRange.EMPTY

    fun List<Card>.finalCount(): Int {
        val map = associateTo(mutableMapOf()) { it.id to 1 }
        for (card in this) {
            for (id in card.idsToUpdate()) map[id] = map.getValue(id) + map.getValue(card.id)
        }
        return map.values.sum()
    }

    fun List<String>.toCards(): List<Card> = map { it.toCard() }

    fun part1(input: List<String>): Int {
        return input.toCards().sumOf { it.value() }
    }

    fun part2(input: List<String>): Int {
        return input.toCards().finalCount()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")
    printOutput(
        day = 4,
        part1 = Results(
            expectedTestResult = 13,
            testResult = measureTimedValue { part1(testInput) },
            actualResult = measureTimedValue { part1(input) }
        ),
        part2 = Results(
            expectedTestResult = 30,
            testResult = measureTimedValue { part2(testInput) },
            actualResult = measureTimedValue { part2(input) }
        ),
    )
}
