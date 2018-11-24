package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

import core.Hand.SortByNumber;
import core.Model.GameStates;

public class JavaFxController implements Controller {

	protected MeldValidatorService meldValidatorService = new MeldValidatorService();
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
			player.meldsInHand.add(meld);
			
			//model.getBoard().addMeld(meld);
			
			return true;
		}else {
			return false;
		}
		
	}

	//Returns true if an AI player wins on this turn
	public boolean playAITurns() {
		if(model.numPlayer == 2) {
			model.aiPlayer1.playTurn();
			model.messageObservers();
			if (model.gameWinCheck()) {
				return true;
			}
		}else if(model.numPlayer ==3) {
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
		}else {
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
		}
		
		return false;
	}

	public boolean playTilestoMeldFront(ArrayList<Tile> tiles, int meldId) {
		Meld meldToAdd = new Meld();
		for(Tile t : tiles) {
			meldToAdd.add(t);		
		}
		
		if(model.getBoard().addTileToMeldBeginning(meldId, meldToAdd)) {
			for(Tile t : tiles) {
				model.userPlayer.hand.remove(t);
			}
			return true;
		}
		return false;
	}

	public boolean playTilestoMeldBack(ArrayList<Tile> tiles, int meldId) {
		Meld meldToAdd = new Meld();
		for(Tile t : tiles) {
			meldToAdd.add(t);		
		}
		
		if(model.getBoard().addTileToMeldEnd(meldId, meldToAdd)) {
			for(Tile t : tiles) {
				model.userPlayer.hand.remove(t);
			}
			return true;
		}
		return false;
	}

	public boolean playMeldsToTable() {
		Player player = model.userPlayer;
		
		if(!player.initial30Played && player.totalAllMelds(player.meldsInHand) < 30) {
			return false;
		}else {
		
			for(int i = 0; i < player.meldsInHand.size(); i++) {
				model.getBoard().currentMelds.add(player.meldsInHand.get(i));
			}
			player.initial30Played = true;
		}
		
		player.meldsInHand.clear();
		
		return true;
	}

	public void returnMeldsToHand() {
		Player player = model.userPlayer;
		
		
		for(int i = 0; i < model.userPlayer.meldsInHand.size(); i++) {
			Meld meld = model.userPlayer.meldsInHand.get(i);
			for(int j = 0; j < meld.size(); j++) {
				player.hand.add(meld.getTile(j));
			}
		}
		model.userPlayer.meldsInHand.clear();
		
	}

	@Override
	public boolean reuseBoardTiles(Map<Meld, ArrayList<Tile>> tilesFromBoard, ArrayList<Tile> playerTiles) {
		Player player = model.userPlayer;
		
		ArrayList<ArrayList<Tile>> meldsToValidate = new ArrayList<ArrayList<Tile>>();
		Meld newMeld = null;
		Hand potentialNewMeld = new Hand();//Hand used so we can call sort
		
		for(Meld boardMeld : tilesFromBoard.keySet()) {
			ArrayList<Tile> tiles = tilesFromBoard.get(boardMeld);
				
			
			potentialNewMeld.getTiles().addAll(tiles);
			
			Meld updatedBoardMeld = new Meld(boardMeld.getTiles());
			
			for(int i = 0; i < tiles.size(); i++) {
				updatedBoardMeld.remove(tiles.get(i));
			}		
			
			if(updatedBoardMeld.getTiles().size() > 0) {
				meldsToValidate.add(updatedBoardMeld.getTiles());
			}
			
		}
		potentialNewMeld.getTiles().addAll(playerTiles);
		potentialNewMeld.sortTilesByNumber();
		
		meldsToValidate.add(potentialNewMeld.getTiles());
		
		
		//Validate melds 
		for(int i  = 0; i < meldsToValidate.size(); i++) {
					
			if(!meldValidatorService.isValidMeld(meldsToValidate.get(i))){
				return false;
			}
		}
		
		
		//All are valid, remove old version of melds on board
		for(Meld meld : tilesFromBoard.keySet()) {
			model.getBoard().currentMelds.remove(meld);
		}
		
		//Update board with new melds
		for(int i  = 0; i < meldsToValidate.size(); i++) {
			
			Meld meld = new Meld();
			for(int j = 0; j < meldsToValidate.get(i).size();j++) {
				meld.add(meldsToValidate.get(i).get(j));
			}
			
			model.getBoard().addMeld(meld);
		}
		
		//Removed used tiles from player hand
		for(Tile t : playerTiles) {
			player.hand.remove(t);
		}

		return true;
	}

	
	
}
