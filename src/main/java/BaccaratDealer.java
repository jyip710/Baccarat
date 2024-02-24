import java.util.ArrayList;
import java.util.Collections;

public class BaccaratDealer {
	ArrayList<Card> deck;
	
	/* Generate a new standard 52 card deck where 
	 * each card is an instance of the Card class 
	 * in the ArrayList<Card> deck
	 * 
	 * You can use the values from 1 - 13 to represent the cards. 1 is ace, 2 ... 10, 
	 * 11 is jack, 12 is queen, and 13 is king. 
	 * For the suites, you can use “Hearts”, “Diamonds”, “Spades” and “Clubs”.
	 * */
	BaccaratDealer()
	{
		shuffleDeck();
	}
	
	public void generateDeck()
	{
		deck = new ArrayList<Card>();
		for(int i = 1; i < 14; i++)
		{
			deck.add(new Card("diamonds", i));
			deck.add(new Card("hearts", i));
			deck.add(new Card("clubs", i));
			deck.add(new Card("spades", i));
		}
	}
	
	/* Deal two cards and return them in an ArrayList<Card>.*/
	public ArrayList<Card> dealHand()
	{
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(drawOne());
		hand.add(drawOne());
		
		return hand;
	}
	
	/* Deal a single card and return it. */
	public Card drawOne()
	{
		if(deck.size() == 0)
		{
			shuffleDeck();
		}
		
		Card card = deck.get(0);
		deck.remove(0);
		return card;
	}
	
	/* Create a new deck of 52 cards and “shuffle”; 
	 * randomize the cards in that ArrayList<Card>*/
	public void shuffleDeck()
	{
		generateDeck();
		Collections.shuffle(deck);
	}
	
	/* Return how many cards are in this.deck at any given time.*/
	public int deckSize()
	{
		return deck.size();
	}
	
	public ArrayList<Card> getDeck()
	{
		return this.deck;
	}
}
