
fun main() {
    val dayName = 3.toDayName();

    fun split(it: String): Pair<String, String> = it.substring(0, it.length/2) to  it.substring(it.length/2, it.length)

    fun determineMatchingChar(pair: Pair<String, String>): Char =
            pair.first.toSet().intersect(pair.second.toSet()).first()

    val allLetters = ('a'..'z') + ('A'..'Z')
            .map { it }
    fun getPriority(c: Char?): Int = allLetters.indexOf(c) + 1


    fun part1(input: List<String>): Int {
        val x = input
                .map(::split)
                .showMe()
                .map(::determineMatchingChar)
                .showMe()
                .map(::getPriority)
                .sum()
        return x
    }

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput("${dayName}_test")

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 157

    val input = readInput(dayName)

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 7716

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo null

    val outputPart2 = part2(input)
    outputPart2 isEqualTo null
}
