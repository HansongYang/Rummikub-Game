package core;

public class Game {

    private enum Players { USER, P1, P2, P3 }
    private Deck deck;
    private Board board;

    public static void main(String[] arg) {
        Game game = new Game();
        game.start();
    }

    public void start() {
        deck = new Deck();
    }
}
