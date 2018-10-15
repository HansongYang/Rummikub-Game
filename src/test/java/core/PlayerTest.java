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

}
