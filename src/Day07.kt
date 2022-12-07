import java.util.*

data class File(val name: String, val size: Int)
data class Dir(val name: String, val subDirs: MutableList<Dir>, val files: MutableList<File>) {
    fun size(): Int {
        return files.sumOf { it.size } + subDirs.sumOf { it.size() }
    }
    companion object {
        fun withName(name: String): Dir {
            return Dir(name, mutableListOf(), mutableListOf())
        }
    }
}
data class FileSystem (val rootDir: Dir, val allDirs: List<Dir>)

fun main() {
    val dayName = 7.toDayName()

    fun parseInput(input: List<String>): FileSystem {
        var cd = Stack<Dir>()
        var root: Dir? = null
        val allDirs = mutableListOf<Dir>();

        input
                .filter { it != "$ ls" }
                .forEach { it ->
                    when {
                        it == "$ cd .." -> {
                            cd.pop()
                        }
                        it.startsWith("$ cd ") -> {
                            val dirName = it.substring(5)

                            val dir = if (cd.size == 0) {
                                val dir = Dir.withName(dirName)
                                root = dir
                                allDirs.add(dir);
                                dir
                            } else cd.peek().subDirs.single { it.name == dirName }

                            cd.push(dir)
                        }
                        it.startsWith("dir ") -> {
                            val dir = Dir.withName(it.substring(4))
                            allDirs.add(dir)
                            cd.peek().subDirs.add(dir)
                        }
                        else -> {
                            val (size, name) = it.split(" ")
                            cd.peek().files.add(File(name, size.toInt()))
                        }
                    }
                }
        return FileSystem(root!!, allDirs)
    }

    fun part1(input: List<String>): Int {
        return parseInput(input).allDirs
                .map { it.size() }
                .filter { it < 100000 }
                .sortedDescending()
                .sum()
    }

    fun part2(input: List<String>): Int {
        val fileSystem = parseInput(input)
        val usedSpace = fileSystem.rootDir.size()
        val availableSpace = 70000000 - usedSpace
        val neededSpace = 30000000 - availableSpace
        return fileSystem.allDirs
                .map { it.size() }
                .sorted()
                .showMe()
                .first { it > neededSpace }
    }

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    // Part 1

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 95437

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 1453349

    // Part 2

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 24933642

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 2948823
}
