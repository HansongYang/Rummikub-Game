package core;

import junit.framework.TestCase;

public class AIStrategyTwoTest extends TestCase {

	public void testExecuteStrategy() {
		Game game = new Game();
		PlayerStrategy<? super AIPlayer> aiStrategyTwo = new AIStrategyTwo();
		AIPlayer aiPlayer = new AIPlayer("AI2", game, aiStrategyTwo);
		
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
	
	
    public void testPlayWithTableTiles() {
        Game game = new Game();
        PlayerStrategy<? super AIPlayer> aiStrategyTwo = new AIStrategyTwo();
        AIPlayer aiPlayer = new AIPlayer("AI2", game, aiStrategyTwo);

        Meld meld = new Meld();

    }
}
