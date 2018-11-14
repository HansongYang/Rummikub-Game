package core;

import javafx.stage.Stage;

import java.util.Iterator;
import java.util.Map;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class JavaFxView extends Application implements View , Runnable{

	private Thread t;
	public Model model; 
	public Scene scene;
	public Stage stage;
	public GridPane panel;
	
	public JavaFxView(Model model) {
		this.model = model;
	}
	
	public JavaFxView() {

	}
	
	public void run() {
		System.out.println("Running javafxthread");
		launch();
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		panel = new GridPane();
		panel.setAlignment(Pos.CENTER); 
	    panel.setStyle("-fx-background-color: PALEGREEN;");
	    panel.setMinSize(1300, 800); 
		
		scene = new Scene(panel); 
		stage.setScene(scene);
	    stage.setTitle("Rummikub Game");
	    stage.show();
	}

	public void indicateTurn(Player player) {
		// TODO Auto-generated method stub
	}

	public void displayPlayerHand() {
		// TODO Auto-generated method stub
		
	}

	public void displayHand(Hand hand) {
		// TODO Auto-generated method stub
		
	}

	public void displayBoard(Board board) {
		// TODO Auto-generated method stub
		
	}

	public void displayMeld(Meld meld) {
		// TODO Auto-generated method stub
		
	}

	public void displayWinner(Player player) {
		// TODO Auto-generated method stub
		
	}

	public void displayFinalTileCounts() {
		// TODO Auto-generated method stub
		
	}

	public void displayTurnOptions_Pass() {
		// TODO Auto-generated method stub
		
	}

	public void displayTurnOptions() {
		// TODO Auto-generated method stub
		
	}

	public void indicateWrongInput() {
		// TODO Auto-generated method stub
		
	}

	public void indicateUserEndsGame(Player player) {
		// TODO Auto-generated method stub
		
	}

	public void displayDrawnTile(Tile tile) {
		// TODO Auto-generated method stub
		
	}

	public void displayCreateAnotherMeldOption() {
		// TODO Auto-generated method stub
		
	}

	public void indicateAvailableTiles() {
		// TODO Auto-generated method stub
		
	}

	public void indicateMeldsLessThan30() {
		// TODO Auto-generated method stub
		
	}

	public void indicateNoTileOnBoard() {
		// TODO Auto-generated method stub
		
	}

	public void displayTileToExistingMeldOptions() {
		// TODO Auto-generated method stub
		
	}

	public void displayTileToMeldSelection() {
		// TODO Auto-generated method stub
		
	}

	public void displayMeldSelection() {
		// TODO Auto-generated method stub
		
	}

	public void indicateInvalidMeld() {
		// TODO Auto-generated method stub
		
	}

	public void displayTileInSelectedMeldSelection() {
		// TODO Auto-generated method stub
		
	}

	public void displayTileToMeldPositionSelection() {
		// TODO Auto-generated method stub
		
	}

	public void displayTileSelection() {
		// TODO Auto-generated method stub
		
	}

	public void printTurns() {
        Iterator it = model.playerOrder.entrySet().iterator();
        System.out.print("Turn Order: ");

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Player player = (Player)pair.getKey();
            System.out.print(player.name + " ");
        }
		
	}
	

}
