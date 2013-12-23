package crazyRommee;

import crazyRommee.Card;
import crazyRommee.Table;
import crazyRommee.Combination;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.NumberFormatException;

public class TerminalPlayer implements PlayerInterface {
	List<Card> allAvailableCards = new ArrayList<Card>();
	List<Card> tempCards = new ArrayList<Card>();

	List<Card> playerCardsBefore = new ArrayList<Card>();
	List<Card> playerCardsNow    = new ArrayList<Card>();

	List<Combination> combinations = null;
	Table table;	

	public List<Combination> play(Table table) {	
		this.table = table;

		playerCardsBefore = table.getMyCards();
		playerCardsNow	  = new ArrayList<Card>(playerCardsBefore);
		combinations 	  = table.getCombinations();

		System.out.print(this);


		Scanner sc = new Scanner(System.in);

		outerloop:
		while(sc.hasNextLine()) {
			
    		String input = sc.nextLine();
    		System.out.println(" --------------------------------------------------------------------------------------------------");
    		if (input.isEmpty()) {
    			input = "f";
    		}
    		switch(input.substring(0, 1)) {
    			case "f":{
    				System.out.println("--------- FINISHED --------------");
	    			break outerloop;
    			} case "r":{
    				System.out.println("--------- REFRESHED --------------");
    				System.out.print(this);
	    			break;
    			} case "t": {
    				try {
    					Card tCard = table.getCardFromDeck();
    					playerCardsNow.add(tCard);
    					Card.sort(playerCardsNow);
						System.out.println("--------- TOOK " + tCard + " FROM DECK ------------");
						System.out.print(this);
					} catch(GameManager.GameManagerException e) {
						System.out.println(e);
					}
    				break;
    			} default: {
    				try {
	    				System.out.println("--------- COMBINATION ------------");
	    				String[] split = input.split(" ");

	    				List<Card> tCards = new ArrayList<Card>();
	    				for(String nr: split) {
	    					int i = Integer.parseInt(nr);
	    					tCards.add(allAvailableCards.get(i));
	    				}
	    				combine(tCards);
	    				System.out.print(this);
					} catch(Combination.CombinationException e) {
						System.out.println(e);
						System.out.print(this);
					} catch(NumberFormatException e) {
						System.out.println(e);
						System.out.print(this);
					}
					break;
    			}
    		}
    	} 
		return combinations;
	}

	public String toString() {
		String str = table.toString();
		allAvailableCards = new ArrayList<Card>();
		
		// COMBINATIONS
		str += "\n";
		str += "\n  Temp. Cards (Count: " + tempCards.size() + "):  \n";

		String str2 = "";
		str  += "   ";
		str2 += "   ";		
		for(Card card: tempCards) {
			str += card + " ";
			str2 += numberCard(card);
		}
		str += "\n" + str2;

		str += "\n";
		str += "  Working combinations (Count: " + combinations.size() + "): \n";
		
		str2 = "";
		str  += "   ";
		str2 += "   ";
		for(Combination combination: combinations) {
			str += combination;
			String tStr2 = "";
			for(Card card: combination.getCards())
				tStr2 += numberCard(card);

			str2 += tStr2;
			str2 += "    ";
		}
		////////////////
		str += "\n" + str2;

			
		str += "\n  Your cards (Count: " + playerCardsNow.size() + "):  \n";

		str2 = "";
		str  += "   ";
		str2 += "   ";		
		for(Card card: playerCardsNow) {
			str += card + " ";
			str2 += numberCard(card);
		}
		str += "\n" + str2;

		str += "\n --------------------------------------------------------------------------------------------------";
		str += "\n ---- INPUT: r: refresh; t: take card from deck; nr1 nr2 nr3 ...: combine cards, F: finished ----";
		str += "\n --------------------------------------------------------------------------------------------------";
		str += "\n ---- Your Input: ";
		return str;
	}

	private String numberCard(Card card) {
		allAvailableCards.add(card);
		if(allAvailableCards.size() < 11)
			return " " + (allAvailableCards.size() - 1) + " ";
		else 
			return (allAvailableCards.size() - 1) + " ";
	}

	private void combine(List<Card> combinationCards) throws Combination.CombinationException {
		Combination tCombination = new Combination(combinationCards);

		List<Integer> toDelete = new ArrayList<Integer>();
		for(Card card: combinationCards) {
			if(tempCards.contains(card)) {
				System.out.println(card + " removed from temp");
				tempCards.remove(card);
				continue;
			}

			if(playerCardsNow.contains(card)) {
				System.out.println(card + " removed from player");
				playerCardsNow.remove(card);
				continue;
			}

			int i = 0;
			for(Combination combination: combinations) {
				if(combination.getCards().contains(card)) {
					System.out.println("have to delete comb.: " + combination);
					if(!toDelete.contains(i))
						toDelete.add(0, i);
				}
				i++;
			}
		}

	    combinations.add(tCombination);
	    for(int i: toDelete) {
	    	for(Card card: combinations.get(i).getCards()) {
	    		if(combinationCards.contains(card))
	    			continue;

	    		if(playerCardsBefore.contains(card))
	    			playerCardsNow.add(card);
	    		else
	    			tempCards.add(card);
	    	}
	    	combinations.remove(i);
	    }
	    Card.sort(tempCards);
	    Card.sort(playerCardsNow);
	}

}