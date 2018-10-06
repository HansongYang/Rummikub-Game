package core;

public class Tile {
	private int colour;
	private int rank;
	private static final char[] colours = {'R', 'B', 'G', 'O'};
    private static final int[] ranks = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    
	public Tile(int colour, int rank) {
		this.colour = colour;
		this.rank = rank;
	}
	
	public int getRank() {
		return rank;
	}
	
	public int getColour() {
		return colour;
	}
}
