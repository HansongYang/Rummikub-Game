package core.Memento;

import core.Model;

public class Memento {
    private Model game;

    Memento(Model model) {
        this.game = model;
    }

    public Model getGame() {
        return game;
    }


}
