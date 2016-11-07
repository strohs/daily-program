package daily_program.blackjack;

/**
 * Created with IntelliJ IDEA.
 * User: Cliff
 * Date: 5/9/2014
 * Time: 2:47 PM
 */
public enum Rank {

    ACE(  11, "A"),
    TWO(   2, "2"),
    THREE( 3, "3"),
    FOUR(  4, "4"),
    FIVE(  5, "5"),
    SIX(   6, "6"),
    SEVEN( 7, "7"),
    EIGHT( 8, "8"),
    NINE(  9, "9"),
    TEN(  10,"10"),
    JACK( 10, "J"),
    QUEEN(10, "Q"),
    KING( 10, "K");

    final int value;
    final String str;

    Rank( int value, String str  ) {
        this.value = value;
        this.str = str;
    }


}
