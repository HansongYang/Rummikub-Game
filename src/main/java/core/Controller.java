package core;

import java.util.ArrayList;
import java.util.Map;

public interface Controller {
	
	public int turnOptionInput();
	public Tile drawTile();
	public boolean createMeld(ArrayList<Tile> tiles);
	public boolean playAITurns();
	public boolean playTilestoMeldFront(ArrayList<Tile> tiles,int meldId);
	public boolean playTilestoMeldBack(ArrayList<Tile> tiles,int meldId);
	public boolean playMeldsToTable();
	public boolean reuseBoardTiles(Map<Meld, ArrayList<Tile>> tilesFromBoard, ArrayList<Tile> playerTiles);
	public void returnMeldsToHand();
}
