package core.Memento;

import core.Board;
import core.Hand;

public class Memento {
    private Board board;
    private Hand hand;

    Memento(Board board, Hand hand) {
        this.board = board; this.hand = hand;
    }

    public Board getBoard() {
        return board;
    }

    public Hand getHand() { return hand; };


}
