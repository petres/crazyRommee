package crazyRommee;

import crazyRommee.Card;

public class GameManager {

	public static void main(String[] args) {
		System.out.println(Card.getHiddenCardString());
		Card[] deck = Card.createDeck(null);
		Card.shuffle(deck);
		for(Card card: deck) {
			System.out.print(card);
		}
		System.out.println();
	}

}
