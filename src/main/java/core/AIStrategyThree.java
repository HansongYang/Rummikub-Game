package core;

import java.util.ArrayList;

public class AIStrategyThree implements PlayerStrategy<AIPlayer>{
	
	private MeldValidatorService meldValidatorService = new MeldValidatorService();

	public void executeStrategy(AIPlayer player) {
		if(!player.initial30Played) {
			ArrayList<Meld> initial = player.hand.getInitialTiles();
			if(initial.size() == 0) {
				Tile newTile = player.game.getDeck().drawTile();
				System.out.println(player.name + " drew: " + newTile.getColour() + ", " + newTile.getRank());
				player.hand.add(newTile);
			}
			else {
				player.initial30Played = true;
				player.playMelds(player.game.getBoard(), initial);
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
					
					ArrayList<Meld> meldsToPlay = player.findBestPlay(runsThenSets,setsThenRuns);
					ArrayList<Tile> remainingTiles = player.hand.getRemainingTiles(meldsToPlay);	
					
					if(!otherPlayerHas3FewerTiles(player)) {
						
						int tilesPlayed = player.playWithTableTiles(remainingTiles);
						if(tilesPlayed == 0) {
							if(player.game.getDeck().getDeckSize() == 0) {
								return;
							}
							Tile newTile = player.game.getDeck().drawTile();
							System.out.println(player.name + " drew: " + newTile.getColour() + ", " + newTile.getRank());
							player.hand.add(newTile);
						}
					}else {//Play most tiles possible
						
						int tilesPlayed = player.playWithTableTiles(remainingTiles);
						
						if(tilesPlayed == 0 && meldsToPlay.size() == 0) {//0 Tiles can be played. Draw tile
							if(player.game.getDeck().getDeckSize() == 0) {
								return;
							}
							System.out.println(player.name + " could play but has no tile to play");
							Tile newTile = player.game.getDeck().drawTile();
							System.out.println(player.name + " drew: " + newTile.getColour() + ", " + newTile.getRank());
							player.hand.add(newTile);

						}else {//Play all melds
						
							player.meldsInHand = meldsToPlay;
							for(int i = 0; i < meldsToPlay.size(); i++) {
								for(int j = 0; j < meldsToPlay.get(i).size();j++) {
									player.hand.remove(meldsToPlay.get(i).getTile(j));
								}
							}
							player.playMelds(player.game.getBoard(), player.meldsInHand);
							
						}
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
				
				player.playWithTableTiles(remainingTiles);
							
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
		int playableTiles = 0;
	
		for (Tile tile : remainingTiles) {
			Meld meldToAdd = new Meld();
			meldToAdd.add(tile);
	
			for (int i = 0; i < player.game.getBoard().currentMelds.size(); i++) {
				Meld meldTempA = new Meld(player.game.getBoard().currentMelds.get(i).getTiles());  // Meld for testing tile add to back
				Meld meldTempB = new Meld(player.game.getBoard().currentMelds.get(i).getTiles());   // Meld for testing tile add to front
				
				meldTempA.add(tile);
				meldTempB.add(0, tile);
				if (meldValidatorService.isValidMeld(meldTempA.getTiles())) {
					playableTiles++;
				} else if (meldValidatorService.isValidMeld(meldTempB.getTiles())) {
					playableTiles++;
				}
			}
		}
	
		return playableTiles == remainingTiles.size();
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
	
}

