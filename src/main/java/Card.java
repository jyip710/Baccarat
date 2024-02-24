
public class Card {
	String suite;
	int value;
	
	Card(String theSuite, int theValue)
	{
		if(theSuite == "hearts" || theSuite == "diamonds" || theSuite == "clubs" || theSuite == "spades")
		{
			suite = theSuite;
		}
		else
		{
			suite = "Not a valid suite";
		}
		if(theValue < 0 || theValue >= 14)
		{
			value = -1;
		}
		else
		{
			value = theValue;
		}
	}
	
	String getSuite()
	{
		return this.suite;
	}
	
	int getValue()
	{
		return this.value;
	}
}
