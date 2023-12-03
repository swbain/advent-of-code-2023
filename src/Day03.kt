fun main() {

    data class Symbol(val x: Int, val y: Int, val symbol: Char)

    data class PartNumber(val startX: Int, val endX: Int, val y: Int, val value: Int)

    data class Data(val symbols: List<Symbol>, val partNumbers: List<PartNumber>)

    fun Char.isSymbol(): Boolean = !isDigit() && this != '.'

    fun String.rowData(y: Int): Data {
        val symbols = mapIndexedNotNull { index, c -> if (c.isSymbol()) Symbol(index, y, c) else null }
        val partNumbers = "\\d+".toRegex().findAll(this).map {
            PartNumber(
                startX = it.range.first,
                endX = it.range.last,
                y = y,
                value = it.value.toInt()
            )
        }.toList()

        return Data(symbols, partNumbers)
    }

    fun List<String>.data(): Data {
        val symbols = mutableListOf<Symbol>()
        val partNumbers = mutableListOf<PartNumber>()

        forEachIndexed { index, s ->
            val rowData = s.rowData(index)
            symbols.addAll(rowData.symbols)
            partNumbers.addAll(rowData.partNumbers)
        }

        return Data(symbols, partNumbers)
    }

    infix fun PartNumber.isAdjacentTo(symbol: Symbol): Boolean = when (y) {
        symbol.y -> symbol.x == startX - 1 || symbol.x == endX + 1
        symbol.y - 1, symbol.y + 1 -> symbol.x in (startX - 1)..(endX + 1)
        else -> false
    }

    fun List<String>.validPartNumbers(): List<PartNumber> {

        val data = data()

        fun PartNumber.isValid() = data.symbols.any { this isAdjacentTo it }

        return data.partNumbers.filter { it.isValid() }
    }

    fun Data.gearRatios(): Int {

        return symbols.filter { it.symbol == '*' }.sumOf { symbol ->
            val adjacentParts = partNumbers.filter { it isAdjacentTo symbol }
            if (adjacentParts.count() == 2) adjacentParts.first().value * adjacentParts.last().value
            else 0
        }
    }

    fun part1(input: List<String>): Int {
        return input.validPartNumbers().sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        return input.data().gearRatios()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
