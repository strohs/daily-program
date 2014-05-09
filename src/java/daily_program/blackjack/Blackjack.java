package daily_program.blackjack;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cliff
 * Date: 5/9/2014
 * Time: 3:55 PM
 */
public class Blackjack {

    public static boolean bAces( List<Card> hand ) {
        boolean aces = true;
        for( Card card : hand ) {
            if ( card.rank != Rank.ACE ) aces = false;
        }
        return aces;
    }

    public static boolean bBlackjack( List<Card> hand ) {
        int value = 0;
        for ( Card card : hand ) value += card.rank.value;
        return ( value == 21 );
    }

    public static void blackjacks( int n ) {
        int blackjacks = 0;
        Deck deck = Deck.build( n );
        deck.shuffle();
        int handCount = 0;

        while ( !deck.empty() ) {
            List<Card> hand = deck.deal( 2 );
            handCount++;
            System.out.println( hand.get(0) + " || " + hand.get(1) );
            if ( bBlackjack( hand ) ) {
                System.out.println("   BlackJack!");
                blackjacks++;
            }
        }
        double percentage = ( blackjacks / (double) handCount) * 100;
        System.out.println( String.format("After %d hands there were %d blackjacks at %.2f percent", handCount, blackjacks, percentage));
    }

    public static void main( String[] args ) {
        Blackjack.blackjacks( 5 );
    }
}
