package core;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleController  {

	Scanner reader = new Scanner(System.in);
	public View view;
	public Model model; 
	
	public ConsoleController(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	public int turnOptionInput() {

		int choice = reader.nextInt();

		return choice;
	}

	public Tile drawTile() {
		// TODO Auto-generated method stub
		return null;
	}


	public void selectPlayTilesToBoard() {
		// TODO Auto-generated method stub
		
	}

	public boolean createMeld(ArrayList<Tile> tiles) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean playAITurns() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
