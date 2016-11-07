package daily_program.blackjack;

/**
 * Created with IntelliJ IDEA.
 * User: Cliff
 * Date: 5/9/2014
 * Time: 2:45 PM
 */
public enum Suit {

    CLUBS   ("C"),
    DIAMONDS("D"),
    HEARTS  ("H"),
    SPADES  ("S");

    final String str;

    Suit( String str ) {
        this.str = str;
    }

}
