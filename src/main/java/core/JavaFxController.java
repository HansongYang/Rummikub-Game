package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import core.Hand.SortByNumber;
import core.Memento.Memento;
import core.Memento.Originator;
import core.Model.GameStates;

public class JavaFxController implements Controller {

	protected MeldValidatorService meldValidatorService = new MeldValidatorService();
	public Model model; 
	
	public Memento savedGame;
	public Originator originator;

	public JavaFxController( Model model) {
		this.model = model;
		this.originator = new Originator();
	}
	
	public int turnOptionInput() {
		//System.out.println("JAVAFX INPUT");
		int choice = 1;

		return choice;
	}

	public Tile drawTile(UserPlayer player) {
		if(model.getDeck().getDeckSize() == 0) {
			return null;
		}
		
		Tile newTile = model.getDeck().drawTile();
		player.hand.add(newTile);
		
		return newTile;
	}
	
	public boolean createMeld(UserPlayer player, ArrayList<Tile> tiles) {
		
		Meld meld = player.createMeld(tiles, player.hand);
		
		if(meld != null) {
			player.meldsInHand.add(meld);
			
			//model.getBoard().addMeld(meld);
			
			return true;
		}else {
			return false;
		}
		
	}
	
	public boolean playAITurn(AIPlayer player) {
		
		player.playTurn();
        model.messageObservers();
        if (model.gameWinCheck()) {
			return true;
		}
		
		return false;
	}

	//Returns true if an AI player wins on this turn
	public boolean playAITurns() {
			Iterator it = model.playerOrder.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                Player player = (Player) pair.getKey();
                
                if(player instanceof AIPlayer) {
	                player.playTurn();
	                model.messageObservers();
	                if (model.gameWinCheck()) {
						return true;
					}
                }
     
            }
	
		return false;
	}

	public boolean playTilestoMeldFront(UserPlayer player, ArrayList<Tile> tiles, int meldId) {
		Meld meldToAdd = new Meld();
		for(Tile t : tiles) {
			meldToAdd.add(t);		
		}
		
		if(model.getBoard().addTileToMeldBeginning(meldId, meldToAdd)) {
			for(Tile t : tiles) {
				player.hand.remove(t);
			}
			return true;
		}
		return false;
	}

	public boolean playTilestoMeldBack(UserPlayer player, ArrayList<Tile> tiles, int meldId) {
		Meld meldToAdd = new Meld();
		for(Tile t : tiles) {
			meldToAdd.add(t);		
		}
		
		if(model.getBoard().addTileToMeldEnd(meldId, meldToAdd)) {
			for(Tile t : tiles) {
				player.hand.remove(t);
			}
			return true;
		}
		return false;
	}

	public boolean playMeldsToTable(UserPlayer player) {
		
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

	public void returnMeldsToHand(UserPlayer player) {		
		
		for(int i = 0; i < player.meldsInHand.size(); i++) {
			Meld meld = player.meldsInHand.get(i);
			for(int j = 0; j < meld.size(); j++) {
				player.hand.add(meld.getTile(j));
			}
		}
		player.meldsInHand.clear();
		
	}

	@Override
	public boolean reuseBoardTiles(UserPlayer player, Map<Meld, ArrayList<Tile>> tilesFromBoard, ArrayList<Tile> playerTiles) {
		
		ArrayList<ArrayList<Tile>> newMelds = new ArrayList<ArrayList<Tile>>();
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
				newMelds.add(updatedBoardMeld.getTiles());
			}
			
		}
		potentialNewMeld.getTiles().addAll(playerTiles);
		potentialNewMeld.sortTilesByNumber();
		
		newMelds.add(potentialNewMeld.getTiles());
				
		
		//remove old version of melds on board
		for(Meld meld : tilesFromBoard.keySet()) {
			model.getBoard().currentMelds.remove(meld);
		}
		
		//Update board with new melds
		for(int i  = 0; i < newMelds.size(); i++) {
			
			Meld meld = new Meld();
			for(int j = 0; j < newMelds.get(i).size();j++) {
				meld.add(newMelds.get(i).get(j));
			}
			
			model.getBoard().addMeld(meld);
		}
		
		//Removed used tiles from player hand
		for(Tile t : playerTiles) {
			player.hand.remove(t);
		}

		return true;
	}

	public void saveGame() {
		Board tempBoard = new Board(this.model.getBoard());
		Hand tempHand = new Hand(this.model.currentUserPlayer.getHand().getTiles());
		this.originator.setGame(tempBoard);
		this.originator.setPlayerHand(tempHand);
		this.savedGame = this.originator.saveStateToMemento();
		System.out.println("SAVING GAME");
	}

	public void restoreGame() {
		this.model.setBoard(this.originator.restoreBoardFromMemento(this.savedGame));
		this.model.currentUserPlayer.setHand(this.originator.restoreHandFromMemento(this.savedGame));
		System.out.println("RESTORING GAME");
	}



}
