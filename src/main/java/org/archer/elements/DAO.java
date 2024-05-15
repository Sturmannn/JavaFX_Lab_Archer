package org.archer.elements;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DAO {
    private Map < String, Archer > archers = new ConcurrentHashMap < > ();
    private final MyPair mainField;
    private Targets targets;
    private GameStatus gameStatus;

    public DAO() {
        mainField = new MyPair(470, 335); // ширина и высота поля
        targets = new Targets(mainField);
    }

    public Map < String, Archer > getArchers() {
        return archers;
    }

    public Archer getArcher(final String nickName) {
        return archers.get(nickName);
    }

    public void setArchers(final Map < String, Archer > archers) {
        this.archers = archers;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(final GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Targets getTargets() {
        return targets;
    }

    public void setTargets(final Targets targets) {
        this.targets = targets;
    }

    public MyPair getMainFieldSize() {
        return mainField;
    }

    public void cleanup() {
        archers.forEach((k, v) -> v.cleanup());
        archers.clear();
        targets = null;
        gameStatus = null;
    }

    @Override
    public String toString() {
        return "DAO{" +
                "archers=" + archers +
                ", mainField=" + mainField +
                ", targets=" + targets +
                ", gameStatus=" + gameStatus +
                '}';
    }
}