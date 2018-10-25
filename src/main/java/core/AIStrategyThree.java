package core;

import java.util.ArrayList;

public class AIStrategyThree implements PlayerStrategy<AIPlayer>{
	
	private MeldValidatorService meldValidatorService = new MeldValidatorService();

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
		}else {
			int initialHandSize = player.hand.size();
			ArrayList<Meld> runsThenSets = player.meldRunsFirst();
			ArrayList<Meld> setsThenRuns = player.meldSetsFirst();
			
			tryToWin(player, runsThenSets);
			if(player.hand.size() == initialHandSize) {//Played 0 tiles
				tryToWin(player,setsThenRuns);
				if(player.hand.size() == initialHandSize) {//Played 0 tiles
					//Can't win this turn
					
					ArrayList<Meld> meldsToPlay = findBestPlay(runsThenSets,setsThenRuns);
					ArrayList<Tile> remainingTiles = player.hand.getRemainingTiles(meldsToPlay);	
					
					if(!otherPlayerHas3FewerTiles(player)) {
						
						playWithTableTiles(player,remainingTiles);
						
					}else {//Play most tiles possible
						
						player.meldsInHand = meldsToPlay;
						for(int i = 0; i < meldsToPlay.size(); i++) {
							for(int j = 0; j < meldsToPlay.get(i).size();j++) {
								player.hand.remove(meldsToPlay.get(i).getTile(j));
							}
						}
						player.playMelds(player.game.getBoard(), player.meldsInHand);
						
						playWithTableTiles(player,remainingTiles);
					}				
				}
			}				
		}
	}
	
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
						
			if(canPlayAllTilesToBoard(player,remainingTiles)) {
				//All remaining tiles can be played, proceed to play all tiles from hand to win
				
				playWithTableTiles(player, remainingTiles);
							
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

	public boolean canPlayAllTilesToBoard(AIPlayer player, ArrayList<Tile> remainingTiles) {
		int playAbleTiles = 0;
	
		for (Tile tile : remainingTiles) {
			Meld meldToAdd = new Meld();
			meldToAdd.add(tile);
	
			for (int i = 0; i < player.game.getBoard().currentMelds.size(); i++) {
				Meld meldTempA = new Meld(player.game.getBoard().currentMelds.get(i).getTiles());  // Meld for testing tile add to back
				Meld meldTempB = new Meld(player.game.getBoard().currentMelds.get(i).getTiles());   // Meld for testing tile add to front
				
				meldTempA.add(tile);
				meldTempB.add(0, tile);
				if (meldValidatorService.isValidMeld(meldTempA.getTiles())) {
					playAbleTiles++;
				} else if (meldValidatorService.isValidMeld(meldTempB.getTiles())) {
					playAbleTiles++;
				}
			}
		}
	
		return playAbleTiles == remainingTiles.size();
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
	
	public boolean otherPlayerHas3FewerTiles(AIPlayer player) {
		boolean fewerTiles = false;
		
		for(int handSize : player.playerHandCount.values()) {
			
			if(player.hand.size() - handSize >= 3) {
				fewerTiles = true;
			}
			
		}
		
		return fewerTiles;
	}
	
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

