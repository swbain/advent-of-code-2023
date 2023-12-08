import kotlin.time.measureTimedValue

@OptIn(ExperimentalStdlibApi::class)
fun main() {

    data class Card(val value: Char, val rank: Char)

    val validCards: Map<Char, Char> = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        .mapIndexed { index, c -> c to index.toHexString().last() }
        .associate { it }

    data class Hand(val cards: List<Card>, val bid: Long) {

        /**
         * score of current hand
         * score values:
         * - 0 points for high card
         * - 1 point for one pair
         * - 2 points for two pair
         * - 3 points for three of a kind
         * - 4 points for full house
         * - 5 points for four of a kind
         * - 6 points for five of a kind
         */
        val score: Long = cards
            .groupBy { it.value }
            .mapValues { it.value.count() }
            .let { map ->
                when (map.size) {
                    1 -> 6 // five of a kind
                    2 -> map.entries.first().run { if (value == 1 || value == 4) 5 else 4 } // could be four of a kind or full house
                    3 -> if (map.entries.any { it.value == 3 }) 3 else 2 // three of a kind or two pair
                    4 -> 1 // one pair
                    else -> 0 // high card
                }
            }

        val scoreHash: Long = score + cards.map { it.rank }.joinToString("").hexToLong()

        override fun toString(): String {
            return cards.map { it.value }.joinToString("")
        }
    }

    fun String.toCards(): List<Card> = map { Card(value = it, rank = validCards.getValue(it)) }

    fun String.toHand(): Hand = split(" ").run { Hand(first().toCards(), last().toLong()) }

    fun part1(input: List<String>): Long {
        return input.map { it.toHand() }
            .groupBy { it.score }
            .mapValues { entry -> entry.value.sortedByDescending { it.scoreHash } }
            .entries
            .asSequence()
            .sortedBy { it.key }
            .map { it.value }
            .flatten().mapIndexed { index, hand -> hand to index + 1L }
            .fold(0L) { acc, pair ->
                acc + pair.first.bid * pair.second
            }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")
    printOutput(
        day = 7,
        part1 = Results(
            expectedTestResult = 6440L,
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
