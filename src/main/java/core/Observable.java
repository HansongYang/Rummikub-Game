package core;

public interface Observable {
    public void messageObservers();
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
}
