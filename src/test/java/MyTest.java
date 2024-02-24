import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	/*
	 * Card class
	 * */
	@Test
	void CardConstructor01() {
		Card myCard = new Card("hearts", 13);
		
		assertEquals("hearts", myCard.getSuite(), "Suite is not the same.");
		assertEquals(13, myCard.getValue(), "Value is not the same.");
	}
	@Test
	void CardConstructor02() {
		Card myCard = new Card("flower", -2);
		
		assertEquals("Not a valid suite", myCard.getSuite(), "Suite is not the same and is not valid.");
		assertEquals(-1, myCard.getValue(), "Value is not the same and is not valid.");
	}
	@Test
	void GetSuite01() {
		Card myCard = new Card("diamonds", 4);
		
		assertEquals("diamonds", myCard.getSuite(), "Suite is not the same.");
		assertEquals(4, myCard.getValue(), "Value is not the same.");
	}
	@Test
	void GetSuite02() {
		Card myCard = new Card("spades", 11);
		
		assertEquals("spades", myCard.getSuite(), "Suite is not the same.");
		assertEquals(11, myCard.getValue(), "Value is not the same.");
	}
	@Test
	void GetValue01() {
		Card myCard = new Card("clubs", 9);
		
		assertEquals("clubs", myCard.getSuite(), "Suite is not the same.");
		assertEquals(9, myCard.getValue(), "Value is not the same.");
	}
	@Test
	void GetValue02() {
		Card myCard = new Card("spades", 3);
		
		assertEquals("spades", myCard.getSuite(), "Suite is not the same.");
		assertEquals(3, myCard.getValue(), "Value is not the same.");
	}

	/*
	 * Baccarat Dealer class
	 * */
	@Test
	void BaccaratDealerConstructor01() {
		ArrayList<Card> deckTest = new ArrayList<Card>();
		for(int i = 1; i < 14; i++)
		{
			deckTest.add(new Card("diamonds", i));
			deckTest.add(new Card("hearts", i));
			deckTest.add(new Card("clubs", i));
			deckTest.add(new Card("spades", i));
		}
		BaccaratDealer theDealer = new BaccaratDealer();

		assertEquals(deckTest.size(), theDealer.deckSize(), "Decks are not the same size.");
		
		
	}

	@Test
	void GenerateDeck01() {
		ArrayList<Card> deckTest = new ArrayList<Card>();
		for(int i = 1; i < 14; i++)
		{
			deckTest.add(new Card("diamonds", i));
			deckTest.add(new Card("hearts", i));
			deckTest.add(new Card("clubs", i));
			deckTest.add(new Card("spades", i));
		}
		BaccaratDealer theDealer = new BaccaratDealer();
		theDealer.generateDeck();
		ArrayList<Card> deck = theDealer.getDeck();
		assertFalse(Arrays.equals(deckTest.toArray(), deck.toArray()), "Decks should not have the same cards as before.");
		assertEquals(deckTest.size(), theDealer.deckSize(), "Decks are not the same size.");
	}
	
	@Test
	void GenerateDeck02() {
		
		ArrayList<Card> deckTest = new ArrayList<Card>();
		for(int i = 1; i < 14; i++)
		{
			deckTest.add(new Card("diamonds", i));
			deckTest.add(new Card("hearts", i));
			deckTest.add(new Card("clubs", i));
			deckTest.add(new Card("spades", i));
		}
		
		BaccaratDealer theDealer = new BaccaratDealer();
		theDealer.generateDeck();
		assertFalse(Arrays.equals(deckTest.toArray(), theDealer.getDeck().toArray()), "Decks should not have the same cards as before.");
		for(int i = 0; i < 52; i++)
		{
			assertEquals(deckTest.get(i).getSuite(), theDealer.deck.get(i).getSuite(), "Suite is not the same.");
			assertEquals(deckTest.get(i).getValue(), theDealer.deck.get(i).getValue(), "Value is not the same.");
		}
		
	}
	
	@Test
	void DealHand01() {
		
		BaccaratDealer theDealer = new BaccaratDealer();
		ArrayList<Card> hand = theDealer.dealHand();
		assertEquals(50, theDealer.deckSize(), "Size is not the same.");
		for(int i = 0; i < 50; i++)
		{
			
			assertNotEquals(hand.get(0), theDealer.getDeck().get(i), "Card is not the same.");
			assertNotEquals(hand.get(1), theDealer.getDeck().get(i), "Card is not the same.");
		}
		
	}
	
	@Test
	void DealHand02() {
		
		BaccaratDealer theDealer = new BaccaratDealer();
		ArrayList<Card> hand1 = theDealer.dealHand();
		ArrayList<Card> hand2 = theDealer.dealHand();
		assertEquals(48, theDealer.deckSize(), "Size is not the same.");
		for(int i = 0; i < 48; i++)
		{
			
			assertNotEquals(hand1.get(0), theDealer.getDeck().get(i), "Card is not the same.");
			assertNotEquals(hand1.get(1), theDealer.getDeck().get(i), "Card is not the same.");
			assertNotEquals(hand2.get(0), theDealer.getDeck().get(i), "Card is not the same.");
			assertNotEquals(hand2.get(1), theDealer.getDeck().get(i), "Card is not the same.");
		}
		
	}
	
	@Test
	void DrawOne01() {
		
		BaccaratDealer theDealer = new BaccaratDealer();
		Card c = theDealer.drawOne();
		assertEquals(51, theDealer.deckSize(), "Size is not the same.");
		for(int i = 0; i < 51; i++)
		{
			assertNotEquals(c, theDealer.getDeck().get(i), "Card is not the same.");

		}
		
	}
	
	@Test
	void DrawOne02() {
		
		BaccaratDealer theDealer = new BaccaratDealer();
		Card c;
		for(int i = 0; i < 52; i++)
		{
			c = theDealer.drawOne();

		}
		assertEquals(0, theDealer.deckSize(), "Size is not the same.");
		c = theDealer.drawOne();
		assertEquals(51, theDealer.deckSize(), "Size is not the same.");

	}
	
	@Test
	void ShuffleDeck01() {
		BaccaratDealer theDealer = new BaccaratDealer();
		theDealer.shuffleDeck();
		BaccaratDealer theDealerFake = new BaccaratDealer();
		theDealerFake.generateDeck();
		
		assertEquals(52, theDealer.deckSize(), "Size is not the same.");
		assertEquals(52, theDealerFake.deckSize(), "Size is not the same.");
		assertEquals(theDealerFake.deckSize(), theDealer.deckSize(), "Size is not the same.");
		String suite = "";
		String suite1 = "";
		int val = 0;
		int val1 = 0;
		boolean isShuffled = false;
		for(int i = 0; i < 52; i++)
		{
			suite = theDealer.getDeck().get(i).getSuite();
			suite1 = theDealerFake.getDeck().get(i).getSuite();
			val = theDealer.getDeck().get(i).getValue();
			val1 = theDealerFake.getDeck().get(i).getValue();
			
			if(!suite.equals(suite1) && val != val1)
			{
				isShuffled = true;
			}
			
		}
		
		assertTrue(isShuffled, "Deck is not shuffled");
		
		
	}
	
	@Test
	void ShuffleDeck02() {
		BaccaratDealer theDealer = new BaccaratDealer();
		theDealer.shuffleDeck();
		BaccaratDealer theDealerFake = new BaccaratDealer();
		theDealerFake.shuffleDeck();
		
		assertEquals(52, theDealer.deckSize(), "Size is not the same.");
		assertEquals(52, theDealerFake.deckSize(), "Size is not the same.");
		assertEquals(theDealerFake.deckSize(), theDealer.deckSize(), "Size is not the same.");
		String suite = "";
		String suite1 = "";
		int val = 0;
		int val1 = 0;
		boolean isShuffled = false;
		for(int i = 0; i < 52; i++)
		{
			suite = theDealer.getDeck().get(i).getSuite();
			suite1 = theDealerFake.getDeck().get(i).getSuite();
			val = theDealer.getDeck().get(i).getValue();
			val1 = theDealerFake.getDeck().get(i).getValue();
			
			if(!suite.equals(suite1) && val != val1)
			{
				isShuffled = true;
			}
			
		}
		
		assertTrue(isShuffled, "Deck is not shuffled");
		
		
	}
	
	@Test
	void DeckSize01() {
		BaccaratDealer theDealer = new BaccaratDealer();
		assertEquals(52, theDealer.deckSize(), "Size is not the same.");
		
	}
	
	@Test
	void DeckSize02() {

		BaccaratDealer theDealer = new BaccaratDealer();
		ArrayList<Card> hand;
		for(int i = 0; i < 26; i++)
		{
			hand = theDealer.dealHand();
		}
		assertEquals(0, theDealer.deckSize(), "Size is not the same.");
		hand = theDealer.dealHand();
		assertEquals(50, theDealer.deckSize(), "Size is not the same.");
	}
	
	
