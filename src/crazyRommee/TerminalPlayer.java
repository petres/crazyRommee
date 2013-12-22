package crazyRommee;

import crazyRommee.Card;
import crazyRommee.Table;
import crazyRommee.Combination;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class TerminalPlayer implements PlayerInterface {
	
	List<Card> cards = new ArrayList<Card>();
	Table table;	

	public List<Combination> play(Table table) {
		this.table = table;
		List<Combination> combinations = table.getCombinations();

		System.out.println(this);


		Scanner sc = new Scanner(System.in);

		outerloop:
		while(sc.hasNextLine()) {

    		String input = sc.nextLine();
    		if (input.isEmpty()) {
    			System.out.println("--------- NEXT ROUND ---------------------");
    			break outerloop;
    		}
    		switch(input.substring(0, 1)) {
    			case "R":{
    				System.out.println("--------- REFRESH ------------------------");
    				System.out.println(this);
    				break;
    			} case "T": {
    				try {
						table.getCardFromDeck();
						System.out.println(this);
						System.out.println("--------- TOOK CARD FROM DECK ------------");
					} catch(GameManager.GameManagerException e) {
						System.out.println(e);
					}
    				break;
    			} case "C": {
    				try {
	    				System.out.println("--------- COMBINATION ------------");
	    				String[] split = input.substring(2).split(" ");

	    				List<Card> tCards = new ArrayList<Card>();
	    				for(String nr: split) {
	    					int i = Integer.parseInt(nr);
	    					tCards.add(cards.get(i));
	    				}

	    				Combination tCombination = new Combination(tCards);
	    				combinations.add(tCombination);
					} catch(Exception e) {
						System.out.println(e);
					}
    				//System.out.println(this);
    			} default: {
    				//System.out.println(input);	
    				break;
    			}
    		}
    	} 

		
		return combinations;
	}

	public String toString() {
		cards = new ArrayList<Card>();
		List<Card> playerCards = table.getMyCards();
		
		String str = table.toString();
		str += "\n  Your cards:  \n";

		String str2 = "";
		str += "   ";
		str2 += "   ";

		int i = 0;
		for(Card card: playerCards) {
			cards.add(card);
			str += card + " ";

			if(i < 10)
				str2 += " ";

			str2 += (i++) + " ";
		}
		str += "\n" + str2;

		return str;
	}

}