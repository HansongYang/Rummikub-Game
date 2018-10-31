package core;

import junit.framework.TestCase;

public class AIStrategyOneTest extends TestCase {

	public void testExecuteStrategy() {
		
		//Play all melds
		Game game = new Game();
		AIPlayer player = new AIPlayer("AI", game, new AIStrategyOne());
		
		player.hand.add(new Tile('R',11));
		player.hand.add(new Tile('R',12));
		player.hand.add(new Tile('R',13));
		
		player.strategy.executeStrategy(player);
		
		//Play one meld on first turn
		assertEquals(1,game.getBoard().currentMelds.size());
		
		
		Game game2 = new Game();
		AIPlayer player2 = new AIPlayer("AI", game2, new AIStrategyOne());
			
		player2.hand.add(new Tile('R',11));
		player2.hand.add(new Tile('R',12));
		player2.hand.add(new Tile('R',13));
		player2.hand.add(new Tile('R',7));
		player2.hand.add(new Tile('B',7));
		player2.hand.add(new Tile('G',7));
		
		player2.strategy.executeStrategy(player2);
		
		//Play multiple melds on first turn
		assertEquals(2,game2.getBoard().currentMelds.size());
		
		
		player.hand.add(new Tile('R',1));
		player.hand.add(new Tile('R',2));
		player.hand.add(new Tile('R',3));
		player.strategy.executeStrategy(player);
		
		//Play one meld on subsequent turn
		assertEquals(2,game.getBoard().currentMelds.size());
		
		player2.hand.add(new Tile('R',1));
		player2.hand.add(new Tile('R',2));
		player2.hand.add(new Tile('R',3));
		player2.hand.add(new Tile('R',9));
		player2.hand.add(new Tile('B',9));
		player2.hand.add(new Tile('G',9));	
		player2.strategy.executeStrategy(player2);
		
		//Play multiple melds on subsequent turn
		assertEquals(4,game2.getBoard().currentMelds.size());
		
		
		Game game3 = new Game();
		AIPlayer player3 = new AIPlayer("AI", game3, new AIStrategyOne());	
		player3.strategy.executeStrategy(player3);
		
		//Draw tile on first turn
		assertEquals(0,game3.getBoard().currentMelds.size());
		assertEquals(1,player3.hand.size());
		
		player2.strategy.executeStrategy(player2);
		
		//Draw tile on subsequent turn
		assertEquals(4,game2.getBoard().currentMelds.size());
		assertEquals(1,player2.hand.size());
	}
}
