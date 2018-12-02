package core.Memento;

import core.Board;
import core.Hand;
import core.Model;

public class Originator {
    private Board board;
    private Hand hand;

    public void setGame(Board board) { this.board = board; }

    public void setPlayerHand(Hand hand) { this.hand = hand; }

    public Memento saveStateToMemento() {
        return new Memento(this.board, this.hand);
    }

    public Board restoreBoardFromMemento(Memento memento) {
        return memento.getBoard();
    }

    public Hand restoreHandFromMemento(Memento memento) { return memento.getHand(); }
}
