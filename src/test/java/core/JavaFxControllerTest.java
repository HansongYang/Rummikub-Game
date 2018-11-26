package core;

import junit.framework.TestCase;

public class JavaFxControllerTest extends TestCase {

    public void testMemento() {
        Model model = new Model();
        String[] strategy = {"AI Strategy 1"};

        model.setNumPlayer(2);
        model.setStrategy(strategy);
        model.initGame();

        JavaFxController controller = new JavaFxController(model);
        controller.saveGame();

        assertTrue(controller.model.getBoard().currentMelds.isEmpty());

        Meld meld = new Meld();
        meld.add(controller.drawTile(controller.model.userPlayer));
        controller.model.getBoard().addMeld(meld);

        assertFalse(controller.model.getBoard().currentMelds.isEmpty());

        controller.restoreGame();

        assertTrue(controller.model.getBoard().currentMelds.isEmpty());
    }
}
