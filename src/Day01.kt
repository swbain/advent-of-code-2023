fun main() {

    fun String.calibrationValueAfterReplace(): Int {
        return first { it.isDigit() }.digitToInt() * 10 + last { it.isDigit() }.digitToInt()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { it.calibrationValueAfterReplace() }
    }

    val wordMap = mapOf(
        "one" to "o1e",
        "two" to "t2o",
        "three" to "t3e",
        "four" to "4",
        "five" to "5e",
        "six" to "6",
        "seven" to "7n",
        "eight" to "e8t",
        "nine" to "9e"
    )

    fun String.replaceTextWithDigits(): String {
        var line = this
        wordMap.forEach { (key, value) ->
            line = line.replace(key, value)
        }
        return line
    }

    fun part2(input: List<String>): Int = input.sumOf { it.replaceTextWithDigits().calibrationValueAfterReplace() }

    val input = readInput("Day01")
    "part 1: ${part1(input)}".println()
    "part 2: ${part2(input)}".println()
}
