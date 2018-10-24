package core;

import java.util.ArrayList;
import java.util.HashMap;

public class Game implements Observable{

    private ArrayList<Observer> observers;
    public HashMap<String, Integer> playerHandCount;
    private enum GameStates { PLAY, END }
    private Deck deck = new Deck();
    private Board board = new Board();
    private GameStates gameState;
    private Player gameWinner;

    public AIPlayer aiPlayer;
    public UserPlayer userPlayer;

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
        observers = new ArrayList<Observer>();
        playerHandCount = new HashMap<String, Integer>();
    	PlayerStrategy<? super UserPlayer> userStrategy = new UserStrategy();
    	PlayerStrategy<? super AIPlayer> aiStrategyOne = new AIStrategyTwo();
    	
        userPlayer = new UserPlayer("USER", this, userStrategy);
        aiPlayer = new AIPlayer("AI1", this, aiStrategyOne);
        this.addObserver(userPlayer);
        this.addObserver(aiPlayer);
    }

    public void gameLoop() {
        int currentPlayerCheck = 0;

        while(gameState == GameStates.PLAY) {

            if (currentPlayerCheck > 3) currentPlayerCheck = 0;

            if (currentPlayerCheck == 0) {
                System.out.println("\nPlayer " + userPlayer.name + "'s turn");
                userPlayer.getHand().sortTilesByColour();
                userPlayer.getHand().printHand();
                userPlayer.playTurn();
                if(gameState == GameStates.END) {
                	break;
                }
                board.printBoard();
                this.messageObservers();
            } else if (currentPlayerCheck == 1) {
                System.out.println("\nPlayer " + aiPlayer.name + "'s turn");
                aiPlayer.playTurn();
                board.printBoard();
                this.messageObservers();
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
    
    public void endGame() {
    	 gameState = GameStates.END;
    }

    public void updatePlayerHashMap() {
        this.playerHandCount.put(userPlayer.name, userPlayer.getHand().size());
        this.playerHandCount.put(aiPlayer.name, aiPlayer.getHand().size());
    }

    public void messageObservers() {
        this.updatePlayerHashMap();
        for (Observer o: observers) {
            o.update(playerHandCount);
        }
    }

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }
}
