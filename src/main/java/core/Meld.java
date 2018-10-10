package core;

import java.util.ArrayList;

public class Meld {
	public int count = 0;
	private ArrayList<Tile> melds;
	public boolean run = false;
	
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
		melds.add(count++, t);
	}
	
	public void remove(Tile t) {
		for(int i = 0; i < melds.size(); i++) {
			if(melds.get(i).getRank() == t.getRank() && melds.get(i).getColour() == t.getColour()) {
				melds.remove(i);
			}
		}
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
				String colour = "";
				if(melds.get(i).getColour() == 'R') {
					colour = "Red";
				}
				if(melds.get(i).getColour() == 'B') {
					colour = "Blue";
				}
				if(melds.get(i).getColour() == 'G') {
					colour = "Green";
				}
				if(melds.get(i).getColour() == 'O') {
					colour = "Orange";
				}
				System.out.print("(" + i + ")" + colour + melds.get(i).getRank() + " ");
			}
			System.out.println();
		}
	}
	
	public int totalValue() {
		int value = 0;
		for(int i = 0; i < melds.size(); i++) {
			value += melds.get(i).getRank();
		}
		return value;
	}
}
