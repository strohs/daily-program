package daily_program;

import java.util.*;

/**
 * Given two sorted arrays of ints and a number t, find the pair whose sum is equal to t, or the closest
 * to t. The pair must have an element from each array.
 *
 * We are given two arrays xs[0…m-1] and ys[0..n-1] and a number t, we need to find the
 * pair xs[i] + ys[j] such that absolute value of (xs[i] + ys[j] – t) is minimum.
 *
 * This implementation uses a O(n) solution. The two input arrays are merged into a single sorted array,
 * and a secondary boolean array is also constructed that stores which array an element came from. The single
 * array of ints is then stepped through from both the left and right sides using two array index "pointers".
 * As we step through the array, we keep track of the pair that is currently closest to the target, once the left
 * and right pointers meet, we are done, and we return the closest pair
 */
public class ClosestPairFromTwoSortedArrays {


    // merge two arrays and keep track of the original array each belonged to in a separate source array
    static ArrayPair arrayMerge(int[] xs, int[] ys) {
        int[] sorted = new int[xs.length + ys.length];
        // integers from the xs array will = true, integers from ys array will = false;
        boolean[] source = new boolean[xs.length + ys.length];

        int si = 0; // index to sorted array
        int xi = 0; // index to shorter length array
        int yi = 0; // index to longer length array

        while (xi < xs.length && yi < ys.length) {
            switch (Integer.compare(xs[xi], ys[yi])) {
                case -1:
                    sorted[si] = xs[xi++];
                    source[si] = true;
                    si++;
                    break;
                case 0:
                    sorted[si] = xs[xi++];
                    source[si++] = true;
                    sorted[si] = ys[yi++];
                    source[si++] = false;
                    break;
                case 1:
                    sorted[si] = ys[yi++];
                    source[si] = false;
                    si++;
                    break;
            }
        }
        // add any remaining values from either of the arrays to the sorted array
        if (xi < xs.length) {
            while (xi < xs.length) {
                sorted[si] = xs[xi++];
                source[si] = true;
                si++;
            }
        } else {
            while (yi < ys.length) {
                sorted[si] = ys[yi++];
                source[si] = false;
                si++;
            }
        }
        return new ArrayPair( sorted, source );
    }


    public static IntPair findClosestPair( int[] xs, int[] ys, int targetSum) {
        // 1. merge the two arrays into a single sorted array O(n), and an array indicated which original array
        //    a integer came from
        ArrayPair ap = arrayMerge(xs,ys);

        int currClosest = Integer.MAX_VALUE;         // stores the current closest distance to target
        IntPair res = new IntPair(0,0);
        int l = 0; int r = ap.arr.length - 1; // indexes to left and right of

        // 2. iterate from both the left side and right side of the merged array, keeping track of the closest pair
        while (l < r) {
            int dist = Math.abs( (ap.arr[l] + ap.arr[r]) - targetSum); // distance to the target sum

            if (dist < currClosest && ap.isFromDifferentArray(l,r)) {
                currClosest = dist;
                res = new IntPair( ap.arr[l], ap.arr[r] );
            } else if ( (ap.arr[l] + ap.arr[r]) <= targetSum ) {
                l++;
            } else {
                r--;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int [] xs = {1,2,3,4,5,8,77};
        int [] ys = {3,10,20,30};
        int target = 88;

        IntPair res = ClosestPairFromTwoSortedArrays.findClosestPair(xs,ys,target);
        System.out.println(res);
    }
}

class IntPair {
    final int p1;
    final int p2;

    IntPair(int p1, int p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    int sum() {
        return p1 + p2;
    }

    @Override
    public String toString() {
        return String.format("(%2d, %2d)", p1, p2);
    }
}

class ArrayPair {
    int [] arr;
    boolean[] source;

    ArrayPair(int[] arr, boolean[] source) {
        this.arr = arr;
        this.source = source;
    }

    boolean isFromDifferentArray(int i1, int i2 ) {
        return source[i1] ^ source[i2];
    }

    @Override
    public String toString() {
        return "ArrayPair{" +
                "arr=" + Arrays.toString(arr) +
                ", source=" + Arrays.toString(source) +
                '}';
    }
}

