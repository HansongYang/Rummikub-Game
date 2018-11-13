package core;

public interface View {

	public void indicateTurn(Player player);
    public void displayPlayerHand();  
    public void displayHand(Hand hand);  
    public void displayBoard(Board board);
    public void displayMeld(Meld meld);
    public void displayWinner(Player player);    
    public void displayFinalTileCounts();    
    public void displayTurnOptions_Pass();    
    public void displayTurnOptions();   
    public void indicateWrongInput();   
    public void indicateUserEndsGame(Player player);    
    public void displayDrawnTile(Tile tile);    
    public void displayCreateAnotherMeldOption();    
    public void indicateAvailableTiles();    
    public void indicateMeldsLessThan30();    
    public void indicateNoTileOnBoard();   
    public void displayTileToExistingMeldOptions();   
    public void displayTileToMeldSelection();    
    public void displayMeldSelection();    
    public void indicateInvalidMeld();    
    public void displayTileInSelectedMeldSelection();   
    public void displayTileToMeldPositionSelection();    
    public void displayTileSelection();
    public void printTurns();
}
