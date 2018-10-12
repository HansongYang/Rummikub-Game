package core;

public class Game {

    private enum Players { USER, P1, P2, P3 }
    private enum GameStates { PLAY, END }
    private Deck deck = new Deck();
    private Board board = new Board();
    private GameStates gameState;
    private Player currentPlayer;

    public static void main(String[] arg) {
        Game game = new Game();
        game.start();
    }

    public void start() {
        //deck = new Deck();
        //board = new Board();

        UserPlayer userPlayer = new UserPlayer("USER", this);
        AIPlayer aiPlayer = new AIPlayer("AI1", this);
        deck.dealTiles(userPlayer);
        deck.dealTiles(aiPlayer);

        gameState = GameStates.PLAY;
        currentPlayer = userPlayer;
        gameLoop();
    }

    public void gameLoop() {
        while(gameState == GameStates.PLAY) {
            System.out.println("Player " + currentPlayer.name + "'s turn");

            if (currentPlayer instanceof UserPlayer) {
                currentPlayer.getHand().sortTilesByColour();
                currentPlayer.getHand().printHand();
                ((UserPlayer) currentPlayer).playTurn();
            } else {
                ((AIPlayer) currentPlayer).playTurn();
            }

            break;
            // call gamewin check
        }
    }
    
    public Deck getDeck() {
    	return deck;
    }
    
    public Board getBoard() {
    	return board;
    }

    // create seperate gamewin check
}
