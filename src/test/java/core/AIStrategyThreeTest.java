package core;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;

public class AIStrategyThreeTest extends TestCase{

	public void testExecuteStrategy() {
		Game game = new Game();
		PlayerStrategy<? super AIPlayer> aiStrategyThree = new AIStrategyThree();
		AIPlayer aiPlayer = new AIPlayer("AI3", game, aiStrategyThree);
		
		aiPlayer.hand.add(new Tile('R',5));
		aiPlayer.hand.add(new Tile('R',6));
		aiPlayer.hand.add(new Tile('R',7));
		aiPlayer.hand.add(new Tile('B',1));
		aiPlayer.hand.add(new Tile('B',2));
		aiPlayer.hand.add(new Tile('B',3));
		
		//Board is empty so draw tile
		aiPlayer.strategy.executeStrategy(aiPlayer);		
		assertEquals(game.getBoard().currentMelds.size(), 0);
		aiPlayer.hand.remove(aiPlayer.hand.getTile(aiPlayer.hand.size()-1));
		
		Meld boardMeld = new Meld();
		boardMeld.add(new Tile('O',10));
		boardMeld.add(new Tile('O',11));
		boardMeld.add(new Tile('O',12));
		game.getBoard().addMeld(boardMeld);
		
		//Board is not empty, but can not make initial 30 meld	
		aiPlayer.strategy.executeStrategy(aiPlayer);		
		assertEquals(game.getBoard().currentMelds.size(), 1);
		aiPlayer.hand.remove(aiPlayer.hand.getTile(aiPlayer.hand.size()-1));
		
		aiPlayer.hand.add(new Tile('R',8));
		aiPlayer.hand.add(new Tile('R',9));
		
		//Board is not empty, play initial 30	
		aiPlayer.strategy.executeStrategy(aiPlayer);	
		assertEquals(game.getBoard().currentMelds.size(), 2);
		
		aiPlayer.hand.add(new Tile('O',13));
		
		//Play O13 to existing meld on board and play final meld from hand
		aiPlayer.strategy.executeStrategy(aiPlayer);		
		assertEquals(game.getBoard().currentMelds.size(), 3);
		assertEquals(aiPlayer.hand.size(),0);
		
	}
	
	public void testExecuteStrategy_multiplePlayers(){
		Game game = new Game();
		PlayerStrategy<? super AIPlayer> aiStrategyThree = new AIStrategyThree();
		PlayerStrategy<? super AIPlayer> aiStrategyTwo = new AIStrategyTwo();
		PlayerStrategy<? super AIPlayer> aiStrategyOne = new AIStrategyOne();
		PlayerStrategy<? super UserPlayer> userPlayerStrategy = new UserStrategy();
		
		AIPlayer aiPlayer3 = new AIPlayer("AI3", game, aiStrategyThree);
		AIPlayer aiPlayer2 = new AIPlayer("AI2", game, aiStrategyTwo);
		AIPlayer aiPlayer1 = new AIPlayer("AI1", game, aiStrategyOne);
		UserPlayer userPlayer = new UserPlayer("User",game, userPlayerStrategy);
		
		game.observers = new ArrayList<Observer>();
		game.playerHandCount = new HashMap<String, Integer>();
		game.aiPlayer1 = aiPlayer1;
		game.aiPlayer2 = aiPlayer2;
		game.aiPlayer3 = aiPlayer3;
		game.userPlayer = userPlayer;
		game.addObserver(aiPlayer1);
        game.addObserver(aiPlayer2);
        game.addObserver(aiPlayer3);
        game.addObserver(userPlayer);
		
		aiPlayer3.initial30Played = true;
		aiPlayer3.hand.add(new Tile('R',5));
		aiPlayer3.hand.add(new Tile('R',6));
		aiPlayer3.hand.add(new Tile('R',7));
		aiPlayer3.hand.add(new Tile('B',10));
		aiPlayer3.hand.add(new Tile('G',13));
		
		aiPlayer2.hand.add(new Tile('B',2));
		aiPlayer2.hand.add(new Tile('B',13));
		aiPlayer2.hand.add(new Tile('B',7));
		
		aiPlayer1.hand.add(new Tile('G',1));
		aiPlayer1.hand.add(new Tile('G',4));
		aiPlayer1.hand.add(new Tile('G',9));
		
		userPlayer.hand.add(new Tile('O',1));
		userPlayer.hand.add(new Tile('G',5));
		userPlayer.hand.add(new Tile('B',9));
		
		game.messageObservers();
		
		Meld boardMeld = new Meld();
		boardMeld.add(new Tile ('R',10));
		boardMeld.add(new Tile ('G',10));
		boardMeld.add(new Tile ('O',10));
		game.getBoard().addMeld(boardMeld);
		
		//No other players have 3 fewer, so only play tiles to existing meld on board
		aiPlayer3.strategy.executeStrategy(aiPlayer3);
		assertEquals(aiPlayer3.hand.size(),4);
		
		aiPlayer1.hand.getTiles().clear();
		aiPlayer2.hand.getTiles().clear();
		userPlayer.hand.getTiles().clear();
		
		game.messageObservers();
		
		//Other player(s) have 3 or less tiles than AI3, so play all tiles possible
		aiPlayer3.strategy.executeStrategy(aiPlayer3);
		assertEquals(aiPlayer3.hand.size(),1);
		
	}
	
}
