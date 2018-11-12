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
				Tile newTile = player.game.getDeck().drawTile();
				System.out.println(player.name + " drew: " + newTile.getColour() + ", " + newTile.getRank());
				player.hand.add(newTile);
			}
			else {
				player.initial30Played = true;
				player.playMelds(player.game.getBoard(), initial);
			}		
		}
		
		else {
			
			this.getBoardTileColours(player);
		}
		
	}
	
	public void getBoardTileColours(AIPlayer player) {
		
		for (Meld meld: player.game.getBoard().currentMelds) {
			for (Tile tile: meld.getTiles()) {
				if (tile.getColour() == 'R') player.red++;
				else if (tile.getColour() == 'B') player.blue++;
				else if (tile.getColour() == 'G') player.green++;
				else if (tile.getColour() == 'O') player.orange++;
			}
		}
	}

}
