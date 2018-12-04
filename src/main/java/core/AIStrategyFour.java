package core;

import java.util.ArrayList;

public class AIStrategyFour implements PlayerStrategy<AIPlayer> {
	
	private MeldValidatorService meldValidatorService = new MeldValidatorService();

	public void executeStrategy(AIPlayer player) {
		
		// If Strategy 4 hasn't played initial 30 points, try to play initial 30
		if(!player.initial30Played) {
			ArrayList<Meld> initial = player.hand.getInitialTiles();
			
			// If Strategy4 is unable to play 30 points, draw a card
			// If Strategy 4 is able to play 30, play the 30
			if(initial.size() == 0) {
				Tile newTile = player.model.getDeck().drawTile();
				System.out.println(player.name + " drew: " + newTile.getColour() + ", " + newTile.getRank());
				player.hand.add(newTile);
			}
			else {
				player.initial30Played = true;
				player.playMelds(player.model.getBoard(), initial);
			}		
		}
		
		else {
			int initialHandSize = player.hand.size();
			ArrayList<Meld> runsThenSets = player.meldRunsFirst();
			ArrayList<Meld> setsThenRuns = player.meldSetsFirst();
			tryToWin(player, runsThenSets);
			if(player.hand.size() == initialHandSize) {//Played 0 tiles
				tryToWin(player,setsThenRuns);
				if (player.hand.size() == initialHandSize) { //Still played 0 tiles
					ArrayList<Meld> meldsToPlay = player.findBestPlay(runsThenSets,setsThenRuns);
					ArrayList<Tile> remainingTiles = player.hand.getRemainingTiles(meldsToPlay);
					
					if(!otherPlayerHas3FewerTiles(player)) {
						
						System.out.println("Strategy4 can do its strategy with remaining tiles!");
						System.out.println("HAND: " + player.hand.getTiles());
						
						int tilesPlayed = 0;
						
						ArrayList<ArrayList<Tile>> setsOfTwo = player.hand.getSetsOfTwo(remainingTiles);
						ArrayList<ArrayList<Tile>> runsOfTwo = player.hand.getRunsOfTwo(remainingTiles);
						
						// Try to do the strategy with board tile probabilities, if you can't just do playWithTableTiles() like in Strategy3
						if (!setsOfTwo.isEmpty()) { 
							int n = 0;
							while (tilesPlayed == 0) {
								if (setsOfTwo.get(n) == null) break;
								tilesPlayed = player.strategyFourPlayWithTableTiles(setsOfTwo.get(n));
								n++;
							}
						} else if (!runsOfTwo.isEmpty()) {
							int n = 0;
							while (tilesPlayed == 0) {
								if (runsOfTwo.get(n) == null) break;
								tilesPlayed = player.strategyFourPlayWithTableTiles(runsOfTwo.get(n));
							}
						}
						else {
							tilesPlayed = player.playWithTableTiles(remainingTiles);
						}
						
						if(tilesPlayed == 0) {
							if(player.model.getDeck().getDeckSize() == 0) {
								return;
							}
							Tile newTile = player.model.getDeck().drawTile();
							System.out.println(player.name + " drew: " + newTile.getColour() + ", " + newTile.getRank());
							player.hand.add(newTile);
						}
					}
					
					// This means Strategy4 can do its strategy
					else {
						System.out.println("Player has 3 tiles fewer than S4");
						player.meldsInHand = meldsToPlay;
						for(int i = 0; i < meldsToPlay.size(); i++) {
							for(int j = 0; j < meldsToPlay.get(i).size();j++) {
								player.hand.remove(meldsToPlay.get(i).getTile(j));
							}
						}
						player.playMelds(player.model.getBoard(), player.meldsInHand);
					}
				}

			}
		}
		
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
	
	public void tryToWin(AIPlayer player, ArrayList<Meld> meldsInHand) {
		
		if(player.totalTilesFromMelds(meldsInHand) == player.hand.size()) {
			//Player can empty hand with just melds in hand
			
			player.meldsInHand = meldsInHand;
			for(int i = 0; i < meldsInHand.size(); i++) {
				for(int j = 0; j < meldsInHand.get(i).size();j++) {
					player.hand.remove(meldsInHand.get(i).getTile(j));
				}
			}
			player.playMelds(player.model.getBoard(), player.meldsInHand);
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
				player.playMelds(player.model.getBoard(), player.meldsInHand);	
			}				
		}
	}
	
	public boolean canPlayAllTilesToBoard(AIPlayer player, ArrayList<Tile> remainingTiles) {
		int playableTiles = 0;
	
		for (Tile tile : remainingTiles) {
			Meld meldToAdd = new Meld();
			meldToAdd.add(tile);
	
			for (int i = 0; i < player.model.getBoard().currentMelds.size(); i++) {
				Meld meldTempA = new Meld(player.model.getBoard().currentMelds.get(i).getTiles());  // Meld for testing tile add to back
				Meld meldTempB = new Meld(player.model.getBoard().currentMelds.get(i).getTiles());   // Meld for testing tile add to front
				
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
}
