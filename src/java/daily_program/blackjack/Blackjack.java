package java.daily_program.blackjack;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cliff
 * Date: 5/9/2014
 * Time: 3:55 PM
 */
public class Blackjack {

    public static int sum( List<Card> hand ) {
        int sum = 0;
        for ( Card card : hand ) sum += card.rank.value;
        //if the hand has at least two aces, one of them will count as 1 instead of 11
        if ( aceCount(hand) >= 2 ) sum -= 10;
        return sum;
    }

    public static int aceCount( List<Card> hand ) {
        int count = 0;
        for( Card card : hand ) {
            if ( card.rank == Rank.ACE ) count++;
        }
        return count;
    }

    public static boolean bBlackjack( List<Card> hand ) {
        return ( sum( hand ) == 21 );
    }

    public static boolean bElevenOrLess( List<Card> hand ) {
        return ( sum( hand ) <= 11 );
    }

    public static void blackjacks( int n ) {
        int blackjacks = 0;
        Deck deck = Deck.build( n );
        deck.shuffle();
        int handCount = 0;

        List<Card> hand = deck.deal( 2 );
        do {

            if ( !hand.isEmpty() ) {
                handCount++;
                System.out.println( printHand( hand ) + "   Total:" + sum(hand) );
                if ( bBlackjack( hand ) ) {
                    System.out.println("   BlackJack!");
                    blackjacks++;
                }
                while ( bElevenOrLess( hand ) && !deck.empty() ) {
                    hand.addAll( deck.deal( 1 ) );
                    System.out.println("   HIT! " + printHand( hand ) + "  Total:" + sum( hand ) );
                    if ( bBlackjack( hand ) ) {
                        blackjacks++;
                        System.out.println("   BlackJack!");
                    }
                }
            }
            hand = deck.deal(2); //returns an empty list if we can't deal at least two cards
        } while ( !hand.isEmpty() );

        double percentage = ( blackjacks / (double) handCount) * 100;
        System.out.println( String.format("After %d hands there were %d blackjacks at %.2f percent", handCount, blackjacks, percentage));
    }

    public static String printHand( List<Card> hand ) {
        StringBuilder sb = new StringBuilder(  );
        for ( Card card : hand ) {
            sb.append( card );
            sb.append(" || ");
        }
        return sb.toString();
    }

    public static void main( String[] args ) {
        Blackjack.blackjacks( 10 );
    }
}
