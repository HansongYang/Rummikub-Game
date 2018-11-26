package core;

import java.util.ArrayList;
import java.util.Map;

public interface Controller {

	public int turnOptionInput();
	public Tile drawTile(UserPlayer player);
	public boolean createMeld(UserPlayer player, ArrayList<Tile> tiles);
	public boolean playAITurns();
	public boolean playTilestoMeldFront(UserPlayer player, ArrayList<Tile> tiles,int meldId);
	public boolean playTilestoMeldBack(UserPlayer player, ArrayList<Tile> tiles,int meldId);
	public boolean playMeldsToTable(UserPlayer player);
	public boolean reuseBoardTiles(UserPlayer player, Map<Meld, ArrayList<Tile>> tilesFromBoard, ArrayList<Tile> playerTiles);
	public void returnMeldsToHand(UserPlayer player);
	public void saveGame();
	public void restoreGame();
}
