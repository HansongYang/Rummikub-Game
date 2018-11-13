package core;

import junit.framework.TestCase;

public class GameTest extends TestCase {

    public void testCreateAndMessageObservers() {
    	Model game = new Model();
        Deck deck = new Deck();
        game.createGamePlayers();
        deck.dealTiles(game.userPlayer);
        deck.dealTiles(game.aiPlayer1);
        deck.dealTiles(game.aiPlayer2);
        deck.dealTiles(game.aiPlayer3);
        game.messageObservers();

        Integer num = new Integer(14);
        assertEquals(num, game.userPlayer.playerHandCount.get("USER"));
        assertEquals(num, game.aiPlayer1.playerHandCount.get("AI1"));
        assertEquals(num, game.aiPlayer2.playerHandCount.get("AI2"));
        assertEquals(num, game.aiPlayer3.playerHandCount.get("AI3"));
        
        Tile tile = deck.drawTile();
        game.userPlayer.hand.add(tile);
        game.messageObservers();
        Integer num2 = new Integer(15);

        assertEquals(num2, game.userPlayer.playerHandCount.get("USER"));
       
        game.aiPlayer1.hand.add(tile);
        game.messageObservers();
        game.aiPlayer2.hand.add(tile);
        game.messageObservers();
        game.aiPlayer3.hand.add(tile);
        game.messageObservers();
        
        assertEquals(num2, game.aiPlayer1.playerHandCount.get("AI1"));
        assertEquals(num2, game.aiPlayer2.playerHandCount.get("AI2"));
        assertEquals(num2, game.aiPlayer3.playerHandCount.get("AI3"));
    }

    public void testGameWinCheck() {
        Model game = new Model();
        game.createGamePlayers();
        game.getDeck().dealTiles(game.aiPlayer1);
        game.getDeck().dealTiles(game.aiPlayer2);
        game.getDeck().dealTiles(game.aiPlayer3);

        assertTrue(game.gameWinCheck());
        assertEquals(game.gameWinner, game.userPlayer);
        assertEquals(game.gameState, game.gameState.END);
    }
}
