package core.Memento;

import core.Board;

public class Memento {
    private Board board;

    Memento(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }


}
