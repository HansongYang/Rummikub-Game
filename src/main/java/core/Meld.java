package core;

import java.util.ArrayList;

public class Meld {
	public int count = 0;
	private ArrayList<Tile> melds;
	
	public Meld() {
		melds = new ArrayList<Tile>();
	}
	
	public void add(Tile t) {
		melds.add(count++, t);
	}
	
	public void split(Tile t) {
		
	}
	
	public int size() {
		return melds.size();
	}
}
