package daily_program.simplifying_fractions;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Main class for Simplifying fractions Daily Challenge #277
 *
 * A fraction exists of a numerator (top part) and a denominator (bottom part) as you probably all know.
 * Simplifying (or reducing) fractions means to make the fraction as simple as possible. Meaning that the denominator
 * is a close to 1 as possible. This can be done by dividing the numerator and denominator by their greatest common
 * divisor.
 *
 * User: Cliff
 * Date: 8/4/2016
 * Time: 11:18 AM
 */
public class SF {

    private static List<List<Integer>> readAndParse( String path ) {
        List<List<Integer>> lines = new ArrayList<>(  );

        try ( BufferedReader reader = new BufferedReader( new FileReader( path ) ) ) {
            lines = reader.lines().map( line -> {
                String[] lineArr = line.split( " " );
                List<Integer> nums = new ArrayList<>( 2 );
                nums.add( Integer.parseInt( lineArr[ 0 ] ) );
                nums.add( Integer.parseInt( lineArr[ 1 ] ) );
                return nums;
            } ).collect( Collectors.toList() );
        } catch ( IOException e ) {
            e.printStackTrace();
        }


        return lines;
    }


    private static LongBinaryOperator gcd = (long a, long b) -> {
        long n1;
        long n2;
        long rem;

        if ( a == b ) return a;
        if ( a <= 0 || b <= 0 ) return 0;

        //find the max and min of the two inputs: a,b
        n2 = Math.max( a,b );
        n1 = Math.min( a,b );

        rem = n2 % n1;
        while ( rem > 0 ) {
            n2 = Math.max( rem, n1 );
            n1 = Math.min( rem, n1 );
            rem = n2 % n1;
        }
        return n1;
    };



    public static void main( String[] args ) throws FileNotFoundException {
        List<Long> nums = Arrays.asList( 34552l,523424l,635542l,534424l,63342234l,635477232l,5634328l,534562345l,23414l,5334l,673l,762l );
        String INPUT_PATH = "src\\java\\daily_program\\simplifying_fractions\\input.txt";

        List<List<Integer>> nds = readAndParse( INPUT_PATH );
        nds.forEach( nd -> {
            long num = nd.get(0);
            long den = nd.get(1);
            long gcd = SF.gcd.applyAsLong( num, den );
            System.out.println( String.format( "%d %d", num/gcd, den/gcd ) );
        });

    }
}
