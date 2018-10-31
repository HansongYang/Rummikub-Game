package core;

import java.util.ArrayList;

public class AIStrategyTwo implements PlayerStrategy<AIPlayer> {

	private MeldValidatorService meldValidatorService = new MeldValidatorService();

	public void executeStrategy(AIPlayer player) {
		
		if(player.game.getBoard().currentMelds.size() == 0) {
			//Board is empty, so draw tile
			Tile newTile = player.game.getDeck().drawTile();
			System.out.println(player.name + " drew: " + newTile.getColour() + ", " + newTile.getRank());
			player.hand.add(newTile);
			return;
		}else if(!player.initial30Played){
			ArrayList<Meld> initial = player.getHand().getInitialTiles();
			
			if(initial.size() == 0) {//Can't make initial 30, draw tile
				Tile newTile = player.game.getDeck().drawTile();
				System.out.println(player.name + " drew: " + newTile.getColour() + ", " + newTile.getRank());
				player.hand.add(newTile);
				return;
			}
			else {//Play initial 30
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
					if(player.game.getDeck().getDeckSize() == 0) {
						return;
					}
					//Draw tile
					Tile newTile = player.game.getDeck().drawTile();
					System.out.println(player.name + " drew: " + newTile.getColour() + ", " + newTile.getRank());
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
						
			int nTilesPlayedToBoard = player.playWithTableTiles(remainingTiles);
			
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

}

