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
}
