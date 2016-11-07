package daily_program.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A deck of cards
 * User: Cliff
 * Date: 5/9/2014
 * Time: 3:16 PM
 */
public class Deck {

    private final List<Card> cards = new ArrayList<>();

    // create a new deck of 52 cards
    private static List<Card> build52() {
        List<Card> cards = new ArrayList<>();
        for ( Suit suit : Suit.values() ) {
            for ( Rank rank : Rank.values() ) {
                cards.add( new Card( rank, suit) );
            }
        }
        return cards;
    }

    // bulld n * 52 card decks
    public static Deck build( int n ) {
        Deck d = new Deck();
        for ( int count = 0; count < n; count++ ) {
            d.cards.addAll( build52() );
        }
        return d;
    }


    public void shuffle() {
        Collections.shuffle( cards );
    }

    public List<Card> getCards() {
        return cards;
    }

    //deal n cards from the deck, if n cards are not available, returns an empty list
    public List<Card> deal( int n ) {
        List<Card> hand = new ArrayList<>();
        int count = 0;
        if ( n > this.size() ) return hand;

        while ( !cards.isEmpty() && count < n ) {
            hand.add( cards.remove( 0 ) );
            count++;
        }
        return hand;
    }

    public boolean empty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

}
