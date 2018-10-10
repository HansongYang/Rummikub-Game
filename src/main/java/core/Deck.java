package core;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private static final char[] COLOURS = {'R', 'B', 'G', 'O'};
    private static final int[] RANKS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private ArrayList<Tile> deck = new ArrayList<Tile>();

    public Deck() {
        this.generateDeck();
        this.shuffleDeck();
    }

    public void generateDeck() {
        // Outer loop for creating every tile twice
        for (int x = 0; x < 2; x++) {
            for (int i = 0; i < COLOURS.length; i++) {
                for (int j = 0; j < RANKS.length; j++) {
                    Tile tile = new Tile(COLOURS[i], RANKS[j]);
                    deck.add(tile);
                }
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public Tile drawTile() {
        return deck.remove(deck.size()-1);
    }

    public void dealTiles(Player player) {
        Hand playerHand = player.getHand();
        for (int i = 0; i < 14; i++) {
            playerHand.add(this.drawTile());
        }
    }

    public int getDeckSize() {
        return deck.size();
    }
}
