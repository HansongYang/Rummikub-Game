package core;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class JavaFxView {

	public Controller controller; 
	public Model model;
	public BorderPane panel;
	public BorderPane centerGamePane;
	public ArrayList<Tile> selectedTiles;
	
	
	public JavaFxView(Model model, Controller controller, BorderPane panel) {
		this.model = model;
		this.controller = controller;
		this.panel = panel;
		this.selectedTiles = new ArrayList<Tile>();
		this.panel.setCenter(centerGamePane = new BorderPane());
	}
	
	public JavaFxView() {

	}
	
	public void refreshWindow() {
		panel.getChildren().clear();
		centerGamePane.getChildren().clear();

		panel.setCenter(centerGamePane);
		displayTurnOptions();
    	displayPlayerHand(model.userPlayer, 1);
    	displayPlayerHand(model.aiPlayer1, 2);
    	displayPlayerHand(model.aiPlayer2, 3);
    	displayPlayerHand(model.aiPlayer3, 4);
    	displayBoard(this.model.getBoard());
	}
	

	public void indicateTurn(Player player) {
		System.out.println("INDICATE TURN " + player.name);
		Label label = new Label(player.name + "'s turn");
		label.setStyle("-fx-font: normal bold 30px 'serif'");
		centerGamePane.setTop(label);
		label.setAlignment(Pos.CENTER);
	}

	public void displayPlayerHands() {
		
	}
	
	public void displayPlayerHand(Player player, int num) {
		player.getHand().sortTilesByColour();
		FlowPane flowPane = new FlowPane(5, 5);
		flowPane.setAlignment(Pos.CENTER);

		switch(num) {
			case 1: panel.setBottom(flowPane); flowPane.setOrientation(Orientation.HORIZONTAL); break;
			case 2: panel.setLeft(flowPane); flowPane.setOrientation(Orientation.VERTICAL); break;
			case 3: panel.setTop(flowPane); flowPane.setOrientation(Orientation.HORIZONTAL); break;
			case 4: panel.setRight(flowPane); flowPane.setOrientation(Orientation.VERTICAL); break;
		}

		for(int i = 0; i < player.getHand().size(); i++) {
			
			final Tile tile = player.getHand().getTile(i);
	    	final Label tileLabel = new Label(Integer.toString(tile.getRank()));
	    	tileLabel.setAlignment(Pos.CENTER);
	    	  	
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
	    	tileLabel.setStyle("-fx-background-color: WHITE; -fx-font-size: 20px");

	    	flowPane.getChildren().add(tileLabel);

	    	 tileLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    	        public void handle(MouseEvent e) {
	    	        	
	    	        	if(!selectedTiles.contains(tile)) {
	    	        		tileLabel.setStyle("-fx-border-color: BLACK; -fx-background-color: WHITE; -fx-font-size: 20px");
	    	        		selectedTiles.add(tile);
	    	        	}else {
	    	        		tileLabel.setStyle("-fx-border-color: WHITE; -fx-background-color: WHITE; -fx-font-size: 20px");
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
		GridPane boardGrid = new GridPane();
		centerGamePane.setCenter(boardGrid);
		boardGrid.setAlignment(Pos.CENTER);
		boardGrid.setHgap(5);
		boardGrid.setVgap(5);

		for (int i = 0; i < board.getCurrentMelds().size(); i++) {
			for (int j = 0; j < board.getCurrentMelds().get(i).size(); j++) {

				final Tile tile = board.getCurrentMelds().get(i).getTile(j);
				final Label tileLabel = new Label(Integer.toString(tile.getRank()));
				tileLabel.setAlignment(Pos.CENTER);

				tileLabel.setMinSize(30,40);
				if(board.getCurrentMelds().get(i).getTile(j).getColour() == 'G') {
					tileLabel.setTextFill(Color.GREEN);
				}else if(board.getCurrentMelds().get(i).getTile(j).getColour() == 'R') {
					tileLabel.setTextFill(Color.RED);
				} else if (board.getCurrentMelds().get(i).getTile(j).getColour() == 'B'){
					tileLabel.setTextFill(Color.BLUE);
				} else if(board.getCurrentMelds().get(i).getTile(j).getColour() == 'O') {
					tileLabel.setTextFill(Color.ORANGE);
				} else {
					tileLabel.setTextFill(Color.BLACK);
				}
				tileLabel.setStyle("-fx-background-color: WHITE; -fx-font-size: 14px");

				boardGrid.add(tileLabel, j, i);
			}
		}
	}

	public void displayMeld(Meld meld) {
		// TODO Auto-generated method stub
		
	}

	public void displayWinner(Player player) {
		Label label = new Label(player.name + " wins the game!");
		label.setStyle("-fx-font: normal bold 30px 'serif'");
		centerGamePane.setTop(label);
		label.setAlignment(Pos.CENTER);
	}

	public void displayFinalTileCounts() {
		// TODO Auto-generated method stub
		
	}

	public void displayTurnOptions_Pass() {
		
		
	}

	public void displayTurnOptions() {
		final Button drawTile = new Button("Draw Tile");
		drawTile.setStyle("-fx-background-color: #f5f6fa");
		final Button createMeld = new Button("Create Meld");
		createMeld.setStyle("-fx-background-color: #f5f6fa");
		final Button playToTable = new Button("Play tiles on the table");
		playToTable.setStyle("-fx-background-color: #f5f6fa");
		final Button endTurn = new Button("End turn");
		endTurn.setStyle("-fx-background-color: #f5f6fa");

		HBox hbox = new HBox(5);
		hbox.setPadding(new Insets(10));
		hbox.getChildren().addAll(drawTile, createMeld, playToTable, endTurn);
		centerGamePane.setBottom(hbox);
		hbox.setAlignment(Pos.BOTTOM_CENTER);

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
		centerGamePane.setTop(label);
		label.setAlignment(Pos.CENTER);
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
