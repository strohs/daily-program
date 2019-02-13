package daily_program;

import java.util.*;

/**
 * Given an input string containing textual data and parenthesis and/or braces i.e  ( ) { } [ ]
 * determine if the parens are balanced
 * User: Cliff
 */
public class BalancedParens {

    private final static Map<String,String> parens;
    private final static Collection<String> openParens;
    private final static Collection<String> closedParens;
    static {
        parens = new HashMap<>();
        parens.put( "(", ")" );
        parens.put( "[", "]" );
        parens.put( "{", "}" );
        openParens = parens.keySet();
        closedParens = parens.values();
    }

    private static boolean isBalanced( String str ) {
        Stack<String> stack = new Stack<>();
        for ( String s : str.split( "" ) ) {
            if ( openParens.contains( s ) ) {
                stack.push( s );
            } else if ( closedParens.contains( s ) ) {
                if ( !s.equals( parens.get( stack.peek() )) ) {
                    return false;
                } else {
                    stack.pop();
                }
            }
        }
        return true;
    }

    public static void main( String[] args ) {
        String instr = "abc({[f]}{})";
        System.out.println( isBalanced(instr) );
    }
}
