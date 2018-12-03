package core;

import java.awt.Paint;
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
import javafx.scene.shape.Circle;
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

	private Label label;
    public enum LocationOnMeld {FRONT, BACK}

    public LocationOnMeld locationOnMeld;

    public boolean time;
    public String playername;
    public String name;
    public int numPlayer;
    public String[] strategy;
    private boolean suggestion;


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
        strategy = new String[4];
        suggestion = false;
    }

    public JavaFxView() {

    }

    public void refreshWindow() {
        panel.getChildren().clear();
        panel.setCenter(centerGamePane);
        centerGamePane.setBottom(userActions);
        
        if(!playername.equals("Watch the game without playing")) {
	        displayTurnOptions();
	        displayPlayerHand(controller.model.userPlayer, 1);
        } 
        if(controller.model.player5 != null) {
        	displayPlayerHand(controller.model.player5, 1);
        }
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
        if(!playername.equals("Watch the game without playing")) {
        	displayMeldsInHand(controller.model.currentUserPlayer);
        }else {
        	AIturn();
        }
    }

    public void AIturn() {
    	final Button next = new Button("Next");
		next.setStyle("-fx-background-color: #f5f6fa");
        restart.setStyle("-fx-background-color: red; -fx-textfill: black;");
        
        HBox hbox = new HBox(5);
        hbox.setPadding(new Insets(10));

        hbox.getChildren().add(next);
        hbox.getChildren().add(restart);
 
        userActions.setTop(hbox);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        next.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                  AIplay();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    
    public void AIplay() {
    	Iterator it = controller.model.playerOrder.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Player player = (Player) pair.getKey();
            
            if(player instanceof AIPlayer) {
	        	if(controller.playAITurn((AIPlayer)player)) {
	        		 displayWinner(controller.model.gameWinner);
	        	}else { 
	                if(controller.model.player5 != null) {
	                	displayPlayerHand(controller.model.player5, 1);
	                }
	                if(controller.model.player2 != null) {
	                	displayPlayerHand(controller.model.player2, 2);
	                }
	                if(controller.model.player3 != null) {
	                	displayPlayerHand(controller.model.player3, 3);
	                }
	                if(controller.model.player4 != null) {
	                	displayPlayerHand(controller.model.player4, 4);
	                }
	        		displayBoard(controller.model.getBoard());
	        	
	        	}
            }
        }
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
                if(playername.equals("Watch the game without playing")) {
                	flowPane.getChildren().add(new Label("Player 2   "));
                }else {
                	flowPane.getChildren().add(new Label(playername + ":  " + name));
                }
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

        if (player == controller.model.currentUserPlayer) {
            Circle circle = new Circle();
            circle.setRadius(10f);
            circle.setFill(Color.ORANGE);
            flowPane.getChildren().add(circle);
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
            
            if(suggestion) {
            	int value = 0;
            	Color colour;
            	ArrayList<ArrayList<Tile>> meld = controller.model.currentUserPlayer.findSets();
            	if(meld.size() == 0) {
            		meld = controller.model.currentUserPlayer.findRuns();
                	if(meld.size() == 0) {
                		indicateSuggestion();
                	}else {
                		if(!controller.model.currentUserPlayer.initial30Played) {
                			for(int j = 0; j < meld.size(); j++) {
                				for(int k = 0; k < meld.size(); k++) {
	                				value += meld.get(j).get(k).getRank();
                				}
                			}
                			if(value >= 30) {
                				if(player == controller.model.currentUserPlayer) {
                    				System.out.println("run");
                					for(int k = 0; k < meld.size(); k++) {
    	            					if (meld.get(k).get(0).getColour() == 'G') {
    	            		                colour = Color.GREEN;
    	            		            } else if (meld.get(k).get(0).getColour()== 'R') {
    	            		                colour = Color.RED;
    	            		            } else if (meld.get(k).get(0).getColour() == 'B') {
    	            		                colour = Color.BLUE;
    	            		            } else if (meld.get(k).get(0).getColour() == 'O') {
    	            		                colour = Color.ORANGE;
    	            		            } else {
    	            		                colour = Color.BLACK;
    	            		            }
    	            					if(tileLabel.getTextFill() == colour) {
    	            						for(int l = 0; l < meld.get(k).size(); l++) {
    		                					if(Integer.parseInt(tileLabel.getText()) == meld.get(k).get(l).getRank()) {
    		                						tileLabel.setStyle("-fx-border-color: RED; -fx-border-width: 3px; -fx-background-color: WHITE; -fx-font-size: 20px");
    		                					}
    	            						}
    	            					}
                					}
                				}else {
                					tileLabel.setStyle("-fx-border-color: WHITE; -fx-background-color: WHITE; -fx-font-size: 20px");
                				}
                			}else {
                				indicateSuggestion();
                			}
                		}else {
                			if(player == controller.model.currentUserPlayer) {
            					if (meld.get(0).get(0).getColour() == 'G') {
            		                colour = Color.GREEN;
            		            } else if (meld.get(0).get(0).getColour()== 'R') {
            		                colour = Color.RED;
            		            } else if (meld.get(0).get(0).getColour() == 'B') {
            		                colour = Color.BLUE;
            		            } else if (meld.get(0).get(0).getColour() == 'O') {
            		                colour = Color.ORANGE;
            		            } else {
            		                colour = Color.BLACK;
            		            }
            					if(tileLabel.getTextFill() == colour) {
            						for(int k = 0; k < meld.get(0).size(); k++) {
    	        						if(Integer.parseInt(tileLabel.getText()) == meld.get(0).get(k).getRank()) {
    	            						tileLabel.setStyle("-fx-border-color: RED; -fx-border-width: 3px; -fx-background-color: WHITE; -fx-font-size: 20px");
    	            					}
            						}
            					}else {
            						indicateSuggestion();
            					}
            				}else {
            					tileLabel.setStyle("-fx-border-color: WHITE; -fx-background-color: WHITE; -fx-font-size: 20px");
            				}
                  		}
                	}
            	}else {
            		if(!controller.model.currentUserPlayer.initial30Played) {
            			for(int j = 0; j < meld.size(); j++) {
            				for(int k = 0; k < meld.get(j).size(); k++) {
	            				value += meld.get(j).get(k).getRank();
            				}
            			}
            			if(value >= 30) {
            				if(player == controller.model.currentUserPlayer) {
            					for(int k = 0; k < meld.size(); k++) {
            						for(int l = 0; l < meld.get(k).size(); l++) {
	                    				if (meld.get(k).get(l).getColour() == 'G') {
	                		                colour = Color.GREEN;
	                		            } else if (meld.get(k).get(l).getColour()== 'R') {
	                		                colour = Color.RED;
	                		            } else if (meld.get(k).get(l).getColour() == 'B') {
	                		                colour = Color.BLUE;
	                		            } else if (meld.get(k).get(l).getColour() == 'O') {
	                		                colour = Color.ORANGE;
	                		            } else {
	                		                colour = Color.BLACK;
	                		            }

	                    				if(tileLabel.getTextFill() == colour && Integer.parseInt(tileLabel.getText()) == meld.get(k).get(l).getRank()) {
	                    					tileLabel.setStyle("-fx-border-color: RED; -fx-border-width: 3px; -fx-background-color: WHITE; -fx-font-size: 20px");
	                					}
            						}
            					}
            				}else {
            					tileLabel.setStyle("-fx-border-color: WHITE; -fx-background-color: WHITE; -fx-font-size: 20px");
            				}
            			}else {
            				indicateSuggestion();
            			}
            		}else {
            			if(player == controller.model.currentUserPlayer) {
            				for(int k = 0; k < meld.get(0).size(); k++) {
            					if (meld.get(0).get(k).getColour() == 'G') {
            		                colour = Color.GREEN;
            		            } else if (meld.get(0).get(k).getColour()== 'R') {
            		                colour = Color.RED;
            		            } else if (meld.get(0).get(k).getColour() == 'B') {
            		                colour = Color.BLUE;
            		            } else if (meld.get(0).get(k).getColour() == 'O') {
            		                colour = Color.ORANGE;
            		            } else {
            		                colour = Color.BLACK;
            		            }
            					if(tileLabel.getTextFill() == colour && Integer.parseInt(tileLabel.getText()) == meld.get(0).get(k).getRank()) {
                					tileLabel.setStyle("-fx-border-color: RED; -fx-border-width: 3px; -fx-background-color: WHITE; -fx-font-size: 20px");
            					}
            				}
        				}else {
        					tileLabel.setStyle("-fx-border-color: WHITE; -fx-background-color: WHITE; -fx-font-size: 20px");
        				}
            		}
            	}
            }
        }

        if (controller.model.rigged) {
            HBox tileRigInputBox = new HBox(5);
            TextField tileRigTextField = new TextField();
            tileRigTextField.setPromptText("Draw");
            tileRigInputBox.getChildren().add(tileRigTextField);
            tileRigTextField.setMinHeight(50);
            tileRigTextField.setMaxWidth(50);

            tileRigTextField.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String text = tileRigTextField.getText();
                    Character textColour = text.charAt(0);
                    String textRank = text.substring(1);
                    Tile tileToGet = new Tile(textColour, Integer.parseInt(textRank));
                    Tile drawnTile = controller.model.getDeck().customDraw(tileToGet);

                    if (drawnTile != null) {
                        player.hand.add(drawnTile);
                        refreshWindow();
                    } else {
                        System.out.println("Invalid input!");
                    }
                    tileRigTextField.clear();
                }
            });

            flowPane.getChildren().add(tileRigInputBox);
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
        label = new Label(player.name + " wins the game!");
        label.setStyle("-fx-font: normal bold 30px 'serif'");
        centerGamePane.setTop(label);
        label.setAlignment(Pos.CENTER);
    }

    public void displayTurnOptions() {
        if(controller.model.currentUserPlayer.getHand().size() == 0) {
            displayWinner(controller.model.currentUserPlayer);
            return;
        }

        final Button drawTile = new Button("Draw Tile");
        drawTile.setStyle("-fx-background-color: #f5f6fa");
        final Button createMeld = new Button("Create Meld");
        createMeld.setStyle("-fx-background-color: #f5f6fa");
        final Button playToTable = new Button("Play tiles on the table");
        playToTable.setStyle("-fx-background-color: #f5f6fa");
        final Button endTurn = new Button("End turn");
        endTurn.setStyle("-fx-background-color: #f5f6fa");
        
        final Button suggestionTiles;
        if(suggestion) {
        	suggestionTiles = new Button("Hide Suggestion");
        }else {
        	suggestionTiles = new Button("Show Suggestion");
        }
        suggestionTiles.setStyle("-fx-background-color: #f5f6fa");
        
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
        hbox.getChildren().add(playCreatedMelds);
        hbox.getChildren().add(playToTable);
    	hbox.getChildren().add(complexTileReuse);
        hbox.getChildren().add(suggestionTiles);
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
                    controller.model.stopClock();
                }
            }
        });

        drawTile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    Tile drawnTile = controller.drawTile(controller.model.currentUserPlayer);
                    centerGamePane.getChildren().remove(label);
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

                        for (int i = 0; i < 3; i++) controller.drawTile(controller.model.currentUserPlayer);
                        
                        endTurnProcess();
                    } else {
                        centerGamePane.getChildren().remove(label);
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
                                centerGamePane.getChildren().remove(label);
                            } else {
                                for (int i = 0; i < 3; i++) controller.drawTile(controller.model.currentUserPlayer);
                                endTurnProcess();
                            }
                        } else {//Add to back
                            if (controller.playTilestoMeldBack(controller.model.currentUserPlayer, selectedTiles, selectedMeldID)) {
                                controller.model.currentUserPlayer.playedTilesOnTurn = true;
                                centerGamePane.getChildren().remove(label);
                            } else {
                                for (int i = 0; i < 3; i++) controller.drawTile(controller.model.currentUserPlayer);
                                endTurnProcess();
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
		
        suggestionTiles.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                	if(suggestion) {
                		suggestion = false;
                		suggestionTiles.setText("Show Suggestion");
                	}else {
                		suggestion = true;
                		suggestionTiles.setText("Hide Suggestion");
                		displayPlayerHand(controller.model.currentUserPlayer,1);
                	}
                }catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
        
		complexTileReuse.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                	if(controller.model.currentUserPlayer.initial30Played) {
	                	controller.reuseBoardTiles(controller.model.currentUserPlayer, selectedTilesFromBoard, selectedTiles);
	                	controller.model.currentUserPlayer.playedTilesOnTurn = true;
                        centerGamePane.getChildren().remove(label);
	            	
                	}else {
                		indicateInitial30NotPlayedYet();
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
                    centerGamePane.getChildren().remove(label);
                    endTurnProcess();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void endTurnProcess() {
    	boolean next = false;

    	// Memento check
        if (this.controller.meldValidatorService.validateBoard(this.controller.model.getBoard())) {
            this.controller.saveGame();
        } else {
            this.controller.restoreGame();
            this.indicateInvalidMeld();
            for (int i = 0; i < 3; i++) controller.drawTile(controller.model.currentUserPlayer);
        }

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
        label = new Label("Total value of Melds less than 30");
        label.setStyle("-fx-font: normal bold 30px 'serif'");
        label.setTextFill(Color.RED);
        centerGamePane.setTop(label);
        label.setAlignment(Pos.CENTER);
    }

    public void indicateInvalidMeld() {
        label = new Label("Invalid Meld");
        label.setStyle("-fx-font: normal bold 30px 'serif'");
        label.setTextFill(Color.RED);
        centerGamePane.setTop(label);
        label.setAlignment(Pos.CENTER);
    }
    
    public void indicateSuggestion() {
    	label = new Label("No valid meld, please draw a tile.");
        label.setStyle("-fx-font: normal bold 30px 'serif'");
        label.setTextFill(Color.RED);
        centerGamePane.setTop(label);
        label.setAlignment(Pos.CENTER);
    }
    
    public void indicateInitial30NotPlayedYet() {
    	label = new Label("Initial 30 points not played yet");
        label.setStyle("-fx-font: normal bold 30px 'serif'");
        label.setTextFill(Color.RED);
        centerGamePane.setTop(label);
        label.setAlignment(Pos.CENTER);
    }

    public void printTurns(Map<Player, Integer> order) {
    	String turnOrderString = "Turn Order: ";
        Iterator it = order.entrySet().iterator();
        
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Player player = (Player) pair.getKey();
            turnOrderString += player.name + " drew:" + pair.getValue() + ", ";
        }
        
        label = new Label(turnOrderString);
        label.setStyle("-fx-font: normal bold 30px 'serif'");
        centerGamePane.setTop(label);
        label.setAlignment(Pos.CENTER);

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
