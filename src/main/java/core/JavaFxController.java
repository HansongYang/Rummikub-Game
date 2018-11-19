package core;

import java.util.ArrayList;
import java.util.Scanner;

import core.Model.GameStates;

public class JavaFxController implements Controller {

	Scanner reader = new Scanner(System.in);
	public Model model; 
	
	public JavaFxController( Model model) {
		this.model = model;
	}
	
	public int turnOptionInput() {
		//System.out.println("JAVAFX INPUT");
		int choice = 1;

		return choice;
	}

	public Tile drawTile() {
		if(model.getDeck().getDeckSize() == 0) {
			return null;
		}
		Player player = model.userPlayer;
		
		Tile newTile = model.getDeck().drawTile();
		player.hand.add(newTile);
		
		return newTile;
	}
	
	public boolean createMeld(ArrayList<Tile> tiles) {
		UserPlayer player = model.userPlayer;
		
		Meld meld = player.createMeld(tiles, player.hand);
		
		if(meld != null) {
			
			model.getBoard().addMeld(meld);
			
			return true;
		}else {
			return false;
		}
		
	}

	//Returns true if an AI player wins on this turn
	public boolean playAITurns() {
		model.aiPlayer1.playTurn();
		model.messageObservers();
		if (model.gameWinCheck()) {
			return true;
		}
		model.aiPlayer2.playTurn();
		model.messageObservers();
		if (model.gameWinCheck()) {
			return true;
		}
		model.aiPlayer3.playTurn();
		model.messageObservers();
		if (model.gameWinCheck()) {
			return true;
		}
		
		return false;
	}

	public void playTilestoMeldFront(ArrayList<Tile> tiles, int meldId) {
		Meld meldToAdd = new Meld();
		for(Tile t : tiles) {
			meldToAdd.add(t);		
		}
		
		if(model.getBoard().addTileToMeldBeginning(meldId, meldToAdd)) {
			for(Tile t : tiles) {
				model.userPlayer.hand.remove(t);
			}
		}
		
	}

	public void playTilestoMeldBack(ArrayList<Tile> tiles, int meldId) {
		Meld meldToAdd = new Meld();
		for(Tile t : tiles) {
			meldToAdd.add(t);		
		}
		
		if(model.getBoard().addTileToMeldEnd(meldId, meldToAdd)) {
			for(Tile t : tiles) {
				model.userPlayer.hand.remove(t);
			}
		}
	}

	
	
}
