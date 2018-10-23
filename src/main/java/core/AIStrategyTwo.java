package core;

import java.util.ArrayList;

public class AIStrategyTwo implements PlayerStrategy<AIPlayer> {

	private MeldValidatorService meldValidatorService = new MeldValidatorService();

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
			int initialHandSize = player.hand.size();
			ArrayList<Meld> runsThenSets = player.meldRunsFirst();
			ArrayList<Meld> setsThenRuns = player.meldSetsFirst();
						
			tryToWin(player, runsThenSets);
			if(player.hand.size() == initialHandSize) {//Played 0 tiles
				tryToWin(player,setsThenRuns);
				if(player.hand.size() == initialHandSize) {//Played 0 tiles
					//Draw tile
					Tile newTile = player.game.getDeck().drawTile();
					player.hand.add(newTile);
					return;
				}
			}		
		}	
	}
	
	//Make winning play if possible, using melds in hand and playing remaining tiles to melds on board
	//If it can't make a winning play it plays tiles to existing melds on board if possible
	public void tryToWin(AIPlayer player, ArrayList<Meld> meldsInHand) {
		
		if(player.totalTilesFromMelds(meldsInHand) == player.hand.size()) {
			//Player can empty hand with just melds in hand
			
			player.meldsInHand = meldsInHand;
			for(int i = 0; i < meldsInHand.size(); i++) {
				for(int j = 0; j < meldsInHand.get(i).size();j++) {
					player.hand.remove(meldsInHand.get(i).getTile(j));
				}
			}
			player.playMelds(player.game.getBoard(), player.meldsInHand);
		}
		else {
			ArrayList<Tile> remainingTiles = player.hand.getRemainingTiles(meldsInHand);
						
			int nTilesPlayedToBoard = playWithTableTiles(player, remainingTiles);
			
			if(nTilesPlayedToBoard == remainingTiles.size()) {
				//All remaining tiles played, proceed to play melds from hand to win
				player.meldsInHand = meldsInHand;
				for(int i = 0; i < meldsInHand.size(); i++) {
					for(int j = 0; j < meldsInHand.get(i).size();j++) {
						player.hand.remove(meldsInHand.get(i).getTile(j));
					}
				}
				player.playMelds(player.game.getBoard(), player.meldsInHand);		
			}		
		}
	}

	public int playWithTableTiles(AIPlayer player, ArrayList<Tile> remainingTiles) {
		int tilesPlayed = 0;

		for (Tile tile : remainingTiles) {
			Meld meldToAdd = new Meld();
			meldToAdd.add(tile);

			for (int i = 0; i < player.game.getBoard().currentMelds.size(); i++) {
				Meld meldTempA = new Meld(player.game.getBoard().currentMelds.get(i).getTiles());  // Meld for testing tile add to back
				Meld meldTempB = new Meld(player.game.getBoard().currentMelds.get(i).getTiles());   // Meld for testing tile add to front
				
				meldTempA.add(tile);
				meldTempB.add(0, tile);
				if (meldValidatorService.isValidMeld(meldTempA.getTiles())) {
					player.game.getBoard().addTileToMeldEnd(i, meldToAdd);
					player.hand.remove(tile);
					tilesPlayed++;
				} else if (meldValidatorService.isValidMeld(meldTempB.getTiles())) {
					player.game.getBoard().addTileToMeldBeginning(i, meldToAdd);
					player.hand.remove(tile);
					tilesPlayed++;
				}
			}
		}

		return tilesPlayed;
	}

	public void makeMeldFromBoardTile() {
		// look through remaining tiles
		// look through boards tiles
		// pick a board tile
		// can it make a set or run with the addition of the tile
		// if so remove tile from current meld and add as new meld with addition of other tiles from hand
		// continue loop if not
	}
}

