package core;

import java.util.*;

import core.Model.GameStates;

import javafx.stage.Stage;

import java.util.Iterator;
import java.util.Map;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.animation.*;

public class Game extends Application {
    
    public Model model;
    public View view;
    public Controller controller;
    
	public GridPane panel;
	public Scene scene;
	public Stage stage;

    public static void main(String[] arg) {
    	launch(arg);
    }
    
    public Game() {

    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.stage = primaryStage;
		System.out.println("Start");
		panel = new GridPane();
		panel.setAlignment(Pos.CENTER); 
		panel.setGridLinesVisible(true);
	
	    panel.setStyle("-fx-background-color: PALEGREEN;");
	    panel.setMinSize(1300, 800); 
	    
		
		scene = new Scene(panel); 
		stage.setScene(scene);
	    stage.setTitle("Rummikub Game");
	    stage.show();
	    
	    model = new Model();   	
	    controller = new JavaFxController(model);
    	view = new JavaFxView(model, controller, panel);	  	
    	
	    
    	this.model.initGame();
    	//this.gameLoop();
    	
    	 this.loop();
	}
	

    public void gameLoop() {
       	view.printTurns(model.playerOrder);
        while(model.gameState == GameStates.PLAY) {
			Iterator it = model.playerOrder.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Player player = (Player) pair.getKey();
				view.indicateTurn(player);
				view.displayPlayerHands();
				if (player instanceof UserPlayer) userPlayerTurnLoop(model.userPlayer);
				else player.playTurn();
				view.displayBoard(model.getBoard());
				model.messageObservers();
				if (model.gameState == GameStates.END) break;
			}
            
            if(model.getDeck().getDeckSize() == 0) {
				System.out.println("The deck is empty.");
            	int value1 = Math.min(model.userPlayer.getHand().size(), model.aiPlayer1.getHand().size());
            	int value2 = Math.min(model.aiPlayer2.getHand().size(), model.aiPlayer3.getHand().size());
            	int minimum = Math.min(value1, value2);
            	if(minimum == model.userPlayer.getHand().size()) {
            		view.displayFinalTileCounts();
            		view.displayWinner(model.userPlayer);
            	}else if(minimum == model.aiPlayer1.getHand().size()) {
            		view.displayFinalTileCounts();
            		view.displayWinner(model.aiPlayer1);
            	}else if(minimum == model.aiPlayer2.getHand().size()) {
            		view.displayFinalTileCounts();
            		view.displayWinner(model.aiPlayer2);
            	}else {
            		view.displayFinalTileCounts();
            		view.displayWinner(model.aiPlayer3);
            	}
            	model.gameState = GameStates.END;
            }
            
            if (model.gameWinCheck()) view.displayWinner(model.gameWinner);
        }
    }
    
    public void loop() {
    	view.printTurns(model.playerOrder);
    	view.displayTurnOptions();
    	view.displayPlayerHand(model.userPlayer, 1);
    	view.displayPlayerHand(model.aiPlayer1, 2);
    	view.displayPlayerHand(model.aiPlayer2, 3);
    	view.displayPlayerHand(model.aiPlayer3, 4);
    		
    }
    
    public void userPlayerTurnLoop(UserPlayer player) {
		
		//Scanner reader = new Scanner(System.in);
		int choice = 0;
		int numOfTiles = player.getHand().size();
		boolean pass = false;
		while(true) {
			if(numOfTiles > player.getHand().size()) {
				view.displayTurnOptions_Pass();			
				pass = true;
			}else {
				view.displayTurnOptions();		
			}
			
			/*while(!reader.hasNextInt()) {
				System.out.println("Wrong input. Please input again.");
				if(pass) {
					System.out.println("(1) Pass, (2) Create Meld, (3) Play tiles on the table. Enter -1 to quit.");
				}else {
					System.out.println("(1) Draw Tile, (2) Create Meld, (3) Play tiles on the table.  Enter -1 to quit.");
				}
				reader.nextLine();
			}*/
			
			choice = controller.turnOptionInput();
			
			if(choice == -1) {
				player.model.endGame();
				view.indicateUserEndsGame(player);
				break;
			}
		
			if(choice == 1) {//Draw tile
				if(player.model.getDeck().getDeckSize() == 0) {
					break;
				}
				if(pass) {
					pass = false;
					break;
				}
				
				Tile newTile = player.model.getDeck().drawTile();
				view.displayDrawnTile(newTile);	
				player.hand.add(newTile);
				break;
			}
			else if(choice == 2) {//Play Meld
			
				int createAdditionalMelds;
				
				Hand availableTiles = player.hand;
				
				tileSelectionLoop(player, availableTiles);
				
				view.displayCreateAnotherMeldOption();
				/*while(!reader.hasNextInt()) {
					System.out.println("Wrong input. Please input again.");
					System.out.println("Create another meld? (1)Yes (2)No");
					reader.nextLine();
				}*/
				createAdditionalMelds = controller.turnOptionInput();
				
				while(createAdditionalMelds == 1) {
					view.indicateAvailableTiles();
					view.displayHand(availableTiles);
					
					tileSelectionLoop(player, availableTiles);
					
					view.displayCreateAnotherMeldOption();
					/*while(!reader.hasNextInt()) {
						System.out.println("Wrong input. Please input again.");
						System.out.println("Create another meld? (1)Yes (2)No");
						reader.nextLine();
					}*/
					createAdditionalMelds = controller.turnOptionInput();
					
				}
				
				if(!player.initial30Played) {
					if(player.totalAllMelds(player.meldsInHand) < 30){
						view.indicateMeldsLessThan30();
					}
					else {
						player.playMelds(player.model.getBoard(), player.meldsInHand);
						player.initial30Played = true;
					}
				}
				else {
					player.playMelds(player.model.getBoard(), player.meldsInHand);
				}
			}
			else if(choice == 3) {
				if(player.model.getBoard().currentMelds.size() == 0) {
					view.indicateNoTileOnBoard();
					userPlayerTurnLoop(player);
					return;
				}
				
				Hand availableTiles = player.hand;
				
				while(true) {
					view.displayTileToExistingMeldOptions();
					/*while(!reader.hasNextInt()) {
						System.out.println("Wrong input. Please input again.");
						System.out.println("Choose an option: 1. Add a tile to the end of a meld. 2. Add a tile to the beginning of a meld. 3. Add a tile to create a new meld. Enter -1 to go back.");
						reader.nextLine();
					}*/
					int option = controller.turnOptionInput();
					if(option == -1) {
						break;
					}
					if(option == 1) {
						while(true) {
							view.displayTileToMeldSelection();
							int tileSelected = controller.turnOptionInput();
							if(tileSelected == -1) {
								break;
							}
							
							if(tileSelected >= availableTiles.size() || tileSelected < -1) {//Invalid Tile
								view.indicateWrongInput();
							}else {
								while(true) {
									view.displayBoard(player.model.getBoard());
									view.displayMeldSelection();
									int meldSelected = controller.turnOptionInput();
									if(meldSelected == -1) { break;}
									if(meldSelected >=  player.model.getBoard().currentMelds.size() || meldSelected < 0) {//Invalid Tile
										view.indicateWrongInput();
									}else {
										Meld meld = new Meld();
										meld.add(player.hand.getTile(tileSelected));
										if(player.model.getBoard().addTileToMeldEnd(meldSelected, meld)) {
											player.getHand().remove(player.hand.getTile(tileSelected));						
											view.displayBoard(model.getBoard());
											view.displayHand(player.getHand());
										}else {
											view.indicateInvalidMeld();
										}
										break;
									 }
								}
							}
						}
					}else if(option == 2) {
						while(true) {
							view.displayTileToMeldSelection();
							int tileSelected = controller.turnOptionInput();
							if(tileSelected == -1) {
								break;
							}
								
							if(tileSelected >= availableTiles.size() || tileSelected < -1) {//Invalid Tile
								view.indicateWrongInput();
							}else {
								while(true) {
									view.displayBoard(player.model.getBoard());
									view.displayMeldSelection();
									int meldSelected = controller.turnOptionInput();
									if(meldSelected == -1) { break;}
									if(meldSelected >= player.model.getBoard().currentMelds.size() || meldSelected < 0) {//Invalid Tile
										view.indicateWrongInput();
									}else {
										Meld meld = new Meld();
										meld.add(player.hand.getTile(tileSelected));
										if(player.model.getBoard().addTileToMeldBeginning(meldSelected, meld)) {
											player.getHand().remove(player.hand.getTile(tileSelected));
											view.displayBoard(model.getBoard());
											view.displayHand(player.getHand());
										}else {
											view.indicateInvalidMeld();
										}
										break;
									}
								}
							}
						}
					}else if(option == 3) {
							Meld meld = new Meld();
							while(true) {
								view.displayTileToMeldSelection();
								int tileSelected = controller.turnOptionInput();
								if(tileSelected == -1) {
									break;
								}
								
								if(tileSelected >= availableTiles.size() || tileSelected < -1) {//Invalid Tile
									view.indicateWrongInput();
								}else {
									meld.add(player.hand.getTile(tileSelected));
								}
							}
							while(true) {
								view.displayBoard(player.model.getBoard());
								view.displayMeldSelection();
								int meldSelected = controller.turnOptionInput();
								if(meldSelected == -1) { break;}
								if(meldSelected >= player.model.getBoard().currentMelds.size() || meldSelected < 0) {//Invalid Tile
									view.indicateWrongInput();
								}else {
									while(true) {
										view.displayMeld(player.model.getBoard().currentMelds.get(meldSelected));
										view.displayTileInSelectedMeldSelection();
										int tileOfMeld = controller.turnOptionInput();
										if(tileOfMeld == -1) { break;}
										if(tileOfMeld >= player.model.getBoard().currentMelds.get(meldSelected).size() || tileOfMeld < 0) {
											view.indicateWrongInput();
										}else {
											while(true) {
												view.displayTileToMeldPositionSelection();
												int positionOfMeld = controller.turnOptionInput();
												if(positionOfMeld == -1) { break;}
												if(positionOfMeld < 0 || positionOfMeld >= meld.size()) {
													view.indicateWrongInput();
												}else {
													if(player.model.getBoard().takeTileToFormNewMeld(meldSelected,tileOfMeld, positionOfMeld, meld)) {
														for(int i = 0; i < meld.size(); i++) {
															player.getHand().remove(meld.getTile(i));
														}
														view.displayBoard(model.getBoard());
														view.displayHand(player.getHand());
													}
													else {
														view.indicateInvalidMeld();
													}
												}
												break;
											}
										}
									}
								}
							}
					 }else {
						 view.indicateWrongInput();
					 }
				}
			}
			else {
				view.indicateWrongInput();
			}
		}
	}
    

	public void tileSelectionLoop(UserPlayer player, Hand availableTiles) {
		int tileSelected;
		
		ArrayList<Integer> tileIndices = new ArrayList<Integer>();
		
		//Select tiles to create meld
		while(true) {
			view.displayTileSelection();
			/*while(!reader.hasNextInt()) {
				System.out.println("Wrong input. Please input again.");
				System.out.println("Enter the index of the Tile you want to select. (-1) to stop selecting");
				reader.nextLine();
			}*/
			tileSelected = controller.turnOptionInput();
			
			if(tileSelected >= availableTiles.size() || tileSelected < -1) {//Invalid Tile
				view.indicateWrongInput();
			}
			else if(tileSelected == -1) {//Finished selecting
				Meld meld = player.createMeld(player.selectTiles(tileIndices, availableTiles), availableTiles);
				if(meld != null) {
					player.meldsInHand.add(meld);
				}else {
					view.indicateInvalidMeld();
				}
				
				tileIndices.clear();
				break;
			}
			else {//Select tile
				tileIndices.add(tileSelected);
			}
		}
	}



}
