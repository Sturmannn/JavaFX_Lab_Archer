package org.archer.elements;

import java.util.ArrayList;
import java.util.Map;

public class Model {
    private final DAO dao = new DAO();
    public ArrayList < IObserver > observers = new ArrayList < > ();

    public void addObserver(final IObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(final IObserver observer) {
        observers.remove(observer);
    }
    public void notifyObservers() {
        synchronized(this) {
            for (IObserver observer: observers) {
                observer.eventHandler(this);
            }
        }
    }
    public void setArchers(final Map < String, Archer > archers) {
        dao.setArchers(archers);
    }
    public void setTargets(final Targets targets) {
        dao.setTargets(targets);
    }
    public void setGameStatus(final GameStatus gameStatus) {
        dao.setGameStatus(gameStatus);
    }
    public GameStatus getGameStatus() {
        return dao.getGameStatus();
    }
    public Targets getTargets() {
        return dao.getTargets();
    }
    public Map < String, Archer > getArchers() {
        return dao.getArchers();
    }
    public Archer getArcher(final String nickName) {
        return dao.getArcher(nickName);
    }
    public MyPair getMainFieldSize() {
        return dao.getMainFieldSize();
    }
    public void setWinner(final String nickName) {
        getArcher(nickName).setWinner(true);
    }
}