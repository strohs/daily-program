package daily_program;

/**
 * Given an array of integers, move all zeroes in the array, to the end of the array.
 * <p>
 * This solution will modify the array in place, and use two index 'pointers' to keep track of the most recently moved
 * zero, and another pointer to move thru the array from front to back. When the two pointers meet, all zeroes will
 * have been moved and we are done.
 * <p>
 * User: Cliff
 */
public class ArrayZeros {


    public static int[] moveZeroes( int[] arr ) {
        int si = 0;                 //starting index of the array
        int li = arr.length - 1;    //index of the last moved zero

        //need to check if last most positions of the array are already zero(s), and move the last index accordingly
        while ( arr[li] == 0 && li > 0) {
            li--;
        }

        while ( si < li ) {
            if ( arr[ si ] == 0 ) {
                arr[ si ] = arr[ li ];
                arr[ li ] = 0;
                li--;
            }
            si++;
        }
        return arr;
    }

    public static void main( String[] args ) {
        int[] arr1 = {1, 2, 3, 0, 4, 5, 0, 6, 7, 8, 0, 9, 10, 11, 0, 0, 12, 13, 14, 15, 0,0,0};
        int[] arr2 = {0,0,0};
        int[] movedArr = moveZeroes( arr1 );
        for ( int i : movedArr ) {
            System.out.print( i + "," );
        }

    }
}
