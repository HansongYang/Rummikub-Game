package core;

import java.util.ArrayList;
import java.util.HashMap;

public class Player implements Observer {

	public String name = "Default";
	protected Hand hand;
	public Model model;
	public boolean initial30Played = false;
	protected ArrayList<Meld> meldsInHand;
	protected MeldValidatorService meldValidatorService = new MeldValidatorService();
	public HashMap<String, Integer> playerHandCount;

	public Player() {
		this.hand = new Hand();
		this.meldsInHand = new ArrayList<Meld>();
	}
	
	public Player(String name, Model model) {
		this.name = name;
		this.hand = new Hand();
		this.model = model;
		this.meldsInHand = new ArrayList<Meld>();
	}

	// Kept here for override
	public void playTurn() {}

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
		System.out.println(this.name + " played tiles");
		hand.printHand();
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

	public void update(HashMap<String, Integer> playerData) {
		this.playerHandCount = playerData;
	}
}
