package crazyRommee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Table {
	protected Stack<Card> stack;
	protected List<List<Card>> allPlayerCards;
	protected List<Combination> combinations;

	static protected int countOfCardsAtStart = 10;

	protected Table(int playerCount) {
		allPlayerCards = new ArrayList<List<Card>>();
		for(int i = 0; i < playerCount; i++)
			allPlayerCards.add(new ArrayList<Card>());
		stack = new Stack<Card>();
		combinations = new ArrayList<Combination>();
	}

	protected void init() {
		List<Card> redDeck  = Card.createDeck(Card.CardSet.RED);
		List<Card> blueDeck = Card.createDeck(Card.CardSet.BLUE);

		List<Card> decks = new ArrayList<Card>();
		decks.addAll(redDeck);
		decks.addAll(blueDeck);
		
		Card.shuffle(decks);

		stack.addAll(decks);

		for(List<Card> playerCards: allPlayerCards)
			for(int i = 0; i < countOfCardsAtStart; i++)
				playerCards.add(stack.pop());

	}
	
	public Card getCardFromDeck()  throws GameManager.GameManagerException {
		return null;
		//throw new Exception("not allowed");
	}
	
	public List<Card> getMyCards() {
		return null;
	}

	public List<Combination> getCombinations() {
		return new ArrayList<Combination>(combinations);
	}

	public String toString() {
		String str = ""; 

		str += "\n----------------------------------------------------------------";
		// Stack
		str += "\n";
		str += "  Stack (Count: " + stack.size() + "): \n    ";
		str += stack.peek().getHiddenCardString() + "\n";
		////////////////
		
		// CARDS OF PLAYER
		str += "\n";
		int p = 0;
		for(List<Card> playerCards: allPlayerCards) {
			str += "  Cards of player " + (++p) + " (Count: " + playerCards.size() + "): \n    ";
			for(Card card: playerCards) {
				str += card.getHiddenCardString();
			}
			str += "\n";
		}
		////////////////


		// COMBINATIONS
		str += "\n";
		str += "  Combinations on the table (Count: " + combinations.size() + "): \n    ";
		for(Combination combination: combinations) {
			str += combination;
		}
		////////////////

		str += "\n----------------------------------------------------------------\n";
		return str;
	}
}
