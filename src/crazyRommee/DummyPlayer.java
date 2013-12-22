package crazyRommee;

import crazyRommee.Card;
import crazyRommee.Table;
import crazyRommee.Combination;

import java.util.List;

public class DummyPlayer implements PlayerInterface {
	public List<Combination> play(Table table) {
		try {
			table.getCardFromDeck();
		} catch(GameManager.GameManagerException e) {
			System.out.println(e);
		}
		return table.getCombinations();
	}
}