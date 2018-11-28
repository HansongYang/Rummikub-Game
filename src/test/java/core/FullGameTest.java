package core;


import core.Model.GameStates;
import junit.framework.TestCase;

public class FullGameTest extends TestCase{

	
	public void testGame() {
		
		Game game = new Game();
		game.model.createGamePlayers();
		
		
		createUserPlayerHand(game.model);//Custom deal
		//game.getDeck().dealTiles(game.userPlayer);//Random deal
		
		createAI1Hand(game.model);//Custom deal
		//game.getDeck().dealTiles(game.aiPlayer1);//Random deal
		
		createAI2Hand(game.model);//Custom deal
		//game.getDeck().dealTiles(game.aiPlayer2);//Random deal
		
		createAI3Hand(game.model);//Custom deal
		//game.getDeck().dealTiles(game.aiPlayer3);//Random deal
		
		
		game.model.gameState = GameStates.PLAY;
		//game.gameLoop();
	}
	
	public void createUserPlayerHand(Model game){		
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
	
	public void createAI1Hand(Model game){
		game.player2.hand.add(new Tile('B',1));
		game.player2.hand.add(new Tile('B',2));
		game.player2.hand.add(new Tile('B',3));
		game.player2.hand.add(new Tile('B',4));
		game.player2.hand.add(new Tile('B',5));
		game.player2.hand.add(new Tile('B',6));
		game.player2.hand.add(new Tile('B',7));
		game.player2.hand.add(new Tile('B',8));
		game.player2.hand.add(new Tile('B',9));
		game.player2.hand.add(new Tile('B',10));
		game.player2.hand.add(new Tile('B',11));
		game.player2.hand.add(new Tile('B',12));
		game.player2.hand.add(new Tile('B',13));
		game.player2.hand.add(new Tile('B',1));
		
		for(Tile tile : game.player2.hand.getTiles()) {
			game.getDeck().removeTile(tile);
		}
	}
	
	public void createAI2Hand(Model game){
		game.player3.hand.add(new Tile('G',1));
		game.player3.hand.add(new Tile('G',2));
		game.player3.hand.add(new Tile('G',3));
		game.player3.hand.add(new Tile('G',4));
		game.player3.hand.add(new Tile('G',5));
		game.player3.hand.add(new Tile('G',6));
		game.player3.hand.add(new Tile('G',7));
		game.player3.hand.add(new Tile('G',8));
		game.player3.hand.add(new Tile('G',9));
		game.player3.hand.add(new Tile('G',10));
		game.player3.hand.add(new Tile('G',11));
		game.player3.hand.add(new Tile('G',12));
		game.player3.hand.add(new Tile('G',13));
		game.player3.hand.add(new Tile('G',1));
		
		for(Tile tile : game.player3.hand.getTiles()) {
			game.getDeck().removeTile(tile);
		}
	}
	
	public void createAI3Hand(Model game){
		game.player4.hand.add(new Tile('O',1));
		game.player4.hand.add(new Tile('O',2));
		game.player4.hand.add(new Tile('O',3));
		game.player4.hand.add(new Tile('O',4));
		game.player4.hand.add(new Tile('O',5));
		game.player4.hand.add(new Tile('O',6));
		game.player4.hand.add(new Tile('O',7));
		game.player4.hand.add(new Tile('O',8));
		game.player4.hand.add(new Tile('O',9));
		game.player4.hand.add(new Tile('O',10));
		game.player4.hand.add(new Tile('O',11));
		game.player4.hand.add(new Tile('O',12));
		game.player4.hand.add(new Tile('O',13));
		game.player4.hand.add(new Tile('O',1));
		
		for(Tile tile : game.player4.hand.getTiles()) {
			game.getDeck().removeTile(tile);
		}
	}
	
}
