package core;

import java.util.ArrayList;

public interface Controller {
	
	public int turnOptionInput();
	public Tile drawTile();
	public boolean createMeld(ArrayList<Tile> tiles);
	public boolean playAITurns();
	public void selectPlayTilesToBoard();
}
