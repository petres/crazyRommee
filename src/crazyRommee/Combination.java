package crazyRommee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combination {
	public enum Type {
		STREET,
		RANK
	}

	public class CombinationException extends Exception {
		public CombinationException(String str) {
			super(str);
		}
	}

	private List<Card> cards;
	private Type type;

	private Card.Rank rank;
	private Card.Color color;
	
	public Combination(Card[] cards) throws CombinationException{
		this(Arrays.asList(cards));
	}

	public Combination(List<Card> cards) throws CombinationException{
		this.cards = cards;
		color = null;
		rank = null;

		if(cards.size() < 3)
			throw new Combination.CombinationException("Can not create Combination, to less cards!");

		boolean sameRank = true;
		boolean sameColor = true;

		List<Card.Rank>  ranks  = new ArrayList<Card.Rank>();
		List<Card.Color> colors = new ArrayList<Card.Color>();

		for(Card card: cards) {
			if(rank != null && card.getRank() != rank)
				sameRank = false;
			rank = card.getRank();
			ranks.add(rank);

			if(color != null && card.getColor() != color)
				sameColor = false;
			color = card.getColor();
			colors.add(color);
		}

		if(!sameColor && !sameRank)
			throw new Combination.CombinationException("[" + this + "] Can not create Combination, cards contain different colors and ranks!");

		// RANK CONDITIONS
		if(sameRank) {
			boolean[] inCards = new boolean[Card.Color.values().length];
			for(Card.Color colorT: colors) {
				if(!inCards[colorT.ordinal()])
					inCards[colorT.ordinal()] = true;
				else
					throw new Combination.CombinationException("[" + this + "] Can not create Rank Combination, cards contain one rank in the same color!");
			}
			type = Type.RANK;
			return;
		}

		// STREET CONDITIONS
		if(sameColor) {
			int[] inCards = new int[Card.Rank.values().length];
			for(Card.Rank rankT: ranks) {
				inCards[rankT.ordinal()]++;
			}

			// GET START OF STREET
			int tIndex = 0;
			int tValue = inCards[tIndex];
			for(int i = 1; i < Card.Rank.values().length; i++) {
				if(inCards[i] > tValue) {
					tValue = inCards[i];
					tIndex = i;
				}
			}

			// GO ALONG THIS STREET
			int j = tIndex;			
			while (inCards[j%Card.Rank.values().length] > 0) {
				inCards[j%Card.Rank.values().length]--;
				j++;
			}

			// STILL CARDS
			for(int i = 1; i < Card.Rank.values().length; i++) {
				if(inCards[i] != 0)
					throw new Combination.CombinationException("[" + this + "] Can not create Street Combination, some cards do not fit!");
			}


			type = Type.STREET;
			return;
		}

	}

	public List<Card> getCards() {
		return cards;
	}

	public Combination.Type getType() {
		return type;
	}

	public String toString() {
		//String str = "COMB. (" + getType() + "): ";
		String str = "";
		for(Card card: getCards()) {
			str += card;
		}
		if(type != null)
			str += " (" + type +")";

		return str;
	}

}
