package core;

import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.binding.Bindings;

public class JavaFxView {

    public JavaFxController controller;
    public BorderPane panel;
    public BorderPane centerGamePane;
    public BorderPane userActions;
    public ArrayList<Tile> selectedTiles;
    public int selectedMeldID = -1;
    public final Button restart = new Button("Restart Game");
	public Map<Meld, ArrayList<Tile>> selectedTilesFromBoard;

    public enum LocationOnMeld {FRONT, BACK}

    public LocationOnMeld locationOnMeld;

    public boolean time;
    public String playername;
    public String name;
    public int numPlayer;
    public String[] strategy;


    public JavaFxView(JavaFxController controller, BorderPane panel) {
        this.controller = controller;
        this.panel = panel;
        this.selectedTiles = new ArrayList<Tile>();
		this.selectedTilesFromBoard = new HashMap<Meld, ArrayList<Tile>>();
        this.panel.setCenter(centerGamePane = new BorderPane());
        this.centerGamePane.setBottom(userActions = new BorderPane());
        this.time = false;
        this.name = "";
        this.playername = "";
        strategy = new String[3];
    }

    public JavaFxView() {

    }

    public void refreshWindow() {
        panel.getChildren().clear();
        centerGamePane.getChildren().clear();
        panel.setCenter(centerGamePane);
        centerGamePane.setBottom(userActions);
        
       /* if(controller.model.playerOrder.entrySet().iterator().next().getKey().name.equals("USER")) {
        	displayTurnOptions();
        }else {
        	controller.playAITurns();
        }*/

        displayTurnOptions();
        displayPlayerHand(controller.model.userPlayer, 1);    
        if(controller.model.player2 != null) {
        	displayPlayerHand(controller.model.player2, 2);
        }
        if(controller.model.player3 != null) {
        	displayPlayerHand(controller.model.player3, 3);
        }
        if(controller.model.player4 != null) {
        	displayPlayerHand(controller.model.player4, 4);
        }

		selectedTiles.clear();
    	selectedTilesFromBoard.clear();

        displayBoard(controller.model.getBoard());
        displayMeldsInHand(controller.model.currentUserPlayer);
    }

    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }

    public void setStrategy(String[] strategy) {
        this.strategy = strategy;
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

        for (int i = 0; i < player.meldsInHand.size(); i++) {
            Meld meld = player.meldsInHand.get(i);

            for (int j = 0; j < meld.size(); j++) {
                final Tile tile = meld.getTile(j);
                final Label tileLabel = new Label(Integer.toString(tile.getRank()));
                tileLabel.setAlignment(Pos.CENTER);

                tileLabel.setMinSize(20, 30);
                if (tile.getColour() == 'G') {
                    tileLabel.setTextFill(Color.GREEN);
                } else if (tile.getColour() == 'R') {
                    tileLabel.setTextFill(Color.RED);
                } else if (tile.getColour() == 'B') {
                    tileLabel.setTextFill(Color.BLUE);
                } else if (tile.getColour() == 'O') {
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

        switch (num) {
            case 1:
                panel.setBottom(flowPane);
                flowPane.setOrientation(Orientation.HORIZONTAL);
                flowPane.getChildren().add(new Label(playername + ":  " + name));
                break;
            case 2:
                panel.setLeft(flowPane);
                flowPane.setOrientation(Orientation.VERTICAL);
                if (!playername.equals("Player 1")) {
                    flowPane.getChildren().add(new Label("Player 1   "));
                } else {
                    flowPane.getChildren().add(new Label("Player 2   "));
                }
                break;
            case 3:
                panel.setTop(flowPane);
                flowPane.setOrientation(Orientation.HORIZONTAL);
                if (playername.equals("Player 4")) {
                    flowPane.getChildren().add(new Label("Player 2   "));
                } else if (!playername.equals("Player 3")) {
                    flowPane.getChildren().add(new Label("Player 3    "));
                } else {
                    flowPane.getChildren().add(new Label("Player 2   "));
                }
                break;
            case 4:
                panel.setRight(flowPane);
                flowPane.setOrientation(Orientation.VERTICAL);
                if (!playername.equals("Player 4")) {
                    flowPane.getChildren().add(new Label("Player 4   "));
                } else {
                    flowPane.getChildren().add(new Label("Player 3   "));
                }
                break;
        }

        for (int i = 0; i < player.getHand().size(); i++) {

            final Tile tile = player.getHand().getTile(i);
            final Label tileLabel = new Label(Integer.toString(tile.getRank()));
            tileLabel.setAlignment(Pos.CENTER);

            tileLabel.setMinSize(40, 50);
            if (player.getHand().getTile(i).getColour() == 'G') {
                tileLabel.setTextFill(Color.GREEN);
            } else if (player.getHand().getTile(i).getColour() == 'R') {
                tileLabel.setTextFill(Color.RED);
            } else if (player.getHand().getTile(i).getColour() == 'B') {
                tileLabel.setTextFill(Color.BLUE);
            } else if (player.getHand().getTile(i).getColour() == 'O') {
                tileLabel.setTextFill(Color.ORANGE);
            } else {
                tileLabel.setTextFill(Color.BLACK);
            }

            if (num == 4) {
                tileLabel.setRotate(-90);
            }
            if (num == 2) {
                tileLabel.setRotate(90);
            }

            tileLabel.setStyle("-fx-background-color: WHITE; -fx-font-size: 20px");

            flowPane.getChildren().add(tileLabel);

            tileLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) {
                	if(player == controller.model.currentUserPlayer) {
	                    if (!selectedTiles.contains(tile)) {
	                        tileLabel.setStyle("-fx-border-color: BLACK; -fx-border-width: 3px; -fx-background-color: WHITE; -fx-font-size: 20px");
	                        selectedTiles.add(tile);
	                    } else {
	                        tileLabel.setStyle("-fx-border-color: WHITE; -fx-background-color: WHITE; -fx-font-size: 20px");
	                        selectedTiles.remove(tile);
	                    }
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
            plusLabelLeft.setMinSize(30, 40);
            boardGrid.add(plusLabelLeft, 0, i);
            plusLabelLeft.setStyle("-fx-background-color: WHITE; -fx-font-size: 20px");

            plusLabelLeft.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) {
                    if (selectedMeldID == -1) {
                        plusLabelLeft.setStyle("-fx-border-color: BLACK; -fx-background-color: WHITE; -fx-font-size: 20px");
                        selectedMeldID = meldID;
                        locationOnMeld = LocationOnMeld.FRONT;

                    } else if (selectedMeldID == meldID && locationOnMeld == LocationOnMeld.FRONT) {
                        plusLabelLeft.setStyle("-fx-border-color: WHITE; -fx-background-color: WHITE; -fx-font-size: 20px");
                        selectedMeldID = -1;
                    }
                }
            });

            for (int j = 1; j < meld.size() + 1; j++) {

                final Tile tile = meld.getTile(j - 1);
                final Label tileLabel = new Label(Integer.toString(tile.getRank()));
                tileLabel.setAlignment(Pos.CENTER);

                tileLabel.setMinSize(30, 40);
                if (tile.getColour() == 'G') {
                    tileLabel.setTextFill(Color.GREEN);
                } else if (tile.getColour() == 'R') {
                    tileLabel.setTextFill(Color.RED);
                } else if (tile.getColour() == 'B') {
                    tileLabel.setTextFill(Color.BLUE);
                } else if (tile.getColour() == 'O') {
                    tileLabel.setTextFill(Color.ORANGE);
                } else {
                    tileLabel.setTextFill(Color.BLACK);
                }

				if (tile.justPlayed()) {
					tileLabel.setStyle("-fx-background-color: WHITE; -fx-font-size: 14px; -fx-border-color: #f1c40f; -fx-border-width: 3px");
				} else {
					tileLabel.setStyle("-fx-background-color: WHITE; -fx-border-color: WHITE; -fx-font-size: 14px");
				}

                boardGrid.add(tileLabel, j, i);

				tileLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    	        public void handle(MouseEvent e) {
	    	        	
	    	        	if(!selectedTilesFromBoard.containsKey(meld)) {
	    	        		selectedTilesFromBoard.put(meld, new ArrayList<Tile>());
	    	        		selectedTilesFromBoard.get(meld).add(tile);
	    	        		tileLabel.setStyle("-fx-border-color: BLACK; -fx-background-color: WHITE; -fx-font-size: 14px");
	    	        		
	    	        	}else {
	    	        		
	    	        		if(!selectedTilesFromBoard.get(meld).contains(tile)) {
	    	        			tileLabel.setStyle("-fx-border-color: BLACK; -fx-background-color: WHITE; -fx-font-size: 14px");
	    	        			selectedTilesFromBoard.get(meld).add(tile);
	    	        		}
	    	        		else {
	    	        			tileLabel.setStyle("-fx-border-color: WHITE; -fx-background-color: WHITE; -fx-font-size: 14px");
	    	        			selectedTilesFromBoard.get(meld).remove(tile);
	    	        			
	    	        			if(selectedTilesFromBoard.get(meld).size() == 0) {
	    	        				selectedTilesFromBoard.remove(meld);
	    	        			}
	    	        		}
	    	        		
	    	        	}
	    	        }
	    	    });
            }

            final Label plusLabelRight = new Label("+");
            plusLabelRight.setAlignment(Pos.CENTER);
            plusLabelRight.setMinSize(30, 40);
            boardGrid.add(plusLabelRight, board.getCurrentMelds().get(i).size() + 1, i);
            plusLabelRight.setStyle("-fx-background-color: WHITE; -fx-font-size: 20px");

            plusLabelRight.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) {
                    if (selectedMeldID == -1) {
                        plusLabelRight.setStyle("-fx-border-color: BLACK; -fx-background-color: WHITE; -fx-font-size: 20px");
                        selectedMeldID = meldID;
                        locationOnMeld = LocationOnMeld.BACK;

                    } else if (selectedMeldID == meldID && locationOnMeld == LocationOnMeld.BACK) {
                        plusLabelRight.setStyle("-fx-border-color: WHITE; -fx-background-color: WHITE; -fx-font-size: 20px");
                        selectedMeldID = -1;
                    }

                }
            });

			meld.resetAllJustPlayedFlag();
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
		final Button complexTileReuse = new Button("Reuse board tiles");
		complexTileReuse.setStyle("-fx-background-color: #f5f6fa");

        final Label timer = new Label();
        timer.setStyle("-fx-border-color: BLACK; -fx-background-color: WHITE; -fx-font-size: 20px");
        restart.setStyle("-fx-background-color: red; -fx-textfill: black;");
        
        HBox hbox = new HBox(5);
        hbox.setPadding(new Insets(10));


        if (!controller.model.currentUserPlayer.playedTilesOnTurn) {
            hbox.getChildren().add(drawTile);
        } else {
            hbox.getChildren().add(endTurn);
        }
        hbox.getChildren().add(createMeld);
        hbox.getChildren().add(playToTable);
        hbox.getChildren().add(playCreatedMelds);
		hbox.getChildren().add(complexTileReuse);
        hbox.getChildren().add(restart);

        if (time & controller.model.interval == 120) {
            controller.model.startClock();
            timer.setText(Integer.toString(controller.model.interval));
            hbox.getChildren().add(timer);
        }else if(time) {
        	timer.setText(Integer.toString(controller.model.interval));
        	hbox.getChildren().add(timer);
        }
         
        userActions.setTop(hbox);
        hbox.setAlignment(Pos.BOTTOM_CENTER);

        controller.model.valueProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                timer.textProperty().bind(Bindings.convert(controller.model.valueProperty()));
                if (Integer.parseInt(controller.model.getValue()) < 1) {
                    if (controller.playAITurns()) {// AI wins on this turn
                        //Game over
                        displayWinner(controller.model.gameWinner);
                    }
                    controller.model.stopClock();
                }
            }
        });

        drawTile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    Tile drawnTile = controller.drawTile(controller.model.currentUserPlayer);

                    //Return created melds back to hand if not played
                    controller.returnMeldsToHand(controller.model.currentUserPlayer);
                    
                    endTurnProcess();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        createMeld.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    if (!controller.createMeld(controller.model.currentUserPlayer,selectedTiles)) {
                        controller.saveGame();
                        Meld meld = new Meld(selectedTiles);
                        controller.model.getBoard().addMeld(meld);
                        controller.restoreGame();
                        for (int i = 0; i < 3; i++) controller.drawTile(controller.model.currentUserPlayer);
                        
                        endTurnProcess();
                    } else {
                        selectedTiles.clear();
                        
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
                    if (selectedMeldID != -1) {
                        if (locationOnMeld == LocationOnMeld.FRONT) {//Add to front
                            if (controller.playTilestoMeldFront(controller.model.currentUserPlayer,selectedTiles, selectedMeldID)) {
                                controller.model.currentUserPlayer.playedTilesOnTurn = true;
                            } else {
                                controller.saveGame();
                                Meld meld = new Meld(selectedTiles);
                                controller.model.getBoard().addTileToMeldBeginning(selectedMeldID, meld);
                                controller.restoreGame();
                                for (int i = 0; i < 3; i++) controller.drawTile(controller.model.currentUserPlayer);
                                controller.playAITurns();
                                refreshWindow();
                            }
                        } else {//Add to back
                            if (controller.playTilestoMeldBack(controller.model.currentUserPlayer, selectedTiles, selectedMeldID)) {
                                controller.model.currentUserPlayer.playedTilesOnTurn = true;
                            } else {
                                controller.saveGame();
                                Meld meld = new Meld(selectedTiles);
                                controller.model.getBoard().addTileToMeldBeginning(selectedMeldID, meld);
                                controller.restoreGame();
                                for (int i = 0; i < 3; i++) controller.drawTile(controller.model.currentUserPlayer);
                                controller.playAITurns();
                                refreshWindow();
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
		
		complexTileReuse.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                	if(controller.model.currentUserPlayer.initial30Played) {
	                	if(controller.reuseBoardTiles(controller.model.currentUserPlayer, selectedTilesFromBoard, selectedTiles)) {
	                		controller.model.currentUserPlayer.playedTilesOnTurn = true;
	                	}
	                	else {
	                		indicateInvalidMeld();
	                	}
                	}
                	selectedTiles.clear();
                	selectedTilesFromBoard.clear();
                	refreshWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });

        playCreatedMelds.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    if (controller.playMeldsToTable(controller.model.currentUserPlayer)) {
                        controller.model.currentUserPlayer.playedTilesOnTurn = true;
                        if (controller.model.gameWinCheck()) {
                            displayWinner(controller.model.gameWinner);
                        }
                        
                        refreshWindow();
                    } else {
                        indicateMeldsLessThan30();
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        endTurn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    endTurnProcess();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // public void playInvalidTiles(ArrayList<Tile> tiles) {
    //     Meld meld = new Meld(tiles);
    //     this.controller.model.getBoard().addMeld(meld);
    //     indicateInvalidMeld();
    //     this.model = controller.restoreGame();
    //     if (controller.playAITurns()) displayWinner(controller.model.gameWinner);
    //     for (int i = 0; i < 3; i++) controller.drawTile();
    // }

    public void endTurnProcess() {
    	
	
    	boolean next = false;
    	
    	//Find next player
    	Iterator it = controller.model.playerOrder.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Player player = (Player) pair.getKey();
            
            if(next) {
            	if(player instanceof AIPlayer) {
            		if (controller.playAITurn((AIPlayer) player)) {// AI wins on this turn
                        //Game over
                        displayWinner(controller.model.gameWinner);
                    }
            	}
            	else {
            		controller.model.currentUserPlayer = (UserPlayer) player;
            		break;
            	}
            	        	
            }
            
            if(player.equals(controller.model.currentUserPlayer)) {
            	next = true;
            }
            
            if(!it.hasNext()) {//Loop back to start
        		it = controller.model.playerOrder.entrySet().iterator();
        	}
        }
    	
    	
    	
        controller.model.currentUserPlayer.playedTilesOnTurn = false;
        
        if(time) {
            controller.model.stopClock();
            controller.model.startClock();
        }
        
        refreshWindow();
    }
    
    public void indicateMeldsLessThan30() {
        Label label = new Label("Total value of Melds less than 30");
        label.setStyle("-fx-font: normal bold 30px 'serif'");
        centerGamePane.setTop(label);
        label.setAlignment(Pos.CENTER);
    }

    public void indicateInvalidMeld() {
        Label label = new Label("Invalid Meld");
        label.setStyle("-fx-font: normal bold 30px 'serif'");
        label.setTextFill(Color.RED);
        centerGamePane.setTop(label);
        label.setAlignment(Pos.CENTER);
    }

    public void printTurns(Map<Player, Integer> order) {
        Iterator it = order.entrySet().iterator();
        System.out.print("Turn Order: ");

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Player player = (Player) pair.getKey();
            System.out.print(player.name + " ");
        }

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


    public void displayTileInSelectedMeldSelection() {
        // TODO Auto-generated method stub

    }

    public void displayTileToMeldPositionSelection() {
        // TODO Auto-generated method stub

    }

    public void displayTileSelection() {
        // TODO Auto-generated method stub

    }

    public void displayInitialScreen() {
    }
}
