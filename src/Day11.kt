val operations = mapOf(
        "+" to {a: Long, b: Long -> a + b},
        "*" to {a: Long, b: Long -> a * b}
)

data class ItemForMonkey(val item: Long, val monkey: Int)

class Monkey(
        val items: MutableList<Long>,
        val operation: String,
        val divisibleBy: Long,
        val trueMonkeyIndex: Int,
        val falseMonkeyIndex: Int,
        var inspectedItems: Long = 0) {

    private fun inspectItem(old: Long): Long {
        inspectedItems++
        val (first, operator , second) = operation.replace("old", old.toString()).split(" ")
        return operations.getValue(operator)(first.toLong(), second.toLong())
    }

    fun executeRound(divideFunction: (Long) -> Long): List<ItemForMonkey> {
        val itemsForMonkey = items.map {
            var newVal = divideFunction(inspectItem(it))

            if (newVal % divisibleBy == 0L)
                ItemForMonkey(newVal, trueMonkeyIndex)
            else
                ItemForMonkey(newVal, falseMonkeyIndex)

        }
        items.clear()
        return itemsForMonkey
    }

    override fun toString(): String {
        return "Monkey(items=$items, $inspectedItems)"
    }


    companion object {
        fun parse(input: List<String>): Monkey {
            println(input)
            val items = input[1].substring(18).split(", ").map { it.toLong() }
            val operation = input[2].substring(19)
            val divisibleBy = input[3].substring(21).toLong()
            val trueMonkeyIndex = input[4].substring(29).toInt()
            val falseMonkeyIndex = input[5].substring(30).toInt()
            return Monkey(items.toMutableList(), operation, divisibleBy, trueMonkeyIndex, falseMonkeyIndex)
        }
    }
}
fun main() {
    val dayName = 11.toDayName()

    fun executeRound(monkeys: List<Monkey>, divider: (Long) -> Long) {
        monkeys.forEach { monkey ->
            val itemsForMonkey = monkey.executeRound(divider)
            itemsForMonkey.forEach { (item, monkeyIndex) ->
                monkeys[monkeyIndex].items.add(item)
            }
        }
    }


    fun calculateMonkeyBusiness(monkeys: List<Monkey>): Long {
        val (first, second) = monkeys
                .map { it.inspectedItems }
                .sortedDescending()
                .take(2)

        return first * second
    }

    fun part1(input: List<String>): Long {
        val monkeys = input.chunked(7)
                .map(Monkey.Companion::parse)

        repeat(20) { i ->
            executeRound(monkeys) { it / 3 }
            println("Round $i")
        }

        return calculateMonkeyBusiness(monkeys)
    }

    fun part2(input: List<String>): Long {
        val magicNumber = input
                .filter { it.contains("divisible by") }
                .map { it.substring(21).toInt() }
                .reduce(Int::times)

        val monkeys = input.chunked(7)
                .map(Monkey.Companion::parse)

        repeat(10_000) {i ->
            executeRound(monkeys) { it % magicNumber }
            println("Round $i")
        }

        return calculateMonkeyBusiness(monkeys)
    };

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    // Part 1

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 10605L

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 112815L

    // Part 2

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 2713310158L

    val outputPart2 = part2(input)
    outputPart2 isEqualTo null
}
