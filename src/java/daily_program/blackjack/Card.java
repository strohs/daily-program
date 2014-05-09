package daily_program.blackjack;

/**
 * Represents a single card with a rank and suit
 * User: Cliff
 * Date: 5/9/2014
 * Time: 3:08 PM
 */
public class Card {

    final Rank rank;
    final Suit suit;

    public Card( Rank rank, Suit suit ) {
        this.rank = rank;
        this.suit = suit;
    }

    public boolean bTenCard() {
        boolean face = false;
        switch ( rank ) {
            case TEN: case JACK:case QUEEN:case KING: face = true;
        }
        return face;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Card card = (Card) o;

        if ( rank != card.rank ) return false;
        if ( suit != card.suit ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rank.hashCode();
        result = 31 * result + suit.hashCode();
        return result;
    }

    @Override
    public String toString() {
         return rank.str + ":" + suit.str;
    }
}
