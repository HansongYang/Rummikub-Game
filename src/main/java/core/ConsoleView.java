package core;

public class ConsoleView implements View{

	public Model model; 
	
	public ConsoleView(Model model) {
		this.model = model;
	}
	
	public void indicateTurn(Player player) {
		System.out.println("\nPlayer " + player.name + "'s turn");
	}

	
    public void displayPlayerHand(Player player) {
        player.getHand().sortTilesByColour();
        player.getHand().printHand();
    }
    
    public void displayHand(Hand hand) {
    	hand.printHand();
    }
    
    public void displayBoard(Board board) {
    	board.printBoard();
    }
    
    public void displayMeld(Meld meld) {
    	meld.printMeld();
    }
    
    public void displayWinner(Player player) {
		System.out.println(player.name + " wins the game!");
    }
    
    public void displayFinalTileCounts() {
    	System.out.println("User has "+model.userPlayer.getHand().size() +" tiles. Player 1 has " + model.aiPlayer1.getHand().size()+" tiles. Player 2 has " + model.aiPlayer2.getHand().size() + " tiles. Player 3 has " + model.aiPlayer3.getHand().size() + " tiles.");
    }
    
    public void displayTurnOptions_Pass() {
    	System.out.println("(1) Pass, (2) Create Meld, (3) Play tiles on the table. Enter -1 to quit.");
    }
    
    public void displayTurnOptions() {
    	System.out.println("(1) Draw Tile, (2) Create Meld, (3) Play tiles on the table.  Enter -1 to quit.");
    }
    
    public void indicateWrongInput() {
    	System.out.println("Invalid input. Please input again.");
    }
    
    public void indicateUserEndsGame(Player player) {
		System.out.println(player.name +  " ends the game.");
    }
    
    public void displayDrawnTile(Tile tile) {
    	System.out.println("You drew: " + tile.getColour() + "," + tile.getRank());		
    }
    
    public void displayCreateAnotherMeldOption() {
    	System.out.println("Create another meld? (1)Yes (2)No");
    }
    
    public void indicateAvailableTiles() {
	   System.out.println("Available Tiles:");
    }
    
    public void indicateMeldsLessThan30() {
    	System.out.println("The total of all your melds does not exceed 30.");
    }
    
    public void indicateNoTileOnBoard() {
    	System.out.println("No tile on the board.");
    }
    
    public void displayTileToExistingMeldOptions() {
    	System.out.println("Choose an option: 1. Add a tile to the end of a meld. 2. Add a tile to the beginning of a meld. 3. Add a tile to create a new meld. Enter -1 to go back.");
    }
    
    public void displayTileToMeldSelection() {
    	System.out.println("Enter the index of the Tile you want to select to add the end of a meld. One tile at each time. Enter -1 to go back.");
    }
    
    public void displayMeldSelection() {
    	System.out.println("Enter the index of the meld you want to select. Enter -1 to quit.");
    }
    
    public void indicateInvalidMeld() {
    	System.out.println("Invalid meld!");
    }
    
    public void displayTileInSelectedMeldSelection() {
    	System.out.println("\nEnter the index of a tile in your selected meld that you want to choose. Enter -1 to go back.");
    }
    
    public void displayTileToMeldPositionSelection() {
    	System.out.println("Enter the index position that you want to put the tile in the new meld. Enter -1 to go back.");
    }
    
    public void displayTileSelection() {
    	System.out.println("Enter the index of the Tile you want to select. (-1) to stop selecting");
    }
}
