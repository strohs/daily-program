package daily_program;

/**
 * Find the subarray with the maximum sum and return the maximum sum
 * This approach will use Kadane's algorithm
 *
 * This algo only works for arrays containing at least one positive integer, arrays with all negatives will return
 * 0 as the max
 * 
 * User: Cliff
 */
public class MaxSubarraySum {

    public static int maxSubarraySum( int[] arr ) {
        int maxSoFar = 0;
        int maxEndingHere = 0;

        for ( int n : arr ) {
            maxEndingHere += n;
            if ( maxEndingHere < 0 ) maxEndingHere = 0;
            if ( maxSoFar < maxEndingHere )
                maxSoFar = maxEndingHere;
        }
        return maxSoFar;
    }

    public static void main( String[] args ) {
        int [] arr1 = {-2,-3,4,-1,-2,1,5,-3};
        int [] arr2 = {1,-2,-3};

        int max = maxSubarraySum( arr1 );
        System.out.println(max);
    }
}
