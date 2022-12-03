
fun main() {
    val dayName = 3.toDayName()

    fun split(it: String): Pair<String, String> = it.substring(0, it.length/2) to  it.substring(it.length/2, it.length)

    fun String.intersect(other: String): Set<Char> = this.toSet().intersect(other.toSet())

    val allLetters = ('a'..'z') + ('A'..'Z')
    fun getPriority(c: Char?): Int = allLetters.indexOf(c) + 1

    fun part1(input: List<String>): Int = input
            .map(::split)
            .map { (a, b) -> a.intersect(b).single() }
            .map(::getPriority)
            .sum()

    fun intersect(first: String, second: String, third: String): Set<Char> {
        return first.intersect(second).joinToString("").intersect(third)
    }

    fun part2(input: List<String>): Int = input
            .chunked(3)
            .map { (a, b, c) -> intersect(a,b,c).first() }
            .map(::getPriority)
            .sum()

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
