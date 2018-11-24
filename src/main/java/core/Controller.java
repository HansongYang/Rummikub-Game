package core;

import java.util.ArrayList;

public interface Controller {

	public int turnOptionInput();
	public Tile drawTile();
	public boolean createMeld(ArrayList<Tile> tiles);
	public boolean playAITurns();
	public boolean playTilestoMeldFront(ArrayList<Tile> tiles,int meldId);
	public boolean playTilestoMeldBack(ArrayList<Tile> tiles,int meldId);
	public boolean playMeldsToTable();
	public void returnMeldsToHand();
	public void saveGame();
	public Model restoreGame();
}
