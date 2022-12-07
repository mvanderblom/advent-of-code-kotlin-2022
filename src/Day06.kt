fun main() {
    val dayName = 6.toDayName()

    fun part1(input: String, windowSize: Int = 4): Int = input
            .windowed(windowSize)
            .indexOfFirst { it.toSet().size == windowSize } + windowSize

    val testInput = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
    val input = readInput(dayName)[0]

    // Part 1

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 7

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 1238

    // Part 2

    val testOutputPart2 = part1(testInput, 14)
    testOutputPart2 isEqualTo 19

    val outputPart2 = part1(input, 14)
    outputPart2 isEqualTo null
}
