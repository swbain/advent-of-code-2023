fun main() {

    fun String.sumAfterReplace(): Int {
        return first { it.isDigit() }.digitToInt() * 10 + last { it.isDigit() }.digitToInt()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {  line ->
            line.sumAfterReplace()
        }
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

    fun part2(input: List<String>): Int {
        return input.sumOf { lineIn ->
            var line = lineIn
            wordMap.forEach { (key, value) ->
                line = line.replace(key, value)
            }
            line.sumAfterReplace()
        }
    }

    val input = readInput("Day01")
    "part 1: ${part1(input)}".println()
    "part 2: ${part2(input)}".println()
}
