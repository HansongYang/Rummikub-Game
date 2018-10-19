package core;

import java.util.ArrayList;

public class AIStrategyTwo implements PlayerStrategy<AIPlayer>{

	public void executeStrategy(AIPlayer player) {
		
		if(player.game.getBoard().currentMelds.size() == 0) {
			//Board is empty, so draw tile
			Tile newTile = player.game.getDeck().drawTile();
			player.hand.add(newTile);
			return;
		}else if(!player.initial30Played){
			ArrayList<Meld> initial = player.getHand().getInitialTiles();
			
			if(initial.size() == 0) {//Can't make initial 30, draw tile
				Tile newTile = player.game.getDeck().drawTile();
				player.hand.add(newTile);
				return;
			}
			else {//Play initial 30
				player.initial30Played = true;
				for(int i = 0; i < initial.get(0).size(); i++) {
					player.hand.remove(initial.get(0).getTile(i));
				}
				player.playMeld(player.game.getBoard(), initial.get(0));
			}	
		}else {
			ArrayList<Meld> runsThenSets = player.meldRunsFirst();
			ArrayList<Meld> setsThenRuns = player.meldSetsFirst();
						
			if(tryToWin(player, runsThenSets)) {
				return;
			}else if(tryToWin(player, setsThenRuns)) {
				return;
			}else {//Draw tile
				Tile newTile = player.game.getDeck().drawTile();
				player.hand.add(newTile);
				return;
			}		
			
		}
		
	}
	
	//Make winning play if possible, using melds in hand and playing remaining tiles to melds on board
	public boolean tryToWin(AIPlayer player, ArrayList<Meld> meldsInHand) {
		
		
		if(player.totalTilesFromMelds(meldsInHand) == player.hand.size()) {
			//Player can empty hand with just melds in hand
			
			player.meldsInHand = meldsInHand;
			for(int i = 0; i < meldsInHand.size(); i++) {
				for(int j = 0; j < meldsInHand.get(i).size();j++) {
					player.hand.remove(meldsInHand.get(i).getTile(j));
				}
			}
			player.playMelds(player.game.getBoard(), player.meldsInHand);
			return true;
		}
		else {
			//Check if player can empty hand by playing remaining tiles to board
			ArrayList<Tile> remainingTiles = player.hand.getRemainingTiles(meldsInHand);
			
			//TODO check if remainingTiles can be played to board
		
			//If all tiles can be played, play them and return true
		}
		
		return false;
	}
}

