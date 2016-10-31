package java.daily_program.has77;

/**
 * Given an array of ints, return true if the array contains two 7's next to each other, or there are two 7's
 * separated by one element, such as with {7, 1, 7}.
 * User: Cliff
 * Date: 5/14/2014
 * Time: 4:30 PM
 */
public class Has77 {

    public boolean has77( int[] nums ) {
        int i0 = 0;
        int i1 = 1;
        int i2 = 2;
        boolean bHas = false;
        while ( i1 < nums.length && !bHas ) {
            if ( i2 < nums.length ) {
                if ( (nums[i0] == 7 && nums[i1] == nums[i0]) ||
                        (nums[i1] == nums[i2] && nums[i1] == 7) ||
                        (nums[i0] == nums[i2] && nums[i2] == 7) )
                    bHas = true;
            } else if ( i1 < nums.length ) {
                if ( (nums[i0] == 7 && nums[i1] == nums[i0]) )
                    bHas = true;
            }
            i0++;
            i1++;
            i2++;
        }
        return bHas;
    }

    public static void main( String[] args ) {
        Has77 h77 = new Has77();
        int [] t1 = {1,7,7};
        int [] t2 = {1,7,1,7};
        int [] t3 = {1,7,1,1,7};
        int [] empty = new int[3];
        System.out.println(  h77.has77( t1 ) );
        System.out.println(  h77.has77( t2 ) );
        System.out.println(  h77.has77( t3 ) );
    }
}
