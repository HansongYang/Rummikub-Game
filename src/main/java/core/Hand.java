package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Hand {
	private ArrayList<Tile> hands;
	
	public Hand() {
		hands = new ArrayList<Tile>();
	}

	public Tile getTile(int i) {
		return this.hands.get(i);
	}
	
	public ArrayList<Tile> getTiles() {
		return this.hands;
	}
	
	public void add(Tile t) {
		hands.add(t);
	}
	
	public void remove(Tile t) {
		for(int i = 0; i < hands.size(); i++) {
			if(hands.get(i).getRank() == t.getRank() && hands.get(i).getColour() == t.getColour()) {
				hands.remove(i);
				return;
			}
		}
	}
	
	public boolean win() {
		if(hands.size() == 0) {
			return true;
		}
		return false;
	}
	
	public int size() {
		return this.hands.size();
	}
	
	public void printHand() {
		if(size() == 0) {
			System.out.println("Player doesn't have any tile.");
		} else {
			System.out.print("Player's hand: ");
			for(int i = 0; i < hands.size(); i++) {
				System.out.print("(" + i + ")");
				hands.get(i).printTile();
			}
			System.out.println();
		}
	}
	
	//sort the hands by number
	public void sortTilesByNumber() {
		ArrayList<Tile> newHands = hands;
		Collections.sort(newHands, new SortByNumber());
	}
	
	//sort the hands by colour.
	public void sortTilesByColour(){
		ArrayList<Hand> colouredTiles = new ArrayList<Hand>();
		ArrayList<Tile> newHand = new ArrayList<Tile>();	
		colouredTiles = this.separateTilesByColour();
		
		for(int i = 0; i < 5; i++){
			colouredTiles.get(i).sortTilesByNumber();
			newHand.addAll(colouredTiles.get(i).getTiles());
		}
		this.hands = newHand;
	}
	
	//Separate Tiles by Colour and put these groups of tiles into an arraylist
	public ArrayList<Hand> separateTilesByColour() {
		ArrayList<Hand> colouredTiles = new ArrayList<Hand>();
		Tile current;
		
		for(int i = 0; i < 5; i++) {
			colouredTiles.add(new Hand());
		}
		
		for(int i = 0; i < hands.size(); i++){
			current = this.getTile(i);
			
			if(current.getColour() == 'R') {
				colouredTiles.get(0).add(current);
			} else if(current.getColour() == 'B') {
				colouredTiles.get(1).add(current);
			} else if(current.getColour() == 'G') {
				colouredTiles.get(2).add(current);
			} else if(current.getColour() == 'O'){
				colouredTiles.get(3).add(current);
			} else if(current.getColour() == 'J'){
				colouredTiles.get(4).add(current);
			}
		}
		return colouredTiles;
	}
	
	//Separate Tiles by number and put these groups of tiles into an arraylist
	public ArrayList<Hand> separateTilesByNumber(){
		ArrayList<Hand> tiles = new ArrayList<Hand>();
		this.sortTilesByNumber();
		
		for(int i = 0; i < 14; i++) {
			tiles.add(new Hand());
		}
		
		for(int i = 0; i < hands.size(); i++)
			tiles.get(this.getTile(i).getRank()).add(this.getTile(i));
		
		return tiles;
	}
	
	//Discard the redundant tile which is a helper function of getMeldSets()
	public void discardRedundantTiles() {
		ArrayList<Tile> deck = new ArrayList<Tile>();
		for(Tile tile : this.getTiles()){
			if(!deck.contains(tile))
				deck.add(tile);
		}
		this.hands = deck;
	}
	
	//Separate all tiles of the same number into an arraylist which is a helper function of getMeldSets()
	public ArrayList<Meld> getSets(){
		ArrayList<Meld> sets = new ArrayList<Meld>();
		ArrayList<Tile> tiles; 
		
		if(hands.size() > 2) {
			sets.add(new Meld(this.getTiles()));
		}
		
		if(hands.size() > 3){
			for(Tile removedTile : this.getTiles()){
				tiles = new ArrayList<Tile>(this.getTiles());
				tiles.remove(removedTile);
				sets.add(new Meld(tiles));
			}
		}
		
		return sets;
	}
	
	public ArrayList<ArrayList<Tile>> getSetsOfTwo(){
		
		ArrayList<ArrayList<Tile>> setsTwo = new ArrayList<ArrayList<Tile>>();
		
		// Go through all the tiles in player's hand
		// With each tile, try to find a set of 2 
		// prevColours array is used for each possible set to keep track of colors we've added to the possible set
		
		// Example: If player Hand is [B2,G2,R5], method will initially return [[B2,G2],[G2,B2]]
		// However, since [B2, G2] and [G2, B2] are the same thing, setsTwo is converted to a set and then back to an ArrayList
		// to erase duplicates
		
		
		for (Tile currentTile: this.getTiles()) {
			ArrayList<Tile> possibleSet = new ArrayList<Tile>();
			possibleSet.add(currentTile);
			ArrayList<Character> prevColours = new ArrayList<Character>();
			prevColours.add(currentTile.getColour());
			
			for (Tile possibleTile: this.getTiles()) {
				if (possibleTile.getRank() == currentTile.getRank() && !prevColours.contains(possibleTile.getColour())) {
					possibleSet.add(possibleTile);
					prevColours.add(possibleTile.getColour());
				}
			}
			
			if (possibleSet.size() == 2) setsTwo.add(possibleSet);
		}
		
		for (ArrayList<Tile> set : setsTwo) {
			Collections.sort(set);
		}
		
		Set<ArrayList<Tile>> javaSet = new HashSet<ArrayList<Tile>>(setsTwo);
		setsTwo.clear();
		setsTwo.addAll(javaSet);
		
		return setsTwo;
	}
	
	//Find all of the possible meld of sets that this hand can get
	public ArrayList<Meld> getMeldSets() {
		ArrayList<Hand> tiles = separateTilesByNumber();
		ArrayList<Meld> sets = new ArrayList<Meld>(); 
		boolean duplicate = false;
	
		for(Hand currentSet : tiles){
			currentSet.discardRedundantTiles();
			sets.addAll(currentSet.getSets());
		}
		if(sets.size() == 0) {
			return sets;
		}

		int i = 0;
		while(true) {
			for(int j = 0; j < sets.get(i).size(); j++) {
				for(int k = j+1; k < sets.get(i).size(); k++) {
					if(sets.get(i).getTile(j).getColour() == sets.get(i).getTile(k).getColour()) {
						sets.remove(i);
						duplicate = true;
						break;
					}
				}
				if(duplicate) {
					break;
				}
			}
			if(duplicate) {
				duplicate = false;
				i = 0;
			}else {
				i++;
			}
			if(i == sets.size()) {
				break;
			}
		}
		return sets;
	}
	
	//Find all of the possible meld of runs that this hand can get
	public ArrayList<Meld> getMeldRuns(){
		ArrayList<Hand> colouredTiles = separateTilesByColour();
		ArrayList<Meld> runs = new ArrayList<Meld>();;
		ArrayList<Tile> possibleRun;
		
		for(Hand hand : colouredTiles){
			hand.discardRedundantTiles();
			hand.sortTilesByNumber();
			
			for(int i = 0; i < hand.size(); i++){
				possibleRun = new ArrayList<Tile>();
				possibleRun.add(hand.getTile(i));
				
				for(int j = i+1; j < hand.size(); j++){
					if(hand.getTile(j-1).getRank() == hand.getTile(j).getRank() - 1 || hand.getTile(j-1).getColour() == 'J') {
						possibleRun.add(hand.getTile(j));
					}
					else {
						break;
					}
					
					if(possibleRun.size() > 2)
						runs.add(new Meld(possibleRun));
				}
			}
		}
		
		for(int i = 0; i < runs.size(); i++) {
			if(runs.get(i).size() > 3) {
				Meld goodMeld = new Meld();
				goodMeld = runs.remove(i);
				runs.add(0, goodMeld);
			}
		}
		return runs;
	}
	
	//Find the initial 30 points for a player, if there is no set or run, it will return null.
	public ArrayList<Meld> getInitialTiles() {
		ArrayList<Meld> runs = getMeldRuns();
		ArrayList<Meld> initial = new ArrayList<Meld>();
		
		if(runs.size() > 0) {
			while(runs.size() > 0) {
				initial.add(runs.get(0));
				for(int i = 0; i < runs.get(0).size(); i++) {
					hands.remove(runs.get(0).getTile(i));
				}
				runs = getMeldRuns();
			}
		}
		
		ArrayList<Meld> sets = getMeldSets();
		
		if(sets.size() > 0) {
			while(sets.size() > 0) {
				initial.add(sets.get(0));
				for(int i = 0; i < sets.get(0).size(); i++) {
					hands.remove(sets.get(0).getTile(i));
				}
				sets = getMeldSets();
			}
		}
		
		int value = 0;
		for(int i = 0; i < initial.size(); i++) {
			value += initial.get(i).totalValue();
		}
		if(value < 30) {
			for(int i = initial.size() - 1; i >= 0; i--) {
				for(int j = 0; j < initial.get(i).size(); j++) {
					hands.add(initial.get(i).getTile(j));
				}
				initial.remove(i);
			}
		}
		
		return initial;
	}
	
	//Return tiles in hand that are not part of the specified melds
	public ArrayList<Tile> getRemainingTiles(ArrayList<Meld> melds){
		ArrayList<Tile> remainingTiles = new ArrayList<Tile>();
		
		for(int i = 0; i < hands.size(); i++) {
			Tile tile = hands.get(i);
			boolean contains = false;
			
			for(int j = 0; j < melds.size(); j++) {
				if(melds.get(j).getTiles().contains(tile)) {
					contains = true;
				}
			}		
			if(!contains) {
				remainingTiles.add(tile);
			}			
		}		
		return remainingTiles;
	}
	
	class SortByNumber implements Comparator<Tile> {
		public int compare(Tile t1, Tile t2) {
			return t1.getRank() - t2.getRank();
		}
	}
}
