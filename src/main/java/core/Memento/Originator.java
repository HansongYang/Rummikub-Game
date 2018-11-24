package core.Memento;

import core.Board;
import core.Model;

public class Originator {
    private Board board;

    public void setGame(Board board) { this.board = board; }

    public Memento saveStateToMemento() {
        return new Memento(this.board);
    }

    public Board restoreFromMemento(Memento memento) {
        return memento.getBoard();
    }
}
