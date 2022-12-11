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

    fun display(width: Int, height: Int, arr: Array<Pair<Int,Int>>) {
        repeat(height) {y ->
            repeat(width) {x ->
                val coord = x to (height-y)
                when {
                    arr[0] == coord -> print("H")
                    arr.last() == coord -> print("T")
                    arr.contains(coord) -> print("o")
                    else -> print(".")
                }
            }
            println()
        }
        println()
    }

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

    fun follow(head: Pair<Int, Int>, tail: List<Pair<Int, Int>>): List<Pair<Int, Int>> {

        val distance = head - tail[0]
        if (abs(distance.first) >= 2 || abs(distance.second) >= 2) {
            println("head moved to $head, moving tail by $distance")
            val tailPath = tailMovesByDistance.getValue(distance)
                    .map { moves.getValue(it)(tail[0]) }
            if( tail.size == 1 )
                return tailPath
        }
        return follow(tail[0], tail.subList(1, tail.size))
    }

    fun part2(input: List<String>): Int {
        val instructions = input
                .map { it.split(" ") }
                .map { (direction, amount) -> direction to amount.toInt() }

        val tailPath = mutableListOf<Pair<Int, Int>>()

        val rope = Array(10) { Pair(15,15) }
        display(25, 25, rope)
        instructions.forEach { (direction, amount) ->
            println("$direction $amount")
            repeat(amount) {
                rope[0] = moves.getValue(direction)(rope[0])
                (0 until rope.size - 1).forEach { i ->
                    val head = rope[i]
                    val tail = rope[i + 1]
                    val distance = head - tail

                    if (abs(distance.first) >= 2 || abs(distance.second) >= 2) {
                        val path = tailMovesByDistance
                                .getValue(distance)
                                .map {
                                    val newPosition = moves.getValue(it)(tail)
                                    rope[i+1] = newPosition
                                    newPosition
                                }
                        if(i == rope.size - 2) {
                            tailPath.addAll(path)
                        }
                    }
                }
                display(25, 25, rope)
            }
        }


        return 1
    }

    val input = readInput(dayName)

    // Part 1

//    val testOutputPart1 = part1(readInput("${dayName}_test"))
//    testOutputPart1 isEqualTo 13
//
//    val outputPart1 = part1(input)
//    outputPart1 isEqualTo 6044

    // Part 2

    val testOutputPart2 = part2(readInput("${dayName}_test2"))
    testOutputPart2 isEqualTo 36

    val outputPart2 = part2(input)
    outputPart2 isEqualTo null
}

