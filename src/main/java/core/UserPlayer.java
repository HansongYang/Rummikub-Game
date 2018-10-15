package core;

import java.util.ArrayList;
import java.util.Scanner;

public class UserPlayer extends Player{

	public UserPlayer() {
		super();
	}
	
	public UserPlayer(String name, Game game) {
		super(name, game);
	}
	
	public void playTurn() {
		Scanner reader = new Scanner(System.in);
		int choice = -1;
		
		
		System.out.println("(1)Draw Tile, (2)Create Meld");
		choice = reader.nextInt();
		
		while(choice != 1 && choice != 2) {
			System.out.println("Invalid input, try again");
			System.out.println("(1)Draw Tile, (2)Create Meld");
			choice = reader.nextInt();
		}
		
		
		if(choice == 1) {//Draw tile
			Tile newTile = game.getDeck().drawTile();
			System.out.println("You drew: " + newTile.getColour() + ", " + newTile.getRank());		
			hand.add(newTile);
			
			return;
		}
		else if(choice == 2) {//Play Meld
		
			int createAdditionalMelds;
			
			Hand availableTiles = hand;
			
			tileSelectionInput(reader, availableTiles);
			
			System.out.println("Create another meld? (1)Yes (2)No");
			createAdditionalMelds = reader.nextInt();
			
			while(createAdditionalMelds == 1) {
				System.out.println("Available Tiles:");
				availableTiles.printHand();
				tileSelectionInput(reader, availableTiles);
				
				System.out.println("Create another meld? (1)Yes (2)No");
				createAdditionalMelds = reader.nextInt();
				
			}
			
			if(!initial30Played) {
				if(totalAllMelds(meldsInHand) < 30){
					System.out.println("The total of all your melds does not exceed 30");
				}
				else {
					playMelds(game.getBoard(), meldsInHand);
					initial30Played = true;
				}
			}
			else {
				playMelds(game.getBoard(), meldsInHand);
			}
			
		}

		
	}
	
	public Meld createMeld(ArrayList<Tile> selectedTiles, Hand availableTiles) {
		
		if(meldValidatorService.isValidMeld(selectedTiles)) {
			Meld meld = new Meld();
			
			for(int i = 0; i < selectedTiles.size();i++) {
				meld.add(selectedTiles.get(i));
				availableTiles.remove(selectedTiles.get(i));
			}
			return meld;
		}

		return null;
	}
	
	public ArrayList<Tile> selectTiles(ArrayList<Integer> tileIndices, Hand availableTiles){
		ArrayList<Tile> selectedTiles = new ArrayList<Tile>();
		
		for(int i = 0; i < tileIndices.size(); i++) {
			selectedTiles.add(availableTiles.getTile(tileIndices.get(i)));
			
		}
		
		return selectedTiles;
	}
	
	public void tileSelectionInput(Scanner reader, Hand availableTiles) {
		int tileSelected;
		
		ArrayList<Integer> tileIndices = new ArrayList<Integer>();
		
		
		//Select tiles to create meld
		while(true) {
			System.out.println("Enter the index of the Tile you want to select. (-1) to stop selecting");
			tileSelected = reader.nextInt();
			
			if(tileSelected >= availableTiles.size() || tileSelected < -1) {//Invalid Tile
				System.out.println("Invalid tile, try again.");
			}
			else if(tileSelected == -1) {//Finished selecting
				Meld meld = createMeld(selectTiles(tileIndices, availableTiles), availableTiles);
				if(meld != null) {
					meldsInHand.add(meld);
				}else {
					System.out.println("Invalid Meld");
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
