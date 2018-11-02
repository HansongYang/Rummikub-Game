package core;

import core.Game.GameStates;
import junit.framework.TestCase;

public class FullGameTest extends TestCase{

	
	public void testGame() {
		
		Game game = new Game();
		game.createGamePlayers();
		
		
		createUserPlayerHand(game);//Custom deal
		//game.getDeck().dealTiles(game.userPlayer);//Random deal
		
		createAI1Hand(game);//Custom deal
		//game.getDeck().dealTiles(game.aiPlayer1);//Random deal
		
		createAI2Hand(game);//Custom deal
		//game.getDeck().dealTiles(game.aiPlayer2);//Random deal
		
		createAI3Hand(game);//Custom deal
		//game.getDeck().dealTiles(game.aiPlayer3);//Random deal
		
		
		game.gameState = GameStates.PLAY;
		game.gameLoop();
	}
	
	public void createUserPlayerHand(Game game){		
		game.userPlayer.hand.add(new Tile('R',1));
		game.userPlayer.hand.add(new Tile('R',2));
		game.userPlayer.hand.add(new Tile('R',3));
		game.userPlayer.hand.add(new Tile('R',4));
		game.userPlayer.hand.add(new Tile('R',5));
		game.userPlayer.hand.add(new Tile('R',6));
		game.userPlayer.hand.add(new Tile('R',7));
		game.userPlayer.hand.add(new Tile('R',8));
		game.userPlayer.hand.add(new Tile('R',9));
		game.userPlayer.hand.add(new Tile('R',10));
		game.userPlayer.hand.add(new Tile('R',11));
		game.userPlayer.hand.add(new Tile('R',12));
		game.userPlayer.hand.add(new Tile('R',13));
		game.userPlayer.hand.add(new Tile('R',1));
		
		for(Tile tile : game.userPlayer.hand.getTiles()) {
			game.getDeck().removeTile(tile);
		}
	}
	
	public void createAI1Hand(Game game){
		game.aiPlayer1.hand.add(new Tile('B',1));
		game.aiPlayer1.hand.add(new Tile('B',2));
		game.aiPlayer1.hand.add(new Tile('B',3));
		game.aiPlayer1.hand.add(new Tile('B',4));
		game.aiPlayer1.hand.add(new Tile('B',5));
		game.aiPlayer1.hand.add(new Tile('B',6));
		game.aiPlayer1.hand.add(new Tile('B',7));
		game.aiPlayer1.hand.add(new Tile('B',8));
		game.aiPlayer1.hand.add(new Tile('B',9));
		game.aiPlayer1.hand.add(new Tile('B',10));
		game.aiPlayer1.hand.add(new Tile('B',11));
		game.aiPlayer1.hand.add(new Tile('B',12));
		game.aiPlayer1.hand.add(new Tile('B',13));
		game.aiPlayer1.hand.add(new Tile('B',1));
		
		for(Tile tile : game.aiPlayer1.hand.getTiles()) {
			game.getDeck().removeTile(tile);
		}
	}
	
	public void createAI2Hand(Game game){
		game.aiPlayer2.hand.add(new Tile('G',1));
		game.aiPlayer2.hand.add(new Tile('G',2));
		game.aiPlayer2.hand.add(new Tile('G',3));
		game.aiPlayer2.hand.add(new Tile('G',4));
		game.aiPlayer2.hand.add(new Tile('G',5));
		game.aiPlayer2.hand.add(new Tile('G',6));
		game.aiPlayer2.hand.add(new Tile('G',7));
		game.aiPlayer2.hand.add(new Tile('G',8));
		game.aiPlayer2.hand.add(new Tile('G',9));
		game.aiPlayer2.hand.add(new Tile('G',10));
		game.aiPlayer2.hand.add(new Tile('G',11));
		game.aiPlayer2.hand.add(new Tile('G',12));
		game.aiPlayer2.hand.add(new Tile('G',13));
		game.aiPlayer2.hand.add(new Tile('G',1));
		
		for(Tile tile : game.aiPlayer2.hand.getTiles()) {
			game.getDeck().removeTile(tile);
		}
	}
	
	public void createAI3Hand(Game game){
		game.aiPlayer3.hand.add(new Tile('O',1));
		game.aiPlayer3.hand.add(new Tile('O',2));
		game.aiPlayer3.hand.add(new Tile('O',3));
		game.aiPlayer3.hand.add(new Tile('O',4));
		game.aiPlayer3.hand.add(new Tile('O',5));
		game.aiPlayer3.hand.add(new Tile('O',6));
		game.aiPlayer3.hand.add(new Tile('O',7));
		game.aiPlayer3.hand.add(new Tile('O',8));
		game.aiPlayer3.hand.add(new Tile('O',9));
		game.aiPlayer3.hand.add(new Tile('O',10));
		game.aiPlayer3.hand.add(new Tile('O',11));
		game.aiPlayer3.hand.add(new Tile('O',12));
		game.aiPlayer3.hand.add(new Tile('O',13));
		game.aiPlayer3.hand.add(new Tile('O',1));
		
		for(Tile tile : game.aiPlayer3.hand.getTiles()) {
			game.getDeck().removeTile(tile);
		}
	}
	
}
