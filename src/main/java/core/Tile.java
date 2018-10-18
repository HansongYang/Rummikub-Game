package core;

public class Tile {
	private char colour;
	private int rank;

	public Tile(char colour, int rank) {
		this.colour = colour;
		this.rank = rank;
	}

	public void printTile(boolean justPlayedFlag, boolean movedToFormNewFlag) {
		String colourTemp = "";
		switch (this.colour) {
			case 'R': colourTemp = "Red"; break;
			case 'B': colourTemp = "Blue"; break;
			case 'G': colourTemp = "Green"; break;
			case 'O': colourTemp = "Orange"; break;
		}

		if (justPlayedFlag) {
			System.out.print(colourTemp + rank + "* ");
		} else if (movedToFormNewFlag){
			System.out.print(colourTemp + rank + "! ");
		} else {
			System.out.print(colourTemp  + rank + " ");
		}
	}

	public void printTile() {
		String colourTemp = "";
		switch (this.colour) {
			case 'R': colourTemp = "Red"; break;
			case 'B': colourTemp = "Blue"; break;
			case 'G': colourTemp = "Green"; break;
			case 'O': colourTemp = "Orange"; break;
		}

		System.out.print(colourTemp  + rank + " ");
	}

	public int getRank() {
		return rank;
	}
	
	public char getColour() {
		return colour;
	}
}
