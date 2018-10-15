package core;

import java.util.ArrayList;

import junit.framework.TestCase;

public class AIPlayerTest extends TestCase {

	public void testMeldRunsFirst() {
		
		AIPlayer player = new AIPlayer();
		
		player.hand.add(new Tile('R',1));
		player.hand.add(new Tile('R',2));
		player.hand.add(new Tile('R',3));
		player.hand.add(new Tile('B',1));
		player.hand.add(new Tile('G',1));
		
		ArrayList<Meld> allMelds =  player.meldRunsFirst();
		
		//Check that the meld R1 R2 R3 was made; not R1 B1 G1
		for(int i = 0; i < allMelds.get(0).size(); i++) {
			assertEquals(i+1,allMelds.get(0).getTile(i).getRank());
		}
		
	}
	
	public void testMeldSetsFirst() {
		
		AIPlayer player = new AIPlayer();
		
		player.hand.add(new Tile('R',1));
		player.hand.add(new Tile('R',2));
		player.hand.add(new Tile('R',3));
		player.hand.add(new Tile('B',1));
		player.hand.add(new Tile('G',1));
		
		ArrayList<Meld> allMelds =  player.meldSetsFirst();
		
		//Check that the meld R1 B1 G1 was made; not R1 R2 R3
		for(int i = 0; i < allMelds.get(0).size(); i++) {
			assertEquals(1,allMelds.get(0).getTile(i).getRank());
		}
		
	}
	
	public void testFindBestPlay() {
		
		AIPlayer player = new AIPlayer();
		
		//Sample melds to test with
		Meld meld1 = new Meld();
		meld1.add(new Tile('R', 10));
		meld1.add(new Tile('R', 11));
		meld1.add(new Tile('R', 12));
		
		Meld meld2 = new Meld();
		meld2.add(new Tile('B', 10));
		meld2.add(new Tile('B', 11));
		meld2.add(new Tile('B', 12));
		
		Meld meld3 = new Meld();
		meld3.add(new Tile('B', 2));
		meld3.add(new Tile('G', 2));
		meld3.add(new Tile('O', 2));
		
		Meld meld4 = new Meld();
		meld4.add(new Tile('B', 1));
		meld4.add(new Tile('G', 1));
		meld4.add(new Tile('O', 1));
		meld4.add(new Tile('R', 1));
		
		
		//Both total > 30, select highest tile total
		ArrayList<Meld> melds1a = new ArrayList<Meld>();
		ArrayList<Meld> melds2a = new ArrayList<Meld>();	
		melds1a.add(meld1);
		melds1a.add(meld3);
		melds2a.add(meld2);
		melds2a.add(meld4);	
		assertEquals(melds2a, player.findBestPlay(melds1a, melds2a));
		player.initial30Played = false;
		
		//Only 1 has total > 30, select this one
		ArrayList<Meld> melds1b = new ArrayList<Meld>();
		ArrayList<Meld> melds2b = new ArrayList<Meld>();	
		melds1b.add(meld1);
		melds2b.add(meld3);
		melds2b.add(meld4);	
		assertEquals(melds1b, player.findBestPlay(melds1b, melds2b));
		player.initial30Played = false;
		
		//None have total > 30, return null
		ArrayList<Meld> melds1c = new ArrayList<Meld>();
		ArrayList<Meld> melds2c = new ArrayList<Meld>();	
		melds1c.add(meld3);
		melds2c.add(meld4);
		assertEquals(null, player.findBestPlay(melds1c, melds2c));
		
		
	}
	
	public void testPlayTurn() {
		
		//Play all melds
		Game game = new Game();
		AIPlayer player = new AIPlayer("AI", game);
			
		player.hand.add(new Tile('R',1));
		player.hand.add(new Tile('R',2));
		player.hand.add(new Tile('R',3));
		player.hand.add(new Tile('B',1));
		player.hand.add(new Tile('G',1));
		
		player.playTurn();
		
		assertEquals(1,game.getBoard().currentMelds.size());
		assertEquals(2, player.hand.size());
		
		
		//Can't play, draw tile
		Game game2 = new Game();
		AIPlayer player2 = new AIPlayer("AI", game2);
			
		player2.hand.add(new Tile('R',1));
		player2.hand.add(new Tile('R',2));
		
		player.playTurn();
		
		assertEquals(0,game2.getBoard().currentMelds.size());
		assertEquals(3, player2.hand.size());
		
		
	}
	
}
