package core;

import junit.framework.TestCase;

public class AiStrategyFourTest extends TestCase {
	
	public void testInitial() {
		Meld meld = new Meld();
		meld.add(B5);
		meld.add(G5);
		meld.add(O5);
		game.getBoard().addMeld(meld);
		aiStrategyFour.getBoardTileColours();
		
		assertEquals(1, aiStrategyFour.blue);
		
	}
	
	Game game = new Game();
	PlayerStrategy<? super AIPlayer> aiStrategyFour = new AIStrategyFour();
	AIPlayer aiPlayer4 = new AIPlayer("AI4", game, aiStrategyFour);
	
	Tile B5 = new Tile('B',5);
	Tile G5 = new Tile('G',5);
	Tile O5 = new Tile('O',5);

}
