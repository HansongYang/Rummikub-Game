package core;

import java.util.ArrayList;

public class AIPlayer extends Player{
	
	public int tilesUsedOnTurn = 0;
	
	public int blue = 0;
	public int red = 0;
	public int orange = 0;
	public int green = 0;
	
	PlayerStrategy<? super AIPlayer> strategy;
	
	public AIPlayer() {
		super();
	}
	
	public AIPlayer(String name, Game game, PlayerStrategy<? super AIPlayer> strategy) {
		super(name, game);
		this.strategy = strategy;
	}
	
	public void playTurn() {
		strategy.executeStrategy(this);		
	}
	
	//Find all possible melds with runs as priority 
	public ArrayList<Meld> meldRunsFirst() {
		ArrayList<Meld> runMelds = hand.getMeldRuns();
		ArrayList<Meld> setMelds;
		Hand newHand = hand;
		
		//Remove used tiles 
		for(int i = 0; i < runMelds.size(); i++) {
			for(int j = 0; j < runMelds.get(i).size(); j++) {				
				newHand.remove(runMelds.get(i).getTile(j));
				hand.add(runMelds.get(i).getTile(j));
			}
		}

		setMelds = newHand.getMeldSets();
		runMelds.addAll(setMelds);
		
		return runMelds;
	}
	
	//Find all possible melds with sets as priority 
	public ArrayList<Meld> meldSetsFirst() {
		ArrayList<Meld> runMelds; 
		ArrayList<Meld> setMelds = hand.getMeldSets();
		Hand newHand = hand;
		
		//Remove used tiles 
		for(int i = 0; i < setMelds.size(); i++) {
			for(int j = 0; j < setMelds.get(i).size(); j++) {				
				newHand.remove(setMelds.get(i).getTile(j));	
				hand.add(setMelds.get(i).getTile(j));
			}
		}
		
		runMelds = newHand.getMeldRuns();
		setMelds.addAll(runMelds);
		
		return setMelds;
	}
	
	//Determine the best set of melds to play based on number of tiles
	public ArrayList<Meld> findBestPlay(ArrayList<Meld> melds1, ArrayList<Meld> melds2){
		int totalTiles1 = 0;
		int totalTiles2 = 0;
		
		//Get total number of tiles used
		for(int i = 0; i < melds1.size();i++) {
			totalTiles1 += melds1.get(i).size();
		}
		for(int i = 0; i < melds2.size();i++) {
			totalTiles2 += melds2.get(i).size();
		}
		
		if(totalTiles1 > totalTiles2) {
			return melds1;
		}
		else {
			return melds2;
		}
	}
	
	public int playWithTableTiles(ArrayList<Tile> remainingTiles) {
		int tilesPlayed = 0;
	
		for (Tile tile : remainingTiles) {
			Meld meldToAdd = new Meld();
			meldToAdd.add(tile);
	
			for (int i = 0; i < game.getBoard().currentMelds.size(); i++) {
				Meld meldTempA = new Meld(game.getBoard().currentMelds.get(i).getTiles());  // Meld for testing tile add to back
				Meld meldTempB = new Meld(game.getBoard().currentMelds.get(i).getTiles());   // Meld for testing tile add to front
				
				meldTempA.add(tile);
				meldTempB.add(0, tile);
				if (meldValidatorService.isValidMeld(meldTempA.getTiles())) {
					game.getBoard().addTileToMeldEnd(i, meldToAdd);
					hand.remove(tile);
					tilesPlayed++;
				} else if (meldValidatorService.isValidMeld(meldTempB.getTiles())) {
					game.getBoard().addTileToMeldBeginning(i, meldToAdd);
					hand.remove(tile);
					tilesPlayed++;
				}
			}
		}

		return tilesPlayed;
	}
	
	public int strategyFourPlayWithTableTiles(ArrayList<Tile> remainingTiles) {
	
		int tilesPlayed = 0;
		
		if (remainingTiles.get(0).getRank() == remainingTiles.get(1).getRank()) {
			//this means it's a set
		}
		else {
			//this means it's a run
			int oneRankLower = remainingTiles.get(0).getRank() - 1;
			int oneRankHigher = remainingTiles.get(1).getRank() + 1;
			char sameColour = remainingTiles.get(0).getColour();
			boolean boolOneRankLower = false;
			boolean boolOneRankHigher = false;
			
			for (Meld meld: game.getBoard().currentMelds) {
				for (Tile tile: meld.getTiles()) {
					if (tile.getRank() == oneRankLower) boolOneRankLower = true;
					if (tile.getRank() == oneRankHigher) boolOneRankHigher = true;
				}
			}
			
			if (boolOneRankHigher && boolOneRankLower) {
				//both sides of the run are already on the board
				for (int i = 0 ; i < game.getBoard().currentMelds.size(); i++) {
					ArrayList<Tile> tempMeld = new ArrayList<Tile>(game.getBoard().currentMelds.get(i).getTiles());
					tempMeld.add(0, remainingTiles.get(0));
					if (meldValidatorService.isValidMeld(tempMeld)) {
						Meld newMeld = new Meld();
						newMeld.add(remainingTiles.get(0));
						game.getBoard().addTileToMeldBeginning(i, newMeld);
					}
				}
			}
			
		}
		
		return tilesPlayed;
	}
	
	

}
