package daily_program;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Given an array of integers, find the length of the longest consecutive sequence in the array.
 * For example: given the array [2 1 6 9 4 3], the longest consecutive sequence is [1 2 3 4] with a length
 * of 4, so we should return 4
 *
 * The easiest approach would be to sort the input array and then iterate through it while keeping track of the
 * longest consecutive sequence. A different approach (shown here) will be to use a HashSet to store all the values
 * of the input array, and then iterate through the input array while using the HashSet to quickly determine of there
 * are values greater than the current array value we are examining.
 *
 * User: Cliff
 */
public class LongestConsecutiveSequence {

    public static int lcs( int [] arr ) {
        int lcsLength = 0;
        int currLength = 1;

        //create a HashSet to store all values of the array for quicker lookup
        Set<Integer> set = new HashSet<>();
        Arrays.stream( arr ).forEach( set::add );

        for (int n : arr) {
            //count how many integers are one greater than the current array element
            int curInt = n;
            //count how many integers are one greater than the current array element
            while ( set.contains( curInt + 1 ) ) {
                currLength++;
                curInt++;
            }
            if ( currLength > lcsLength ) lcsLength = currLength;
            currLength = 1; //reset currentLength for next iteration
        }
        return lcsLength;
    }


    public static void main( String[] args ) {
        int [] in1 = {2,1,6,9,4,3};
        int [] in2 = {9,7,5,3,1,10};
        int [] in3 = {2,4,6,8};

        int lcs = lcs( in1 );       //should return 4
        System.out.println(lcs);
        lcs = lcs( in2 ); //should return 2
        System.out.println( lcs );
        lcs = lcs( in3 ); //should return 1
        System.out.println(lcs);

    }
}
