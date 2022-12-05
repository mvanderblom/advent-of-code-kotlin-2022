import java.util.*

data class Move(val amount: Int, val from: Int, val to: Int )
data class GameState(val stacks: List<Stack<String>>, val moves: List<Move>) {
    fun getTopLevelCrates() = stacks.joinToString("") { it.last() }
}

fun String.replaceAll(searches: List<String>, replacement: String): String {
    var s = this
    searches.forEach{ s = s.replace(it, replacement)}
    return s
}

fun main() {
    val dayName = 5.toDayName()

    fun parseLoadingArea(rows: List<String>): List<Stack<String>> {
        var parsedRows = rows
                .map { it.chunked(4).map { cell -> cell.replaceAll(listOf(" ", "[", "]"), "") } }
                .reversed()

        val stacks = (0 until parsedRows.first().size).map { Stack<String>() }

        parsedRows.subList(1, parsedRows.size)
                .forEach { row -> row
                        .forEachIndexed{ i, it -> if(it.isNotEmpty()) stacks[i].push(it) }}

        return stacks
    }

    fun parseMoves(moves: List<String>): List<Move> {
        return moves
                .map { it.replaceAll(listOf("move ", "from ", "to "), "") }
                .map { it.split(" ").map { it.toInt() } }
                .map { (amount, from, to) -> Move(amount, from, to) }
    }

    fun parseGameState(input: List<String>): GameState {
        val splitPoint = input.indexOfFirst { it.isEmpty() }
        val stacks = parseLoadingArea(input.subList(0, splitPoint))
        val moves = parseMoves(input.subList(splitPoint + 1, input.size))
        return GameState(stacks, moves)
    }

    fun lafoStrategy(move: Move, state: GameState): Unit {
        (0 until move.amount).forEach { _ ->
            state.stacks[move.to - 1].push(state.stacks[move.from - 1].pop())
        }
    }

    fun fifoStrategy(move: Move, state: GameState): Unit {
        val x = mutableListOf<String>()
        (0 until move.amount).forEach { _ ->
            x.add(state.stacks[move.from - 1].pop())
        }
        x.reversed().forEach {
            state.stacks[move.to - 1].push(it)
        }
    }


    fun part1(input: List<String>, strategy: (Move, GameState) -> Unit): String {
        val state = parseGameState(input)
        state.moves.forEach { move -> strategy(move, state)}
        return state.getTopLevelCrates()
    }

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    // Part 1

    val testOutputPart1 = part1(testInput, ::lafoStrategy)
    testOutputPart1 isEqualTo "CMZ"

    val outputPart1 = part1(input, ::lafoStrategy)
    outputPart1 isEqualTo "MQTPGLLDN"

    // Part 2

    val testOutputPart2 = part1(testInput, ::fifoStrategy)
    testOutputPart2 isEqualTo "MCD"

    val outputPart2 = part1(input, ::fifoStrategy)
    outputPart2 isEqualTo "LVZPSTTCZ"
}
