fun String.toIntRange(delimiter: String): IntRange {
    val (from, to) = this
            .split(delimiter)
            .map { it.toInt() }
    return IntRange(from, to)
}

fun IntRange.contains(other: IntRange): Boolean = this.first <= other.first && other.last <= this.last

fun main() {
    val dayName = 4.toDayName()

    fun parseRange(it: String) = it
            .split(",")
            .map { it.toIntRange("-") }

    fun parseRanges(input: List<String>) = input
            .map(::parseRange)

    fun part1(input: List<String>): Int = parseRanges(input)
            .count { (first, second) -> first.contains(second) || second.contains(first) }

    fun part2(input: List<String>): Int = parseRanges(input)
            .count { (first, second) -> first.intersect(second).isNotEmpty() }

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    // Part 1

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 2

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 450

    // Part 2

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 4

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 837
}
