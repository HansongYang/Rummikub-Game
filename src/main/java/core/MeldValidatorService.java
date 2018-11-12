package core;

import java.util.ArrayList;
import java.util.Collections;

public class MeldValidatorService {

    public MeldValidatorService() {}

    public boolean isValidMeld(ArrayList<Tile> tiles) {

        if(tiles.size() < 3) {
            return false;
        }

        boolean validSet = isValidSet(tiles);
        boolean validRun = isValidRun(tiles);

        return validSet || validRun;
    }

    private boolean isValidSet(ArrayList<Tile> tiles) {

        ArrayList<Character> colours = new ArrayList<Character>();
        ArrayList<Integer> ranks = new ArrayList<Integer>();

        for(int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);

            if (tile.getColour() != 'J') {
                //Check color
                if(colours.contains(tile.getColour())){
                    return false;
                } else {
                    colours.add(tile.getColour());
                }

                //Check rank
                if(ranks.size() == 0) {
                    ranks.add(tile.getRank());
                }
                else if(ranks.contains(tile.getRank())) {
                    ranks.add(tile.getRank());
                }
                else { //Does not contain same rank
                    return false;
                }
            }

        }

        return true;
    }

    private boolean isValidRun(ArrayList<Tile> tiles) {

        char color = ' ';
        ArrayList<Integer> ranks = new ArrayList<Integer>();

        for(int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);

            //Check color
            if(color == ' ' && tile.getColour() != 'J') {
                color = tile.getColour();
            } else if(color != tile.getColour() && tile.getColour() != 'J') {
                return false;
            }

            ranks.add(tile.getRank());
        }

        int prevRank = (ranks.get(0) != 0) ? ranks.get(0) : (ranks.get(1) - 1);

        for(int i = 1; i < ranks.size(); i++) {

            if (ranks.get(i) != 0) {
                if(ranks.get(i) - prevRank != 1) {
                    return false;
                }
                prevRank = ranks.get(i);
            } else {
                prevRank = (ranks.get(i-1) != null) ? (ranks.get(i-1) + 1) : (ranks.get(i+1) - 1);
            }
        }


        return true;
    }

    public int getJokerValue(ArrayList<Tile> tiles, int jokerLocation) {
        int value;

        if (isValidRun(tiles)) {
            value = (jokerLocation == 0) ? (tiles.get(jokerLocation+1).getRank() - 1)
                    : (tiles.get(jokerLocation-1).getRank() + 1);
        } else if (isValidSet(tiles)) {
            value = (jokerLocation == 0) ? tiles.get(jokerLocation+1).getRank() : tiles.get(jokerLocation-1).getRank();
        } else {
            return -1;
        }

        return value;
    }
}
