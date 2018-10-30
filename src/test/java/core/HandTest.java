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
		
		Tile t1 = new Tile('O', 10);
		Tile t2 = new Tile('B', 10);
		Tile t3 = new Tile('G', 10);
		hand.add(t1);
		hand.add(t2);
		hand.add(t3);
		ArrayList<Meld> initial = hand.getInitialTiles();
		
		assertTrue(1 == initial.size());
		assertTrue(10 == initial.get(0).getTile(0).getRank() && 'O' == initial.get(0).getTile(0).getColour());
		assertTrue(10 == initial.get(0).getTile(1).getRank() && 'B' == initial.get(0).getTile(1).getColour());
		assertTrue(10 == initial.get(0).getTile(2).getRank() && 'G' == initial.get(0).getTile(2).getColour());
		
		Hand hand2 = new Hand();
		Tile t4 = new Tile('O', 13);
		Tile t5 = new Tile('B', 13);
		Tile t6 = new Tile('G', 13);
		hand2.add(t4);
		hand2.add(t5);
		hand2.add(t6);
		initial = hand2.getInitialTiles();
		
		assertTrue(1 == initial.size());
		assertTrue(13 == initial.get(0).getTile(0).getRank() && 'O' == initial.get(0).getTile(0).getColour());
		assertTrue(13 == initial.get(0).getTile(1).getRank() && 'B' == initial.get(0).getTile(1).getColour());
		assertTrue(13 == initial.get(0).getTile(2).getRank() && 'G' == initial.get(0).getTile(2).getColour());
		
		Hand hand3 = new Hand();
		Tile t7 = new Tile('O', 9);
		Tile t8 = new Tile('R', 9);
		Tile t9 = new Tile('B', 9);
		Tile t10 = new Tile('O', 1);
		Tile t11 = new Tile('B', 1);
		Tile t12 = new Tile('G', 1);
		
		hand3.add(t7);
		hand3.add(t8);
		hand3.add(t9);
		hand3.add(t10);
		hand3.add(t11);
		hand3.add(t12);
	    initial = hand3.getInitialTiles();
		
		assertTrue(2 == initial.size());
		assertTrue(1 == initial.get(0).getTile(0).getRank() && 'O' == initial.get(0).getTile(0).getColour());
		assertTrue(1 == initial.get(0).getTile(1).getRank() && 'B' == initial.get(0).getTile(1).getColour());
		assertTrue(1 == initial.get(0).getTile(2).getRank() && 'G' == initial.get(0).getTile(2).getColour());
		assertTrue(9 == initial.get(1).getTile(0).getRank() && 'O' == initial.get(1).getTile(0).getColour());
		assertTrue(9 == initial.get(1).getTile(1).getRank() && 'R' == initial.get(1).getTile(1).getColour());
		assertTrue(9 == initial.get(1).getTile(2).getRank() && 'B' == initial.get(1).getTile(2).getColour());
		
		
		Hand hand4 = new Hand();
		Tile t13 = new Tile('O', 5);
		Tile t14 = new Tile('R', 5);
		Tile t15 = new Tile('B', 5);
		Tile t16 = new Tile('O', 9);
		Tile t17 = new Tile('B', 9);
		Tile t18 = new Tile('G', 9);
		hand4.add(t13);
		hand4.add(t14);
		hand4.add(t15);
		hand4.add(t16);
		hand4.add(t17);
		hand4.add(t18);
	    initial = hand4.getInitialTiles();
		
		assertTrue(2 == initial.size());
		assertTrue(5 == initial.get(0).getTile(0).getRank() && 'O' == initial.get(0).getTile(0).getColour());
		assertTrue(5 == initial.get(0).getTile(1).getRank() && 'R' == initial.get(0).getTile(1).getColour());
		assertTrue(5 == initial.get(0).getTile(2).getRank() && 'B' == initial.get(0).getTile(2).getColour());
		assertTrue(9 == initial.get(1).getTile(0).getRank() && 'O' == initial.get(1).getTile(0).getColour());
		assertTrue(9 == initial.get(1).getTile(1).getRank() && 'B' == initial.get(1).getTile(1).getColour());
		assertTrue(9 == initial.get(1).getTile(2).getRank() && 'G' == initial.get(1).getTile(2).getColour());
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
