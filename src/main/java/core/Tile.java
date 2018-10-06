package core;

public class Tile {
	private char colour;
	private int rank;

	public Tile(char colour, int rank) {
		this.colour = colour;
		this.rank = rank;
	}
	
	public int getRank() {
		return rank;
	}
	
	public char getColour() {
		return colour;
	}
}
