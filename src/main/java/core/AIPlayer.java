package core;

import java.util.ArrayList;

public class AIPlayer extends Player{
	
	public int tilesUsedOnTurn = 0;
	
	public AIPlayer() {
		super();
	}
	
	public AIPlayer(String name, Game game) {
		super(name, game);
	}
	
	public void playTurn() {
		
		ArrayList<Meld> runsThenSets = meldRunsFirst();
		ArrayList<Meld> setsThenRuns = meldSetsFirst();
		
		meldsInHand = findBestPlay(runsThenSets, setsThenRuns);
		
		if(meldsInHand != null && meldsInHand.size() > 0) {
			
			//Discard used tiles from hand
			for(int i = 0; i < meldsInHand.size(); i++) {
				for(int j = 0; j < meldsInHand.get(i).size(); i++) {					
					hand.remove(meldsInHand.get(i).getTile(j));
				}
			}			
			
			playMelds(game.getBoard(), meldsInHand);
		}
		else {
			//Draw tile
			Tile newTile = game.getDeck().drawTile();
			hand.add(newTile);
		}
		
	}
	
	//Find all possible melds with runs as priority 
	public ArrayList<Meld> meldRunsFirst() {
		ArrayList<Meld> runMelds =  hand.getMeldRuns();
		ArrayList<Meld> setMelds;
		Hand newHand = hand;
		
		//Remove used tiles 
		for(int i = 0; i < runMelds.size(); i++) {
			for(int j = 0; j < runMelds.get(i).size(); j++) {				
				newHand.remove(runMelds.get(i).getTile(j));	
			}
		}
		
		setMelds = newHand.getMeldSets();
		
		runMelds.addAll(setMelds);
		
		return runMelds;
	}
	
	//Find all possible melds with sets as priority 
	public ArrayList<Meld> meldSetsFirst() {
		ArrayList<Meld> runMelds; 
		ArrayList<Meld> setMelds =  hand.getMeldSets();
		Hand newHand = hand;
		
		//Remove used tiles 
		for(int i = 0; i < setMelds.size(); i++) {
			for(int j = 0; j < setMelds.get(i).size(); j++) {				
				newHand.remove(setMelds.get(i).getTile(j));	
			}
		}
		
		runMelds = newHand.getMeldRuns();
		
		setMelds.addAll(runMelds);
		
		return setMelds;
	}
	
	//Determine the best set of melds to play based on strategy 1
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
		
		//Determine which set of melds is the best to use
		if(!initial30Played) {
			
			if(totalAllMelds(melds1) >= 30 &&
					totalAllMelds(melds2) >= 30) {
				
				initial30Played = true;
				
				if(totalTiles1 > totalTiles2) {
					return melds1;
				}
				else {
					return melds2;
				}
			}
			else if(totalAllMelds(melds1) >= 30) {
				initial30Played = true;
				return melds1;
			}
			else if(totalAllMelds(melds2) >= 30){
				initial30Played = true;
				return melds2;
			}
			else {
				//Cannot play, draw tile
				return null;
			}
			
		}
		else {
			if(totalTiles1 > totalTiles2) {
				return melds1;
			}
			else {
				return melds2;
			}
		}
		
	}
	
	
}
