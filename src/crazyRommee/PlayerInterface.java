package crazyRommee;

import crazyRommee.Card;
import crazyRommee.Combination;

public interface PlayerInterface {
	public Combination[] play(Card[] playerCards, Combination[] tableCombinations, Card stackCard);
}
