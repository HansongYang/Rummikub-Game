package core;

import java.util.ArrayList;

public class Board {

    public ArrayList<Meld> currentMelds = new ArrayList<Meld>();

    public Board() {

    }
    
    public void AddMeld(Meld meld) {
    	currentMelds.add(meld);
    }
}
