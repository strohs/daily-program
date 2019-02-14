/**
 * Closest pair to a target sum.
 * Find a pair of integers that are closest to (or equal to) a target sum. The integers are sorted and contained in two
 * separate Lists. The returned pair must contain an integer from each List.
 *
 *
 */

// holds the merged array, and source array
data class ArrayPair (val merged: List<Int>, val source: List<Boolean>) {
    fun isFromDiffList (i1: Int, i2: Int): Boolean {
        return source[i1] xor source[i2]
    }
}

// holds a pair of integers
data class IntPair (val p1: Int, val p2: Int) {
    fun sum(): Int {
        return p1 + p2
    }

    override fun toString(): String {
        return String.format("(%2d, %2d)", p1, p2)
    }
}

fun mergeLists (xs:List<Int>, ys: List<Int>) : ArrayPair {
    val merged = mutableListOf<Int>()
    val source = mutableListOf<Boolean>()
    var xi = 0
    var yi = 0

    while (xi < xs.count() && yi < ys.count()) {
        when (xs[xi].compareTo( ys[yi] )) {
            -1,0 -> {
                merged.add( xs[xi++] )
                source.add(true)
            }
            1 -> {
                merged.add( ys[yi++] )
                source.add(false)
            }
        }
    }
    while (xi < xs.count()) {
        merged.add( xs[xi++] )
        source.add(true)
    }
    while (yi < ys.count()) {
        merged.add( ys[yi++] )
        source.add(false)
    }
    return ArrayPair(merged, source)
}

fun findClosestPair (xs: List<Int>, ys: List<Int>, targetSum: Int): IntPair {
    // 1. merge the two arrays into a single sorted array O(n), and an array indicated which original array
    //    a integer came from
    val ap: ArrayPair = mergeLists(xs, ys)

    var currClosest = Integer.MAX_VALUE         // stores the current closest distance to target
    var res = IntPair(0, 0)
    var l = 0
    var r = ap.merged.count() - 1

    // 2. iterate from both the left side and right side of the merged array, keeping track of the closest pair
    while (l < r) {
        val dist = Math.abs(ap.merged[l] + ap.merged[r] - targetSum) // distance to the target sum

        if (dist < currClosest && ap.isFromDiffList(l, r)) {
            currClosest = dist
            res = IntPair(ap.merged[l], ap.merged[r])
        } else if (ap.merged[l] + ap.merged[r] <= targetSum) {
            l++
        } else {
            r--
        }
    }
    return res
}

fun main() {
    val l1 = listOf(1,2,3,4,5,8,77)
    val l2 = listOf(3,10,20,30)
    val target = 17

    val closestPair = findClosestPair(l1,l2,target)
    println(closestPair)
}


