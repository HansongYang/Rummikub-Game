package core;

import java.util.ArrayList;

public interface Controller {
	
	public int turnOptionInput();
	public Tile drawTile();
	public boolean createMeld(ArrayList<Tile> tiles);
	public boolean playAITurns();
	public void playTilestoMeldFront(ArrayList<Tile> tiles,int meldId);
	public void playTilestoMeldBack(ArrayList<Tile> tiles,int meldId);
}
