package org.archer.elements;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class DAO {
    private ArrayList<Archer> archers = new ArrayList<>();
    MyPair mainField;
    private Targets targets;
//    private Score score;
    private GameStatus gameStatus;

    public DAO() {
        mainField = new MyPair(470,335); // ширина и высота поля
        targets = new Targets(mainField);
//        archers.add(new Archer(40,170, "Pedro"));
    }
    public ArrayList<Archer> getArchers() {
        return archers;
    }
    public Archer getArcher(int index) {
        return archers.get(index);
    }

    public void setArchers(ArrayList<Archer> archers) {
        this.archers = archers;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
    public Targets getTargets() {
        return targets;
    }
    public void setTargets(Targets targets) {
        this.targets = targets;
    }
    public MyPair getMainFieldSize() {
        return mainField;
    }
    public void setMainFieldSize(MyPair mainField) {
        this.mainField = mainField;
    }
//    public Score getScore() {
//        return score;
//    }
//    public void setScore(Score score) {
//        this.score = score;
//    }
//    public int getPoints() {
//        return score.getPoints();
//    }
//    public int getShotCount() {
//        return score.getShotCount();
//    }
//    public void setPointsLabel(Label pointsLabel) {
//        score.setPointsLabel(pointsLabel);
//    }
//    public void setPoints(int points) {
//        score.setPoints(points);
//    }
//    public void setShotCountLabel(Label shotCountLabel) {
//        score.setShotCountLabel(shotCountLabel);
//    }
//    public void setShotCount(int shotCount) {
//        score.setShotCount(shotCount);
//    }
//    public void resetScore() {
//        score.setPoints(0);
//        score.setShotCount(0);
//    }
    public void cleanup() {
        archers.forEach(Archer::cleanup);
        archers.clear();
        targets = null;
//        score = null;
        gameStatus = null;
    }

    @Override
    public String toString() {
        return "DAO{" +
                "archers=" + archers +
                ", targets=" + targets +
                ", gameStatus=" + gameStatus +
                '}';
    }
}
