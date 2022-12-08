fun List<Int>.takeUntilIncluding(predicate: (Int) -> Boolean): List<Int> {
    val toIndex = this.indexOfFirst(predicate)
    return when {
        toIndex == -1 || (toIndex == 0 && this.size == 1) -> this
        toIndex == 0 ->  emptyList()
        else -> this.subList(0, toIndex+1)
    }
}

class Forest(val input: List<String>) {

    private val rows = input.size
    private val cols= input[0].length
    private val grid = Array(rows) { Array(cols) { 0 } }

    init {
        input.forEachIndexed { rowNum, row ->
            row.forEachIndexed { colNum, cell ->
               this.grid[rowNum][colNum] = cell.digitToInt()
            }
        }
    }

    fun countVisisbleNodes(): Int = grid.mapIndexed { y: Int, row: Array<Int> ->
        row.mapIndexed { x: Int, cell: Int ->
            if (isEdge(x, y) || isOnlySurroundedByLowerTrees(x, y)) {
                true
            } else
                null
        }
    }.flatten().filterNotNull().count()

    private fun isEdge(x: Int, y: Int): Boolean = x == 0 || y == 0 || x == cols-1 || y == rows-1

    private fun isOnlySurroundedByLowerTrees(x: Int, y: Int): Boolean = isVisibleIn(y, column(x)) || isVisibleIn(x, row(y))

    private fun isVisibleIn(index: Int, list: List<Int>): Boolean {
        val valueAtIndex = list[index]
        val before = list.subList(0, index)
        val after = list.subList(index+1, rows)

        return before.count {  it >= valueAtIndex } == 0 || after.count {  it >= valueAtIndex } == 0
    }

    fun getHighestScenicScore(): Int = (0 until rows).map { y ->
        val scores = (0 until cols).map { x ->
            val scenicScore = calculateScenicScore(x, y)
            scenicScore
        }
        scores
    }.flatten().max()

    private fun calculateScenicScore(x: Int, y: Int): Int = calculateScenicScore(y, column(x)) * calculateScenicScore(x, row(y))

    private fun calculateScenicScore(index: Int, list: List<Int>): Int {
        val valueAtIndex = list[index]
        val before = list.subList(0, index)
        val after = list.subList(index+1, rows)
        return before.reversed().takeUntilIncluding { it >= valueAtIndex }.count() * after.takeUntilIncluding { it >= valueAtIndex }.count()
    }

    private fun row(y: Int) = grid[y].toList()

    private fun column(x: Int): List<Int> = grid.map { it[x] }

}

fun main() {
    val dayName = 8.toDayName()

    fun part1(input: List<String>): Int = Forest(input).countVisisbleNodes()

    fun part2(input: List<String>): Int = Forest(input).getHighestScenicScore()

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    // Part 1

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 21

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 1736

    // Part 2

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 8

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 268800
}
