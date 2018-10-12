package core;

import java.util.ArrayList;
import java.util.Scanner;

import junit.framework.TestCase;

public class UserPlayerTest extends TestCase {

	public void testSelectTiles() {
		UserPlayer player = new UserPlayer("player", new Game());
		Deck deck = new Deck();
		
		deck.dealTiles(player);
		
		ArrayList<Integer> indices = new ArrayList<Integer>();
		indices.add(5);
		indices.add(7);
		indices.add(10);
		
		ArrayList<Tile> tiles = player.selectTiles(indices, player.hand);
		
		for(int i = 0; i < indices.size(); i++) {
			int index = indices.get(i);
			
			assertEquals(tiles.get(i).getRank(), player.hand.getTile(index).getRank());
			assertEquals(tiles.get(i).getColour(), player.hand.getTile(index).getColour());
		}
		
	}
	
	public void testCreateMeld() {
		UserPlayer player = new UserPlayer("player", new Game());

		Hand hand = new Hand();
		hand.add(new Tile('R',6));
		hand.add(new Tile('R',7));
		hand.add(new Tile('R',8));
		hand.add(new Tile('B',3));
	
		//Valid meld
		ArrayList<Tile> tiles = new ArrayList<Tile>();	
		tiles.add(new Tile('R',6));
		tiles.add(new Tile('R',7));
		tiles.add(new Tile('R',8));	
		
		Meld meld = player.createMeld(tiles, hand);	
		assertFalse(meld == null);
		
		//Invalid meld
		ArrayList<Tile> tiles2 = new ArrayList<Tile>();		
		tiles2.add(new Tile('R',6));
		tiles2.add(new Tile('R',7));
		tiles2.add(new Tile('B',3));
		
		Meld meld2 = player.createMeld(tiles2, hand);	
		assertEquals(meld2, null);
		
		
	}
	
	public void testTotalAllMelds() {
		
		UserPlayer player = new UserPlayer();
		
		Meld meld = new Meld();
		meld.add(new Tile('G',7));
		meld.add(new Tile('R',7));
		meld.add(new Tile('B',7));
		player.meldsInHand.add(meld);
			
		assertEquals(player.totalAllMelds(player.meldsInHand), 21);
		
		Meld meld2 = new Meld();
		meld2.add(new Tile('B',1));
		meld2.add(new Tile('B',2));
		meld2.add(new Tile('B',3));
		meld2.add(new Tile('B',4));
		player.meldsInHand.add(meld2);
		
		assertEquals(player.totalAllMelds(player.meldsInHand), 31);
		
	}
	
	public void testTileSelectionInput() {
		UserPlayer player = new UserPlayer();
		Hand hand = new Hand();
		hand.add(new Tile('R',6));
		hand.add(new Tile('R',7));
		hand.add(new Tile('R',8));
		hand.add(new Tile('B',3));
		
		Scanner reader = new Scanner(System.in);
		player.tileSelectionInput(reader, hand);
		
		//Create the meld with R6 R7 R8 during the user input for this test to pass
		assertEquals(player.meldsInHand.size(),1);
	}
	
	public void testPlayTurn() {
		Game game = new Game();
		UserPlayer player = new UserPlayer("player", game);
		player.hand.add(new Tile('R',10));
		player.hand.add(new Tile('R',11));
		player.hand.add(new Tile('R',12));
		player.hand.add(new Tile('G',5));
		player.hand.add(new Tile('R',5));
		player.hand.add(new Tile('B',5));
		
		System.out.println("Play turn input loop:");
		player.hand.printHand();
		
		//Test playing the 2 melds on player turn
		player.playTurn();	
		assertEquals(2,game.getBoard().currentMelds.size());
	}
}
