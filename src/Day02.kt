enum class Value(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCICCORS(3);
    fun beats(other: Value) = when {
        this == other -> GameOutcome.TIE
        this == ROCK && other == SCICCORS -> GameOutcome.WIN
        this == SCICCORS && other == ROCK -> GameOutcome.LOSS
        this.ordinal > other.ordinal -> GameOutcome.WIN
        else -> GameOutcome.LOSS
    }
}

enum class GameOutcome(val score: Int) {
    WIN(6),
    LOSS(0),
    TIE(3)
}

fun main() {
    val inputMapping = mapOf(
            "A" to Value.ROCK,
            "B" to Value.PAPER,
            "C" to Value.SCICCORS
    )

    val outputMapping = mapOf(
            "X" to Value.ROCK,
            "Y" to Value.PAPER,
            "Z" to Value.SCICCORS
    )

    val desiredOutputMapping = mapOf(
            "X" to GameOutcome.LOSS,
            "Y" to GameOutcome.TIE,
            "Z" to GameOutcome.WIN
    )

    val actionsByDesiredOutcome = mapOf(
            GameOutcome.LOSS to mapOf(
                    Value.ROCK to Value.SCICCORS,
                    Value.PAPER to Value.ROCK,
                    Value.SCICCORS to Value.PAPER
            ),
            GameOutcome.WIN to mapOf(
                    Value.SCICCORS to Value.ROCK,
                    Value.ROCK to Value.PAPER,
                    Value.PAPER to Value.SCICCORS
            ),
            GameOutcome.TIE to mapOf(
                    Value.SCICCORS to Value.SCICCORS,
                    Value.ROCK to Value.ROCK,
                    Value.PAPER to Value.PAPER
            )
    )

    val dayName = 2.toDayName();

    fun part1(input: List<String>): Int = input
            .map { it.split(" ") }
            .map { (userMove, reply) -> inputMapping.getValue(userMove) to outputMapping.getValue(reply) }
            .sumOf { (userMove, reply) -> reply.beats(userMove).score + reply.score }

    fun part2(input: List<String>): Int = input
            .map { it.split(" ") }
            .map { (userMove, desiredOutcome) -> inputMapping.getValue(userMove) to desiredOutputMapping.getValue(desiredOutcome) }
            .map { (userMove, desiredOutcome) -> desiredOutcome to actionsByDesiredOutcome.getValue(desiredOutcome).getValue(userMove) }
            .sumOf { (outcome, reply) -> outcome.score + reply.score };

    val testInput = readInput("${dayName}_test")

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 15

    val input = readInput(dayName)

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 14264

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 12

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 12382
}


