package core;

import java.util.*;


import core.Model.GameStates;


import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.Map;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.animation.*;
import javafx.beans.value.*;

public class Game extends Application {

    public Model model;
    public JavaFxView view;
    public JavaFxController controller;

    public BorderPane panel;
    public Scene scene;
    public Stage stage;
    public String name;
    public boolean time;
    public String playername;
    public int numPlayer;
    public String[] strategy;

    public static void main(String[] arg) {
        launch(arg);
    }

    public Game() {
        name = "";
        time = false;
        playername = "";
        numPlayer = 0;
        strategy = new String[3];
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        this.stage = primaryStage;
        System.out.println("Start");

        panel = new BorderPane();
        panel.setPadding(new Insets(10, 10, 10, 10));

        scene = new Scene(panel, 1300, 800);
        stage.setScene(scene);
        stage.setTitle("Rummikub Game");
        stage.show();

        model = new Model();
        controller = new JavaFxController(model);
        view = new JavaFxView(controller, panel);

        this.startMenu();
    }

    public void startMenu() {
        final Button start = new Button("Start Game");
        start.setStyle("-fx-background-color: red; -fx-textfill: black;");

        final Text label = new Text("Welcome to Rummikub Game, what is your name?");
        final TextField nameText = new TextField();

        final Text label4 = new Text("How many players do you want to play with?");
        final ComboBox playerComboBox = new ComboBox();
        playerComboBox.getItems().addAll(
                2, 3, 4
        );

        playerComboBox.getSelectionModel().selectFirst();
        final Text label2 = new Text("Which player do you want to choose?");
        final ToggleGroup playerGroup = new ToggleGroup();
        final RadioButton player1 = new RadioButton("Player 1");

        player1.setToggleGroup(playerGroup);
        player1.setSelected(true);
        RadioButton player2 = new RadioButton("Player 2");
        player2.setToggleGroup(playerGroup);
        RadioButton player3 = new RadioButton("Player 3");
        player3.setToggleGroup(playerGroup);
        RadioButton player4 = new RadioButton("Player 4");
        player4.setToggleGroup(playerGroup);

        label.setStyle("-fx-font: normal bold 30px 'serif'");
        label2.setStyle("-fx-font: normal 20px 'serif'");
        label4.setStyle("-fx-font: normal 20px 'serif'");

        final Text label3 = new Text("Do you want to use 2 minutes timer for your turn?");
        label3.setStyle("-fx-font: normal 20px 'serif'");
        final ToggleGroup timer = new ToggleGroup();
        RadioButton yes = new RadioButton("yes");
        yes.setToggleGroup(timer);
        RadioButton no = new RadioButton("no");
        no.setToggleGroup(timer);
        no.setSelected(true);

        GridPane boardGrid = new GridPane();
        panel.setCenter(boardGrid);
        boardGrid.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: PALEGREEN;");

        final ComboBox aiComboBox1 = new ComboBox();
        final ComboBox aiComboBox2 = new ComboBox();
        final ComboBox aiComboBox3 = new ComboBox();
        final Label ai1 = new Label("AI player 1:  ");
        final Label ai2 = new Label("AI player 2:  ");
        final Label ai3 = new Label("AI player 3:  ");
        final Text label5 = new Text("Please select strategy for each AI player.");
        label5.setStyle("-fx-font: normal 20px 'serif'");

        aiComboBox1.getItems().addAll(
                "AI Strategy 1", "AI Strategy 2", "AI Strategy 3"
        );
        aiComboBox2.getItems().addAll(
                "AI Strategy 1", "AI Strategy 2", "AI Strategy 3"
        );
        aiComboBox3.getItems().addAll(
                "AI Strategy 1", "AI Strategy 2", "AI Strategy 3"
        );
        strategy[0] = (String) aiComboBox1.getValue();

        aiComboBox1.getSelectionModel().selectFirst();
        aiComboBox2.getSelectionModel().selectFirst();
        aiComboBox3.getSelectionModel().selectFirst();

        boardGrid.add(label, 0, 0);
        boardGrid.add(nameText, 0, 1);
        boardGrid.add(label4, 0, 2);
        boardGrid.add(playerComboBox, 0, 3);
        boardGrid.add(label2, 0, 4);
        boardGrid.add(player1, 0, 5);
        boardGrid.add(player2, 0, 6);
        boardGrid.add(player3, 0, 7);
        boardGrid.add(player4, 0, 8);
        boardGrid.add(label5, 0, 9);
        boardGrid.add(ai1, 0, 10);
        boardGrid.add(aiComboBox1, 0, 11);
        boardGrid.add(label3, 0, 16);
        boardGrid.add(yes, 0, 17);
        boardGrid.add(no, 0, 18);
        boardGrid.add(start, 2, 20);

        start.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                name = nameText.getText();
                view.setName(name);
                String[] toggle = playerGroup.getSelectedToggle().toString().split("\\'");
                playername = toggle[1];
                view.setPlayerName(playername);
                toggle = timer.getSelectedToggle().toString().split("\\'");
                if (toggle[1].equals("yes")) {
                    time = true;
                } else {
                    time = false;
                }
                view.setTime(time);
                numPlayer = (int) playerComboBox.getValue();
                strategy[0] = (String) aiComboBox1.getValue();
                strategy[1] = (String) aiComboBox2.getValue();
                strategy[2] = (String) aiComboBox3.getValue();
                view.setNumPlayer(numPlayer);
                view.setStrategy(strategy);
                model.setNumPlayer(numPlayer);
                model.setStrategy(strategy);

                model.initGame();
                view.refreshWindow();
                loop();
            }
        });

        playerComboBox.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // TODO Auto-generated method stub
                if ((int) playerComboBox.getValue() == 2) {
                    boardGrid.getChildren().remove(ai2);
                    boardGrid.getChildren().remove(ai3);
                    boardGrid.getChildren().remove(aiComboBox2);
                    boardGrid.getChildren().remove(aiComboBox3);
                } else if ((int) playerComboBox.getValue() == 3) {
                    boardGrid.getChildren().remove(ai3);
                    boardGrid.getChildren().remove(aiComboBox3);
                    boardGrid.getChildren().remove(ai2);
                    boardGrid.getChildren().remove(aiComboBox2);
                    aiComboBox2.getSelectionModel().selectFirst();
                    boardGrid.add(ai2, 0, 12);
                    boardGrid.add(aiComboBox2, 0, 13);
                } else {
                    boardGrid.getChildren().remove(aiComboBox2);
                    boardGrid.getChildren().remove(ai2);
                    boardGrid.add(ai2, 0, 12);
                    boardGrid.add(aiComboBox2, 0, 13);
                    boardGrid.add(ai3, 0, 14);
                    boardGrid.add(aiComboBox3, 0, 15);
                }
            }
        });
    }

    public void gameLoop() {
        view.printTurns(model.playerOrder);
        while (model.gameState == GameStates.PLAY) {
            Iterator it = model.playerOrder.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                Player player = (Player) pair.getKey();
                view.indicateTurn(player);
                //view.displayPlayerHands();
                if (player instanceof UserPlayer) userPlayerTurnLoop(model.userPlayer);
                else player.playTurn();
                view.displayBoard(model.getBoard());
                model.messageObservers();
                if (model.gameState == GameStates.END) break;
            }

            if (model.getDeck().getDeckSize() == 0) {
                System.out.println("The deck is empty.");
                int value1 = Math.min(model.userPlayer.getHand().size(), model.aiPlayer1.getHand().size());
                int value2 = Math.min(model.aiPlayer2.getHand().size(), model.aiPlayer3.getHand().size());
                int minimum = Math.min(value1, value2);
                if (minimum == model.userPlayer.getHand().size()) {
                    view.displayFinalTileCounts();
                    view.displayWinner(model.userPlayer);
                } else if (minimum == model.aiPlayer1.getHand().size()) {
                    view.displayFinalTileCounts();
                    view.displayWinner(model.aiPlayer1);
                } else if (minimum == model.aiPlayer2.getHand().size()) {
                    view.displayFinalTileCounts();
                    view.displayWinner(model.aiPlayer2);
                } else {
                    view.displayFinalTileCounts();
                    view.displayWinner(model.aiPlayer3);
                }
                model.gameState = GameStates.END;
            }

            if (model.gameWinCheck()) view.displayWinner(model.gameWinner);
        }
    }

    public void loop() {
        view.printTurns(model.playerOrder);
        view.displayTurnOptions();
        view.displayPlayerHand(model.userPlayer, 1);
        if (numPlayer == 2) {
            if (strategy[0].equals("AI Strategy 1")) {
                view.displayPlayerHand(model.aiPlayer1, 2);
            } else if (strategy[0].equals("AI Strategy 2")) {
                view.displayPlayerHand(model.aiPlayer1, 2);
            } else {
                view.displayPlayerHand(model.aiPlayer1, 2);
            }
        } else if (numPlayer == 3) {
            if (strategy[0].equals("AI Strategy 1")) {
                view.displayPlayerHand(model.aiPlayer1, 2);
            } else if (strategy[0].equals("AI Strategy 2")) {
                view.displayPlayerHand(model.aiPlayer1, 2);
            } else if (strategy[0].equals("AI Strategy 3")) {
                view.displayPlayerHand(model.aiPlayer1, 2);
            }
            if (strategy[1].equals("AI Strategy 1")) {
                view.displayPlayerHand(model.aiPlayer2, 3);
            } else if (strategy[1].equals("AI Strategy 2")) {
                view.displayPlayerHand(model.aiPlayer2, 3);
            } else {
                view.displayPlayerHand(model.aiPlayer2, 3);
            }
        } else {
            if (strategy[0].equals("AI Strategy 1")) {
                view.displayPlayerHand(model.aiPlayer1, 2);
            } else if (strategy[0].equals("AI Strategy 2")) {
                view.displayPlayerHand(model.aiPlayer1, 2);
            } else if (strategy[0].equals("AI Strategy 3")) {
                view.displayPlayerHand(model.aiPlayer1, 2);
            }
            if (strategy[1].equals("AI Strategy 1")) {
                view.displayPlayerHand(model.aiPlayer2, 3);
            } else if (strategy[1].equals("AI Strategy 2")) {
                view.displayPlayerHand(model.aiPlayer2, 3);
            } else if (strategy[1].equals("AI Strategy 3")) {
                view.displayPlayerHand(model.aiPlayer2, 3);
            }
            if (strategy[2].equals("AI Strategy 1")) {
                view.displayPlayerHand(model.aiPlayer3, 4);
            } else if (strategy[2].equals("AI Strategy 2")) {
                view.displayPlayerHand(model.aiPlayer3, 4);
            } else {
                view.displayPlayerHand(model.aiPlayer3, 4);
            }
        }
        view.displayBoard(model.getBoard());
        view.restart.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    panel.getChildren().clear();
                    start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void userPlayerTurnLoop(UserPlayer player) {

        //Scanner reader = new Scanner(System.in);
        int choice = 0;
        int numOfTiles = player.getHand().size();
        boolean pass = false;
        while (true) {
            if (numOfTiles > player.getHand().size()) {
                view.displayTurnOptions_Pass();
                pass = true;
            } else {
                view.displayTurnOptions();
            }
			
			/*while(!reader.hasNextInt()) {
				System.out.println("Wrong input. Please input again.");
				if(pass) {
					System.out.println("(1) Pass, (2) Create Meld, (3) Play tiles on the table. Enter -1 to quit.");
				}else {
					System.out.println("(1) Draw Tile, (2) Create Meld, (3) Play tiles on the table.  Enter -1 to quit.");
				}
				reader.nextLine();
			}*/

            choice = controller.turnOptionInput();

            if (choice == -1) {
                player.model.endGame();
                view.indicateUserEndsGame(player);
                break;
            }

            if (choice == 1) {//Draw tile
                if (player.model.getDeck().getDeckSize() == 0) {
                    break;
                }
                if (pass) {
                    pass = false;
                    break;
                }

                Tile newTile = player.model.getDeck().drawTile();
//				view.displayDrawnTile(newTile);
                player.hand.add(newTile);
                break;
            } else if (choice == 2) {//Play Meld

                int createAdditionalMelds;

                Hand availableTiles = player.hand;

                tileSelectionLoop(player, availableTiles);

                view.displayCreateAnotherMeldOption();
				/*while(!reader.hasNextInt()) {
					System.out.println("Wrong input. Please input again.");
					System.out.println("Create another meld? (1)Yes (2)No");
					reader.nextLine();
				}*/
                createAdditionalMelds = controller.turnOptionInput();

                while (createAdditionalMelds == 1) {
                    view.indicateAvailableTiles();
                    view.displayHand(availableTiles);

                    tileSelectionLoop(player, availableTiles);

                    view.displayCreateAnotherMeldOption();
					/*while(!reader.hasNextInt()) {
						System.out.println("Wrong input. Please input again.");
						System.out.println("Create another meld? (1)Yes (2)No");
						reader.nextLine();
					}*/
                    createAdditionalMelds = controller.turnOptionInput();

                }

                if (!player.initial30Played) {
                    if (player.totalAllMelds(player.meldsInHand) < 30) {
                        view.indicateMeldsLessThan30();
                    } else {
                        player.playMelds(player.model.getBoard(), player.meldsInHand);
                        player.initial30Played = true;
                    }
                } else {
                    player.playMelds(player.model.getBoard(), player.meldsInHand);
                }
            } else if (choice == 3) {
                if (player.model.getBoard().currentMelds.size() == 0) {
                    view.indicateNoTileOnBoard();
//					userPlayerTurnLoop(player);
                    return;
                }

                Hand availableTiles = player.hand;

                while (true) {
                    view.displayTileToExistingMeldOptions();
					/*while(!reader.hasNextInt()) {
						System.out.println("Wrong input. Please input again.");
						System.out.println("Choose an option: 1. Add a tile to the end of a meld. 2. Add a tile to the beginning of a meld. 3. Add a tile to create a new meld. Enter -1 to go back.");
						reader.nextLine();
					}*/
                    int option = controller.turnOptionInput();
                    if (option == -1) {
                        break;
                    }
                    if (option == 1) {
                        while (true) {
                            view.displayTileToMeldSelection();
                            int tileSelected = controller.turnOptionInput();
                            if (tileSelected == -1) {
                                break;
                            }

                            if (tileSelected >= availableTiles.size() || tileSelected < -1) {//Invalid Tile
                                view.indicateWrongInput();
                            } else {
                                while (true) {
                                    view.displayBoard(player.model.getBoard());
                                    view.displayMeldSelection();
                                    int meldSelected = controller.turnOptionInput();
                                    if (meldSelected == -1) {
                                        break;
                                    }
                                    if (meldSelected >= player.model.getBoard().currentMelds.size() || meldSelected < 0) {//Invalid Tile
                                        view.indicateWrongInput();
                                    } else {
                                        Meld meld = new Meld();
                                        meld.add(player.hand.getTile(tileSelected));
                                        if (player.model.getBoard().addTileToMeldEnd(meldSelected, meld)) {
                                            player.getHand().remove(player.hand.getTile(tileSelected));
                                            view.displayBoard(model.getBoard());
                                            view.displayHand(player.getHand());
                                        } else {
                                            view.indicateInvalidMeld();
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    } else if (option == 2) {
                        while (true) {
                            view.displayTileToMeldSelection();
                            int tileSelected = controller.turnOptionInput();
                            if (tileSelected == -1) {
                                break;
                            }

                            if (tileSelected >= availableTiles.size() || tileSelected < -1) {//Invalid Tile
                                view.indicateWrongInput();
                            } else {
                                while (true) {
                                    view.displayBoard(player.model.getBoard());
                                    view.displayMeldSelection();
                                    int meldSelected = controller.turnOptionInput();
                                    if (meldSelected == -1) {
                                        break;
                                    }
                                    if (meldSelected >= player.model.getBoard().currentMelds.size() || meldSelected < 0) {//Invalid Tile
                                        view.indicateWrongInput();
                                    } else {
                                        Meld meld = new Meld();
                                        meld.add(player.hand.getTile(tileSelected));
                                        if (player.model.getBoard().addTileToMeldBeginning(meldSelected, meld)) {
                                            player.getHand().remove(player.hand.getTile(tileSelected));
                                            view.displayBoard(model.getBoard());
                                            view.displayHand(player.getHand());
                                        } else {
                                            view.indicateInvalidMeld();
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    } else if (option == 3) {
                        Meld meld = new Meld();
                        while (true) {
                            view.displayTileToMeldSelection();
                            int tileSelected = controller.turnOptionInput();
                            if (tileSelected == -1) {
                                break;
                            }

                            if (tileSelected >= availableTiles.size() || tileSelected < -1) {//Invalid Tile
                                view.indicateWrongInput();
                            } else {
                                meld.add(player.hand.getTile(tileSelected));
                            }
                        }
                        while (true) {
                            view.displayBoard(player.model.getBoard());
                            view.displayMeldSelection();
                            int meldSelected = controller.turnOptionInput();
                            if (meldSelected == -1) {
                                break;
                            }
                            if (meldSelected >= player.model.getBoard().currentMelds.size() || meldSelected < 0) {//Invalid Tile
                                view.indicateWrongInput();
                            } else {
                                while (true) {
                                    view.displayMeld(player.model.getBoard().currentMelds.get(meldSelected));
                                    view.displayTileInSelectedMeldSelection();
                                    int tileOfMeld = controller.turnOptionInput();
                                    if (tileOfMeld == -1) {
                                        break;
                                    }
                                    if (tileOfMeld >= player.model.getBoard().currentMelds.get(meldSelected).size() || tileOfMeld < 0) {
                                        view.indicateWrongInput();
                                    } else {
                                        while (true) {
                                            view.displayTileToMeldPositionSelection();
                                            int positionOfMeld = controller.turnOptionInput();
                                            if (positionOfMeld == -1) {
                                                break;
                                            }
                                            if (positionOfMeld < 0 || positionOfMeld >= meld.size()) {
                                                view.indicateWrongInput();
                                            } else {
                                                if (player.model.getBoard().takeTileToFormNewMeld(meldSelected, tileOfMeld, positionOfMeld, meld)) {
                                                    for (int i = 0; i < meld.size(); i++) {
                                                        player.getHand().remove(meld.getTile(i));
                                                    }
                                                    view.displayBoard(model.getBoard());
                                                    view.displayHand(player.getHand());
                                                } else {
                                                    view.indicateInvalidMeld();
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        view.indicateWrongInput();
                    }
                }
            } else {
                view.indicateWrongInput();
            }
        }
    }


    public void tileSelectionLoop(UserPlayer player, Hand availableTiles) {
        int tileSelected;

        ArrayList<Integer> tileIndices = new ArrayList<Integer>();

        //Select tiles to create meld
        while (true) {
            view.displayTileSelection();
			/*while(!reader.hasNextInt()) {
				System.out.println("Wrong input. Please input again.");
				System.out.println("Enter the index of the Tile you want to select. (-1) to stop selecting");
				reader.nextLine();
			}*/
            tileSelected = controller.turnOptionInput();

            if (tileSelected >= availableTiles.size() || tileSelected < -1) {//Invalid Tile
                view.indicateWrongInput();
            } else if (tileSelected == -1) {//Finished selecting
                Meld meld = player.createMeld(player.selectTiles(tileIndices, availableTiles), availableTiles);
                if (meld != null) {
                    player.meldsInHand.add(meld);
                } else {
                    view.indicateInvalidMeld();
                }

                tileIndices.clear();
                break;
            } else {//Select tile
                tileIndices.add(tileSelected);
            }
        }
    }


}
