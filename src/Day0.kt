fun main() {
    val dayName = 0.toDayName();

    fun part1(input: List<String>): Int = input.size

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput("${dayName}_test")

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo null

    val input = readInput(dayName)

    val outputPart1 = part1(input)
    outputPart1 isEqualTo null

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo null

    val outputPart2 = part2(input)
    outputPart2 isEqualTo null
}
