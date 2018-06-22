package daily_program;

import java.util.*;
import java.util.stream.Collectors;

/**
 * find the k most frequent elements in an array
 *
 * User: Cliff
 */
public class KMostFrequent {

    //compares the entry set by values in descending order, if the values are equal, it compares the keys
    Comparator<Map.Entry<Integer,Integer>> vkComparater = ( e1, e2 ) -> {
        if ( e1.getValue().equals( e2.getValue() ) )
            return Integer.compare( e1.getKey(),e2.getKey() ) * -1; //descending order
        else
            return Integer.compare( e1.getValue(), e2.getValue() ) * -1;
    };

    //build a hash map of the frequencies of each integer in the 'arr'
    private Map<Integer,Integer> frequencies( int [] arr ) {
        Map<Integer,Integer> freqs = new HashMap<>(  );
        for ( int i : arr ) {
            if (freqs.containsKey( i ) )
                freqs.put( i, freqs.get( i ) + 1 );
            else
                freqs.put( i, 1 );
        }
        return freqs;
    }

    public List<Map.Entry> kmf( int [] arr, int k ) {
        Map<Integer,Integer> freqs = frequencies( arr );
        return freqs.entrySet().stream()
                .sorted( vkComparater )
                .limit( k )
                .collect( Collectors.toList() );
    }

    public static void main( String[] args ) {
        KMostFrequent kmf = new KMostFrequent();
        int [] arr1 = {7,1,2,3,1,5,1,6,7,1,2,2,8,7};
        List<Map.Entry> kfreqs = kmf.kmf( arr1, 4);
        kfreqs.forEach( entry -> System.out.println( String.format( "key:%d occurs:%d", entry.getKey(), entry.getValue())) );

        
    }
}
