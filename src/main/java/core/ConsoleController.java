package core;

import java.util.Scanner;

public class ConsoleController implements Controller {

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
	

}
