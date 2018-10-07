package core;

import junit.framework.TestCase;

public class TileTest extends TestCase{
	public void testRank() {
		Tile t1 = new Tile('R',1);
		Tile t2 = new Tile('O',2);
		
		assertEquals(2, t2.getRank());
		assertEquals(1, t1.getRank());
	}
	
	public void testColour() {
		Tile t1 = new Tile('R',1);
		Tile t2 = new Tile('O',2);
		
		assertEquals('O', t2.getColour());
		assertEquals('R', t1.getColour());
	}
}
