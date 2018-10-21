package core;

import java.util.ArrayList;

public class Meld {
	public boolean run = false;
	private ArrayList<Tile> melds;
	private boolean justPlayedFlag = false;
	private boolean movedToFormNewFlag = false;
	
	public Meld() {
		melds = new ArrayList<Tile>();
	}
	
	public Meld(ArrayList<Tile> melds) {
		this.melds = melds;
	}
	
	public Tile getTile(int i) {
		return this.melds.get(i);
	}
	
	public ArrayList<Tile> getTiles() {
		return this.melds;
	}
	
	public void add(Tile t) {
		melds.add(t);
	}

	public void add(int index, Tile t) { melds.add(index, t); }
	
	public void remove(Tile t) {
		for(int i = 0; i < melds.size(); i++) {
			if(melds.get(i).getRank() == t.getRank() && melds.get(i).getColour() == t.getColour()) {
				melds.remove(i);
			}
		}
	}

	public Tile remove(int i) {
		if (i > melds.size()) return null;
		return melds.remove(i);
	}
	
	public int size() {
		return melds.size();
	}
	
	public void printMeld() {
		if(size() == 0) {
			System.out.println("Meld is empty.");
		} else {
			System.out.print("Meld's tile: ");
			for(int i = 0; i < melds.size(); i++) {
				System.out.print("(" + i + ")");
				melds.get(i).printTile();
			}
			resetAllJustPlayedFlag();
		}
	}
	
	public int totalValue() {
		int value = 0;
		for(int i = 0; i < melds.size(); i++) {
			value += melds.get(i).getRank();
		}
		return value;
	}

	public void allTilesJustPlayedFlag() {
		for (Tile tile : melds) {
			tile.setJustPlayedFlag();
		}
	}

	public void resetAllJustPlayedFlag() {
		for (Tile tile : melds) {
			tile.resetJustPlayedFlag();
		}
	}
}
