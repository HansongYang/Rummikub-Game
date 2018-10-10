package core;

import junit.framework.TestCase;

public class BoardTest extends TestCase {
	
	public void testAddMeld() {
		Board board = new Board();
		
		Meld meld = new Meld();
		meld.add(new Tile('R',7));
		meld.add(new Tile('G',7));
		meld.add(new Tile('B',7));
		
		board.addMeld(meld);
		
		assertEquals(1, board.currentMelds.size());
		
	}
}
