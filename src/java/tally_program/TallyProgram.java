package tally_program;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.toLowerCase;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;


/**
 * Challenge #361 [Easy] Tally Program
 *
 * 5 Friends (let's call them a, b, c, d and e) are playing a game and need to keep track of the scores. Each time
 * someone scores a point, the letter of his name is typed in lowercase. If someone loses a point, the letter of his
 * name is typed in uppercase. Give the resulting score from highest to lowest.
 *
 * Input:
 *  A series of characters indicating who scored a point. Examples:
 *    abcde
 *    dbbaCEDbdAacCEAadcB
 *
 * Output:
 * The score of every player, sorted from highest to lowest. Examples:
 *    a:1, b:1, c:1, d:1, e:1
 *    b:2, d:2, a:1, c:0, e:-2
 *
 * User: Cliff
 */
public class TallyProgram {

    //tally a single point based on the character passed in
    Function<Character,Integer> tallyPoint = (Character ch) -> {
        if ( isLowerCase( ch ) )
            return 1;
        else return -1;
    };


    public void tallyPoints( String in ) {
        Map<Character,Integer> resMap = Arrays.stream( in.split( "" ) )
                .map( (String s) -> s.charAt( 0 ) )
                .collect( groupingBy( Character::toLowerCase , reducing( 0, tallyPoint, Integer::sum )))
                .entrySet()
                .stream()
                .sorted( comparingInt( e -> -e.getValue() ) )
                .collect( Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        resMap.forEach( ( k, v ) -> System.out.println( k + "::" + v ) );
    }

    public void tallyPoints2( String in ) {
        in.chars().mapToObj( i -> (char)i )
                .collect( groupingBy( k -> toLowerCase(k), reducing(0, k -> isLowerCase(k) ? 1 : -1, Integer::sum) ))
                .entrySet().stream().sorted( comparingInt( e -> -e.getValue() ))
                .forEach( System.out::println );
    }

    public static void main( String[] args ) {
        TallyProgram tp = new TallyProgram();
        tp.tallyPoints( "abcde" );
        System.out.println( "===================================" );
        tp.tallyPoints( "dbbaCEDbdAacCEAadcB" );
        System.out.println( "===================================" );
        tp.tallyPoints( "EbAAdbBEaBaaBBdAccbeebaec" );

    }
}
