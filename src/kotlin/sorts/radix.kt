package sorts

import kotlin.random.Random

/**
 *  radix sort in kotlin for sorting integers
 *
 * Author: Cliff
 */

// build a map of 10 "buckets" to hold the integers based on their radix
fun buildIntBuckets(): MutableMap<Int, MutableList<Int>> {
    val dMap = LinkedHashMap<Int, MutableList<Int>>()
    for (i in 0..9) {
        dMap[i] = mutableListOf()
    }
    return dMap
}

// returns a count of the number of digits in 'i'
fun digitLength (i: Int?): Int = i?.toString()?.length ?: 0

// build a list of size 'size', containing random integers between min to max inclusive
fun randomIntList (min: Int, max: Int, size: Int): MutableList<Int> {
    val rands = mutableListOf<Int>()
    for (i in 1..size) rands.add(Random.nextInt(min, max + 1))
    return rands
}

// get the largest count of digits in the 'ints' list
fun maxDigitLength (ints: List<Int>): Int {
    val i = ints.maxBy { digitLength(it) }
    return digitLength(i)
}

// get the value of the 'd'th digit in 'int', where the rightmost digit starts at 'd'=1...
fun getDigit (int:Int, d:Int): Int {
    if (d > digitLength(int) ) return 0
    var rem = 0
    var num = int
    for (i in 0 until d) {
        rem = num % 10
        num /= 10
    }
    return rem
}


fun main() {
    val buckets = buildIntBuckets()
    // random integers to sort
    var ints = randomIntList(0, 999, 50)
    ints.forEach {
        print(" $it ")
    }
    println("\n--------------------")

    var divisor = 10
    // 1. determine the max number of digits amongst all ints in the list and loop that number of times
    for (i in 1..maxDigitLength(ints)) {
        // 2. put the ints into appropriate buckets based on least significant digits
        ints.forEach { buckets[ getDigit(it,i)]!!.add(it) }
        // 3. remove ints from buckets (in digit order 0 to 9) and place them back into the list
        ints.clear()
        buckets.forEach { k, vals ->
            ints.addAll( buckets[k]!!)
            buckets[k]!!.clear()
        }
        divisor *= 10
    }
    // 4. ints should now be sorted
    ints.forEach { print(" $it ") }
}