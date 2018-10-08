package core;

import java.util.ArrayList;
import java.util.Collections;

public class Player {

	public String name = "Default";
	private Hand hand;
	private Game game;
		
	public Player() {
		this.hand = new Hand();
	}
	
	public Player(String name, Game game) {
		this.name = name;
		this.hand = new Hand();
		this.game = game;
	}
	
	public Hand GetHand() {
		return this.hand;
	}
	
	public void PlayMeld(Board board, ArrayList<Tile> potentialMeld) {
		
		if(IsValidMeld(potentialMeld)) {
			Meld meld = new Meld();
			
			for(int i = 0; i < potentialMeld.size();i++) {
				meld.add(potentialMeld.get(i));
			}
			
			board.AddMeld(meld);
		}
		
	}
	
	public boolean IsValidMeld(ArrayList<Tile> tiles) {
		
		if(tiles.size() < 3) {
			return false;
		}
		
		boolean validSet = IsValidSet(tiles);
		boolean validRun = IsValidRun(tiles);
				
		return validSet || validRun;
	}
	
	private boolean IsValidSet(ArrayList<Tile> tiles) {
		
		ArrayList<Character> colours = new ArrayList<Character>();
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		
		for(int i = 0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			
			//Check color
			if(colours.contains(tile.getColour())){
				return false;
			} else {
				colours.add(tile.getColour());		
			}
			
			//Check rank
			if(ranks.size() == 0) {
				ranks.add(tile.getRank());
			}
			else if(ranks.contains(tile.getRank())) {
				ranks.add(tile.getRank());
			}
			else { //Does not contain same rank
				return false;
			}
			
		}
		
		return true;
	}
	
	private boolean IsValidRun(ArrayList<Tile> tiles) {
		
		char color = ' ';
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		
		for(int i = 0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			
			//Check color
			if(color == ' ') {
				color = tile.getColour();
			} else if(color != tile.getColour()) {
				return false;
			}
					
			ranks.add(tile.getRank());		
		}
		
		Collections.sort(ranks);
		int prevRank = ranks.get(0);
		
		for(int i = 1; i < ranks.size(); i++) {

			if(ranks.get(i) - prevRank != 1) {			
				return false;
			}	
			prevRank = ranks.get(i);
		}
		
		
		return true;
	}
	
}
