package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UserPlayer extends Player{
	
	PlayerStrategy<? super UserPlayer> strategy;

	public UserPlayer() {
		super();
	}
	
	public UserPlayer(String name, Game game, PlayerStrategy<? super UserPlayer> strategy) {
		super(name, game);
		this.strategy = strategy;
	}
	
	public void playTurn() {
		strategy.executeStrategy(this);	
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
			while(!reader.hasNextInt()) {
				System.out.println("Wrong input. Please input again.");
				System.out.println("Enter the index of the Tile you want to select. (-1) to stop selecting");
				reader.nextLine();
			}
			tileSelected = reader.nextInt();
			
			if(tileSelected >= availableTiles.size() || tileSelected < -1) {//Invalid Tile
				System.out.println("Invalid tile, try again.");
			}
			else if(tileSelected == -1) {//Finished selecting
				Meld meld = createMeld(selectTiles(tileIndices, availableTiles), availableTiles);
				if(meld != null) {
					meldsInHand.clear();
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