//	/*
//	 * Baccarat Game Logic class
//	 * */
	@Test
	void WhoWon01() {
		ArrayList<Card> hand1 = new ArrayList<>();
		BaccaratGameLogic l = new BaccaratGameLogic();
		hand1.add(new Card("Heart", 4));
		hand1.add(new Card("Diamond", 5));
		
		ArrayList<Card> hand2 = new ArrayList<>();
		hand2.add(new Card("Spade", 4));
		hand2.add(new Card("Diamond", 3));
		
		String winner = l.whoWon(hand1, hand2);
		
		assertEquals("Player", winner);
	}
//	
//	/*
//	 * Baccarat Game Logic class
//	 * */
	@Test
	void WhoWon02() {
		ArrayList<Card> hand1 = new ArrayList<>();
		BaccaratGameLogic l = new BaccaratGameLogic();
		hand1.add(new Card("Heart", 4));
		hand1.add(new Card("Diamond", 5));
		
		ArrayList<Card> hand2 = new ArrayList<>();
		hand2.add(new Card("Spade", 4));
		hand2.add(new Card("Heart", 5));
		
		String winner = l.whoWon(hand1, hand2);
		
		assertEquals("Draw", winner);
	}
	
	@Test
	void HandTotal01() {
		
		ArrayList<Card> hand = new ArrayList<>();
		BaccaratGameLogic l = new BaccaratGameLogic();
		hand.add(new Card("Heart", 4));
		hand.add(new Card("Diamond", 3));
		
		assertEquals(7, l.handTotal(hand), "Hand Total is not correct.");
	}
	
	@Test
	void HandTotal02() {
		
		ArrayList<Card> hand = new ArrayList<>();
		BaccaratGameLogic l = new BaccaratGameLogic();
		hand.add(new Card("Heart", 10));
		hand.add(new Card("Diamond", 3));
		
		assertEquals(3, l.handTotal(hand), "Hand Total is not correct.");
	}
	
	@Test
	void HandTotal03() {
		
		ArrayList<Card> hand = new ArrayList<>();
		BaccaratGameLogic l = new BaccaratGameLogic();
		hand.add(new Card("Heart", 10));
		hand.add(new Card("Diamond", 12));
		
		assertEquals(0, l.handTotal(hand), "Hand Total is not correct.");
	}
	
	@Test
	void EvaluateBankerDraw01() {
		
		ArrayList<Card> hand = new ArrayList<>();
		BaccaratGameLogic l = new BaccaratGameLogic();
		hand.add(new Card("Heart", 4));
		//hand.add(new Card("Diamond", 2));
		hand.add(new Card("Diamond", 3));
		
		Card player = new Card("Club", 7);
		
		//assertTrue(l.evaluateBankerDraw(hand, player));
		assertFalse(l.evaluateBankerDraw(hand, player));
		
		//evaluateBankerDraw(ArrayList<Card> hand, Card playerCard)
		
	}
	
	@Test
	void EvaluateBankerDraw02() {
		ArrayList<Card> hand = new ArrayList<>();
		BaccaratGameLogic l = new BaccaratGameLogic();
		hand.add(new Card("Heart", 3));
		hand.add(new Card("Diamond", 2));
		
		Card player = new Card("Club", 4);
		
		assertTrue(l.evaluateBankerDraw(hand, player));
		
		//evaluateBankerDraw(ArrayList<Card> hand, Card playerCard)
		
	}
	
	@Test
	void EvaluatePlayerDraw01() {
		ArrayList<Card> hand = new ArrayList<>();
		BaccaratGameLogic l = new BaccaratGameLogic();
		hand.add(new Card("Heart", 3));
		hand.add(new Card("Diamond", 3));
		
		
		assertFalse(l.evaluatePlayerDraw(hand));
		
	}
	
	@Test
	void EvaluatePlayerDraw02() {
		ArrayList<Card> hand = new ArrayList<>();
		BaccaratGameLogic l = new BaccaratGameLogic();
		hand.add(new Card("Heart", 3));
		hand.add(new Card("Diamond", 2));
		
		
		assertTrue(l.evaluatePlayerDraw(hand));
		
	}
