package daily_program;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * POWERSET
 * generate all subsets of a collection of items passed in as an array
 * Example: given the following array of integers: [1,2,3]
 * Returns:  in any order, the following collection:
 *          { {} {1} {2} {3} {1,2} {2,3} {3,1} {1,2,3} }
 *
 * User: Cliff
 */
public class Powerset<T> {


    public List<List<T>> genPowerset( List<T> set ) {

        List<T> currSet = new ArrayList<>();
        List<List<T>> powerSet = new ArrayList<>();
        powersetHelper( set, currSet, powerSet );
        return powerSet;
    }

    private void powersetHelper( List<T> origSet, List<T> currSet, List<List<T>> pSet ) {
        if ( origSet.isEmpty() ) {
            pSet.add( currSet );
            return;
        }

        List<T> currSet2 = new ArrayList<>( currSet ); //currSet2 holds a copy of the currentSet
        currSet.add( origSet.get(0) ); //currSet holds the first element of from origSet
        List<T> origRest = origSet.subList( 1, origSet.size() ); //origRest is all elements of original set minus the first element

        powersetHelper( origRest, currSet, pSet );
        powersetHelper( origRest, currSet2, pSet );
    }
    

    public static void main( String[] args ) {
        Powerset<Integer> powerset = new Powerset<>();
        List<Integer> inList = Arrays.asList( 1,2,3 );
        List<List<Integer>> pset = powerset.genPowerset( inList );
        pset.forEach( System.out::println );
    }
}
