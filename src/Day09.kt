import kotlin.math.abs

operator fun Pair<Int,Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> = this.first - other.first to this.second - other.second
fun main() {
    val dayName = 9.toDayName()
    val moves = mapOf(
            "L" to { coords: Pair<Int, Int> -> coords.first - 1 to coords.second },
            "R" to { coords: Pair<Int, Int> -> coords.first + 1 to coords.second },
            "D" to { coords: Pair<Int, Int> -> coords.first to coords.second - 1 },
            "U" to { coords: Pair<Int, Int> -> coords.first to coords.second + 1 }
    )

    val tailMovesByDistance = mapOf(
            2 to 0 to listOf("R"),
            -2 to 0 to listOf("L"),
            0 to 2 to listOf("U"),
            0 to -2 to listOf("D"),
            1 to 2 to listOf("R", "U"),
            2 to 1 to listOf("R", "U"),
            2 to -1 to listOf("R", "D"),
            1 to -2 to listOf("R", "D"),
            -1 to -2 to listOf("L", "D"),
            -2 to -1 to listOf("L", "D"),
            -1 to 2 to listOf("L", "U"),
            -2 to 1 to listOf("L", "U")
    )


    fun getMoves(input: List<String>) = input
            .map { it.split(" ") }
            .flatMap { (direction, amount) ->
                (1..amount.toInt()).map { direction }
            }

    fun part1(input: List<String>): Int {
        var head = 0 to 0
        var tail = 0 to 0
        println("head: $head, tail: $tail")
        val tailPositions = getMoves(input).map {
                    println(it)
                    head = moves.getValue(it)(head)
                    val distance = head - tail
                    if (abs(distance.first) >= 2 || abs(distance.second) >= 2) {
                        println("head moved to $head, moving tail by $distance")
                        tailMovesByDistance.getValue(distance)
                                .forEach { tail = moves.getValue(it)(tail) }
                    }
                    println("head: $head, tail: $tail")
                    tail
                }

        return tailPositions.toSet().size
    }

    fun part2(input: List<String>): Int = input.size

    val input = readInput(dayName)

    // Part 1

    val testOutputPart1 = part1(readInput("${dayName}_test"))
    testOutputPart1 isEqualTo 13

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 6044

    // Part 2

    val testOutputPart2 = part2(readInput("${dayName}_test2"))
    testOutputPart2 isEqualTo 36

    val outputPart2 = part2(input)
    outputPart2 isEqualTo null
}

