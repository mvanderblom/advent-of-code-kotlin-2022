fun main() {
    val dayName = "Day01"


    fun part1(input: List<Int?>): Int = input
            .mapIndexedNotNull { index, calories ->
                if (index == 0 || index == input.size - 1 || calories == null) index else null
            }
            .windowed(2, 1)
            .print()
            .maxOf { (from, to) -> input.subList(from, to).filterNotNull().sum() }

    fun part2(input: List<Int?>): Int = input.size

    val testInput = readInput("${dayName}_test")
            .map { it.toIntOrNull() }

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 24000

    val input = readInput(dayName)
            .map { it.toIntOrNull() }

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 69795

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo null

    val outputPart2 = part2(input)
    outputPart2 isEqualTo null
}
