package core;

import java.util.ArrayList;

public class Hand {
	public int count = 0;
	private ArrayList<Tile> hands;
	
	public Hand() {
		hands = new ArrayList<Tile>();
	}
	
	public Tile getTile(int i) {
		return hands.get(i);
	}
	
	public void add(Tile t) {
		hands.add(count++, t);
	}
	
	public void remove(Tile t) {
		for(int i = 0; i < count; i++) {
			if(hands.get(i).getRank() == t.getRank() && hands.get(i).getColour() == t.getColour()) {
				hands.remove(i);
			}
		}
	}
	
	public boolean win() {
		if(hands.size() == 0) {
			return true;
		}
		return false;
	}
	
}
