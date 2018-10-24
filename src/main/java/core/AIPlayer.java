package core;

import java.util.ArrayList;
import java.util.HashMap;

public class AIPlayer extends Player implements Observer {
	
	public int tilesUsedOnTurn = 0;
	public HashMap<String, Integer> playerHandCount;
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

	public void update(HashMap<String, Integer> playerData) {
		this.playerHandCount = playerData;
	}
}
