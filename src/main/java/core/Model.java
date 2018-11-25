package core;

import java.util.*;

import javafx.application.Platform;
import javafx.beans.property.*;
import java.util.Timer;
import java.util.TimerTask;

public class Model implements Observable {
		
	public ArrayList<Observer> observers;
	public HashMap<String, Integer> playerHandCount;  
    public Map<Player, Integer> playerOrder = new LinkedHashMap<Player, Integer>();
    private ArrayList<Player> players = new ArrayList<Player>();
	private Deck deck = new Deck();
	private Board board = new Board();
	public int interval = 120;
	private Timer timer;
	private int times = 1;
	public int numPlayer;
	public String [] strategy = new String[3]; 
	
	public enum GameStates { PLAY, END }
	public GameStates gameState;
	
	public Player gameWinner;
	public AIPlayer aiPlayer1;
	public AIPlayer aiPlayer2;
	public AIPlayer aiPlayer3;
	public AIPlayer aiPlayer4;
	public UserPlayer userPlayer;
	
	private SimpleStringProperty value = new SimpleStringProperty(this, "");

    public Model() {
        value.setValue("120");
    }

    public String getValue(){
        return value.get();
    }

    public void setValue(String value){
        this.value.setValue(value);
    }

    public StringProperty valueProperty(){
        return value;
    }

