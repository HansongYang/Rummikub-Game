package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import junit.framework.TestCase;

public class AiStrategyFourTest extends TestCase {
	
	public void testInitial() {
		Meld meld = new Meld();
		meld.add(B5);
		meld.add(G5);
		meld.add(O5);
		game.getBoard().addMeld(meld);
		
		aiPlayer4.initial30Played = true;
		
		System.out.println("RED " + aiPlayer4.red);
		System.out.println("ORANGE " + aiPlayer4.orange);
		System.out.println("GREEN " + aiPlayer4.green);
		System.out.println("BLUE " + aiPlayer4.blue);
		//assertEquals(1, aiPlayer4.blue);
		
	}
	
	public void testFindSetsOfTwo() {
		aiPlayer4.initial30Played = true;
		aiPlayer4.hand.add(B5);
		aiPlayer4.hand.add(G5);
		
		testArrayList = new ArrayList<Tile>();
		testArrayLists = new ArrayList<ArrayList<Tile>>();
		testArrayList.add(B5);
		testArrayList.add(G5);
		testArrayLists.add(testArrayList);
		assertEquals(testArrayLists, aiPlayer4.hand.getSetsOfTwo(aiPlayer4.hand.getTiles()));
	}
	
	public void testSetsOfTwo() {
		aiPlayer4.initial30Played = true;
		
		aiPlayer4.hand.add(B5);
		aiPlayer4.hand.add(G5);
		aiPlayer4.hand.add(O5);
		aiPlayer4.hand.add(G4);
		
		testArrayList = new ArrayList<Tile>();
		testArrayLists = new ArrayList<ArrayList<Tile>>();
		
		assertEquals(testArrayLists, aiPlayer4.hand.getSetsOfTwo(aiPlayer4.hand.getTiles()));
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
		
		aiPlayer4.hand.add(G2);
		aiPlayer4.hand.add(G3);
		
		testArrayList = new ArrayList<Tile>();
		testArrayLists = new ArrayList<ArrayList<Tile>>();
		testArrayList.add(G2);
		testArrayList.add(G3);
		testArrayLists.add(testArrayList);
		
		assertEquals(testArrayLists, aiPlayer4.hand.getRunsOfTwo(aiPlayer4.hand.getTiles()));
	}
	
	public void testRunsOfTwo2() {
		aiPlayer4.initial30Played = true;
		
		aiPlayer4.hand.add(G2);
		aiPlayer4.hand.add(G3);
		aiPlayer4.hand.add(O5);
		aiPlayer4.hand.add(G4);
		aiPlayer4.hand.add(B4);
		aiPlayer4.hand.add(B5);
		
		
		testArrayList = new ArrayList<Tile>();
		testArrayLists = new ArrayList<ArrayList<Tile>>();
		testArrayList.add(B4);
		testArrayList.add(B5);
		testArrayLists.add(testArrayList);
		ArrayList<Tile> testArrayList2 = new ArrayList<Tile>();
		testArrayList2.add(B4);
		testArrayList2.add(B5);
		//testArrayLists.add(testArrayList2);
		ArrayList<Tile> testArrayList3 = new ArrayList<Tile>();
		
		assertEquals(testArrayLists, aiPlayer4.hand.getRunsOfTwo(aiPlayer4.hand.getTiles()));
	}
	
