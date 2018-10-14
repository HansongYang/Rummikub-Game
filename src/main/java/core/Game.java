package core;

public class Game {

    private enum Players { USER, P1, P2, P3 }
    private enum GameStates { PLAY, END }
    private Deck deck = new Deck();
    private Board board = new Board();
    private GameStates gameState;
    private UserPlayer userPlayer;
    private AIPlayer aiPlayer;
    private Player gameWinner;

    public static void main(String[] arg) {
        Game game = new Game();
        game.start();
    }

    // Start function which initializes players, deals tiles, and begins main game loop
    public void start() {
        createGamePlayers();
        deck.dealTiles(userPlayer);
        deck.dealTiles(aiPlayer);
        gameState = GameStates.PLAY;
        gameLoop();
    }

    public void createGamePlayers() {
        userPlayer = new UserPlayer("USER", this);
        aiPlayer = new AIPlayer("AI1", this);
    }

    public void gameLoop() {
        int currentPlayerCheck = 0;

        while(gameState == GameStates.PLAY) {

            if (currentPlayerCheck > 3) currentPlayerCheck = 0;

            if (currentPlayerCheck == 0) {
                System.out.println("Player " + userPlayer.name + "'s turn");
                userPlayer.getHand().sortTilesByColour();
                userPlayer.getHand().printHand();
                userPlayer.playTurn();
                board.printBoard();
            } else if (currentPlayerCheck == 1) {
                System.out.println("Player " + aiPlayer.name + "'s turn");
                aiPlayer.playTurn();
            } else if (currentPlayerCheck == 2) {

            } else if (currentPlayerCheck == 3) {

            }

            currentPlayerCheck++;
            if (gameWinCheck()) System.out.println(gameWinner.name + " wins the game!");
        }
    }
    
    public Deck getDeck() {
    	return deck;
    }
    
    public Board getBoard() {
    	return board;
    }

    public boolean gameWinCheck() {
        if (userPlayer.hand.size() == 0) {
            gameWinner = userPlayer;
            return true;
        } else if (aiPlayer.hand.size() == 0) {
            gameWinner = aiPlayer;
            return true;
        } else {
            return false;
        }
    }
}
