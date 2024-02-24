import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BaccaratGame extends Application {
	HashMap<String, Scene> sceneMap; //holds the scenes to switch to
	Button sceneChangeBtn, newRound;
	
	static final int picHeight = 225;
	static final int picWidth = 225;
	
	PauseTransition pauseResults = new PauseTransition(Duration.seconds((1.75)));
	PauseTransition pauseDeal = new PauseTransition(Duration.seconds(1.50)); 
	PauseTransition pausePlayerNew = new PauseTransition(Duration.seconds(1.25)); 
	PauseTransition pauseBankerNew = new PauseTransition(Duration.seconds(1.25)); 
	
	GenericQueue<String> myQueue;
	ListView<String> displayQueueItems;
	//use this to add and delete from ListView
	ObservableList<String> storeQueueItemsInListView;
	
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	BaccaratDealer theDealer;
	BaccaratGameLogic gameLogic;
	double currentBet;
	double totalWinnings;
	double currentMoney = 10000;
	
	String playerBet; // String of player's bet
	HBox newRoundB;
	int roundCounter = 0; // Counting the rounds
	Label winnings; // Displaying the totalWinnings
	Label moneyLeft;
	Text bankerLabel = new Text(50, 170, "Banker");
	Text playerLabel = new Text(50, 170, "Player");
	String winner; // Display winning message
	
	TextField betAmount = new TextField(); //place to input user's bet amount
	ChoiceBox<String> betDropDown = new ChoiceBox<>(); //drop down for user's to pick who to bet on
	VBox bet = new VBox(20); //VBox to hold the bet drop down and the total winnings
	
	/* Determine if the user won or lost their bet.
	 * Return the amount won or lost based on the value in currentBet.*/
	public double evaluateWinnings()
	{
		if( (gameLogic.whoWon(playerHand, bankerHand).equals(playerBet)) )
		{
			winner = "Congrats, you bet "+ playerBet + "! You win"; //create win message
			if(playerBet.equals("Player"))
			{
				
				return currentBet*2;
				//if the player bets on player and player wins, they win double
			}
			else if(playerBet.equals("Banker"))
			{
				
				return (currentBet*2) - (currentBet*0.05);
				//if the player bets on banker and banker wins, they win double minus 5%
			}
			else if(playerBet.equals("Draw"))
			{
				return (8*currentBet)+ currentBet;
				//if the player bets on a draw and there is a draw, the bet is matched by 8
			}
		}
		else
		{
			winner = "Sorry, you bet "+ playerBet +"! You lost your bet!"; //create lose message
		}
		
		return 0;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// TODO Auto-generated method stub
		primaryStage.setTitle("Project 2: Baccarat");
		
		
		sceneMap = new HashMap<String,Scene>();
		sceneMap.put("Game Page", createGamePage()); //Adds game scene to the map of scenes
		
		
	 // Makes a card spin on the front page
		Image back1 = new Image("back_of_card.png");
        ImageView back = new ImageView(back1);
        back.setFitHeight(picHeight);
        back.setFitWidth(picWidth);
        back.setPreserveRatio(true);
        RotateTransition rt = new RotateTransition(Duration.millis(5000), back);
        rt.setByAngle(270);
        rt.setCycleCount(4);
        rt.setAutoReverse(true);
        SequentialTransition seqTransition = new SequentialTransition(new PauseTransition(Duration.millis(500)), rt);
        seqTransition.play();
        
     // Makes the card fade in and out on the front page
        FadeTransition ft = new FadeTransition(Duration.millis(5000), back);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setCycleCount(4);
        ft.setAutoReverse(true);
        ft.play();
        
     // Front scene is border pane with a VBox in the center to have the card and the start button
        BorderPane root = new BorderPane();
        VBox center = new VBox(45);
        
     // Make the start button go to the game page scene
        sceneChangeBtn = new Button("Start");
        sceneChangeBtn.setOnAction(e -> primaryStage.setScene(sceneMap.get("Game Page")));
        
     // Add the card and button the VBox
        center.getChildren().addAll(back,sceneChangeBtn);
        
     // Set the Box as the center
        root.setCenter(center);
        center.setAlignment(Pos.CENTER);
        
     // Make the front page scene
        Scene scene = new Scene(root, 400, 500);
        root.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		sceneMap.put("Front Page", scene);
		
	 // show the front page scene
		primaryStage.setScene(sceneMap.get("Front Page"));
		primaryStage.show();
	
		
	}
	
//	• There should be an area to display both the Players and Bankers cards
// with each clearly labeled. You may use images or text to display the cards.
//	• There should be a button to start each round of play.
//	• There should be an area where the user can enter the amount to bid and
//	decide if they are bidding on The Player, The Banker or a Draw.
//	• There should be an area displaying the current winnings for the user
//	• There should be an area that displays the results of each round of play
// You will need to have a menu bar in your program with one tab: Options
//	Under options you will have Exit and Fresh Start. Exit will end the program while
//		Fresh Start will reset the current winnings to zero and allow the user to play another
//		game.
		
	// Sets up the game page as well as sets the game start page
	public Scene createGamePage() {
		
	 // Create the scene border pane
		BorderPane pane = new BorderPane();
		moneyLeft = new Label("Current amount of money you have: $" + currentMoney);
		winnings = new Label("Total winnings $" + totalWinnings);
	
		HBox player = new HBox(20); // holds player's cards
		HBox banker = new HBox(20); // holds banker's cards
		HBox spaceForBet = new HBox(50); // HBox to help pad the left from the center of border pane
		VBox menuBar = new VBox(20); // Holds menu bar
		
		myQueue = new GenericQueue<String>("Results per Round"); // hold's the results of the round
		displayQueueItems = new ListView<String>();
		storeQueueItemsInListView = FXCollections.observableArrayList();
		
		storeQueueItemsInListView.add("Results per Round"); // The listView will always inform player that it will display the round results
		displayQueueItems.setItems(storeQueueItemsInListView);
		
		totalWinnings = 0; //clear the total winnings
		
		MenuBar mbar = new MenuBar();
		Menu optionsMenu = new Menu("Options");
        mbar.getMenus().add(optionsMenu); //adding "Options" to the menu bar
       
        MenuItem freshStart = new MenuItem("Fresh Start");
     // If the user click's fresh start
        freshStart.setOnAction(e-> {
        	bet.getChildren().clear();
        	player.getChildren().clear(); // Clear the player's cards
    		banker.getChildren().clear(); // Clear the banker's cards
        	roundCounter = 0;  // reset the # of rounds
        	totalWinnings = 0; // reset the total money won
        	currentMoney = 10000;
        	betAmount = new TextField();
        	moneyLeft = new Label("Current amount of money you have: $" + currentMoney);
			winnings = new Label("Total winnings $" + totalWinnings); // Adjust the winning amount
        	bet.getChildren().add(moneyLeft);
			bet.getChildren().add(betAmount); // add the bet prompt text field
            bet.getChildren().add(betDropDown); // add the bet drop down
            bet.getChildren().add(newRoundB); // add the play button
			bet.getChildren().add(winnings);  // Add the winning Amount to the bet info
        	
         // Remove everything in the list view
        	displayQueueItems.getItems().removeAll(storeQueueItemsInListView);
			storeQueueItemsInListView.clear();
			
		 // Reset the queue that hold's the results
			myQueue.dequeue();
			myQueue = new GenericQueue<String>("Round Results");
			Iterator<String> i = myQueue.createIterator();
			while(i.hasNext()) {
				storeQueueItemsInListView.add(i.next());
			}
			displayQueueItems.setItems(storeQueueItemsInListView);
    		
        });
     // Add fresh start as an menu item under "Options"
        optionsMenu.getItems().add(freshStart);

     // If the user click's exit
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e-> {Platform.exit();}); //exit the program
     // Add exit as an menu item under "Options"
        optionsMenu.getItems().add(exit);
		
        newRound = new Button("Play");
        newRoundB = new HBox(75); //HBox for button
        newRound.setDisable(true); //disable it
     // If the user click's the play button
        newRound.setOnAction(e -> {
        	String bid_string = betAmount.getText(); //get what they bet
        	Double bid_value = Double.parseDouble(bid_string); //make it a double
        	if(bid_value > currentMoney) {
        		return;
        	}
        	currentMoney -= bid_value;
        	
        	betDropDown.setDisable(true); // disable the drop down
        	currentBet = bid_value; // set their current bet
        	
        	betAmount.setDisable(true); // disable the bet prompt text field
        	newRound.setDisable(true); // disable the play button
        	
        	player.getChildren().clear(); // clear the player's cards
    		banker.getChildren().clear(); // clear the banker's cards
        	playGame(pane, player, banker, myQueue, displayQueueItems, storeQueueItemsInListView); //play the game
        });
        
     // Setting up the bet selection fields
        Label playerSelection = new Label("Nothing slected"); // inform player they didn't click any option in dropdown
        
        betAmount.setPromptText("0.00"); // inform them to input a double
        betAmount.setFocusTraversable(false); //make sure the text field is not immediately highlighted for them
        
     // Create the drop down options 
        betDropDown.getItems().add("Player");
        betDropDown.getItems().add("Draw");
        betDropDown.getItems().add("Banker");
        
     // Depending on which they pick
        betDropDown.getSelectionModel().selectedItemProperty().addListener((v, prevVal, currVal) -> {
        	playerBet = currVal; //  set who the player bet on
        	newRound.setDisable(false); // disable the play button
        	playerSelection.setText(playerBet + " selected"); // inform player who they picked
        });
      
     // Add the bet text field, drop down, and the "who was selected label" to the bet box
        bet.getChildren().add(moneyLeft);
        bet.getChildren().add(betAmount);
        bet.getChildren().add(betDropDown);
        bet.getChildren().add(playerSelection);
        Text newText = new Text();
        spaceForBet.getChildren().addAll(bet, newText);
        pane.setLeft(spaceForBet); // add the bet box to the left side of the pane
        
     // Add the play button to the bet box, while also padding to the right of the bet box
        Text filler = new Text();
        newRoundB.getChildren().addAll(newRound, filler);
        bet.getChildren().add(newRoundB);
        
     // Add the menu bar to the top of the pane
		Text filler2 = new Text();
		menuBar.getChildren().addAll(mbar, filler2);
		pane.setTop(menuBar);
		BorderPane.setAlignment(menuBar, Pos.CENTER);
		
	 // Create the scene
		pane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));		
		return new Scene(pane, 1200, 900);
	}
	
	// The real game that handles displaying the cards and results and betting information
	public void playGame(BorderPane pane, HBox player, HBox banker, GenericQueue<String> myQueue,
						ListView<String> displayQueueItems, ObservableList<String> storeQueueItemsInListView)
	{
		
		VBox cardDisplay = new VBox(20); // Holds the player's cards, the banker's cards, and the "deck"
		VBox list = new VBox(100); // VBox for the list view so it doesn't fill the whole right side
		
		theDealer = new BaccaratDealer(); // Make the Dealer
		gameLogic = new BaccaratGameLogic(); // Make the game logic
		
		playerHand = theDealer.dealHand(); // Deal player's cards
		bankerHand = theDealer.dealHand(); // Deal banker's cards
		
		for(Card c: playerHand)
		{
		 // Make all the cards in the player's hand and them to the respective HBox
			int val = c.getValue();
			String suit = c.getSuite();
			
			String path = val + "_of_" +suit + ".png";
			Image pic = new Image(path);
			ImageView v = new ImageView(pic);
			v.setFitHeight(picHeight);
			v.setFitWidth(picWidth);
			v.setPreserveRatio(true);
			
			player.getChildren().add(v);
			
		}
		
		
		for(Card c: bankerHand)
		{
			// Make all the cards in the banker's hand and them to the respective HBox
			int val = c.getValue();
			String suit = c.getSuite();
			
			String path = val + "_of_" +suit + ".png";
			Image pic = new Image(path);
			ImageView v = new ImageView(pic);
			v.setFitHeight(picHeight);
			v.setFitWidth(picWidth);
			v.setPreserveRatio(true);
			
			banker.getChildren().add(v);
			
		}
		
		winner = "";
		// After the cards are dealt....
		pauseDeal.setOnFinished(e -> {
			// Check for a "natural 9" or 8
			if((gameLogic.handTotal(playerHand) == 8) || (gameLogic.handTotal(bankerHand) == 8) || (gameLogic.handTotal(playerHand) == 9) || (gameLogic.handTotal(bankerHand) == 9) )
			{
				gameLogic.whoWon(playerHand, bankerHand);
				roundCounter++;
				
				// Pause and then update all the GUIs related to the results
				pauseResults.setOnFinished(b -> {
					// Update the total winnings based on the bet
					
					totalWinnings += Math.round(evaluateWinnings() * 100.0) / 100.0;
					currentMoney += Math.round(evaluateWinnings() * 100.0) / 100.0;
					// add the results to the queue so they can be displayed
					myQueue.enqueue("Round: " + roundCounter +"\nPlayer Total: " + Integer.toString(gameLogic.handTotal(playerHand)) +
							" Banker Total: " + Integer.toString(gameLogic.handTotal(bankerHand)) +
							"\n" + gameLogic.whoWon(playerHand, bankerHand) + " wins\n"+ winner);
					displayQueueItems.getItems().removeAll(storeQueueItemsInListView);
					storeQueueItemsInListView.clear();
					Iterator<String> i = myQueue.createIterator();
					while(i.hasNext()) {
						storeQueueItemsInListView.add(i.next());
					}
					displayQueueItems.setItems(storeQueueItemsInListView);
					
					// Make the drop down, bet amount text field, and button clickable again
					betDropDown.setDisable(false);
					betAmount.setDisable(false);
					newRound.setDisable(false);
					moneyLeft = new Label("Current amount of money you have: $" + currentMoney);
					winnings = new Label("Total winnings $" + totalWinnings); // Adjust the winning amount
					bet.getChildren().clear();
					bet.getChildren().add(moneyLeft);
					bet.getChildren().add(betAmount); // add the bet prompt text field
		            bet.getChildren().add(betDropDown); // add the bet drop down
		            bet.getChildren().add(newRoundB); // add the play button
					bet.getChildren().add(winnings); // Add the winning mmount to the bet info
				});
				
				pauseResults.play();
			}
			else
			{
				// If there was not a natural 9 or 8, then...
				pausePlayerNew.setOnFinished(p -> {
					// evaluate the player's cards to see if they can pick up a card and add it to the respective HBox
					if(gameLogic.evaluatePlayerDraw(playerHand))
					{
						Card c = theDealer.drawOne();
						int val = c.getValue();
						String suit = c.getSuite();
						playerHand.add(c);
						
						String path = val + "_of_" +suit + ".png";
						Image pic = new Image(path);
						ImageView v = new ImageView(pic);
						v.setFitHeight(picHeight);
						v.setFitWidth(picWidth);
						v.setPreserveRatio(true);
						
						player.getChildren().add(v);
						
						// Pause and check if the banker can also pick up a card based on the player's card and add it to the respective HBox
						pauseBankerNew.setOnFinished(a-> {
							
							if(gameLogic.evaluateBankerDraw(bankerHand, c))
							{
								Card c1 = theDealer.drawOne();
								int val1 = c1.getValue();
								String suit1 = c1.getSuite();
								bankerHand.add(c1);
								
								String path1 = val1 + "_of_" +suit1 + ".png";
								Image pic1 = new Image(path1);
								ImageView v1 = new ImageView(pic1);
								v1.setFitHeight(picHeight);
								v1.setFitWidth(picWidth);
								v1.setPreserveRatio(true);
								
								banker.getChildren().add(v1);
							}
						});
						
						pauseBankerNew.play();
						
						// Pause and then update all the GUIs related to the results
						roundCounter++;
						pauseResults.setOnFinished(b -> {
							// Update the total winnings based on the bet
							totalWinnings += Math.round(evaluateWinnings() * 100.0) / 100.0;
							currentMoney += Math.round(evaluateWinnings() * 100.0) / 100.0;
							// add the results to the queue so they can be displayed
							myQueue.enqueue("Round: " + roundCounter +"\nPlayer Total: " + Integer.toString(gameLogic.handTotal(playerHand)) +
									" Banker Total: " + Integer.toString(gameLogic.handTotal(bankerHand)) +
									"\n" + gameLogic.whoWon(playerHand, bankerHand) + " wins\n"+ winner);
							displayQueueItems.getItems().removeAll(storeQueueItemsInListView);
							storeQueueItemsInListView.clear();
							Iterator<String> i = myQueue.createIterator();
							while(i.hasNext()) {
								storeQueueItemsInListView.add(i.next());
							}
							displayQueueItems.setItems(storeQueueItemsInListView);
							
							// Make the drop down, bet amount text field, and button clickable again
							betDropDown.setDisable(false);
							betAmount.setDisable(false);
							newRound.setDisable(false);

							moneyLeft = new Label("Current amount of money you have: $" + currentMoney);
							winnings = new Label("Total winnings $" + totalWinnings);// Adjust the winning amount
							bet.getChildren().clear();
							bet.getChildren().add(moneyLeft);
							bet.getChildren().add(betAmount); // add the bet prompt text field
				            bet.getChildren().add(betDropDown); // add the bet drop down
				            bet.getChildren().add(newRoundB); // add the play button
							bet.getChildren().add(winnings); // Add the winning mmount to the bet info
						});
						
						pauseResults.play();
						
					}
					else
					{
						// If the player was not able to pick up a card, then see if the banker can pick up a card
						// (follows the rules of the player in this case
						
						// Pause and check if the banker can pick up a card and add it to the respective HBox
						pauseBankerNew.setOnFinished(c -> {
							
							if(gameLogic.evaluatePlayerDraw(bankerHand))
							{
								Card c1 = theDealer.drawOne();
								int val1 = c1.getValue();
								String suit1 = c1.getSuite();
								bankerHand.add(c1);
								
								String path1 = val1 + "_of_" +suit1 + ".png";
								Image pic1 = new Image(path1);
								ImageView v1 = new ImageView(pic1);
								v1.setFitHeight(picHeight);
								v1.setFitWidth(picWidth);
								v1.setPreserveRatio(true);
								
								banker.getChildren().add(v1);
							}
							
						});
						pauseBankerNew.play();
						
						// Pause and then update all the GUIs related to the results
						roundCounter++;
						pauseResults.setOnFinished(b -> {
							// Update the total winnings based on the bet
							totalWinnings += Math.round(evaluateWinnings() * 100.0) / 100.0;
							currentMoney += Math.round(evaluateWinnings() * 100.0) / 100.0;
							// add the results to the queue so they can be displayed
							myQueue.enqueue("Round: " + roundCounter +"\nPlayer Total: " + Integer.toString(gameLogic.handTotal(playerHand)) +
									" Banker Total: " + Integer.toString(gameLogic.handTotal(bankerHand)) +
									"\n" + gameLogic.whoWon(playerHand, bankerHand) + " wins\n"+ winner);
							displayQueueItems.getItems().removeAll(storeQueueItemsInListView);
							storeQueueItemsInListView.clear();
							Iterator<String> i = myQueue.createIterator();
							while(i.hasNext()) {
								storeQueueItemsInListView.add(i.next());
							}
							displayQueueItems.setItems(storeQueueItemsInListView);
							
							// Make the drop down, bet amount text field, and button clickable again
							betDropDown.setDisable(false);
							betAmount.setDisable(false);
							newRound.setDisable(false);
							evaluateWinnings();
							moneyLeft = new Label("Current amount of money you have: $" + currentMoney);
							winnings = new Label("Total winnings $" + totalWinnings); // Adjust the winning amount
							bet.getChildren().clear();
							bet.getChildren().add(moneyLeft);
							bet.getChildren().add(betAmount); // add the bet prompt text field
				            bet.getChildren().add(betDropDown); // add the bet drop down
				            bet.getChildren().add(newRoundB); // add the play button
							bet.getChildren().add(winnings);  // Add the winning Ammount to the bet info
						});
						
						pauseResults.play();
					}
				});
				pausePlayerNew.play();
				
			}
			
		});
		pauseDeal.play();
		
		
		// Adjust ListView and set it to the right side
		displayQueueItems.setPrefWidth(250);
		displayQueueItems.setPrefHeight(450);
		Text filler = new Text();
		list.getChildren().addAll(filler, displayQueueItems);
		pane.setRight(list);
		
		// Make an image of the "back of the deck" for aesthetic
		Image back1 = new Image("back_of_card.png");
		ImageView back = new ImageView(back1);
		back.setFitHeight(picHeight);
		back.setFitWidth(picWidth);
		back.setPreserveRatio(true);
		
		HBox backH = new HBox(175);
		Text filler4 = new Text();
		backH.getChildren().addAll(filler4, back);
		
		// Add the cards to the card display VBox and set to the center
		cardDisplay.getChildren().addAll(bankerLabel, banker, backH, player, playerLabel);
		pane.setCenter(cardDisplay);
		
	}
	
	
}