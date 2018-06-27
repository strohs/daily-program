package daily_program;

import java.util.*;
import java.util.stream.Collectors;

/**
 * different solutions to the various K-Sum programming problems: like 2Sum, 3Sum
 *
 * @author Cliff
 */
public class KSums {

    static int [] arr1 = {3,5,2,-4,8,11,-7,4,7,1};

    /**
     * given an unsorted array, find all pairs of numbers that add up to a given sum, S
     *
     * For example, if the array is [3, 5, 2, -4, 8, 11] and the sum is 7, your program should return [[11, -4], [2, 5]]
     * because 11 + -4 = 7 and 2 + 5 = 7.
     *
     * @author Cliff
     */
    static List<int[]> twoSum( int [] arr, int s ) {
        List<int[]> pairs = new ArrayList<>(  );
        //maps array value -> to its index in the array
        Map<Integer,Integer> sumMap = new HashMap<>();

        for ( int i = 0; i < arr.length; i++ ) {
            int diff = s - arr[i];
            if ( sumMap.containsKey( diff ) ) {
                //add the sum pair to our return list
                int [] pair = { arr[sumMap.get( diff )], arr[i]};
                pairs.add( pair );
            } else {
                sumMap.put( arr[i], i );
            }
        }
        return pairs;
    }

    /**
     * find all triplets in the array, 'arr', that sums to the given value 's'.
     * This solution uses a sort on the input array, O(nlogn) and then traverses the sorted array once to find
     * triplets that equal sum O(n).  Total time complexity is O(n^2)
     * @param arr - input array if integers
     * @param s - the value that the triplets must sum to
     * @return a List containing triplets that sums to s, or an empty list if no triplets was found
     */
    static List<int []> threeSum( int [] arr, int s ) {
        List<int[]> trips = new ArrayList<>();

        //sort the input array in place
        Arrays.sort( arr );

        //loop through array using a left and right index pointer to see if a triplet add to the sum
        for ( int i = 0; i < arr.length - 2; i++ ) {
            int l = i + 1;          //left pointer, points one position ahead of the current element
            int r = arr.length - 1; //right pointer, points to last element in array

            while ( l < r ) {

                if ( arr[i] + arr[l] + arr[r] == s ) {
                    //triplet found, add it to output list
                    int [] triplets = { arr[i], arr[l], arr[r] };
                    trips.add( triplets );
                    break;
                } else if ( arr[i] + arr[l] + arr[r] < s )
                    l++;
                else //arr[i] + arr[l] + arr[r] > s
                    r--;
            }
        }

        return trips;
    }

    public static void main( String[] args ) {
        int sum = 6;
        List< int[] > sumPairs = twoSum( arr1, sum );
        System.out.println("pairs summing to: " + sum );
        sumPairs.forEach( pair -> {
            String intStr = Arrays.stream( pair ).mapToObj( Integer::toString ).collect( Collectors.joining( "," ) );
            System.out.println( intStr );
        } );
        System.out.println("-----3Sum-------");
        sum = 6;
        List<int[]> triplets = threeSum( arr1, sum );
        triplets.forEach( trip -> {
            String intStr = Arrays.stream( trip ).mapToObj( Integer::toString ).collect( Collectors.joining( "," ) );
            System.out.println( intStr );
        } );
    }
}
