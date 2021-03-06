package core;

import java.util.ArrayList;
import java.util.Scanner;

public class UserStrategy implements PlayerStrategy<UserPlayer>{
	
	public void executeStrategy(UserPlayer player) {
		
		Scanner reader = new Scanner(System.in);
		int choice = 0;
		int numOfTiles = player.getHand().size();
		boolean pass = false;
		
		ArrayList<ArrayList<Tile>> runs;
		ArrayList<ArrayList<Tile>> sets;
		ArrayList<Tile> otherSuggestions;
		ArrayList<Tile> usedTiles;
		ArrayList<Tile> remainingTiles;
		
		while(true) {
			if(numOfTiles > player.getHand().size()) {
				System.out.println("(1) Pass, (2) Create Meld, (3) Play tiles on the table. Enter -1 to quit.");
				pass = true;
			}else {
				
				runs = player.findRuns();
				sets = player.findSets();
				usedTiles = new ArrayList<Tile>();
				remainingTiles = new ArrayList<Tile>();
				
//				THE ONLY PART I CHANGED IS LINE 24 - 32
//				you can comment this next part out but runs is an arraylist of possible runs (arraylist of arraylists)
//				and sets is an arraylist of possible sets (only shows longest streaks (e.g. [[12345], [8,9,10,11,12]]))
//				instead of returning [1,2,3,][1,2,4][1,2,5][2,3,4] etc.....
				
				if (!runs.isEmpty()) System.out.println("RUNS: " + runs);
				if (!sets.isEmpty()) System.out.println("SETS: " + sets);
				
				for (ArrayList<Tile> run: runs) {
					for (Tile t: run) {
						usedTiles.add(t);
					}
				}
				
				for (ArrayList<Tile> set: sets) {
					for (Tile t: set) {
						usedTiles.add(t);
					}
				}
				
				for (Tile t: player.getHand().getTiles()) {
					if (!usedTiles.contains(t)) {
						remainingTiles.add(t);
					}
				}
				
				otherSuggestions = player.findOther(remainingTiles);
				System.out.println("OTHER SUGGESTIONS: " + otherSuggestions);
				
				System.out.println("(1) Draw Tile, (2) Create Meld, (3) Play tiles on the table.  Enter -1 to quit.");
			}
			
			while(!reader.hasNextInt()) {
				System.out.println("Wrong input. Please input again.");
				if(pass) {
					System.out.println("(1) Pass, (2) Create Meld, (3) Play tiles on the table. Enter -1 to quit.");
				}else {
					System.out.println("(1) Draw Tile, (2) Create Meld, (3) Play tiles on the table.  Enter -1 to quit.");
				}
				reader.nextLine();
			}
			
			choice = reader.nextInt();
			if(choice == -1) {
				player.model.endGame();
				System.out.println("User ends the game.");
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
				System.out.println("You drew: " + newTile.getColour() + ", " + newTile.getRank());		
				player.hand.add(newTile);
				break;
			}
			else if(choice == 2) {//Play Meld
			
				int createAdditionalMelds;
				
				Hand availableTiles = player.hand;
				
				player.tileSelectionInput(reader, availableTiles);
				
				System.out.println("Create another meld? (1)Yes (2)No");
				while(!reader.hasNextInt()) {
					System.out.println("Wrong input. Please input again.");
					System.out.println("Create another meld? (1)Yes (2)No");
					reader.nextLine();
				}
				createAdditionalMelds = reader.nextInt();
				
				while(createAdditionalMelds == 1) {
					System.out.println("Available Tiles:");
					availableTiles.printHand();
					player.tileSelectionInput(reader, availableTiles);
					
					System.out.println("Create another meld? (1)Yes (2)No");
					while(!reader.hasNextInt()) {
						System.out.println("Wrong input. Please input again.");
						System.out.println("Create another meld? (1)Yes (2)No");
						reader.nextLine();
					}
					createAdditionalMelds = reader.nextInt();
					
				}
				
				if(!player.initial30Played) {
					if(player.totalAllMelds(player.meldsInHand) < 30){
						System.out.println("The total of all your melds does not exceed 30.");
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
					System.out.println("No tile on the board.");
					executeStrategy(player);
					return;
				}
				
				Hand availableTiles = player.hand;
				
				while(true) {
					System.out.println("Choose an option: 1. Add a tile to the end of a meld. 2. Add a tile to the beginning of a meld. 3. Add a tile to create a new meld. Enter -1 to go back.");
					while(!reader.hasNextInt()) {
						System.out.println("Wrong input. Please input again.");
						System.out.println("Choose an option: 1. Add a tile to the end of a meld. 2. Add a tile to the beginning of a meld. 3. Add a tile to create a new meld. Enter -1 to go back.");
						reader.nextLine();
					}
					int option = reader.nextInt();
					if(option == -1) {
						break;
					}
					if(option == 1) {
						while(true) {
							System.out.println("Enter the index of the Tile you want to select to add the end of a meld. One tile at each time. Enter -1 to go back.");
							int tileSelected = reader.nextInt();
							if(tileSelected == -1) {
								break;
							}
							
							if(tileSelected >= availableTiles.size() || tileSelected < -1) {//Invalid Tile
								System.out.println("Invalid input, please try again.");
							}else {
								while(true) {
									player.model.getBoard().printBoard();
									System.out.println("Enter the index of the meld you want to select. Enter -1 to quit.");
									int meldSelected = reader.nextInt();
									if(meldSelected == -1) { break;}
									if(meldSelected >=  player.model.getBoard().currentMelds.size() || meldSelected < 0) {//Invalid Tile
										System.out.println("Invalid input, please try again.");
									}else {
										Meld meld = new Meld();
										meld.add(player.hand.getTile(tileSelected));
										if(player.model.getBoard().addTileToMeldEnd(meldSelected, meld)) {
											player.getHand().remove(player.hand.getTile(tileSelected));
											player.model.getBoard().printBoard();
											player.getHand().printHand();
										}else {
											System.out.println("Invalid meld!");
										}
										break;
									 }
								}
							}
						}
					}else if(option == 2) {
						while(true) {
							System.out.println("Enter the index of the Tile you want to select to add the beginning of a meld. One tile at each time. Enter -1 to go back.");
							int tileSelected = reader.nextInt();
							if(tileSelected == -1) {
								break;
							}
								
							if(tileSelected >= availableTiles.size() || tileSelected < -1) {//Invalid Tile
								System.out.println("Invalid input, please try again.");
							}else {
								while(true) {
									player.model.getBoard().printBoard();
									System.out.println("Enter the index of the meld you want to select. Enter -1 to quit.");
									int meldSelected = reader.nextInt();
									if(meldSelected == -1) { break;}
									if(meldSelected >= player.model.getBoard().currentMelds.size() || meldSelected < 0) {//Invalid Tile
										System.out.println("Invalid input, please try again.");
									}else {
										Meld meld = new Meld();
										meld.add(player.hand.getTile(tileSelected));
										if(player.model.getBoard().addTileToMeldBeginning(meldSelected, meld)) {
											player.getHand().remove(player.hand.getTile(tileSelected));
											player.model.getBoard().printBoard();
											player.getHand().printHand();
										}else {
											System.out.println("Invalid meld!");
										}
										break;
									}
								}
							}
						}
					}else if(option == 3) {
							Meld meld = new Meld();
							while(true) {
								System.out.println("Enter the index of the Tile you want to select to create a new meld. One tile at each time. Enter -1 to go back.");
								int tileSelected = reader.nextInt();
								if(tileSelected == -1) {
									break;
								}
								
								if(tileSelected >= availableTiles.size() || tileSelected < -1) {//Invalid Tile
									System.out.println("Invalid input, please try again.");
								}else {
									meld.add(player.hand.getTile(tileSelected));
								}
							}
							while(true) {
								player.model.getBoard().printBoard();
								System.out.println("Enter the index of the meld you want to select. Enter -1 to go back.");
								int meldSelected = reader.nextInt();
								if(meldSelected == -1) { break;}
								if(meldSelected >= player.model.getBoard().currentMelds.size() || meldSelected < 0) {//Invalid Tile
									System.out.println("Invalid input, please try again.");
								}else {
									while(true) {
										player.model.getBoard().currentMelds.get(meldSelected).printMeld();
										System.out.println("\nEnter the index of a tile in your selected meld that you want to choose. Enter -1 to go back.");
										int tileOfMeld = reader.nextInt();
										if(tileOfMeld == -1) { break;}
										if(tileOfMeld >= player.model.getBoard().currentMelds.get(meldSelected).size() || tileOfMeld < 0) {
											System.out.println("Invalid input, please try again.");
										}else {
											while(true) {
												System.out.println("Enter the index position that you want to put the tile in the new meld. Enter -1 to go back.");
												int positionOfMeld = reader.nextInt();
												if(positionOfMeld == -1) { break;}
												if(positionOfMeld < 0 || positionOfMeld >= meld.size()) {
														System.out.println("Invalid input, please try again.");
												}else {
													if(player.model.getBoard().takeTileToFormNewMeld(meldSelected,tileOfMeld, positionOfMeld, meld)) {
														for(int i = 0; i < meld.size(); i++) {
															player.getHand().remove(meld.getTile(i));
														}
														player.model.getBoard().printBoard();
														player.getHand().printHand();
													}
													else {
														System.out.println("Invalid meld!");
													}
												}
												break;
											}
										}
									}
								}
							}
					 }else {
					   	 System.out.println("Invalid input, please enter again!!!");
					 }
				}
			}
			else {
				System.out.println("Invalid input, please enter again!!!");
			}
		}
	}
}
