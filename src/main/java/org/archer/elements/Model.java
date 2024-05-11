package org.archer.elements;

import javafx.scene.control.Label;
import org.archer.game.Game;
import org.archer.game.SocketServerWrapper;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Model {
    private final DAO dao = new DAO();
    public ArrayList<IObserver> observers = new ArrayList<>();
    int winner = -1;

    private Map<SocketServerWrapper, Integer> clients = new ConcurrentHashMap<>();

    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    public void addClient(SocketServerWrapper client, int id) {
        clients.put(client, id);
    }

    public void removeClient(SocketServerWrapper client) {
        System.out.println(observers + " " + observers.size());
        clients.remove(client);
        observers.remove(0);
        getArchers().remove(0);
    }

    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.eventHandler(this);
        }
    }
    public void setArchers(ArrayList<Archer> archers) {
        dao.setArchers(archers);
        notifyObservers();
    }
    public void setTargets(Targets targets) {
        dao.setTargets(targets);
        notifyObservers();
    }
    public void setScore(Score score, int index) {
        dao.getArcher(index).setScore(score);
        notifyObservers();
    }
    public void setGameStatus(GameStatus gameStatus) {
        dao.setGameStatus(gameStatus);
        notifyObservers();
    }

    public GameStatus getGameStatus() {
        return dao.getGameStatus();
    }
    public Score getScore(int index) {
        return dao.getArcher(index).getScore();
    }

    public Targets getTargets() {
        return dao.getTargets();
    }
    public ArrayList<Archer> getArchers() {
        return dao.getArchers();
    }
    public Archer getArcher(int index) {
        return dao.getArcher(index);
    }
    public void resetScore(int index) {
        dao.getArcher(index).resetScore();
        notifyObservers();
    }
    public int getPoints(int index) {
        return dao.getArcher(index).getScore().getPoints();
    }
    public int getShotCount(int index) {
        return dao.getArcher(index).getScore().getShotCount();
    }
//    public void setPointsLabel(Label pointsLabel, int index) {
//        dao.getArcher(index).getScore().setPointsLabel(pointsLabel);
//        notifyObservers();
//    }
    public void setPoints(int points, int index) {
        dao.getArcher(index).getScore().setPoints(points);
        notifyObservers();
    }
//    public void setShotCountLabel(Label shotCountLabel, int index) {
//        dao.getArcher(index).getScore().setShotCountLabel(shotCountLabel);
//        notifyObservers();
//    }
    public void setShotCount(int shotCount, int index) {
        dao.getArcher(index).getScore().setShotCount(shotCount);
        notifyObservers();
    }
    public MyPair getMainFieldSize() {
        return dao.getMainFieldSize();
    }
    public void setMainFieldSize(MyPair mainField) {
        dao.setMainFieldSize(mainField);
        notifyObservers();
    }
    public int getWinner() {
        return winner;
    }
    public void setWinner(int winner) {
        getArcher(winner).setWinner(true);
        notifyObservers();
    }
    public void cleanup() {
        dao.cleanup();
        notifyObservers();
    }
}
