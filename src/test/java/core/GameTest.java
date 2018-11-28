package core;

import junit.framework.TestCase;

public class GameTest extends TestCase {

    public void testCreateAndMessageObservers() {
    	Model game = new Model();
        Deck deck = new Deck();
        game.createGamePlayers();
        deck.dealTiles(game.userPlayer);
        deck.dealTiles(game.player2);
        deck.dealTiles(game.player3);
        deck.dealTiles(game.player4);
        game.messageObservers();

        Integer num = new Integer(14);
        assertEquals(num, game.userPlayer.playerHandCount.get("USER"));
        assertEquals(num, game.player2.playerHandCount.get("AI1"));
        assertEquals(num, game.player3.playerHandCount.get("AI2"));
        assertEquals(num, game.player4.playerHandCount.get("AI3"));
        
        Tile tile = deck.drawTile();
        game.userPlayer.hand.add(tile);
        game.messageObservers();
        Integer num2 = new Integer(15);

        assertEquals(num2, game.userPlayer.playerHandCount.get("USER"));
       
        game.player2.hand.add(tile);
        game.messageObservers();
        game.player3.hand.add(tile);
        game.messageObservers();
        game.player4.hand.add(tile);
        game.messageObservers();
        
        assertEquals(num2, game.player2.playerHandCount.get("AI1"));
        assertEquals(num2, game.player3.playerHandCount.get("AI2"));
        assertEquals(num2, game.player4.playerHandCount.get("AI3"));
    }

    public void testGameWinCheck() {
        Model game = new Model();
        game.createGamePlayers();
        game.getDeck().dealTiles(game.player2);
        game.getDeck().dealTiles(game.player3);
        game.getDeck().dealTiles(game.player4);

        assertTrue(game.gameWinCheck());
        assertEquals(game.gameWinner, game.userPlayer);
        assertEquals(game.gameState, game.gameState.END);
    }
}
