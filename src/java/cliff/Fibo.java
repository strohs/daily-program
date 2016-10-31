package java.cliff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Cliff
 * Date: 5/8/2014
 * Time: 3:48 PM
 */
public class Fibo {


    public static List<Integer> fib( int n ) {
        if ( n <= 0) throw new IllegalArgumentException( "n must be >= 0 " );

        List<Integer> fibs = new ArrayList<>();
        if ( n == 1 ) {
            fibs.add(0);
        } else if (n == 2) {
            Collections.addAll( fibs, 0, 1);
        } else {
            Collections.addAll( fibs, 0, 1);
            fibIter( fibs, n );
        }
        return fibs;
    }

    private static List<Integer> fibRec( List<Integer> fibs, int count ) {
        if ( fibs.size() >= count ) {
            return fibs;
        } else {
            int n2 = fibs.get( fibs.size() - 2 );
            int n1 = fibs.get( fibs.size() - 1 );
            fibs.add( n1 + n2 );
            return fibRec( fibs, count );
        }
    }

    private static List<Integer> fibIter( List<Integer> fibs, final int count ) {
        while ( fibs.size() < count ) {
            int n2 = fibs.get( fibs.size() - 2 );
            int n1 = fibs.get( fibs.size() - 1 );
            fibs.add( n1 + n2 );
        }
        return fibs;
    }

    public static void main( String[] args ) {
        List<Integer> f = fib(20);
        f.forEach( System.out::println );
        System.out.println("SHUFFLE TEST:");
        long seed = System.nanoTime();
        Collections.shuffle(f, new Random( seed ) );
        f.forEach( System.out::println );
    }
}