	public void testActualStrategy() {
		
		Game game = new Game();
		game.observers = new ArrayList<Observer>();
		game.playerHandCount = new HashMap<String, Integer>();
		game.aiPlayer1 = aiPlayer1;
		game.aiPlayer2 = aiPlayer2;
		game.aiPlayer3 = aiPlayer3;
		game.aiPlayer4 = aiPlayer4;
		game.userPlayer = userPlayer;
		game.addObserver(aiPlayer1);
	    game.addObserver(aiPlayer2);
	    game.addObserver(aiPlayer3);
	    game.addObserver(userPlayer);
	    game.addObserver(aiPlayer4);
		
		aiPlayer4.initial30Played = true;
		
		ArrayList<Tile> testArray = new ArrayList<Tile>();
		ArrayList<ArrayList<Tile>> fakeBoard = new ArrayList<ArrayList<Tile>>();
		
		Meld meld = new Meld();
		meld.add(B5);
		meld.add(G5);
		meld.add(O5);
		
		game.getBoard().addMeld(meld);
		
		aiPlayer4.hand.add(G3);
		aiPlayer4.hand.add(O5);
		aiPlayer4.hand.add(G4);
		aiPlayer4.hand.add(B4);
		aiPlayer4.hand.add(B5);
		aiPlayer4.hand.add(R5);
		
		Collections.sort(aiPlayer4.hand.getTiles());
		
		game.messageObservers();
		System.out.println(userPlayer.hand.size());
		System.out.println(aiPlayer1.hand.size());
		System.out.println(aiPlayer2.hand.size());
		System.out.println(aiPlayer3.hand.size());
		System.out.println(aiPlayer4.hand.size());
		
		Meld testMeld = new Meld();
		testMeld.add(B5);
		testMeld.add(G5);
		testMeld.add(O5);
		System.out.println("BOARD 1: " +game.getBoard().currentMelds.get(0).getTiles());
		
		
		aiPlayer4.strategy.executeStrategy(aiPlayer4);
		
		assertEquals(game.getBoard().currentMelds.get(0).getTiles(), testMeld.getTiles());
		
	}
	
	public void testFindMissingTiles() {
		Game game = new Game();
		
		aiPlayer4.hand.add(G3);
		aiPlayer4.hand.add(O5);
		aiPlayer4.hand.add(G4);
		aiPlayer4.hand.add(B4);
		aiPlayer4.hand.add(B5);
		aiPlayer4.hand.add(R5);
		
		Collections.sort(aiPlayer4.hand.getTiles());
		
		Meld assertMeld = new Meld();
		assertMeld.add(B3);
		assertMeld.add(R3);
		assertMeld.add(O3);
		assertMeld.add(G3);
		
		Meld testMeld = new Meld();
		testMeld.add(B5);
		testMeld.add(G5);
		testMeld.add(O5);
		
		Meld notherMeld = new Meld();
		notherMeld.add(G2);
		
		Meld thirdMeld = new Meld();
		thirdMeld.add(B3);
		thirdMeld.add(R3);
		thirdMeld.add(O3);
		
		game.getBoard().addMeld(testMeld);
		game.getBoard().addMeld(notherMeld);
		game.getBoard().addMeld(thirdMeld);
		
		aiPlayer4.strategy.executeStrategy(aiPlayer4);
		
		assertEquals(game.getBoard().currentMelds.get(2).getTiles(), assertMeld.getTiles());	
		
	}
	
	Game game = new Game();
	PlayerStrategy<? super AIPlayer> aiStrategyFour = new AIStrategyFour();
	AIPlayer aiPlayer4 = new AIPlayer("AI4", game, aiStrategyFour);
	
	PlayerStrategy<? super AIPlayer> aiStrategyThree = new AIStrategyThree();
	PlayerStrategy<? super AIPlayer> aiStrategyTwo = new AIStrategyTwo();
	PlayerStrategy<? super AIPlayer> aiStrategyOne = new AIStrategyOne();
	PlayerStrategy<? super UserPlayer> userPlayerStrategy = new UserStrategy();
	
	AIPlayer aiPlayer3 = new AIPlayer("AI3", game, aiStrategyThree);
	AIPlayer aiPlayer2 = new AIPlayer("AI2", game, aiStrategyTwo);
	AIPlayer aiPlayer1 = new AIPlayer("AI1", game, aiStrategyOne);
	UserPlayer userPlayer = new UserPlayer("User",game, userPlayerStrategy);
	
	
	ArrayList<Tile> testArrayList;
	ArrayList<ArrayList<Tile>> testArrayLists;
	
	Tile R5 = new Tile('R',5);
	Tile B5 = new Tile('B',5);
	Tile B4 = new Tile('B',4);
	Tile G5 = new Tile('G',5);
	Tile O5 = new Tile('O',5);
	Tile G2 = new Tile('G',2);
	Tile G3 = new Tile('G',3);
	Tile G4 = new Tile('G',4);
	
	Tile B3 = new Tile('B',3);
	Tile O3 = new Tile('O',3);
	Tile R3 = new Tile('R',3);

}
