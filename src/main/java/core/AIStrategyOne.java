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
				player.playMelds(player.game.getBoard(), initial);
			}			
		}
		else {
			int initialHandSize = player.hand.size();
			ArrayList<Meld> noDuplication = new ArrayList<Meld>();;
			ArrayList<Meld> runsThenSets = player.meldRunsFirst();
			ArrayList<Meld> setsThenRuns = player.meldSetsFirst();
				
			player.meldsInHand = player.findBestPlay(runsThenSets, setsThenRuns);
			
			ArrayList<Tile> remainingTiles = player.hand.getRemainingTiles(player.meldsInHand);
			player.playWithTableTiles(remainingTiles);
		
			if(player.meldsInHand != null && player.meldsInHand.size() > 0) {
				while(player.meldsInHand.size() != 0) {
					noDuplication.add(player.meldsInHand.get(0));
				    //Discard used tiles from hand
					for(int i = 0; i < player.meldsInHand.get(0).size(); i++) {					
						player.hand.remove(player.meldsInHand.get(0).getTile(i));
					}
					runsThenSets = player.meldRunsFirst();
					setsThenRuns = player.meldSetsFirst();
					player.meldsInHand = player.findBestPlay(runsThenSets, setsThenRuns);
				}
				player.playMelds(player.game.getBoard(), noDuplication);
			}
			else if(player.hand.size() == initialHandSize){//No tiles played to board
				if(player.game.getDeck().getDeckSize() == 0) {
					return;
				}
				//Draw tile
				Tile newTile = player.game.getDeck().drawTile();
				player.hand.add(newTile);
			}
		}
	}
	
	
}
