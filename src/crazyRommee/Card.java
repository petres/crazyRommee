package crazyRommee;

import java.util.Collections;
import java.util.Random;
import java.util.Arrays;

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
		HEART,
		SPADE,
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
			return "\033[1;47;31m" + card +  " \033[0m";
		else
			return "\033[1;47;30m" + card +  " \033[0m";
	}

	static public String getHiddenCardString() {
		return "\033[1;47;30m" + new String(new int[] { 0x1F0A0 }, 0, 1) + " \033[0m";
	}

	

	static public Card[] createDeck(Card.CardSet cardSet) {
		Card[] deck = new Card[Rank.values().length * Color.values().length];
		int i = 0;
		for (Rank rank : Rank.values())
			for (Color color : Color.values())
				deck[i++] = new Card(rank, color, cardSet);
		return deck;
	}

	static public void shuffle(Card[] cards) {
		Collections.shuffle(Arrays.asList(cards), new Random(System.nanoTime()));
	}
}