    public void startClock() {
    	timer = new Timer();
    	timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	Platform.runLater(() ->setValue(Integer.toString(setInterval())));
            }
        }, 1000L, 3500L * times);
    	times++;
    }
    
    public void stopClock() {
    	timer.cancel();
    	interval = 120;
    }
    
    private final int setInterval() {
        if (interval == 1)
            timer.cancel();
        return interval--;
    }
    
	// Start function which initializes players, deals tiles, and begins main game loop
    public void initGame() {
        createGamePlayers();
        deck.dealTiles(userPlayer);
        if(numPlayer == 2) {
        	deck.dealTiles(aiPlayer1);
        }else if(numPlayer == 3) {
        	deck.dealTiles(aiPlayer1);
        	deck.dealTiles(aiPlayer2);
        }else {
        	deck.dealTiles(aiPlayer1);
        	deck.dealTiles(aiPlayer2);
            deck.dealTiles(aiPlayer3);
        }
        settleTurns();
        gameState = GameStates.PLAY;
    }
    
    public void createGamePlayers() {
        observers = new ArrayList<Observer>();
        playerHandCount = new HashMap<String, Integer>();
    	PlayerStrategy<? super UserPlayer> userStrategy = new UserStrategy();
    	PlayerStrategy<? super AIPlayer> aiStrategyOne = new AIStrategyOne();
    	PlayerStrategy<? super AIPlayer> aiStrategyTwo = new AIStrategyTwo();
    	PlayerStrategy<? super AIPlayer> aiStrategyThree = new AIStrategyThree();
    	PlayerStrategy<? super AIPlayer> aiStrategyFour = new AIStrategyFour();
    	
        userPlayer = new UserPlayer("USER", this, userStrategy);
        if(numPlayer == 2) {
        	if(strategy[0].equals("AI Strategy 1")) {
        		aiPlayer1 = new AIPlayer("AI1", this, aiStrategyOne);
        	}else if(strategy[0].equals("AI Strategy 2")) {
        		aiPlayer1 = new AIPlayer("AI1", this, aiStrategyTwo);
        	}else if(strategy[0].equals("AI Strategy 3")){
        		aiPlayer1 = new AIPlayer("AI1", this, aiStrategyThree);
        	}else {
        		aiPlayer1 = new AIPlayer("AI1", this, aiStrategyFour);
        	}
        	 this.players.add(aiPlayer1);
        }else if(numPlayer == 3) {
        	if(strategy[0].equals("AI Strategy 1")) {
        		aiPlayer1 = new AIPlayer("AI1", this, aiStrategyOne);
        	}else if(strategy[0].equals("AI Strategy 2")) {
        		aiPlayer1 = new AIPlayer("AI1", this, aiStrategyTwo);
        	}else if(strategy[0].equals("AI Strategy 3")){
        		aiPlayer1 = new AIPlayer("AI1", this, aiStrategyThree);
        	}else if(strategy[0].equals("AI Strategy 4")) {
        		aiPlayer1 = new AIPlayer("AI1", this, aiStrategyFour);
        	}
        	if(strategy[1].equals("AI Strategy 1")) {
        		aiPlayer2 = new AIPlayer("AI2", this, aiStrategyOne);
        	}else if(strategy[1].equals("AI Strategy 2")) {
        		aiPlayer2 = new AIPlayer("AI2", this, aiStrategyTwo);
        	}else if(strategy[1].equals("AI Strategy 3")){
        		aiPlayer2 = new AIPlayer("AI2", this, aiStrategyThree);
        	}else {
        		aiPlayer2 = new AIPlayer("AI2", this, aiStrategyFour);
        	}
        	this.players.add(aiPlayer1);
        	this.players.add(aiPlayer2);
        	this.addObserver(aiPlayer1);
        	this.addObserver(aiPlayer2);
        }else {
        	if(strategy[0].equals("AI Strategy 1")) {
        		aiPlayer1 = new AIPlayer("AI1", this, aiStrategyOne);
        	}else if(strategy[0].equals("AI Strategy 2")) {
        		aiPlayer1 = new AIPlayer("AI1", this, aiStrategyTwo);
        	}else if(strategy[0].equals("AI Strategy 3")){
        		aiPlayer1 = new AIPlayer("AI1", this, aiStrategyThree);
        	}else if(strategy[0].equals("AI Strategy 4")) {
        		aiPlayer1 = new AIPlayer("AI1", this, aiStrategyFour);
        	}
        	if(strategy[1].equals("AI Strategy 1")) {
        		aiPlayer2 = new AIPlayer("AI2", this, aiStrategyOne);
        	}else if(strategy[1].equals("AI Strategy 2")) {
        		aiPlayer2 = new AIPlayer("AI2", this, aiStrategyTwo);
        	}else if(strategy[1].equals("AI Strategy 3")) {
        		aiPlayer2 = new AIPlayer("AI2", this, aiStrategyThree);
        	}else if(strategy[1].equals("AI Strategy 4")) {
        		aiPlayer2 = new AIPlayer("AI2", this, aiStrategyFour);
        	}
        	if(strategy[2].equals("AI Strategy 1")) {
        		aiPlayer3 = new AIPlayer("AI3", this, aiStrategyOne);
        	}else if(strategy[2].equals("AI Strategy 2")) {
        		aiPlayer3 = new AIPlayer("AI3", this, aiStrategyTwo);
        	}else if(strategy[2].equals("AI Strategy 3")){
        		aiPlayer3 = new AIPlayer("AI3", this, aiStrategyThree);
        	}else {
        		aiPlayer3 = new AIPlayer("AI3", this, aiStrategyFour);
        	}
        	this.players.add(aiPlayer1);
        	this.players.add(aiPlayer2);
        	this.players.add(aiPlayer3);
        	this.addObserver(aiPlayer1);
        	this.addObserver(aiPlayer2);
        	this.addObserver(aiPlayer3);
        }
        this.players.add(userPlayer);
    }
    
	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
	}
	
	public void setStrategy(String[] strategy) {
		this.strategy = strategy;
	}
    
    public Deck getDeck() {
    	return deck;
    }
    
    public Board getBoard() {
    	return board;
    }

    public void setBoard(Board board) { this.board = board; }

    public boolean gameWinCheck() {
    	if(numPlayer == 2) {
	        if (userPlayer.hand.size() == 0) {
	        	gameState = GameStates.END;
	            gameWinner = userPlayer;
	            return true;
	        } else if (aiPlayer1.hand.size() == 0) {
	        	gameState = GameStates.END;
	            gameWinner = aiPlayer1;
	            return true;
	        }else {
	        	return false;
	        }
    	}else if(numPlayer == 3) {
    		 if (userPlayer.hand.size() == 0) {
 	        	gameState = GameStates.END;
 	            gameWinner = userPlayer;
 	            return true;
 	        } else if (aiPlayer1.hand.size() == 0) {
 	        	gameState = GameStates.END;
 	            gameWinner = aiPlayer1;
 	            return true;
 	        }else if(aiPlayer2.hand.size() == 0){
 	        	gameState = GameStates.END;
 	        	gameWinner = aiPlayer2;
 	            return true;
 	        }else {
 	        	return false;
 	        }
    	}else {
    		 if (userPlayer.hand.size() == 0) {
  	        	gameState = GameStates.END;
  	            gameWinner = userPlayer;
  	            return true;
  	        } else if (aiPlayer1.hand.size() == 0) {
  	        	gameState = GameStates.END;
  	            gameWinner = aiPlayer1;
  	            return true;
  	        }else if(aiPlayer2.hand.size() == 0){
  	        	gameState = GameStates.END;
  	        	gameWinner = aiPlayer2;
  	            return true;
  	        }
    		else if(aiPlayer3.hand.size() == 0){
            	gameState = GameStates.END;
            	gameWinner = aiPlayer3;
                return true;
            }else {
                return false;
            }
    	}
    }
    
    public void endGame() {
   	 gameState = GameStates.END;
    }
    
    public void updatePlayerHashMap() {
        this.playerHandCount.put(userPlayer.name, userPlayer.getHand().size());
        if(numPlayer == 2) {
        	this.playerHandCount.put(aiPlayer1.name, aiPlayer1.getHand().size());
        }else if (numPlayer == 3) {
        	this.playerHandCount.put(aiPlayer1.name, aiPlayer1.getHand().size());
        	this.playerHandCount.put(aiPlayer2.name, aiPlayer2.getHand().size());
        }else {
        	this.playerHandCount.put(aiPlayer1.name, aiPlayer1.getHand().size());
        	this.playerHandCount.put(aiPlayer2.name, aiPlayer2.getHand().size());
        	this.playerHandCount.put(aiPlayer3.name, aiPlayer3.getHand().size());
        }
    }

    public void messageObservers() {
        this.updatePlayerHashMap();
        for (Observer o: observers) {
            o.update(playerHandCount);
        }
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

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }
}
