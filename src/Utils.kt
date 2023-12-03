import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

data class Results<T>(val expectedTestResult: T, val testResult: T, val actualResut: T)

fun <T> printOutput(
    day: Int,
    part1: Results<T>,
    part2: Results<T>
) {
    println("********** Day $day **********")
    printTestResult(1, part1.expectedTestResult, part1.testResult)
    printTestResult(2, part2.expectedTestResult, part2.testResult)
    printRealDataOutput(1, part1.actualResut)
    printRealDataOutput(2, part2.actualResut)
}

private fun <T> printTestResult(part: Int, expected: T, actual: T) {
    val success = expected == actual
    val icon = if (success) "✅" else "❌"
    val status = if (success) "succeeded" else "failed"
    println("$icon part $part test data $status - expected: $expected actual: $actual $icon")
}

private fun <T> printRealDataOutput(part: Int, result: T) {
    println("🤖 part $part answer: $result")
}


