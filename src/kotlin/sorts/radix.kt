package sorts

import kotlin.random.Random

/**
 *  radix sort in kotlin for sorting integers
 *
 * Author: Cliff
 */


// build a list of size 'size', containing random integers between min to max inclusive
fun randomIntList (min: Int, max: Int, size: Int): MutableList<Int> {
    val rands = mutableListOf<Int>()
    for (i in 1..size) rands.add(Random.nextInt(min, max + 1))
    return rands
}

// returns a count of the number of digits in 'i'
fun length (i: Int?): Int = i?.toString()?.length ?: 0

// get the largest count of digits in the 'ints' list
fun maxDigitLength (ints: List<Int>): Int {
    val i = ints.maxBy { length(it) }
    return length(i)
}

// get the value of the 'd'th digit in 'int', where the rightmost digit starts at position 'd'=1...
fun digitSelector (int:Int, d:Int): Int {
    
    if (d > length(int) )
        return 0
    else {
        var rem = 0
        var num = int
        for (i in 0 until d) {
            rem = num % 10
            num /= 10
        }
        return rem
    }
}

fun main() {
    // random integers to sort
    val ints = randomIntList(0, 999999, 1_000_000)
    //ints.forEach { println(" $it ") }
    //println()

    // 1. determine the max number of digits amongst all ints in the list and loop that number of times
    for (digitPos in 1..maxDigitLength(ints)) {
        // 2. sort the list by individual digits, moving from right to left
        ints.sortBy { digitSelector( it, digitPos ) }
    }
    // 4. ints should now be sorted
    ints.take(10).forEach { println(" $it ") }
}