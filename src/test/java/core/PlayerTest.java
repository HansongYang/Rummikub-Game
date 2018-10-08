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
		
		player.PlayMeld(board, meld);
		
		assertEquals(1, board.currentMelds.size());
		
	}
	
	public void testIsValidMeld() {
		
		Player player = new Player();
		
		ArrayList<Tile> meld1 = new ArrayList<Tile>();
		meld1.add(new Tile('R',7));
		meld1.add(new Tile('G',7));
		meld1.add(new Tile('B',7));
		
		assertTrue(player.IsValidMeld(meld1));
		
		ArrayList<Tile> meld2 = new ArrayList<Tile>();
		meld2.add(new Tile('R',7));
		meld2.add(new Tile('G',7));
		meld2.add(new Tile('R',7));
		
		assertFalse(player.IsValidMeld(meld2));
		
		ArrayList<Tile> meld3 = new ArrayList<Tile>();
		meld3.add(new Tile('R',7));
		meld3.add(new Tile('R',8));
		meld3.add(new Tile('R',9));
		meld3.add(new Tile('R',5));
		
		assertFalse(player.IsValidMeld(meld3));
				
		ArrayList<Tile> meld4 = new ArrayList<Tile>();
		meld4.add(new Tile('R',8));
		meld4.add(new Tile('R',9));
		meld4.add(new Tile('R',10));
		meld4.add(new Tile('R',11));
		
		assertTrue(player.IsValidMeld(meld4));
		
		ArrayList<Tile> meld5 = new ArrayList<Tile>();
		meld5.add(new Tile('R',1));
		meld5.add(new Tile('O',2));
		meld5.add(new Tile('R',3));
		meld5.add(new Tile('B',4));
		
		assertFalse(player.IsValidMeld(meld5));
		
		ArrayList<Tile> meld6 = new ArrayList<Tile>();
		meld6.add(new Tile('R',1));
		meld6.add(new Tile('G',1));
		
		assertFalse(player.IsValidMeld(meld6));
		
	}
}
