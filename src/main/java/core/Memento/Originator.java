package core.Memento;

import core.Model;

public class Originator {
    private Model game;

    public void setGame(Model model) {
        this.game = model;
    }

    public Memento saveStateToMemento() {
        return new Memento(this.game);
    }

    public Model restoreFromMemento(Memento memento) {
        return memento.getGame();
    }
}
