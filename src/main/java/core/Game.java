package core;

import java.util.*;

public class Game implements Observable{

    private Deck deck = new Deck();
    private Board board = new Board();
    private Map<Player, Integer> playerOrder = new LinkedHashMap<Player, Integer>();
    private ArrayList<Player> players = new ArrayList<Player>();

    public ArrayList<Observer> observers;
    public HashMap<String, Integer> playerHandCount;
    public enum GameStates { PLAY, END }
    public GameStates gameState;
    public Player gameWinner;
    public AIPlayer aiPlayer1;
    public AIPlayer aiPlayer2;
    public AIPlayer aiPlayer3;
    public AIPlayer aiPlayer4;
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
        settleTurns();
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
        this.players.add(userPlayer);
        this.players.add(aiPlayer1);
        this.players.add(aiPlayer2);
        this.players.add(aiPlayer3);
        this.addObserver(userPlayer);
        this.addObserver(aiPlayer1);
        this.addObserver(aiPlayer2);
        this.addObserver(aiPlayer3);
    }

    public void gameLoop() {
        printTurns();


        while(gameState == GameStates.PLAY) {

            Iterator it = this.playerOrder.entrySet().iterator();

            while (it.hasNext()) {

                Map.Entry pair = (Map.Entry)it.next();
                Player player = (Player)pair.getKey();

                System.out.println("\nPlayer " + player.name + "'s turn");
                printPlayerHand(player);
                player.playTurn();
                if(gameState == GameStates.END) break;
                board.printBoard();
                this.messageObservers();
            }

            if(deck.getDeckSize() == 0) {
				System.out.println("The deck is empty.");
            	int value1 = Math.min(userPlayer.getHand().size(), aiPlayer1.getHand().size());
            	int value2 = Math.min(aiPlayer2.getHand().size(), aiPlayer3.getHand().size());
            	int minimum = Math.min(value1, value2);
            	if(minimum == userPlayer.getHand().size()) {
            		System.out.println("User has "+userPlayer.getHand().size() +" tiles. Player 1 has " + aiPlayer1.getHand().size()+" tiles. Player 2 has " + aiPlayer2.getHand().size() + " tiles. Player 3 has " + aiPlayer3.getHand().size() + " tiles.");
            		System.out.println(userPlayer.name + " wins the game!");
            	}else if(minimum == aiPlayer1.getHand().size()) {
            		System.out.println("User has "+userPlayer.getHand().size() +" tiles. Player 1 has " + aiPlayer1.getHand().size()+" tiles. Player 2 has " + aiPlayer2.getHand().size() + " tiles. Player 3 has " + aiPlayer3.getHand().size() + " tiles.");
            		System.out.println(aiPlayer1.name + " wins the game!");
            	}else if(minimum == aiPlayer2.getHand().size()) {
            		System.out.println("User has "+userPlayer.getHand().size() +" tiles. Player 1 has " + aiPlayer1.getHand().size()+" tiles. Player 2 has " + aiPlayer2.getHand().size() + " tiles. Player 3 has " + aiPlayer3.getHand().size() + " tiles.");
            		System.out.println(aiPlayer2.name + " wins the game!");
            	}else {
            		System.out.println("User has "+userPlayer.getHand().size() +" tiles. Player 1 has " + aiPlayer1.getHand().size()+" tiles. Player 2 has " + aiPlayer2.getHand().size() + " tiles. Player 3 has " + aiPlayer3.getHand().size() + " tiles.");
            		System.out.println(aiPlayer3.name + " wins the game!");
            	}
            	gameState = GameStates.END;
            }

            if (gameWinCheck()) System.out.println(gameWinner.name + " wins the game!");
        }
    }
    
    public Deck getDeck() {
    	return deck;
    }
    
    public Board getBoard() {
    	return board;
    }

    public void settleTurns() {
        Deck turnDeck = new Deck();

        for (int i = 0; i < this.players.size(); i++) {
            Tile tile = turnDeck.drawTile();
            this.playerOrder.put(this.players.get(i), tile.getRank());
        }

        // Credit: https://www.mkyong.com/java/how-to-sort-a-map-in-java/
        List<Map.Entry<Player, Integer>> list = new LinkedList<Map.Entry<Player, Integer>>(this.playerOrder.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Player, Integer>>() {
            public int compare(Map.Entry<Player, Integer> o1, Map.Entry<Player, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        Map<Player, Integer> sortedPlayerOrder = new LinkedHashMap<Player, Integer>();
        for (Map.Entry<Player, Integer> entry : list) {
            sortedPlayerOrder.put(entry.getKey(), entry.getValue());
        }

        this.playerOrder = sortedPlayerOrder;
    }

    public void printTurns() {
        Iterator it = this.playerOrder.entrySet().iterator();
        System.out.print("Turn Order: ");

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Player player = (Player)pair.getKey();
            System.out.print(player.name + " ");
        }
    }

    public void printPlayerHand(Player player) {
        player.getHand().sortTilesByColour();
        player.getHand().printHand();
    }

    public boolean gameWinCheck() {
        if (userPlayer.hand.size() == 0) {
        	gameState = GameStates.END;
            gameWinner = userPlayer;
            return true;
        } else if (aiPlayer1.hand.size() == 0) {
        	gameState = GameStates.END;
            gameWinner = aiPlayer1;
            return true;
        } else if(aiPlayer2.hand.size() == 0){
        	gameState = GameStates.END;
        	gameWinner = aiPlayer2;
            return true;
        }else if(aiPlayer3.hand.size() == 0){
        	gameState = GameStates.END;
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
        this.playerHandCount.put(aiPlayer3.name, aiPlayer3.getHand().size());
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