//	
//	/*
//	 * Baccarat Game class
//	 * */
	@Test
	void EvaluateWinnings01() {
		
		BaccaratDealer theDealer = new BaccaratDealer();
		BaccaratGameLogic gameLogic = new BaccaratGameLogic();
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("Heart", 3));
		hand.add(new Card("Diamond", 2)); 
		
		ArrayList<Card> hand2 = new ArrayList<>();
		hand2.add(new Card("Heart", 6));
		hand.add(new Card("Diamond", 0)); 
		
		double currentBet = 4;
		String playerBet = "Player";
		
		if( (gameLogic.whoWon(hand, hand2).equals("Player")) )
		{
			if(playerBet.equals("Player"))
			{
				
				currentBet*=2;
				//if the player bets on player and player wins, they win double
			}
			else if(playerBet.equals("Banker"))
			{
				
				currentBet = (currentBet*2) - (currentBet*0.05);
				//if the player bets on banker and banker wins, they win double minus 5%
			}
			else if(playerBet.equals("Draw"))
			{
				currentBet = (8*currentBet)+ currentBet;
				//if the player bets on a draw and there is a draw, the bet is matched by 8
			}
		}
		
		
		assertEquals(4.0, currentBet, "Total winnings Wrong");
	}
	
	@Test
	void EvaluateWinnings02() {
		
		BaccaratDealer theDealer = new BaccaratDealer();
		BaccaratGameLogic gameLogic = new BaccaratGameLogic();
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("Heart", 3));
		hand.add(new Card("Diamond", 6)); 
		
		ArrayList<Card> hand2 = new ArrayList<>();
		hand2.add(new Card("Heart", 6));
		hand.add(new Card("Diamond", 0)); 
		
		double currentBet = 4;
		String playerBet = "Player";
		
		if( (gameLogic.whoWon(hand, hand2).equals("Player")) )
		{
			if(playerBet.equals("Player"))
			{
				
				currentBet*=2;
				//if the player bets on player and player wins, they win double
			}
			else if(playerBet.equals("Banker"))
			{
				
				currentBet = (currentBet*2) - (currentBet*0.05);
				//if the player bets on banker and banker wins, they win double minus 5%
			}
			else if(playerBet.equals("Draw"))
			{
				currentBet = (8*currentBet)+ currentBet;
				//if the player bets on a draw and there is a draw, the bet is matched by 8
			}
		}
		
		
		assertEquals(8.0, currentBet, "Total winnings Wrong");
	}
}
