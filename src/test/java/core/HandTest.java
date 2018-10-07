package core;

import junit.framework.TestCase;

public class HandTest extends TestCase{
	public void testAddTile() {
		Deck d = new Deck();
		Hand hand = new Hand();
		
		hand.add(d.drawTile());
		hand.add(d.drawTile());
		hand.add(d.drawTile());
		
		assertEquals(3, hand.size());
	}
	
	public void testPlayTile() {
		Deck d = new Deck();
		Hand hand = new Hand();
		Tile t = new Tile('R', 3);
		
		hand.add(d.drawTile());
		hand.add(t);
		hand.add(d.drawTile());
		hand.remove(t);
		
		assertEquals(2, hand.size());
		
	}
}
