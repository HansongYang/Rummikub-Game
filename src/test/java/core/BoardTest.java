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

	public void testAddTileToMeldEnd() {
		Board board = new Board();

		Meld meld = new Meld();
		meld.add(new Tile('R', 9));
		meld.add(new Tile('R', 10));
		meld.add(new Tile('R', 11));
		board.addMeld(meld);

		Meld newMeld = new Meld();
		newMeld.add(new Tile('R', 12));
		newMeld.add(new Tile('R', 13));

		board.addTileToMeld(0, 3, newMeld);
		assertEquals(12, board.currentMelds.get(0).getTile(3).getRank());
		assertEquals(13, board.currentMelds.get(0).getTile(4).getRank());
	}

	public void testAddTileToMeldBeginning() {
		Board board = new Board();

		Meld meld = new Meld();
		meld.add(new Tile('R', 9));
		meld.add(new Tile('R', 10));
		meld.add(new Tile('R', 11));
		board.addMeld(meld);

		Meld newMeld = new Meld();
		newMeld.add(new Tile('R', 7));
		newMeld.add(new Tile('R', 8));

		board.addTileToMeld(0, -1, newMeld);
		assertEquals(7, board.currentMelds.get(0).getTile(0).getRank());
		assertEquals(8, board.currentMelds.get(0).getTile(1).getRank());
	}
}
