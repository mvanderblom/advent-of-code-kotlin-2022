fun String.inHalf(): List<String> = listOf(
        this.substring(0, this.length/2),
        this.substring(this.length/2, this.length)
)

fun List<String>.intersection(): Set<Char> = this
            .map { it.toSet() }
            .reduceIndexed { i, acc, it -> if (i == 0) it else acc.intersect(it) }

val allLetters = ('a'..'z') + ('A'..'Z')
fun Char.toPriority(): Int = allLetters.indexOf(this) + 1

fun main() {
    val dayName = 3.toDayName()

    fun part1(input: List<String>): Int = input
            .map { it.inHalf() }
            .map { it.intersection().single() }
            .sumOf { it.toPriority() }

    fun part2(input: List<String>): Int = input
            .chunked(3)
            .map { it.intersection().single() }
            .sumOf { it.toPriority() }

    val testInput = readInput("${dayName}_test")

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 157

    val input = readInput(dayName)

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 7716

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 70

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 2973
}
