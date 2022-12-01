import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

infix fun Any.isEqualTo(expected: Any?): Boolean {
    if (expected == null) {
        error("Expectation not set...")
    }

    if (!this.equals(expected)) {
        error("Expected $expected, but got $this")
    }
    println(this)
    return true
}

fun <T> T.print(): T {
    println(this)
    return this;
}
