package core;

import java.util.ArrayList;

public class Board {

    public ArrayList<Meld> currentMelds = new ArrayList<Meld>();
    private MeldValidatorService meldValidatorService = new MeldValidatorService();

    public Board() { }
    
    public void addMeld(Meld meld) {
    	meld.allTilesJustPlayedFlag();
        currentMelds.add(meld);
    }

    /**
     * Function that adds new meld to end of a current meld
     *
     * @param rowLocation Number where existing meld is located on the board
     * @param meld New meld being added to the board
     * @return boolean
     */
    public boolean addTileToMeldEnd(int rowLocation, Meld meld) {
        Meld checkMeld = currentMelds.get(rowLocation);

        for (int i = 0; i < meld.size(); i++) {
            checkMeld.add(meld.getTile(i));
            meld.allTilesJustPlayedFlag();
        }

        if (meldValidatorService.isValidMeld(checkMeld.getTiles())) {
            currentMelds.set(rowLocation, checkMeld);
            return true;
        } else {
            meld.resetAllJustPlayedFlag();
           
            
            for(int i = 0; i < meld.size(); i++) {
            	checkMeld.remove(checkMeld.size() - 1);
            }
            
            return false; // Invalid meld created
        }
    }

    /**
     * Function that adds new meld to beginning of a current meld
     *
     * @param rowLocation Number where existing meld is located on the board
     * @param meld New meld being added to the board
     * @return boolean
     */
    public boolean addTileToMeldBeginning(int rowLocation, Meld meld) {
        Meld checkMeld = currentMelds.get(rowLocation);
        for (int i = 0; i < meld.size(); i++) {
            checkMeld.add(i, meld.getTile(i));
            meld.allTilesJustPlayedFlag();
        }

        if (meldValidatorService.isValidMeld(checkMeld.getTiles())) {
            currentMelds.set(rowLocation, checkMeld);
            return true;
        } else {
        	meld.resetAllJustPlayedFlag(); 
        	
        	for(int i = 0; i < meld.size(); i++) {
            	checkMeld.remove(0);
            }
        	
            return false; // Invalid meld created
        }
        
    }


    /**
     * Function for taking an existing meld and adding it to new meld to play on board
     *
     * @param rowLocation Location of meld to take tile from
     * @param tileMeldLocation Tile location of meld on board
     * @param tileLocationForNewMeld Where to place newly received tile into new meld
     * @param meld New meld just generated
     * @return boolean
     */
    public boolean takeTileToFormNewMeld(int rowLocation, int tileMeldLocation, int tileLocationForNewMeld, Meld meld) {
        Meld checkMeld = currentMelds.get(rowLocation);
        Meld newMeld = meld;

        Tile removedTile = checkMeld.remove(tileMeldLocation);
        if (removedTile != null && meldValidatorService.isValidMeld(checkMeld.getTiles())) {

            if (tileLocationForNewMeld >= newMeld.size()) {
                newMeld.add(removedTile);
            } else {
                newMeld.add(tileLocationForNewMeld, removedTile);
            }

            if (meldValidatorService.isValidMeld(newMeld.getTiles())) {
                removedTile.setMovedToFormNewFlagTrue();
                currentMelds.add(newMeld);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void printBoard() {
    	System.out.println("Board: ");
        for (int i = 0; i < currentMelds.size(); i++) {
            System.out.print(i + ": " + "{ ");
            currentMelds.get(i).printMeld();
            System.out.print("}\n\n");
        }
    }

    public ArrayList<Meld> getCurrentMelds() {
        return currentMelds;
    }
}
