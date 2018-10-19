package core;

import java.util.ArrayList;

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
	
	public void testWin() {
		Hand hand = new Hand();
		assertEquals(hand.win(), true);
	}
	
	public void testSortTileByNumber() {
		Deck d = new Deck();
		Hand hand = new Hand();
		Tile t3 = new Tile('R', 3);
		Tile t2 = new Tile('R', 2);
		Tile t1 = new Tile('R', 1);
		
		hand.add(t3);
		hand.add(t2);
		hand.add(t1);
		hand.sortTilesByNumber();
		
		assertEquals(1, hand.getTile(0).getRank());
		assertEquals(3, hand.getTile(2).getRank());
	}
	
	public void testSortTileByColour() {
		Deck d = new Deck();
		Hand hand = new Hand();
		Tile t4 = new Tile('O', 3);
		Tile t3 = new Tile('B', 3);
		Tile t2 = new Tile('G', 2);
		Tile t1 = new Tile('R', 1);
		
		hand.add(t4);
		hand.add(t3);
		hand.add(t2);
		hand.add(t1);
		hand.sortTilesByColour();
		
		assertEquals('R', hand.getTile(0).getColour());
		assertEquals('B', hand.getTile(1).getColour());
		assertEquals('G', hand.getTile(2).getColour());
		assertEquals('O', hand.getTile(3).getColour());
	}
	
	public void testGetMeldSets() {
		Deck d = new Deck();
		Hand hand = new Hand();
		Tile t4 = new Tile('O', 3);
		Tile t3 = new Tile('B', 3);
		Tile t2 = new Tile('G', 3);
		Tile t1 = new Tile('R', 1);
		
		hand.add(t4);
		hand.add(t1);
		hand.add(t2);
		hand.add(t3);
		ArrayList<Meld> set = hand.getMeldSets();
		
		assertTrue(3 == set.get(0).size());
		assertTrue(3 == set.get(0).getTile(0).getRank() && 'O' == set.get(0).getTile(0).getColour());
		assertTrue(3 == set.get(0).getTile(1).getRank() && 'G' == set.get(0).getTile(1).getColour());
		assertTrue(3 == set.get(0).getTile(2).getRank() && 'B' == set.get(0).getTile(2).getColour());
	}
	
	public void testGetMeldRuns() {
		Deck d = new Deck();
		Hand hand = new Hand();
		Tile t4 = new Tile('O', 3);
		Tile t3 = new Tile('R', 3);
		Tile t2 = new Tile('R', 2);
		Tile t1 = new Tile('R', 1);
		
		hand.add(t4);
		hand.add(t1);
		hand.add(t2);
		hand.add(t3);
		ArrayList<Meld> run = hand.getMeldRuns();
		
		assertTrue(3 == run.get(0).size());
		assertTrue(1 == run.get(0).getTile(0).getRank() && 'R' == run.get(0).getTile(0).getColour());
		assertTrue(2 == run.get(0).getTile(1).getRank() && 'R' == run.get(0).getTile(1).getColour());
		assertTrue(3 == run.get(0).getTile(2).getRank() && 'R' == run.get(0).getTile(2).getColour());
	}
	
	public void testGetInitialPoints() {
		Hand hand = new Hand();
		Tile t4 = new Tile('O', 3);
		Tile t3 = new Tile('R', 8);
		Tile t2 = new Tile('R', 9);
		Tile t1 = new Tile('R', 10);
		Tile t5 = new Tile('O', 11);
		Tile t6 = new Tile('B', 11);
		Tile t7 = new Tile('G', 11);
		Tile t8 = new Tile('R', 1);
		
		hand.add(t4);
		hand.add(t1);
		hand.add(t2);
		hand.add(t3);
		hand.add(t5);
		hand.add(t6);
		hand.add(t7);
		hand.add(t8);
		ArrayList<Meld> initial = hand.getInitialTiles();
		
		assertTrue(1 == initial.size());
		assertTrue(11 == initial.get(0).getTile(0).getRank() && 'O' == initial.get(0).getTile(0).getColour());
		assertTrue(11 == initial.get(0).getTile(1).getRank() && 'B' == initial.get(0).getTile(1).getColour());
		assertTrue(11 == initial.get(0).getTile(2).getRank() && 'G' == initial.get(0).getTile(2).getColour());
	}
	
	public void testGetRemainingTiles() {
		Hand hand = new Hand();
		
		hand.add(new Tile('R',1));
		hand.add(new Tile('R',2));
		hand.add(new Tile('R',3));
		hand.add(new Tile('R',8));
		hand.add(new Tile('R',9));
		hand.add(new Tile('R',10));
		hand.add(new Tile('B',7));
		hand.add(new Tile('G',5));
		
		Meld meld1 = new Meld();
		meld1.add(hand.getTile(0));
		meld1.add(hand.getTile(1));
		meld1.add(hand.getTile(2));
		
		Meld meld2 = new Meld();
		meld2.add(hand.getTile(3));
		meld2.add(hand.getTile(4));
		meld2.add(hand.getTile(5));
		
		ArrayList<Meld> melds = new ArrayList<Meld>();
		
		melds.add(meld1);
		melds.add(meld2);
		
		ArrayList<Tile> remaining = hand.getRemainingTiles(melds);
		
		assertEquals(remaining.get(0).getColour(), 'B');
		assertEquals(remaining.get(0).getRank(), 7);
		assertEquals(remaining.get(1).getColour(), 'G');
		assertEquals(remaining.get(1).getRank(), 5);
		
		hand.remove(hand.getTile(hand.size()-1));
		hand.remove(hand.getTile(hand.size()-1));
		
		ArrayList<Tile> remaining2 = hand.getRemainingTiles(melds);
		
		assertEquals(remaining2.size(), 0);
		
	}
}
