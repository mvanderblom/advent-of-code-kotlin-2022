fun main() {
    val dayName = 1.toDayName();

    fun getCaloriesPerElf(input: List<Int?>) = input
            .mapIndexedNotNull { index, calories ->
                when {
                    (index == 0 || calories == null) -> index
                    (index == input.size - 1) -> input.size
                    else -> null
                }
            }
            .windowed(2, 1)
            .map { (from, to) -> input.subList(from, to).filterNotNull() }
            .map { it.sum() }
            .sortedDescending()

    fun part1(input: List<Int?>): Int = getCaloriesPerElf(input)
            .max()

    fun part2(input: List<Int?>): Int = getCaloriesPerElf(input)
            .subList(0,3)
            .sum()

    val testInput = readInput("${dayName}_test")
            .map { it.toIntOrNull() }

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 24000

    val input = readInput(dayName)
            .map { it.toIntOrNull() }

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 69795

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 45000

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 208437
}
