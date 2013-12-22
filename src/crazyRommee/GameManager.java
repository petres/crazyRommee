package crazyRommee;

import crazyRommee.Card;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
	public static void main(String[] args) {
		System.out.println(Card.getHiddenCardString());
		List<Card> deck = Card.createDeck(null);
		Card.shuffle(deck);
		for(Card card: deck) {
			System.out.print(card);
		}
		System.out.println();
		testCombination();
	}

	public static void testCombination() {
		List<Card> cards  = new ArrayList<Card>();
		// cards.add(new Card(Card.Rank.NINE, Card.Color.HEART));
		// cards.add(new Card(Card.Rank.TEN, Card.Color.HEART));
		// cards.add(new Card(Card.Rank.JACK, Card.Color.HEART));
		// cards.add(new Card(Card.Rank.EIGHT, Card.Color.HEART));

		cards.add(new Card(Card.Rank.JACK, Card.Color.CLUB));
		cards.add(new Card(Card.Rank.JACK, Card.Color.HEART));
		cards.add(new Card(Card.Rank.JACK, Card.Color.DIAMOND));
		cards.add(new Card(Card.Rank.JACK, Card.Color.DIAMOND));

		try {
			Combination comb = new Combination(cards);
			System.out.println(comb);
		} catch (Combination.CombinationException e) {
			System.out.println(e);
		}
		//new Card(rank, color);
	}

}
