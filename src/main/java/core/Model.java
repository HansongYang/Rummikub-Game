package core;

import java.util.*;

import javafx.application.Platform;
import javafx.beans.property.*;

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
	public String [] strategy = new String[4]; 
	public boolean rigged = false;
	public boolean withoutPlay = false;
	
	public enum GameStates { PLAY, END }
	public GameStates gameState;
	
	public Player gameWinner;
	public UserPlayer userPlayer;
	public Player player2;
	public Player player3;
	public Player player4;
	public Player player5;
	public UserPlayer currentUserPlayer;
		
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

        if(numPlayer == 2) {
        	deck.dealTiles(player2);
        	if(withoutPlay) {
            	deck.dealTiles(player3);
            }
        }else if(numPlayer == 3) {
        	deck.dealTiles(player2);
        	deck.dealTiles(player3);
        	if(withoutPlay) {
            	deck.dealTiles(player4);
            }
        }else {
        	deck.dealTiles(player2);
        	deck.dealTiles(player3);
            deck.dealTiles(player4);
            if(withoutPlay) {
            	deck.dealTiles(player5);
            }
        }
        if(!withoutPlay) {
        	deck.dealTiles(userPlayer);
        }
        
        settleTurns();
        
        Iterator it = playerOrder.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Player player = (Player) pair.getKey();
            
            if(player instanceof UserPlayer) {
            	currentUserPlayer = (UserPlayer) player;
            	break;
            }
 
        }
        
        gameState = GameStates.PLAY;
    }
    
    public void initGameRigged(ArrayList<Tile> p1Hand, ArrayList<Tile> p2Hand, ArrayList<Tile> p3Hand, ArrayList<Tile> p4Hand ) {
        createGamePlayers();
        if(!withoutPlay) {
        	userPlayer.hand.addAll(p1Hand);
        }else {
        	player5.hand.addAll(p1Hand);
        }
        
        if(player2 != null) {
        	player2.hand.addAll(p2Hand);
        }
        if(player3 != null) {
        	player3.hand.addAll(p3Hand);
        }
        if(player4 != null) {
        	player4.hand.addAll(p4Hand);
        }
        
       // Standard order
        Map<Player, Integer> sortedPlayerOrder = new LinkedHashMap<Player, Integer>();
        sortedPlayerOrder.put(this.userPlayer, 5);
        sortedPlayerOrder.put(this.player2, 4);
        sortedPlayerOrder.put(this.player3, 3);
        sortedPlayerOrder.put(this.player4, 2);
    

        this.playerOrder = sortedPlayerOrder;
        
        Iterator it = playerOrder.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Player player = (Player) pair.getKey();
            
            if(player instanceof UserPlayer) {
            	currentUserPlayer = (UserPlayer) player;
            	break;
            }
        }
        
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
    	
    	if(!withoutPlay) {
    		userPlayer = new UserPlayer("USER", this, userStrategy);
            this.players.add(userPlayer);
    	}
        if(numPlayer == 2) {
        	if(strategy[0].equals("AI Strategy 1")) {
        		player2 = new AIPlayer("AI1", this, aiStrategyOne);
        	}else if(strategy[0].equals("AI Strategy 2")) {
        		player2 = new AIPlayer("AI1", this, aiStrategyTwo);
        	}else if(strategy[0].equals("AI Strategy 3")){
        		player2 = new AIPlayer("AI1", this, aiStrategyThree);
        	}else if(strategy[0].equals("AI Strategy 4")) {
        		player2 = new AIPlayer("AI1", this, aiStrategyFour);
        	}else {
        		player2 = new UserPlayer("User2", this, userStrategy);
        	}
        	this.players.add(player2);
        	this.addObserver(player2);
        	if(withoutPlay) {
        		if(strategy[1].equals("AI Strategy 1")) {
            		player3 = new AIPlayer("AI2", this, aiStrategyOne);
            	}else if(strategy[1].equals("AI Strategy 2")) {
            		player3 = new AIPlayer("AI2", this, aiStrategyTwo);
            	}else if(strategy[1].equals("AI Strategy 3")){
            		player3 = new AIPlayer("AI2", this, aiStrategyThree);
            	}else if(strategy[1].equals("AI Strategy 4")) {
            		player3 = new AIPlayer("AI2", this, aiStrategyFour);
            	}else {
            		player3 = new UserPlayer("User3", this, userStrategy);
            	}
        		this.players.add(player3);
        		this.addObserver(player3);
        	}
        }else if(numPlayer == 3) {
        	if(strategy[0].equals("AI Strategy 1")) {
        		player2 = new AIPlayer("AI1", this, aiStrategyOne);
        	}else if(strategy[0].equals("AI Strategy 2")) {
        		player2 = new AIPlayer("AI1", this, aiStrategyTwo);
        	}else if(strategy[0].equals("AI Strategy 3")){
        		player2 = new AIPlayer("AI1", this, aiStrategyThree);
        	}else if(strategy[0].equals("AI Strategy 4")) {
        		player2 = new AIPlayer("AI1", this, aiStrategyFour);
        	}else {
        		player2 = new UserPlayer("User2", this, userStrategy);
        	}
        	if(strategy[1].equals("AI Strategy 1")) {
        		player3 = new AIPlayer("AI2", this, aiStrategyOne);
        	}else if(strategy[1].equals("AI Strategy 2")) {
        		player3 = new AIPlayer("AI2", this, aiStrategyTwo);
        	}else if(strategy[1].equals("AI Strategy 3")){
        		player3 = new AIPlayer("AI2", this, aiStrategyThree);
        	}else if(strategy[1].equals("AI Strategy 4")) {
        		player3 = new AIPlayer("AI2", this, aiStrategyFour);
        	}else {
        		player3 = new UserPlayer("User3", this, userStrategy);
        	}
        	this.players.add(player2);
        	this.players.add(player3);
        	this.addObserver(player2);
        	this.addObserver(player3);
        	if(withoutPlay) {
        		if(strategy[2].equals("AI Strategy 1")) {
            		player4 = new AIPlayer("AI3", this, aiStrategyOne);
            	}else if(strategy[2].equals("AI Strategy 2")) {
            		player4 = new AIPlayer("AI3", this, aiStrategyTwo);
            	}else if(strategy[2].equals("AI Strategy 3")){
            		player4 = new AIPlayer("AI3", this, aiStrategyThree);
            	}else if(strategy[2].equals("AI Strategy 4")){
            		player4 = new AIPlayer("AI3", this, aiStrategyFour);
            	}else {
            		player4 = new UserPlayer("User4", this, userStrategy);
            	}
        		this.players.add(player4);
        		this.addObserver(player4);
        	}
        }else {
        	if(strategy[0].equals("AI Strategy 1")) {
        		player2 = new AIPlayer("AI1", this, aiStrategyOne);
        	}else if(strategy[0].equals("AI Strategy 2")) {
        		player2 = new AIPlayer("AI1", this, aiStrategyTwo);
        	}else if(strategy[0].equals("AI Strategy 3")){
        		player2 = new AIPlayer("AI1", this, aiStrategyThree);
        	}else if(strategy[0].equals("AI Strategy 4")) {
        		player2 = new AIPlayer("AI1", this, aiStrategyFour);
        	}else {
        		player2 = new UserPlayer("User2", this, userStrategy);
        	}
        	if(strategy[1].equals("AI Strategy 1")) {
        		player3 = new AIPlayer("AI2", this, aiStrategyOne);
        	}else if(strategy[1].equals("AI Strategy 2")) {
        		player3 = new AIPlayer("AI2", this, aiStrategyTwo);
        	}else if(strategy[1].equals("AI Strategy 3")) {
        		player3 = new AIPlayer("AI2", this, aiStrategyThree);
        	}else if(strategy[1].equals("AI Strategy 4")) {
        		player3 = new AIPlayer("AI2", this, aiStrategyFour);
        	}else {
        		player3 = new UserPlayer("User3", this, userStrategy);
        	}
        	if(strategy[2].equals("AI Strategy 1")) {
        		player4 = new AIPlayer("AI3", this, aiStrategyOne);
        	}else if(strategy[2].equals("AI Strategy 2")) {
        		player4 = new AIPlayer("AI3", this, aiStrategyTwo);
        	}else if(strategy[2].equals("AI Strategy 3")){
        		player4 = new AIPlayer("AI3", this, aiStrategyThree);
        	}else if(strategy[2].equals("AI Strategy 4")){
        		player4 = new AIPlayer("AI3", this, aiStrategyFour);
        	}else {
        		player4 = new UserPlayer("User4", this, userStrategy);
        	}
        	this.players.add(player2);
        	this.players.add(player3);
        	this.players.add(player4);
        	this.addObserver(player2);
        	this.addObserver(player3);
        	this.addObserver(player4);
        	if(withoutPlay) {
	        	if(strategy[3].equals("AI Strategy 1")) {
	        		player5 = new AIPlayer("AI4", this, aiStrategyOne);
	        	}else if(strategy[3].equals("AI Strategy 2")) {
	        		player5 = new AIPlayer("AI4", this, aiStrategyTwo);
	        	}else if(strategy[3].equals("AI Strategy 3")){
	        		player5 = new AIPlayer("AI4", this, aiStrategyThree);
	        	}else if(strategy[3].equals("AI Strategy 4")){
	        		player5 = new AIPlayer("AI4", this, aiStrategyFour);
	        	}else {
	        		player5 = new UserPlayer("User", this, userStrategy);
	        	}
	        	this.players.add(player5);
	        	this.addObserver(player5);
        	}
        }
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
	        if (!withoutPlay && userPlayer.hand.size() == 0) {
	        	gameState = GameStates.END;
	            gameWinner = userPlayer;
	            return true;
	        } else if (player2.hand.size() == 0) {
	        	gameState = GameStates.END;
	            gameWinner = player2;
	            return true;
	        }else if(withoutPlay && player3.hand.size() == 0){
	        	gameState = GameStates.END;
	            gameWinner = player3;
	            return true;
	        }else {
	        	return false;
	        }
    	}else if(numPlayer == 3) {
    		 if (!withoutPlay && userPlayer.hand.size() == 0) {
 	        	gameState = GameStates.END;
 	            gameWinner = userPlayer;
 	            return true;
 	        } else if (player2.hand.size() == 0) {
 	        	gameState = GameStates.END;
 	            gameWinner = player2;
 	            return true;
 	        }else if(player3.hand.size() == 0){
 	        	gameState = GameStates.END;
 	        	gameWinner = player3;
 	            return true;
 	        }else if(withoutPlay && player4.hand.size() == 0){
 	        	gameState = GameStates.END;
 	        	gameWinner = player4;
 	            return true;
 	        }else {
 	        	return false;
 	        }
    	}else {
    		 if (!withoutPlay && userPlayer.hand.size() == 0) {
  	        	gameState = GameStates.END;
  	            gameWinner = userPlayer;
  	            return true;
  	        } else if (player2.hand.size() == 0) {
  	        	gameState = GameStates.END;
  	            gameWinner = player2;
  	            return true;
  	        }else if(player3.hand.size() == 0){
  	        	gameState = GameStates.END;
  	        	gameWinner = player3;
  	            return true;
  	        }
    		else if(player4.hand.size() == 0){
            	gameState = GameStates.END;
            	gameWinner = player4;
                return true;
            }else if(withoutPlay && player5.hand.size() == 0){
            	gameState = GameStates.END;
            	gameWinner = player5;
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
    	if(!withoutPlay) {
    		this.playerHandCount.put(userPlayer.name, userPlayer.getHand().size());
    	}
        if(numPlayer == 2) {
        	this.playerHandCount.put(player2.name, player2.getHand().size());
        	if(withoutPlay) {
        		this.playerHandCount.put(player3.name, player3.getHand().size());
        	}
        }else if (numPlayer == 3) {
        	this.playerHandCount.put(player2.name, player2.getHand().size());
        	this.playerHandCount.put(player3.name, player3.getHand().size());
        	if(withoutPlay) {
        		this.playerHandCount.put(player4.name, player4.getHand().size());
        	}
        }else {
        	this.playerHandCount.put(player2.name, player2.getHand().size());
        	this.playerHandCount.put(player3.name, player3.getHand().size());
        	this.playerHandCount.put(player4.name, player4.getHand().size());
        	if(withoutPlay) {
        		this.playerHandCount.put(player5.name, player4.getHand().size());
        	}
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
