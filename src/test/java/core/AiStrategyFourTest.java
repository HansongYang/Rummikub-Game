package core;

import java.util.ArrayList;
import java.util.Collections;

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
	
	public void testFindSetsOfTwo() {
		aiPlayer4.initial30Played = true;
		aiPlayer4.strategy.executeStrategy(aiPlayer4);
		aiPlayer4.hand.add(B5);
		aiPlayer4.hand.add(G5);
		
		testArrayList = new ArrayList<Tile>();
		testArrayLists = new ArrayList<ArrayList<Tile>>();
		testArrayList.add(B5);
		testArrayList.add(G5);
		testArrayLists.add(testArrayList);
		assertEquals(testArrayLists, aiPlayer4.hand.getSetsOfTwo());
	}
	
	public void testSetsOfTwo() {
		aiPlayer4.initial30Played = true;
		aiPlayer4.strategy.executeStrategy(aiPlayer4);
		
		aiPlayer4.hand.add(B5);
		aiPlayer4.hand.add(G5);
		aiPlayer4.hand.add(O5);
		aiPlayer4.hand.add(G4);
		
		testArrayList = new ArrayList<Tile>();
		testArrayLists = new ArrayList<ArrayList<Tile>>();
		
		assertEquals(testArrayLists, aiPlayer4.hand.getSetsOfTwo());
	}
	
	public void testSort() {
		
		aiPlayer4.hand.add(O5);
		aiPlayer4.hand.add(G4);
		aiPlayer4.hand.add(G5);
		aiPlayer4.hand.add(B5);
		
		ArrayList<Tile> test = new ArrayList<Tile>();
		test.add(O5);
		test.add(G4);
		test.add(G5);
		test.add(B5);
		
		Collections.sort(test);
		System.out.println(test);
		
		
		Collections.sort(aiPlayer4.hand.getTiles());
		System.out.println(aiPlayer4.hand.getTiles());
		//System.out.println(aiPlayer4.hand.getSetsOfTwo());
		
	}
	
	public void testRunsOfTwo() {
		aiPlayer4.initial30Played = true;
		aiPlayer4.strategy.executeStrategy(aiPlayer4);
		
		aiPlayer4.hand.add(G2);
		aiPlayer4.hand.add(G3);
		
		testArrayList = new ArrayList<Tile>();
		testArrayLists = new ArrayList<ArrayList<Tile>>();
		testArrayList.add(G2);
		testArrayList.add(G3);
		testArrayLists.add(testArrayList);
		
		assertEquals(testArrayLists, aiPlayer4.hand.getRunsOfTwo());
	}
	
	Game game = new Game();
	PlayerStrategy<? super AIPlayer> aiStrategyFour = new AIStrategyFour();
	AIPlayer aiPlayer4 = new AIPlayer("AI4", game, aiStrategyFour);
	ArrayList<Tile> testArrayList;
	ArrayList<ArrayList<Tile>> testArrayLists;
	
	Tile B5 = new Tile('B',5);
	Tile G5 = new Tile('G',5);
	Tile O5 = new Tile('O',5);
	Tile G2 = new Tile('G',2);
	Tile G3 = new Tile('G',3);
	Tile G4 = new Tile('G',4);

}
