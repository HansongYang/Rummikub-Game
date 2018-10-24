package core;

import junit.framework.TestCase;

public class GameTest extends TestCase {

    public void testStart() {
        Game game = new Game();
        game.start();
    }

    public void testCreateAndMessageObservers() {
        Game game = new Game();
        Deck deck = new Deck();
        game.createGamePlayers();
        deck.dealTiles(game.userPlayer);
        deck.dealTiles(game.aiPlayer);
        game.messageObservers();

        Integer num = new Integer(14);
        assertEquals(num, game.userPlayer.playerHandCount.get("USER"));

        Tile tile = deck.drawTile();
        game.userPlayer.hand.add(tile);
        game.messageObservers();
        Integer num2 = new Integer(15);

        assertEquals(num2, game.userPlayer.playerHandCount.get("USER"));
    }
}
