package core;

import java.util.ArrayList;

public class AIStrategyOne implements PlayerStrategy<AIPlayer>{

	public void executeStrategy(AIPlayer player) {
		
		if(!player.initial30Played) {
			ArrayList<Meld> initial = player.hand.getInitialTiles();
			if(initial.size() == 0) {
				Tile newTile = player.game.getDeck().drawTile();
				player.hand.add(newTile);
			}
			else {
				player.initial30Played = true;
				for(int i = 0; i < initial.get(0).size(); i++) {
					player.hand.remove(initial.get(0).getTile(i));
				}
				player.playMeld(player.game.getBoard(), initial.get(0));
			}			
		}
		else {
			ArrayList<Meld> noDuplication = new ArrayList<Meld>();;
			ArrayList<Meld> runsThenSets = player.meldRunsFirst();
			ArrayList<Meld> setsThenRuns = player.meldSetsFirst();
				
			player.meldsInHand = findBestPlay(runsThenSets, setsThenRuns);
		
			if(player.meldsInHand != null && player.meldsInHand.size() > 0) {
				while(player.meldsInHand.size() != 0) {
					noDuplication.add(player.meldsInHand.get(0));
				    //Discard used tiles from hand
					for(int i = 0; i < player.meldsInHand.get(0).size(); i++) {					
						player.hand.remove(player.meldsInHand.get(0).getTile(i));
					}
					runsThenSets = player.meldRunsFirst();
					setsThenRuns = player.meldSetsFirst();
					player.meldsInHand = findBestPlay(runsThenSets, setsThenRuns);
				}
				player.playMelds(player.game.getBoard(), noDuplication);
			}
			else {
				//Draw tile
				Tile newTile = player.game.getDeck().drawTile();
				player.hand.add(newTile);
			}
		}
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
		
		if(totalTiles1 > totalTiles2) {
			return melds1;
		}
		else {
			return melds2;
		}
	}
}
