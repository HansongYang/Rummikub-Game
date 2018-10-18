package core;

import java.util.Scanner;

public class UserStrategy implements PlayerStrategy<UserPlayer>{
	
	public void executeStrategy(UserPlayer player) {
		
		Scanner reader = new Scanner(System.in);
		int choice = -1;
		
		
		System.out.println("(1)Draw Tile, (2)Create Meld");
		choice = reader.nextInt();
		
		while(choice != 1 && choice != 2) {
			System.out.println("Invalid input, try again");
			System.out.println("(1)Draw Tile, (2)Create Meld");
			choice = reader.nextInt();
		}
		
		
		if(choice == 1) {//Draw tile
			Tile newTile = player.game.getDeck().drawTile();
			System.out.println("You drew: " + newTile.getColour() + ", " + newTile.getRank());		
			player.hand.add(newTile);
			
			return;
		}
		else if(choice == 2) {//Play Meld
		
			int createAdditionalMelds;
			
			Hand availableTiles = player.hand;
			
			player.tileSelectionInput(reader, availableTiles);
			
			System.out.println("Create another meld? (1)Yes (2)No");
			createAdditionalMelds = reader.nextInt();
			
			while(createAdditionalMelds == 1) {
				System.out.println("Available Tiles:");
				availableTiles.printHand();
				player.tileSelectionInput(reader, availableTiles);
				
				System.out.println("Create another meld? (1)Yes (2)No");
				createAdditionalMelds = reader.nextInt();
				
			}
			
			if(!player.initial30Played) {
				if(player.totalAllMelds(player.meldsInHand) < 30){
					System.out.println("The total of all your melds does not exceed 30");
				}
				else {
					player.playMelds(player.game.getBoard(), player.meldsInHand);
					player.initial30Played = true;
				}
			}
			else {
				player.playMelds(player.game.getBoard(), player.meldsInHand);
			}
			
		}

	}

}
