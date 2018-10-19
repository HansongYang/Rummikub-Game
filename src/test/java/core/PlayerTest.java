package core;

import junit.framework.TestCase;
import java.util.ArrayList;

public class PlayerTest extends TestCase {

	
	public void testPlayMeld() {
		Player player = new Player();
		Board board = new Board();
		
		ArrayList<Tile> meld = new ArrayList<Tile>();
		meld.add(new Tile('R',7));
		meld.add(new Tile('G',7));
		meld.add(new Tile('B',7));
		
		player.playMeld(board, meld);
		
		assertEquals(1, board.currentMelds.size());
		
	}
	
	public void testPlayMelds() {
		
		ArrayList<Meld> melds = new ArrayList<Meld>();
		Player player = new Player();
		Board board = new Board();
		
		melds.add(new Meld());
		melds.add(new Meld());
		melds.add(new Meld());
		
		player.playMelds(board, melds);
		
		assertEquals(3, board.currentMelds.size());
	}
	
	public void testTotalAllMelds() {
		
		Player player = new Player();
		
		Meld meld = new Meld();
		meld.add(new Tile('G',7));
		meld.add(new Tile('R',7));
		meld.add(new Tile('B',7));
		player.meldsInHand.add(meld);
			
		assertEquals(player.totalAllMelds(player.meldsInHand), 21);
		
		Meld meld2 = new Meld();
		meld2.add(new Tile('B',1));
		meld2.add(new Tile('B',2));
		meld2.add(new Tile('B',3));
		meld2.add(new Tile('B',4));
		player.meldsInHand.add(meld2);
		
		assertEquals(player.totalAllMelds(player.meldsInHand), 31);
		
	}
	
	public void testTotalTilesFromMelds() {
		
		Player player = new Player();
		
		Meld meld = new Meld();
		meld.add(new Tile('G',7));
		meld.add(new Tile('R',7));
		meld.add(new Tile('B',7));
		player.meldsInHand.add(meld);
			
		assertEquals(player.totalTilesFromMelds(player.meldsInHand), 3);

		Player player2 = new Player();
		
		assertEquals(player2.totalTilesFromMelds(player2.meldsInHand), 0);
		
	}
	

}
