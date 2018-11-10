package core;

import junit.framework.TestCase;

public class DeckTest extends TestCase {

    public void testNumOfCardsInDeck() {
        Deck deck = new Deck();
        assertEquals(106, deck.getDeckSize());
    }

    public void testCardReturnedFromTakeCard() {
        Deck deck = new Deck();
        assertTrue(deck.drawTile() instanceof Tile);
    }

    public void testDeckSizeDecreasesWhenRemoveFromDeck() {
        Deck deck = new Deck();
        Tile tile = deck.drawTile();
        assertEquals(105, deck.getDeckSize());
    }

    public void testCheckForValidDuplicateTiles() {
        Deck deck = new Deck();

        int numOfBlueFive = 0;

        for (int i = 0; i < 106; i++) {
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

    public void testTwoJokersInDeck() {
        Deck deck = new Deck();
        int numOfJokers = 0;

        for (int i = 0; i < 106; i++) {
            Tile tile = deck.drawTile();

            if (tile.getRank() == 0 && tile.getColour() == 'J') numOfJokers++;
        }

        assertEquals(2, numOfJokers);
    }
}
