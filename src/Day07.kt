data class File(val name: String, val size: Long)
data class Dir(val name: String, val subDirs: MutableList<Dir>, val files: MutableList<File>, val parent: Dir? = null) {
    fun size(): Long {
        return files.sumOf { it.size } + subDirs.sumOf { it.size() }
    }
    companion object {
        fun withName(name: String, parent: Dir? = null): Dir {
            return Dir(name, mutableListOf(), mutableListOf(), parent)
        }
    }

    override fun toString(): String {
        return name + " : " + size()
    }
}


fun main() {
    val dayName = 7.toDayName()

    fun part1(input: List<String>): Long {
        var cd: Dir? = null
        val allDirs = mutableListOf<Dir>();

        input
            .filter { it != "$ ls" }
            .forEach { it ->
                println(it)
                when {
                    it == "$ cd .." -> {
                        cd = cd!!.parent
                    }
                    it.startsWith("$ cd ") -> {
                        val dirName = it.substring(5)

                        if ( cd == null) {
                            val dir = Dir.withName(dirName)
                            cd = dir
                            allDirs.add(dir);
                        } else {
                            cd = cd!!.subDirs.find { it.name == dirName}
                        }
                    }
                    it.startsWith("dir ") -> {
                        val dir = Dir.withName(it.substring(4), cd!!)
                        allDirs.add(dir)
                        cd!!.subDirs.add(dir)
                    }
                    else -> {
                        cd!!.files.add(File(it.split(" ")[1], it.split(" ")[0].toLong()))
                    }
                }
            }
        return allDirs
                .sortedByDescending { it.size() }
                .showMe()
                .filter { it.size() < 100000 }
                .showMe()
                .sumOf { it.size() }
    }

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    // Part 1

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 95437L

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 1453349L

    // Part 2

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo null

    val outputPart2 = part2(input)
    outputPart2 isEqualTo null
}
