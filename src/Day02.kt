val ROCK = 1
val PAPER = 2
val SCICCORS = 3

val LOSS = 0
val TIE = 3
val WIN = 6

val inputMapping = mapOf(
        "A" to ROCK,
        "B" to PAPER,
        "C" to SCICCORS
)

fun <K, V> Map<K, V>.pivot(): Map<V, List<K>> = this.entries.groupBy({it.value}, {it.key})
fun <K, V> List<Pair<K, V>>.toMap(): Map<K, V> = this.associate { it.first to it.second }

val outcomesByGameState = mapOf(
        (ROCK to SCICCORS) to LOSS,
        (PAPER to ROCK) to LOSS,
        (SCICCORS to PAPER) to LOSS,
        (SCICCORS to ROCK) to WIN,
        (ROCK to PAPER) to WIN,
        (PAPER to SCICCORS) to WIN,
        (SCICCORS to SCICCORS) to TIE,
        (ROCK to ROCK) to TIE,
        (PAPER to PAPER) to TIE
)

val gameStatesByOutcome = outcomesByGameState
        .pivot()
        .mapValues { (_, values) ->  values.toMap() }

fun main() {

    val replyMaping = mapOf(
            "X" to ROCK,
            "Y" to PAPER,
            "Z" to SCICCORS
    )

    val desiredOutcomeMapping = mapOf(
            "X" to LOSS,
            "Y" to TIE,
            "Z" to WIN
    )

    val dayName = 2.toDayName()

    fun part1(input: List<String>): Int = input
            .map { it.split(" ") }
            .map { (userMove, reply) -> inputMapping.getValue(userMove) to replyMaping.getValue(reply) }
            .sumOf { gameState -> outcomesByGameState.getValue(gameState) + gameState.second}

    fun part2(input: List<String>): Int = input
            .map { it.split(" ") }
            .map { (userMove, desiredOutcome) -> inputMapping.getValue(userMove) to desiredOutcomeMapping.getValue(desiredOutcome) }
            .map { (userMove, desiredOutcome) -> desiredOutcome to gameStatesByOutcome.getValue(desiredOutcome).getValue(userMove) }
            .sumOf { (outcome, reply) -> outcome + reply }

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 15

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 14264

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 12

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 12382
}


