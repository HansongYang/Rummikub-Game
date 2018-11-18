package core;

import junit.framework.TestCase;

import java.util.ArrayList;

public class MeldValidatorServiceTest extends TestCase {

    public void testIsValidMeld() {

        MeldValidatorService meldValidatorService = new MeldValidatorService();

        ArrayList<Tile> meld1 = new ArrayList<Tile>();
        meld1.add(new Tile('R',7));
        meld1.add(new Tile('G',7));
        meld1.add(new Tile('B',7));

        assertTrue(meldValidatorService.isValidMeld(meld1));

        ArrayList<Tile> meld2 = new ArrayList<Tile>();
        meld2.add(new Tile('R',7));
        meld2.add(new Tile('G',7));
        meld2.add(new Tile('R',7));

        assertFalse(meldValidatorService.isValidMeld(meld2));

        ArrayList<Tile> meld3 = new ArrayList<Tile>();
        meld3.add(new Tile('R',7));
        meld3.add(new Tile('R',8));
        meld3.add(new Tile('R',9));
        meld3.add(new Tile('R',5));

        assertFalse(meldValidatorService.isValidMeld(meld3));

        ArrayList<Tile> meld4 = new ArrayList<Tile>();
        meld4.add(new Tile('R',8));
        meld4.add(new Tile('R',9));
        meld4.add(new Tile('R',10));
        meld4.add(new Tile('R',11));

        assertTrue(meldValidatorService.isValidMeld(meld4));

        ArrayList<Tile> meld5 = new ArrayList<Tile>();
        meld5.add(new Tile('R',1));
        meld5.add(new Tile('O',2));
        meld5.add(new Tile('R',3));
        meld5.add(new Tile('B',4));

        assertFalse(meldValidatorService.isValidMeld(meld5));

        ArrayList<Tile> meld6 = new ArrayList<Tile>();
        meld6.add(new Tile('R',1));
        meld6.add(new Tile('G',1));

        assertFalse(meldValidatorService.isValidMeld(meld6));

        ArrayList<Tile> meld7 = new ArrayList<Tile>();
        meld7.add(new Tile('R',9));
        meld7.add(new Tile('R',10));
        meld7.add(new Tile('J',0));
        meld7.add(new Tile('R',12));
        meld7.add(new Tile('R',13));

        assertTrue(meldValidatorService.isValidMeld(meld7));

        ArrayList<Tile> meld8 = new ArrayList<Tile>();
        meld8.add(new Tile('R',4));
        meld8.add(new Tile('G',4));
        meld8.add(new Tile('B',4));
        meld8.add(new Tile('O',4));
        meld8.add(new Tile('J',0));

        assertTrue(meldValidatorService.isValidMeld(meld8));

    }

    public void testGetJokerValue() {
        MeldValidatorService meldValidatorService = new MeldValidatorService();

        ArrayList<Tile> meld1 = new ArrayList<Tile>();
        meld1.add(new Tile('R',9));
        meld1.add(new Tile('R',10));
        meld1.add(new Tile('R',11));
        meld1.add(new Tile('J',0));
        meld1.add(new Tile('R',13));

        assertEquals(12, meldValidatorService.getJokerValue(meld1, 3));

        ArrayList<Tile> meld2 = new ArrayList<Tile>();
        meld2.add(new Tile('J',0));
        meld2.add(new Tile('R',4));
        meld2.add(new Tile('G',4));
        meld2.add(new Tile('B',4));
        meld2.add(new Tile('O',4));

        assertEquals(4, meldValidatorService.getJokerValue(meld2, 0));
    }
}
