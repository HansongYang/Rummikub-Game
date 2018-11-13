package core;

public class Tile implements Comparable<Tile> {
	private char colour;
	private int rank;
	private boolean movedToFormNewFlag = false;
	private boolean justPlayedFlag = false;
	private static char[] colours = {'R','B','G','O'};

	public Tile(char colour, int rank) {
		this.colour = colour;
		this.rank = rank;
	}

	public void printTile() {
		String colourTemp = "";
		switch (this.colour) {
			case 'R': colourTemp = "Red"; break;
			case 'B': colourTemp = "Blue"; break;
			case 'G': colourTemp = "Green"; break;
			case 'O': colourTemp = "Orange"; break;
			case 'J': colourTemp = "Joker"; break;
		}

		if (justPlayedFlag) {
			System.out.print(colourTemp + rank + "* ");
		} else if (movedToFormNewFlag){
			System.out.print(colourTemp + rank + "! ");
		} else {
			System.out.print(colourTemp  + rank + " ");
		}
	}

	public int getRank() {
		return rank;
	}
	
	public char getColour() {
		return colour;
	}

	public void setMovedToFormNewFlagTrue() { movedToFormNewFlag = true; }

	public void setJustPlayedFlag() { justPlayedFlag = true; }

	public void resetJustPlayedFlag() { justPlayedFlag = false; }
	
	public String toString() {
		return this.colour + Integer.toString(this.rank);
	}
	
	public int findIndex(char targetColour) {
		int index = 0;
		for (char c : colours) {
			if (c == targetColour) return index;
			else index++;
		}
		return 0;
	}

	public int compareTo(Tile nextTile) {
		
		if (findIndex(this.getColour()) < findIndex(nextTile.getColour())) return -1;
		else if (findIndex(this.getColour()) > findIndex(nextTile.getColour())) return 1;
		else {
			if (this.getRank() < nextTile.getRank()) return -1;
			else if (this.getRank() > nextTile.getRank()) return 1;
			return 0;
		}
		
	}

}
