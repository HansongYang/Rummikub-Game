package core;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.Map;

import core.Model.GameStates;
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
	public BorderPane userActions;
	public ArrayList<Tile> selectedTiles;
	public int selectedMeldID = -1;	
	public enum LocationOnMeld { FRONT, BACK }
	public LocationOnMeld locationOnMeld;

	public boolean time;
	public String playername;
	public String name;
	
	
	public JavaFxView(Model model, Controller controller, BorderPane panel) {
		this.model = model;
		this.controller = controller;
		this.panel = panel;
		this.selectedTiles = new ArrayList<Tile>();
		this.panel.setCenter(centerGamePane = new BorderPane());
		this.centerGamePane.setBottom(userActions = new BorderPane());
		this.time = false;
		this.name = "";
		this.playername = "";
	}
	
	public JavaFxView() {

	}
	
	public void refreshWindow() {
		panel.getChildren().clear();

		panel.setCenter(centerGamePane);
		centerGamePane.setBottom(userActions);
		displayTurnOptions();
    	displayPlayerHand(model.userPlayer, 1);
    	displayPlayerHand(model.aiPlayer1, 2);
    	displayPlayerHand(model.aiPlayer2, 3);
    	displayPlayerHand(model.aiPlayer3, 4);
    	displayBoard(this.model.getBoard());
    	displayMeldsInHand(model.userPlayer);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPlayerName(String playername) {
		this.playername = playername;
	}
	
	public void setTime(Boolean time) {
		this.time = time;
	}
	
	public void indicateTurn(Player player) {
		System.out.println("INDICATE TURN " + player.name);
		Label label = new Label(player.name + "'s turn");
		label.setStyle("-fx-font: normal bold 30px 'serif'");
		centerGamePane.setTop(label);
		label.setAlignment(Pos.CENTER);
	}

	public void displayMeldsInHand(Player player) {
		HBox hbox = new HBox(5);
		hbox.setPadding(new Insets(10));
		hbox.setAlignment(Pos.BOTTOM_CENTER);

		for(int i = 0; i < player.meldsInHand.size(); i++) {
			Meld meld = player.meldsInHand.get(i);
			
			for(int j = 0; j < meld.size(); j++) {
				final Tile tile = meld.getTile(j);
		    	final Label tileLabel = new Label(Integer.toString(tile.getRank()));
		    	tileLabel.setAlignment(Pos.CENTER);
		    	  	
		    	tileLabel.setMinSize(20,30);
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
		    	
		    	tileLabel.setStyle("-fx-background-color: WHITE; -fx-font-size: 10px");

		    	hbox.getChildren().add(tileLabel);
			}
			
		}

		userActions.setBottom(hbox);
	}
	
	public void displayPlayerHand(Player player, int num) {
		player.getHand().sortTilesByColour();
		FlowPane flowPane = new FlowPane(5, 5);
		flowPane.setAlignment(Pos.CENTER);
		
		switch(num) {
			case 1: panel.setBottom(flowPane); 
					flowPane.setOrientation(Orientation.HORIZONTAL); 
					flowPane.getChildren().add(new Label(playername + ":  " + name));
					break;
			case 2: panel.setLeft(flowPane); flowPane.setOrientation(Orientation.VERTICAL); 
					if(!playername.equals("Player 1")) {
			    		flowPane.getChildren().add(new Label("Player 1   "));
			    	}else {
			    		flowPane.getChildren().add(new Label("Player 2   "));
			    	}
					break;
			case 3: panel.setTop(flowPane);
					flowPane.setOrientation(Orientation.HORIZONTAL);
					if(playername.equals("Player 4")) {
			    		flowPane.getChildren().add(new Label("Player 2   "));
			    	} else if(!playername.equals("Player 3")) {
			    		flowPane.getChildren().add(new Label("Player 3    "));
			    	} else {
			    		flowPane.getChildren().add(new Label("Player 2   "));
			    	}
					break;
			case 4: panel.setRight(flowPane);
					flowPane.setOrientation(Orientation.VERTICAL);
					if(!playername.equals("Player 4")) {
						flowPane.getChildren().add(new Label("Player 4   "));
			    	}else {
			    		flowPane.getChildren().add(new Label("Player 3   "));
			    	}
					break;
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
	    	
	    	if(num == 4) {
	    		tileLabel.setRotate(-90);
	    	}
	    	if(num == 2) {
	    		tileLabel.setRotate(90);
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
			final Meld meld = board.getCurrentMelds().get(i);
			final int meldID = i;
			
			final Label plusLabelLeft = new Label("+");
			plusLabelLeft.setAlignment(Pos.CENTER);
			plusLabelLeft.setMinSize(30,40);
			boardGrid.add(plusLabelLeft, 0, i);
			plusLabelLeft.setStyle("-fx-background-color: WHITE; -fx-font-size: 20px");
			
			plusLabelLeft.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    	        public void handle(MouseEvent e) {
	    	        	if(selectedMeldID == -1) {
	    	        		plusLabelLeft.setStyle("-fx-border-color: BLACK; -fx-background-color: WHITE; -fx-font-size: 20px");
	    	        		selectedMeldID = meldID;
	    	        		locationOnMeld = LocationOnMeld.FRONT;
	    	        		
	    	        	}else if(selectedMeldID == meldID && locationOnMeld == LocationOnMeld.FRONT) {
	    	        		plusLabelLeft.setStyle("-fx-border-color: WHITE; -fx-background-color: WHITE; -fx-font-size: 20px");
	    	        		selectedMeldID = -1;
	    	        	}
	    	        	
	    	        
	    	        }
	    	    });
			
			for (int j = 1; j < meld.size() + 1; j++) {

				final Tile tile = meld.getTile(j - 1);
				final Label tileLabel = new Label(Integer.toString(tile.getRank()));
				tileLabel.setAlignment(Pos.CENTER);

				tileLabel.setMinSize(30,40);
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
				tileLabel.setStyle("-fx-background-color: WHITE; -fx-font-size: 14px");

				boardGrid.add(tileLabel, j, i);
			}
			
			final Label plusLabelRight = new Label("+");
			plusLabelRight.setAlignment(Pos.CENTER);
			plusLabelRight.setMinSize(30,40);
			boardGrid.add(plusLabelRight, board.getCurrentMelds().get(i).size() + 1, i);
			plusLabelRight.setStyle("-fx-background-color: WHITE; -fx-font-size: 20px");
			
			plusLabelRight.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	        public void handle(MouseEvent e) {
    	        	if(selectedMeldID == -1) {
    	        		plusLabelRight.setStyle("-fx-border-color: BLACK; -fx-background-color: WHITE; -fx-font-size: 20px");
    	        		selectedMeldID = meldID;
    	        		locationOnMeld = LocationOnMeld.BACK;
    	        		
    	        	}else if(selectedMeldID == meldID && locationOnMeld == LocationOnMeld.BACK) {
    	        		plusLabelRight.setStyle("-fx-border-color: WHITE; -fx-background-color: WHITE; -fx-font-size: 20px");
    	        		selectedMeldID = -1;
    	        	}
    	        
    	        }
    	    });
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
		final Button playCreatedMelds = new Button("Play created melds");
		playCreatedMelds.setStyle("-fx-background-color: #f5f6fa");

		HBox hbox = new HBox(5);
		hbox.setPadding(new Insets(10));
		
		if(!model.userPlayer.playedTilesOnTurn) {
			hbox.getChildren().add(drawTile);
		}
		else {
			hbox.getChildren().add(endTurn);
		}
		hbox.getChildren().add(createMeld);
		hbox.getChildren().add(playToTable);
		hbox.getChildren().add(playCreatedMelds);
		
		userActions.setTop(hbox);
		hbox.setAlignment(Pos.BOTTOM_CENTER);

		drawTile.setOnAction(new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent event) {

	                try {
						Tile drawnTile = controller.drawTile();

						if(controller.playAITurns()) {// AI wins on this turn
							//Game over
							displayWinner(model.gameWinner);
						}
						
						//Return created melds back to hand if not played
						controller.returnMeldsToHand();

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

						//model.userPlayer.playedTilesOnTurn = true;
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
					if(selectedMeldID != -1) {
						
						if(locationOnMeld == LocationOnMeld.FRONT) {//Add to front
							if(controller.playTilestoMeldFront(selectedTiles, selectedMeldID)) {
								model.userPlayer.playedTilesOnTurn = true;
							}
						}
						else {//Add to back
							if(controller.playTilestoMeldBack(selectedTiles, selectedMeldID)) {
								model.userPlayer.playedTilesOnTurn = true;
							}
						}
						selectedTiles.clear();
						selectedMeldID = -1;
						locationOnMeld = null;
						
					}

					refreshWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
		
		playCreatedMelds.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                try {
                	
                	controller.playMeldsToTable();
                	
                	model.userPlayer.playedTilesOnTurn = true;
                	
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
					
					

					model.userPlayer.playedTilesOnTurn = false;
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
