package core;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class JavaFxView implements View {

	public Controller controller; 
	public Model model;
	public GridPane panel;
	public ArrayList<Tile> selectedTiles;
	
	
	public JavaFxView(Model model, Controller controller, GridPane panel) {
		this.model = model;
		this.controller = controller;
		this.panel = panel;
		this.selectedTiles = new ArrayList<Tile>();
		
	}
	
	public JavaFxView() {

	}
	
	public void refreshWindow() {
		panel.getChildren().clear();
		
		displayTurnOptions();
    	displayPlayerHand(model.userPlayer, 1);
    	displayPlayerHand(model.aiPlayer1, 2);
    	displayPlayerHand(model.aiPlayer2, 3);
    	displayPlayerHand(model.aiPlayer3, 4);
		
	}
	

	public void indicateTurn(Player player) {
		System.out.println("INDICATE TURN " + player.name);
		Label label = new Label(player.name + "'s turn");
		label.setStyle("-fx-font: normal bold 30px 'serif'");
		panel.add(label, 4, 4); 
		
	}

	public void displayPlayerHands() {
		
	}
	
	public void displayPlayerHand(Player player, int num) {
		player.getHand().sortTilesByColour();
		for(int i = 0; i < player.getHand().size(); i++) {
			
			final Tile tile = player.getHand().getTile(i);
	    	final Label tileLabel = new Label("    " + Integer.toString(tile.getRank()));
	    	  	
	    	tileLabel.setMinSize(40,50);
	    	if(player.getHand().getTile(i).getColour() == 'G') {
	    		tileLabel.setTextFill(Color.GREEN);
	    	}else if(player.getHand().getTile(i).getColour() == 'R') {
	    		tileLabel.setTextFill(Color.RED);
	    	} else if (player.getHand().getTile(i).getColour() == 'B'){
	    		tileLabel.setTextFill(Color.BLUE);
	    	} else if(player.getHand().getTile(i).getColour() == 'O') {
	    		tileLabel.setTextFill(Color.ORANGE);
	    	} else {
	    		tileLabel.setTextFill(Color.BLACK);
	    	}
	    	tileLabel.setStyle("-fx-border-color: BLACK;");
	    	panel.add(tileLabel, i+5, num);
	    	
	    	 tileLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    	        public void handle(MouseEvent e) {
	    	        	
	    	        	if(!selectedTiles.contains(tile)) {
	    	        		tileLabel.setStyle("-fx-border-color: WHITE;");
	    	        		selectedTiles.add(tile);
	    	        	}else {
	    	        		tileLabel.setStyle("-fx-border-color: BLACK;");
	    	        		selectedTiles.remove(tile);
	    	        	}
	    	        }
	    	    });
	    	 
    	}
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
		Label label = new Label(player.name + " wins the game!");
		label.setStyle("-fx-font: normal bold 30px 'serif'");
		panel.add(label, 0, 10); 
		
		
	}

	public void displayFinalTileCounts() {
		// TODO Auto-generated method stub
		
	}

	public void displayTurnOptions_Pass() {
		
		
	}

	public void displayTurnOptions() {
		final Button drawTile = new Button("Draw Tile"); 
		drawTile.setStyle("-fx-background-color: red; -fx-textfill: black;"); 
		final Button createMeld = new Button("Create Meld"); 
		createMeld.setStyle("-fx-background-color: red; -fx-textfill: black;"); 
		final Button playToTable = new Button("Play tiles on the table"); 
		playToTable.setStyle("-fx-background-color: red; -fx-textfill: black;"); 
		final Button endTurn = new Button("End turn"); 
		endTurn.setStyle("-fx-background-color: red; -fx-textfill: black;"); 
		
		
		panel.add(drawTile, 0, 1);
		panel.add(createMeld, 0, 2);
		panel.add(playToTable, 0, 3);
		panel.add(endTurn, 0, 4);
		
		drawTile.setOnAction(new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent event) {
	            	
	                try {
						Tile drawnTile = controller.drawTile();
						//displayDrawnTile(drawnTile);
						
						if(controller.playAITurns()) {// AI wins on this turn
							//Game over
							displayWinner(model.gameWinner);
						}
						
						refreshWindow();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
	            }
	        });
		
		createMeld.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	
                try {
					if(!controller.createMeld(selectedTiles)) {
						indicateInvalidMeld();
					}
					else {
						selectedTiles.clear();
						if(model.gameWinCheck()) {
							displayWinner(model.gameWinner);
						}
						
						refreshWindow();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
		
		playToTable.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	
              	
                try {
					controller.selectPlayTilesToBoard();
					
					refreshWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
		
		endTurn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	
              	
                try {
                	
					if(controller.playAITurns()) {// AI wins on this turn
						//Game over
						displayWinner(model.gameWinner);
					}
									
					
					refreshWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
		
		
	}

	public void indicateWrongInput() {
		// TODO Auto-generated method stub
		
	}

	public void indicateUserEndsGame(Player player) {
		// TODO Auto-generated method stub
		
	}

	public void displayDrawnTile(Tile tile) {
		System.out.println("DRAWN TILE");
		
		Label tileLabel = new Label("    " + tile.getRank());
    	tileLabel.setMinSize(40,50);
    	if(tile.getColour() == 'G') {
    		tileLabel.setTextFill(Color.GREEN);
    	}else if(tile.getColour() == 'R') {
    		tileLabel.setTextFill(Color.RED);
    	} else if (tile.getColour() == 'B'){
    		tileLabel.setTextFill(Color.BLUE);
    	} else if(tile.getColour() == 'O') {
    		tileLabel.setTextFill(Color.ORANGE);
    	} else {
    		tileLabel.setTextFill(Color.BLACK);
    	}
    	tileLabel.setStyle("-fx-border-color: BLACK;");
    	panel.add(tileLabel, 0, 0);
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
		Label label = new Label("Invalid Meld");
		label.setStyle("-fx-font: normal bold 30px 'serif'");
		panel.add(label, 0, 10); 
		
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

	public void printTurns(Map<Player, Integer> order) {
        Iterator it = order.entrySet().iterator();
        System.out.print("Turn Order: ");

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Player player = (Player)pair.getKey();
            System.out.print(player.name + " ");
        }
		
	}
	
	public void displayInitialScreen() {
		

	}


	

}
