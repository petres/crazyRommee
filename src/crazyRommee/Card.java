package crazyRommee;

import java.util.Collections;
import java.util.Random;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;


public class Card {
	public enum Rank {
		ACE,
		TWO,
		THREE,
		FOUR,
		FIVE,
		SIX,
		SEVEN,
		EIGHT,
		NINE,
		TEN,
		JACK,
		QUEEN,
		KING
	}

	public enum Color {
		SPADE,
		HEART,
		DIAMOND,
		CLUB
	}

	public enum CardSet {
		RED,
		BLUE
	}

	private CardSet cardSet = null;
	private Color color;
	private Rank rank;
	private boolean revealed = false;
	
	public Card(Card.Rank rank, Card.Color color) {
		this.rank = rank;
		this.color = color;
	}

	public Card(Card.Rank rank, Card.Color color, Card.CardSet cardSet) {
		this(rank, color);
		this.cardSet = cardSet;
	}

	public Card.CardSet getCardSet() {
		return cardSet;
	}
	
	public Card.Color getColor() {
		revealed = true;
		return color;
	}

	public Card.Rank getRank() {
		revealed = true;
		return rank;
	}

	public boolean isRevealed() {
		return revealed;
	}

	public String toString() {
		int rankOffset = getRank().ordinal();
		if(rankOffset > Rank.JACK.ordinal())
			rankOffset++;
		String card = new String(new int[] { 0x1F0A1 + rankOffset + 16*getColor().ordinal() }, 0, 1);
		if(getColor() == Color.HEART || getColor() == Color.DIAMOND)
			return "\033[1;47;31m" + card +  " \033[0m"; // RED
		else
			return "\033[1;47;30m" + card +  " \033[0m"; // BLACK
	}

	public String getHiddenCardString() {
		return getHiddenCardString(cardSet);
	}

	public int hashCode() {
        return rank.ordinal()*19 + color.ordinal()*41 + cardSet.ordinal()*17;
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj == this)
            return true;
        
        if (!(obj instanceof Card))
            return false;

        Card card = (Card) obj;
       
       	if(card.rank != this.rank)
       		return false;
       	if(card.color != this.color)
       		return false;
       	if(card.cardSet != this.cardSet)
       		return false;

       	return true;
    }

	static public String getHiddenCardString(Card.CardSet cardSet) {
		int color = 30;
		switch(cardSet) {
			case RED:
				color = 31;
			break;
			case BLUE:
				color = 34;
			break;
		}
		return "\033[1;47;" + color + "m" + new String(new int[] { 0x1F0A0 }, 0, 1) + " \033[0m";
	}

	static public List<Card> createDeck(Card.CardSet cardSet) {
		List<Card> deck = new ArrayList<Card>();
		//Card[] deck = new Card[Rank.values().length * Color.values().length];
		int i = 0;
		for (Rank rank : Rank.values())
			for (Color color : Color.values())
				deck.add(new Card(rank, color, cardSet));
				//deck[i++] = new Card(rank, color, cardSet);
		return deck;
	}

	static public void shuffle(List<Card> cards) {
		Collections.shuffle(cards, new Random(System.nanoTime()));
	}

	static public void sort(List<Card> cards) {
	    Collections.sort(cards, new Comparator<Card>() {
	        @Override
	        public int compare(Card card1, Card card2) {
	        	Integer color1 = card1.getColor().ordinal();
	        	Integer color2 = card2.getColor().ordinal();

	        	if(color1 != color2) {
	        		return color1.compareTo(color2);
	        	} else {
	        		Integer rank1 = card1.getRank().ordinal();
	        		Integer rank2 = card2.getRank().ordinal();
	        		if(rank1 != rank2)
	        			return rank1.compareTo(rank2);
	        		else
	        			return 0;
	        	}
	        }
	    });
	}
}
