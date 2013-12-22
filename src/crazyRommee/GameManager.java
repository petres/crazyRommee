package crazyRommee;

import crazyRommee.Card;
import crazyRommee.Table;
import crazyRommee.PlayerInterface;

import java.util.ArrayList;
import java.util.List;

public class GameManager extends Table {
	private int currentPlayer;
	private PlayerInterface[] playerInterfaces;
	private Card cardFromStack = null;

	public class GameManagerException extends Exception {
		public GameManagerException(String str) {
			super(str);
		}
	}

	private GameManager(PlayerInterface[] playerInterfaces) {
		super(playerInterfaces.length);
		this.playerInterfaces = playerInterfaces;
	}

	private boolean play() throws GameManagerException {
		cardFromStack = null;
		List<Combination> combinations = playerInterfaces[currentPlayer].play(this);

		// CHECK
		
		List<Card> combinationCardsBefore = Combination.getCards(this.combinations);
		List<Card> combinationCardsAfter  = Combination.getCards(combinations);
		List<Card> playerCardsBefore  	  = allPlayerCards.get(currentPlayer);
		List<Card> playerCardsAfter  	  = new ArrayList<Card>();

		// - are all cards in the new combinations that has already been in the old combinations
		for(Card card: combinationCardsBefore) {
			if(!combinationCardsAfter.contains(card))
				throw new GameManagerException(card + "(" + card.getHiddenCardString() + ") has been in old combinations but is not in the new ones!");
		} 

		// - are all cards of the new combination from the 
		for(Card card: combinationCardsAfter) {
			if(!combinationCardsBefore.contains(card) && !playerCardsBefore.contains(card))
				throw new GameManagerException(card + "(" + card.getHiddenCardString() + ") comes from nowhere!");
		} 

		// - has the user taken a card from the stack?
		if(combinationCardsBefore.size() == combinationCardsAfter.size() && cardFromStack == null) {
			//throw new GameManagerException("You have to take a card, I will not do it for you, bastard!");
			System.out.println("You have to take a card, I do it this time for you, bastard!");
			getCardFromDeck();
		}

		// - are there two combinations with same rank 
		boolean[] ranks = new boolean[Card.Rank.values().length];
		for(Combination combination: combinations) {
			if(combination.getType() == Combination.Type.RANK) {
				if(ranks[combination.getRank().ordinal()])
					throw new GameManagerException(combination + " already on table!");
				ranks[combination.getRank().ordinal()] = true;
			}
		}

		// calc player cards
		for(Card card: playerCardsBefore) {
			if(!combinationCardsAfter.contains(card))
				playerCardsAfter.add(card);
		}


		allPlayerCards.set(currentPlayer, playerCardsAfter);
		this.combinations = combinations;
		//allPlayerCards.set(currentPlayer, );
		


		// NEXT PLAYER 
		currentPlayer = (++currentPlayer)%playerInterfaces.length;

		return false;
	}

	public static void main(String[] args) throws GameManagerException {
		//System.out.println(Card.getHiddenCardString());
		PlayerInterface[] players = new PlayerInterface[2];
		players[0] = new TerminalPlayer();
		players[1] = new DummyPlayer();


		GameManager gameManager = new GameManager(players);
		gameManager.init();

		while(!gameManager.play()) {}

		//testCombination();
	}

	public Card getCardFromDeck() throws GameManagerException{
		if(cardFromStack != null) 
			throw new GameManagerException("You have already taken a card!");
		cardFromStack = stack.pop();
		allPlayerCards.get(currentPlayer).add(cardFromStack);
		return cardFromStack;
	}

	public List<Card> getMyCards() {
		return new ArrayList<Card>(allPlayerCards.get(currentPlayer));
	}

	public String toString() {
		String str = super.toString();
		
		/*
		// CARDS OF CURENT PLAYER
		str += "\n";
		List<Card> playerCards = allPlayerCards.get(currentPlayer);
		str += "  Player " + (currentPlayer + 1) + ", your cards:  \n    ";
		for(Card card: playerCards) {
			str += card;
		}
		str += "\n";
		////////////////

		str += "\n----------------------------------------------------------------\n";*/
		return str;
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
	}

}
