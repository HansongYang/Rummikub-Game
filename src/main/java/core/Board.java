package core;

import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    public ArrayList<Meld> currentMelds = new ArrayList<Meld>();
    private MeldValidatorService meldValidatorService = new MeldValidatorService();

    public Board() { }
    
    public void addMeld(Meld meld) {
    	currentMelds.add(meld);
    }

    // Function that first adds { Meld meld } param into { rowLocation} of currentMelds and validates it
    // if valid it get saved into currentMelds
    // works for adding melds to end, and beginning
    public void addTileToMeld(int rowLocation, int tileMeldLocation, Meld meld) {
        Meld checkMeld = currentMelds.get(rowLocation);

        for (int i = 0; i < meld.size(); i++) {
            if (tileMeldLocation == meld.size()) { // for adding meld to the end
                checkMeld.add(meld.getTile(i));
            } else if (tileMeldLocation == -1) { // for adding meld to beginning
                checkMeld.add(i, meld.getTile(i));
            }
        }

        if (meldValidatorService.isValidMeld(checkMeld.getTiles())) {
            currentMelds.set(rowLocation, checkMeld);
        } else {
            System.out.println("Invalid Meld");
        }
    }

//	public void addToExistingMelds() {
//		Scanner reader = new Scanner(System.in);
//
//		game.getBoard().printBoard();
//
//		while (true) {
//			System.out.println("Pick row number from existing melds on board. (-1) to cancel");
//			int rowNum = reader.nextInt();
//
//			if (rowNum == -1) {
//				break;
//			} else if (rowNum > game.getBoard().currentMelds.size()-1 || rowNum < 0) {
//				System.out.println("Invalid row number, try again");
//			} else {
//				System.out.println("Pick location to place tile in existing meld: ");
//				int tileMeldLocation = reader.nextInt();
//
//				if (tileMeldLocation > game.getBoard().currentMelds.size()-1 || tileMeldLocation < 0) {
//					System.out.println("Invalid tile number, try again");
//				} else {
//					game.getBoard().addTileToMeld(rowNum, tileMeldLocation, )
//				}
//			}
//
//
//		}
//	}

    public void printBoard() {
    	System.out.println("Board: ");
        for (int i = 0; i < currentMelds.size(); i++) {
            System.out.print(i + ": " + "{ ");
            currentMelds.get(i).printMeld();
            System.out.print("}\n\n");
        }
    }
}
