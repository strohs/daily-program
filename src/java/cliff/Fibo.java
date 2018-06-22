package cliff;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * User: Cliff
 * Date: 5/8/2014
 * Time: 3:48 PM
 */
public class Fibo {

    //find the fibonacci number at a particular position. pos starts at 1. this is the iterative solution
    public static long fibPos( int pos ) {
        if (pos < 3) return pos - 1;
        int curPos = 3;
        long sLast = 0;
        long last = 1;
        while (curPos <= pos) {
            long temp = last;
            last = sLast + last;
            sLast = temp;
            curPos++;
        }
        return last;
    }

    //recursive solution for finding the fibo number at a specific position. pos starts at 1
    public static long fibPosRec( final int pos ) {
        if ( pos < 3 ) return pos - 1;
        return fibPosRec( 0,1, 3, pos );
    }
    private static long fibPosRec( long slast, long last, int curPos, int pos ) {
        if ( curPos >= pos )
            return slast + last;
        else 
            return fibPosRec( last, slast + last, ++curPos, pos );

    }

    //return the first n fibonacci numbers
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

    //generate the a sequence of fibonacci numbers up to the limit
    public static List<Long> fiboList( int limit ) {
        return Stream.iterate( new long[]{ 1, 1 }, p -> new long[]{ p[1], p[0]+p[1] })
                .limit( limit )
                .map( longPair -> longPair[0] )
                .collect(Collectors.toList());
    }

    public static void main( String[] args ) {
        List<Integer> f = fib(20);
        f.forEach( System.out::println );

        int pos = 20;
        long fnum = fibPosRec( pos );
        System.out.println( String.format("Fib num at pos %d is %d", pos,fnum) );
        System.out.println("---------------------");
        fiboList( 20 ).forEach( System.out::println );
       // System.out.println("SHUFFLE TEST:");
        //long seed = System.nanoTime();
        //Collections.shuffle(f, new Random( seed ) );
        //f.forEach( System.out::println );
    }
}
