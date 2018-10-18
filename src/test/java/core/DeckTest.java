package core;

import junit.framework.TestCase;

public class DeckTest extends TestCase {

    public void testNumOfCardsInDeck() {
        Deck deck = new Deck();
        assertEquals(104, deck.getDeckSize());
    }

    public void testCardReturnedFromTakeCard() {
        Deck deck = new Deck();
        assertTrue(deck.drawTile() instanceof Tile);
    }

    public void testDeckSizeDecreasesWhenRemoveFromDeck() {
        Deck deck = new Deck();
        Tile tile = deck.drawTile();
        assertEquals(103, deck.getDeckSize());
    }

    public void testCheckForValidDuplicateTiles() {
        Deck deck = new Deck();

        int numOfBlueFive = 0;

        for (int i = 0; i < 104; i++) {
            Tile tile = deck.drawTile();

            if (tile.getRank() == 5 && tile.getColour() == 'B') numOfBlueFive++;
            if (numOfBlueFive == 2) break;
        }

        assertEquals(2, numOfBlueFive);
    }

    public void testDealTiles() {
        Player player = new Player();
        Deck deck = new Deck();

        deck.dealTiles(player);

        assertEquals(14, player.getHand().size());
    }
}
