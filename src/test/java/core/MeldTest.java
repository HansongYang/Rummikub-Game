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
}
