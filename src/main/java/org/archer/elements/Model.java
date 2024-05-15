package org.archer.elements;

import org.archer.db.ArcherDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Model {
    private final DAO dao = new DAO();
    private final DAO_HIBERNATE dao_hibernate = new DAO_HIBERNATE();

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
        // Получите объект ArcherDB для этого игрока
        ArcherDB archerDB = dao_hibernate.getPlayer(nickName);
        if (archerDB != null) {
            // Увеличьте количество побед
            archerDB.setVictoryCount(archerDB.getVictoryCount() + 1);

            // Обновите объект в базе данных
            dao_hibernate.updatePlayer(archerDB);
        }
    }
    public void addArcherDB(final ArcherDB archer) {
        dao_hibernate.addPlayer(archer);
    }
    public List < ArcherDB > getArchersDB() {
        return dao_hibernate.getAllPlayersFromDB();
    }
}