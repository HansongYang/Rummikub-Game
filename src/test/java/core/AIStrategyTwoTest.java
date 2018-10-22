package core;

import junit.framework.TestCase;

public class AIStrategyTwoTest extends TestCase {

    public void testPlayWithTableTiles() {
        Game game = new Game();
        PlayerStrategy<? super AIPlayer> aiStrategyTwo = new AIStrategyTwo();
        AIPlayer aiPlayer = new AIPlayer("AI1", game, aiStrategyTwo);

        Meld meld = new Meld();

    }
}
