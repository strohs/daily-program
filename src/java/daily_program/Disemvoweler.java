package daily_program;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Disemvowler programming challenge... Java Version
 * User: Cliff
 * Date: 3/5/14
 * Time: 5:20 PM
 */
public class Disemvoweler {

    static Pattern vowels = Pattern.compile("[aeiou]");
    static Pattern space_vowels = Pattern.compile("[aeiou ]");

    static String getVowels( String str ) {
        Matcher m = vowels.matcher( str );
        StringBuilder sb = new StringBuilder(  );

        while ( m.find() ) {
            sb.append( m.group() );
        }
        return sb.toString();
    }

    static String removeVowels( String str ) {
        String [] splits = space_vowels.split( str );

        StringBuilder sb = new StringBuilder();
        for ( String s : splits ) {
            sb.append( s );
        }
        return sb.toString();
    }

    public static void main( String[] args ) {
        String test = "this is a test";
        System.out.println( removeVowels( test ) );
        System.out.println( getVowels( test ) );
    }
}
