package core;

import junit.framework.TestCase;

public class MeldTest extends TestCase {
	public void testAddTile() {
		Deck deck = new Deck();
		Meld meld = new Meld();
		
		meld.add(deck.drawTile());
		meld.add(deck.drawTile());
		meld.add(deck.drawTile());
		meld.add(deck.drawTile());
		
		assertEquals(4, meld.size());
	}
	
	public void testRemoveTile() {
		Meld meld = new Meld();
		Tile t1 = new Tile('R', 4);
		Tile t2 = new Tile('R', 3);
		
		meld.add(t1);
		meld.add(t2);
		meld.remove(t1);
		meld.remove(t2);
		
		assertEquals(0, meld.size());
	}
	
	public void testGetTile() {
		Meld meld = new Meld();
		Tile t1 = new Tile('R', 4);
		Tile t2 = new Tile('R', 3);
		
		meld.add(t1);
		meld.add(t2);
		
		assertEquals(3, meld.getTile(1).getRank());
	}
	
	public void testTotalValue() {
		Meld meld = new Meld();
		Tile t1 = new Tile('R', 1);
		Tile t2 = new Tile('R', 2);
		Tile t3 = new Tile('R', 3);
		
		meld.add(t1);
		meld.add(t2);
		meld.add(t3);
	
		assertEquals(6, meld.totalValue());
		
		meld.add(new Tile('R', 4));
		assertEquals(10, meld.totalValue());
	}
}
