package crazyRommee;

import crazyRommee.Card;
import crazyRommee.Table;
import crazyRommee.Combination;
import java.util.List;

public interface PlayerInterface {
	public List<Combination> play(Table table);
}
