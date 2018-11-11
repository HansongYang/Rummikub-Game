package core;

import junit.framework.TestCase;

public class AiStrategyFourTest extends TestCase {
	
	public void testInitial() {
		Meld meld = new Meld();
		meld.add(B5);
		meld.add(G5);
		meld.add(O5);
		game.getBoard().addMeld(meld);
		
		aiPlayer4.initial30Played = true;
		aiPlayer4.strategy.executeStrategy(aiPlayer4);
		
		System.out.println("RED " + aiPlayer4.red);
		System.out.println("ORANGE " + aiPlayer4.orange);
		System.out.println("GREEN " + aiPlayer4.green);
		System.out.println("BLUE " + aiPlayer4.blue);
		assertEquals(1, aiPlayer4.blue);
		
	}
	
	Game game = new Game();
	PlayerStrategy<? super AIPlayer> aiStrategyFour = new AIStrategyFour();
	AIPlayer aiPlayer4 = new AIPlayer("AI4", game, aiStrategyFour);
	
	Tile B5 = new Tile('B',5);
	Tile G5 = new Tile('G',5);
	Tile O5 = new Tile('O',5);

}
