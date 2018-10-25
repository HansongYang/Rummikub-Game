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

    public AIPlayer aiPlayer1;
    public AIPlayer aiPlayer2;
    public AIPlayer aiPlayer3;
    public UserPlayer userPlayer;

    public static void main(String[] arg) {
        Game game = new Game();
        game.start();
    }

    // Start function which initializes players, deals tiles, and begins main game loop
    public void start() {
        createGamePlayers();
        deck.dealTiles(userPlayer);
        deck.dealTiles(aiPlayer1);
        deck.dealTiles(aiPlayer2);
        deck.dealTiles(aiPlayer3);
        gameState = GameStates.PLAY;
        gameLoop();
    }

    public void createGamePlayers() {
        observers = new ArrayList<Observer>();
        playerHandCount = new HashMap<String, Integer>();
    	PlayerStrategy<? super UserPlayer> userStrategy = new UserStrategy();
    	PlayerStrategy<? super AIPlayer> aiStrategyOne = new AIStrategyOne();
    	PlayerStrategy<? super AIPlayer> aiStrategyTwo = new AIStrategyTwo();
    	PlayerStrategy<? super AIPlayer> aiStrategyThree = new AIStrategyThree();
    	
        userPlayer = new UserPlayer("USER", this, userStrategy);
        aiPlayer1 = new AIPlayer("AI1", this, aiStrategyOne);
        aiPlayer2 = new AIPlayer("AI2", this, aiStrategyTwo);
        aiPlayer3 = new AIPlayer("AI3", this, aiStrategyThree);
        this.addObserver(userPlayer);
        this.addObserver(aiPlayer1);
        this.addObserver(aiPlayer2);
        this.addObserver(aiPlayer3);
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
                System.out.println("\nPlayer " + aiPlayer1.name + "'s turn");
                aiPlayer1.playTurn();
                board.printBoard();
                this.messageObservers();
            } else if (currentPlayerCheck == 2) {
            	 System.out.println("\nPlayer " + aiPlayer2.name + "'s turn");
                 aiPlayer2.playTurn();
                 board.printBoard();
                 this.messageObservers();
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
        } else if (aiPlayer1.hand.size() == 0) {
            gameWinner = aiPlayer1;
            return true;
        } else if(aiPlayer2.hand.size() == 0){
        	gameWinner = aiPlayer2;
            return true;
        }else if(aiPlayer3.hand.size() == 0){
        	gameWinner = aiPlayer3;
            return true;
        }else {
            return false;
        }
    }
    
    public void endGame() {
    	 gameState = GameStates.END;
    }

    public void updatePlayerHashMap() {
        this.playerHandCount.put(userPlayer.name, userPlayer.getHand().size());
        this.playerHandCount.put(aiPlayer1.name, aiPlayer1.getHand().size());
        this.playerHandCount.put(aiPlayer2.name, aiPlayer2.getHand().size());
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
