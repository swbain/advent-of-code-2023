fun main() {

    data class Symbol(val x: Int, val y: Int, val symbol: Char)

    data class PartNumber(val x: IntRange, val y: Int, val value: Int) {
        val validX = x.first - 1..x.last + 1
        val validY = y - 1..y + 1
    }

    data class Data(val symbols: List<Symbol>, val partNumbers: List<PartNumber>)

    fun Char.isSymbol(): Boolean = !isDigit() && this != '.'

    fun String.rowData(y: Int): Data {
        val symbols = mapIndexedNotNull { index, c -> if (c.isSymbol()) Symbol(index, y, c) else null }
        val partNumbers = "\\d+".toRegex().findAll(this).map {
            PartNumber(
                x = it.range,
                y = y,
                value = it.value.toInt()
            )
        }.toList()

        return Data(symbols, partNumbers)
    }

    fun List<String>.data(): Data  = mapIndexed { index, s -> s.rowData(index) }.reduce { acc, data ->
        acc.copy(symbols = data.symbols + acc.symbols, partNumbers = data.partNumbers + acc.partNumbers)
    }

    infix fun PartNumber.isAdjacentTo(symbol: Symbol): Boolean = symbol.x in validX && symbol.y in validY

    fun List<String>.validPartNumbers(): List<PartNumber> {
        val data = data()
        fun PartNumber.isValid() = data.symbols.any { this isAdjacentTo it }
        return data.partNumbers.filter { it.isValid() }
    }

    fun Data.gearRatios(): Int = symbols.filter { it.symbol == '*' }.sumOf { symbol ->
        partNumbers.filter { it isAdjacentTo symbol }
            .takeIf { it.count() == 2 }
            ?.map(PartNumber::value)
            ?.reduce(Int::times) ?: 0
    }

    fun part1(input: List<String>): Int = input.validPartNumbers().sumOf { it.value }

    fun part2(input: List<String>): Int = input.data().gearRatios()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val input = readInput("Day03")
    printOutput(
        day = 3,
        part1 = Results(4361, runAndMeasure { part1(testInput) }, runAndMeasure { part1(input) }),
        part2 = Results(467835, runAndMeasure { part2(testInput) }, runAndMeasure { part2(input) }),
    )
}
