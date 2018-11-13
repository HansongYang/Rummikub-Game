package core;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene; 
import javafx.scene.paint.Color; 
import javafx.stage.Stage;
import javafx.scene.control.*; 
import javafx.scene.text.Text; 
import javafx.geometry.*; 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.shape.*;


public class mainWindow extends Application implements View{
	public Model model;
	private Scene scene;
	private String playernumber;
	private boolean time = false;
	private ToggleGroup playerGroup, timer;
	private TextField nameText;
	private GridPane panel;
	private ArrayList <Label> tiles = new ArrayList<Label>();
	private String name;
	private Stage stage;
	private int position = 2;
	private int boardPositionX = 2;
	private int boardPositionY = 0;
	private Button play, draw;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		stage = primaryStage;
		question();
	}
	
	public void question() {
		Button start = new Button("Start Game"); 
		start.setStyle("-fx-background-color: red; -fx-textfill: black;"); 
		Text label = new Text("Welcome to Rummikub Game, what is your name?");
	    nameText = new TextField(); 
	    
	    Text label2 = new Text("Which player do you want to choose?"); 
		playerGroup = new ToggleGroup(); 
	    RadioButton player1 = new RadioButton("Player 1"); 
	    player1.setToggleGroup(playerGroup);
	    player1.setSelected(true);
	    RadioButton player2 = new RadioButton("Player 2"); 
	    player2.setToggleGroup(playerGroup); 
	    RadioButton player3 = new RadioButton("Player 3"); 
	    player3.setToggleGroup(playerGroup); 
	    RadioButton player4 = new RadioButton("Player 4"); 
	    player4.setToggleGroup(playerGroup); 
	    
	    label.setStyle("-fx-font: normal bold 30px 'serif'");
	    label2.setStyle("-fx-font: normal 20px 'serif'");
	    
	    Text label3 = new Text("Do you want to use 2 minutes timer for your turn?");
	    label3.setStyle("-fx-font: normal 20px 'serif'"); 
	    timer = new ToggleGroup(); 
	    RadioButton yes = new RadioButton("yes"); 
	    yes.setToggleGroup(timer); 
	    RadioButton no = new RadioButton("no"); 
	    no.setToggleGroup(timer); 
	    no.setSelected(true);
	    
	    panel = new GridPane();
	    panel.setAlignment(Pos.CENTER); 
	    panel.add(label, 0, 0); 
	    panel.add(nameText, 0, 1);
	    panel.add(label2, 0, 2);
	    panel.add(player1, 0, 3);       
	    panel.add(player2, 0, 4);
	    panel.add(player3, 0, 5);
	    panel.add(player4, 0, 6);
	    panel.add(label3, 0, 7);
	    panel.add(yes, 0, 8);
	    panel.add(no, 0, 9);
	    panel.add(start, 2, 11);
	    panel.setStyle("-fx-background-color: PALEGREEN;");
	    panel.setMinSize(1300, 800); 
	    
	    scene = new Scene(panel); 

	    start.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	name = nameText.getText();
            	String[] toggle = playerGroup.getSelectedToggle().toString().split("\\'");
            	playernumber = toggle[1];
            	toggle = timer.getSelectedToggle().toString().split("\\'");
            	if(toggle[1].equals("yes")){
            		time = true;
            	}else {
            		time = false;
            	}
              	panel.getChildren().clear();
                try {
					game();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	    stage.setScene(scene);
	    stage.setTitle("Rummikub Game");
	    stage.show();
	}
	
	public void game() {
		model = new Model();
		model.initGame();
		displayPlayerHand();
    	displayHand(model.userPlayer.getHand());
    	
    	play();
    	
    	Button restart = new Button("Restart Game"); 
    	panel.add(restart, 70, 700, 20, 20);
    	restart.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent event) {
            	panel.getChildren().clear();
             	question();
             }
         });
	}
	
	public void play() {
    	play = new Button("Play Tiles"); 
    	panel.add(play, 30, 700, 20, 20);
    	play.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent event) {
         		for(int i = 0; i < tiles.size(); i++) {
        			Label newTile = tiles.get(i);
                	panel.getChildren().remove(tiles.get(i));
        			panel.add(newTile, ++boardPositionX, boardPositionY);
        		}
        		++boardPositionX;
        		if(boardPositionX >= 16) {
        			System.out.println(boardPositionX);
        			boardPositionY += 3;
        			boardPositionX = 3;
        		}
        		tiles.clear();
        		panel.getChildren().remove(draw);
        		Button pass = new Button("Pass");
        		panel.add(pass, 50, 700, 20, 20);
            	pass.setOnAction(new EventHandler<ActionEvent>() {
                     public void handle(ActionEvent event) {
                    	 indicateTurn(model.aiPlayer3);
                     }
                });
             }
        });
    	
    	draw = new Button("Draw Tiles"); 
    	panel.add(draw, 50, 700, 20, 20);
    	draw.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent event) {
            	 displayDrawnTile(model.getDeck().drawTile());
             }
        });
	}
	
	public static void main(String args[]){          
	   launch(args);     
	}

	@Override
	public void indicateTurn(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayPlayerHand() {
		// TODO Auto-generated method stub
    	Label playername = new Label("");
    	
    	if(!playernumber.equals("Player 1")) {
    		playername = new Label("Player 1  ");
    		panel.add(playername, 1,0);
    	}else {
    		playername = new Label("Player 2  ");
    		panel.add(playername, 1,0);
    	}
    	
    	for(int i = 0; i < model.aiPlayer1.getHand().size(); i++) {
    		model.aiPlayer1.getHand().sortTilesByColour();
	    	Label tile = new Label("    " + Integer.toString(model.aiPlayer1.getHand().getTile(i).getRank()));
	    	tile.setMinSize(40,50);
	    	if(model.aiPlayer1.getHand().getTile(i).getColour() == 'G') {
	    		tile.setTextFill(Color.GREEN);
	    	}else if(model.aiPlayer1.getHand().getTile(i).getColour() == 'R') {
	    		tile.setTextFill(Color.RED);
	    	} else if (model.aiPlayer1.getHand().getTile(i).getColour() == 'B'){
	    		tile.setTextFill(Color.BLUE);
	    	} else if(model.aiPlayer1.getHand().getTile(i).getColour() == 'O') {
	    		tile.setTextFill(Color.ORANGE);
	    	} else {
	    		tile.setTextFill(Color.BLACK);
	    	}
	    	tile.setStyle("-fx-border-color: BLACK;");
	    	panel.add(tile, i+2, 0);
    	}
    	
    	if(playernumber.equals("Player 4")){
    		playername = new Label("Player 2");
    		panel.add(playername, 0,500);
    	} else if(!playernumber.equals("Player 3")) {
    		playername = new Label("Player 3");
    		panel.add(playername, 0,500);
    	} else {
    		playername = new Label("Player 2");
    		panel.add(playername, 0,500);
    	}
    	
    	for(int i = 0; i < model.aiPlayer2.getHand().size(); i++) {
    		model.aiPlayer2.getHand().sortTilesByColour();
	    	Label tile = new Label("    " + Integer.toString(model.aiPlayer2.getHand().getTile(i).getRank()));
	    	tile.setMinSize(40,50);
	    	if(model.aiPlayer2.getHand().getTile(i).getColour() == 'G') {
	    		tile.setTextFill(Color.GREEN);
	    	}else if(model.aiPlayer2.getHand().getTile(i).getColour() == 'R') {
	    		tile.setTextFill(Color.RED);
	    	} else if (model.aiPlayer2.getHand().getTile(i).getColour() == 'B'){
	    		tile.setTextFill(Color.BLUE);
	    	} else if(model.aiPlayer2.getHand().getTile(i).getColour() == 'O') {
	    		tile.setTextFill(Color.ORANGE);
	    	} else {
	    		tile.setTextFill(Color.BLACK);
	    	}
	    	tile.setRotate(90);
	    	tile.setStyle("-fx-border-color: BLACK;");
	    	panel.add(tile, 0, i);
    	}
    	
    	if(!playernumber.equals("Player 4")) {
    		playername = new Label("Player 4");
    		panel.add(playername, 700,500);
    	}else {
    		playername = new Label("Player 3");
    		panel.add(playername, 700,500);
    	}
    	
    	for(int i = 0; i < model.aiPlayer3.getHand().size(); i++) {
    		model.aiPlayer3.getHand().sortTilesByColour();
	    	Label tile = new Label("    " + Integer.toString(model.aiPlayer3.getHand().getTile(i).getRank()));
	    	tile.setMinSize(40,50);
	    	if(model.aiPlayer3.getHand().getTile(i).getColour() == 'G') {
	    		tile.setTextFill(Color.GREEN);
	    	}else if(model.aiPlayer3.getHand().getTile(i).getColour() == 'R') {
	    		tile.setTextFill(Color.RED);
	    	} else if (model.aiPlayer3.getHand().getTile(i).getColour() == 'B'){
	    		tile.setTextFill(Color.BLUE);
	    	} else if(model.aiPlayer3.getHand().getTile(i).getColour() == 'O') {
	    		tile.setTextFill(Color.ORANGE);
	    	} else {
	    		tile.setTextFill(Color.BLACK);
	    	}
	    	tile.setRotate(-90);
	    	tile.setStyle("-fx-border-color: BLACK;");
	    	panel.add(tile, 800, i);
    	}
	}

	@Override
	public void displayHand(Hand hand) {
		// TODO Auto-generated method stub
		Label playername = new Label(playernumber +": "+ name);
    	panel.add(playername, 0,700, 700,700);
    	hand.sortTilesByColour();
    	
		for(int i = 0; i < 14; i++) {
	    	Label tile = new Label("    " + Integer.toString(hand.getTile(i).getRank()));
	    	tile.setMinSize(40,50);
	    	if(hand.getTile(i).getColour() == 'G') {
	    		tile.setTextFill(Color.GREEN);
	    	}else if(hand.getTile(i).getColour() == 'R') {
	    		tile.setTextFill(Color.RED);
	    	} else if (hand.getTile(i).getColour() == 'B'){
	    		tile.setTextFill(Color.BLUE);
	    	} else if(hand.getTile(i).getColour() == 'O') {
	    		tile.setTextFill(Color.ORANGE);
	    	} else {
	    		tile.setTextFill(Color.BLACK);
	    	}
	    	tile.setStyle("-fx-border-color: BLACK;");
	    	
	    	tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent e) {
	                if(panel.getRowIndex(tile) == 600) {
	                	Label newTile = tile;
	                	int columnIndex = panel.getColumnIndex(tile);
	                	panel.getChildren().remove(tile);
	                	
	                	tiles.remove(tile);
	                	panel.add(newTile, columnIndex, 700, 700, 700);
	                }else {
	                	Label newTile = tile;
	                	int columnIndex = panel.getColumnIndex(tile);
	                	panel.getChildren().remove(tile);
	                	tiles.add(newTile);
	                	
	                	panel.add(newTile, columnIndex, 600, 700, 700);
	                }
	            }
	        });
	    	panel.add(tile, i+2, 700, 700,700);
    	}
	}

	@Override
	public void displayBoard(Board board) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMeld(Meld meld) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayWinner(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayFinalTileCounts() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayTurnOptions_Pass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayTurnOptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void indicateWrongInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void indicateUserEndsGame(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayDrawnTile(Tile tile) {
		// TODO Auto-generated method stub
    	Label t = new Label("    " + Integer.toString(tile.getRank()));
    	t.setMinSize(40,50);
    	if(tile.getColour() == 'G') {
    		t.setTextFill(Color.GREEN);
    	}else if(tile.getColour() == 'R') {
    		t.setTextFill(Color.RED);
    	} else if (tile.getColour() == 'B'){
    		t.setTextFill(Color.BLUE);
    	} else if(tile.getColour() == 'O') {
    		t.setTextFill(Color.ORANGE);
    	} else {
    		t.setTextFill(Color.BLACK);
    	}
    	t.setStyle("-fx-border-color: BLACK;");
    	
    	t.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(panel.getRowIndex(t) == 600) {
                	Label newTile = t;
                	int columnIndex = panel.getColumnIndex(t);
                	panel.getChildren().remove(tile);
                	
                	tiles.remove(tile);
                	panel.add(newTile, columnIndex, 700, 700, 700);
                }else {
                	Label newTile = t;
                	int columnIndex = panel.getColumnIndex(t);
                	panel.getChildren().remove(tile);
                	tiles.add(newTile);
                	
                	panel.add(newTile, columnIndex, 600, 700, 700);
                }
            }
        });
    	panel.add(t, position, 450); 
    	position++;
	}

	@Override
	public void displayCreateAnotherMeldOption() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void indicateAvailableTiles() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void indicateMeldsLessThan30() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void indicateNoTileOnBoard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayTileToExistingMeldOptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayTileToMeldSelection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMeldSelection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void indicateInvalidMeld() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayTileInSelectedMeldSelection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayTileToMeldPositionSelection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayTileSelection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printTurns() {
		// TODO Auto-generated method stub
		
	} 
}
