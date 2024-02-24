import java.util.ArrayList;

public class BaccaratGameLogic {
		
	/* Evaluate two hands at the end of the game and 
	 * return a string depending on the winner: 
	 * “Player”, “Banker”, “Draw”.*/
	public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2)
	{
		int playerTotal = handTotal(hand1);
		int bankerTotal = handTotal(hand2);
		
		
		if(playerTotal < bankerTotal)
		{
			return "Banker";
		}
		else if(playerTotal > bankerTotal)
		{
			return "Player";
		}
		return "Draw";
	}
	
	/* Take a hand and return how many points that hand is worth*/
	public int handTotal(ArrayList<Card> hand)
	{
		int sum = 0;
		for(int i = 0; i < hand.size(); i++)
		{
			if(hand.get(i).getValue() >= 10)
			{
				sum += 0;
			}
			else
			{
				sum += hand.get(i).getValue();
			}
		}
		if(sum >= 10)
		{
			sum = sum % 10;
		}
		return sum;
	}

	/* Return true if Banker should be dealt a third card, otherwise return false.*/
	public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard)
	{
		
		int sum = handTotal(hand);
		if(sum <= 2)
		{
			return true;
		}
		else if(sum == 3)
		{
			if(playerCard.getValue() != 8)
			{
				return true;
			}
			//return false;
		}
		else if(sum == 4)
		{
			if(playerCard.getValue() == 2 || playerCard.getValue() == 3 || playerCard.getValue() == 4 || playerCard.getValue() == 5 || playerCard.getValue() == 6 || playerCard.getValue() == 7)
			{
				return true;
			}
			//return false;
		}
		else if(sum == 5)
		{
			if(playerCard.getValue() == 4 || playerCard.getValue() == 5 || playerCard.getValue() == 6 || playerCard.getValue() == 7)
			{
				return true;
			}
			//return false;
		}
		else if(sum == 6)
		{
			if(playerCard.getValue() == 6 || playerCard.getValue() == 7)
			{
				return true;
			}
			//return false;
		}
		return false;
	}

	
	/* Return true if Player should be dealt a third card, otherwise return false.
	 * If the player's first two cards total 6 or more, then the player must stand 
	 * without drawing a card. If the player's first two cards total 5 or less, the 
	 * player must draw one additional card. */
	public boolean evaluatePlayerDraw(ArrayList<Card> hand)
	{
		
		int sum = handTotal(hand);
		
		if(sum <= 5)
		{
			return true;
		}
		return false;
	}
}
