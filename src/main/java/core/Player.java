package core;

import java.util.ArrayList;

public class Player {

	public String name = "Default";
	protected Hand hand;
	public Game game;
	public boolean initial30Played = false;
	protected ArrayList<Meld> meldsInHand;
	protected MeldValidatorService meldValidatorService = new MeldValidatorService();
		
	public Player() {
		this.hand = new Hand();
		this.meldsInHand = new ArrayList<Meld>();
	}
	
	public Player(String name, Game game) {
		this.name = name;
		this.hand = new Hand();
		this.game = game;
		this.meldsInHand = new ArrayList<Meld>();
	}
	
	public Hand getHand() {
		return this.hand;
	}
	
	public void playMeld(Board board, ArrayList<Tile> potentialMeld) {
		
		if(meldValidatorService.isValidMeld(potentialMeld)) {
			Meld meld = new Meld();
			
			for(int i = 0; i < potentialMeld.size();i++) {
				meld.add(potentialMeld.get(i));
			}
			
			board.addMeld(meld);
		}
		
	}
	
	public void playMeld(Board board, Meld meld) {
		board.addMeld(meld);
	}
	
	public void playMelds(Board board, ArrayList<Meld> melds) {
		for(int i = 0; i < melds.size(); i++) {
			board.addMeld(melds.get(i));
		}
	}
	
	public int totalAllMelds(ArrayList<Meld> melds) {
		int total = 0;
		
		for(int i = 0; i < melds.size(); i++) {
			total += melds.get(i).totalValue();
		}
		
		return total;
	}
	
	public int totalTilesFromMelds(ArrayList<Meld> melds) {
		int totalTiles = 0;
		
		for(int i = 0; i < melds.size(); i++) {
			totalTiles += melds.get(i).size();
		}
		return totalTiles;
	}

}
