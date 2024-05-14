package org.archer.elements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Archer {
    private final ArrayList<Arrow> arrows = new ArrayList<>();
    private final Score score = new Score();
    private Serializable_Polygon archer;
    private String nickName = null;
    private boolean isReady = false;
    private boolean isWinner = false;

    public Archer(double X, double Y, String nickName) {
        this.nickName = nickName;
        List<Double> position = Arrays.asList(
//                7.62939453125E-6, 40.0928955078125,
//                27.600013732910156, -1.5258789289873675E-6,
//                7.62939453125E-6, -40.79999923706055
                0.0, 30.0,
                20.0, 0.0,
                0.0, -30.0
        );
        archer = new Serializable_Polygon(position, "black");
//        archer.getPoints().addAll(Arrays.asList(
//                7.62939453125E-6, 40.0928955078125,
//                27.600013732910156, -1.5258789289873675E-6,
//                7.62939453125E-6, -40.79999923706055
//        ));
        archer.setLayoutX(X);
        archer.setLayoutY(Y);
    }
    public Archer(Serializable_Polygon archer) {
        this.archer = archer;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getNickName() {
        return nickName;
    }
    public void setPosition(double y) {
        archer.setLayoutY(y);
    }
    public Serializable_Polygon getArcher() {
        return archer;
    }
    public void addArrow(Arrow arrow) {
        arrows.add(arrow);
    }
    public ArrayList<Arrow> getArrows() {
        return arrows;
    }
    public void removeArrow(Arrow arrow) {
        arrows.remove(arrow);
    }
    public void removeAllArrows() {
        arrows.clear();
    }
    public double getArcherY() {
        return archer.getPoints().get(3); // Получение самой правой точки
    }
    public Polygon getArcherFX() {
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(archer.getPoints());
        polygon.setFill(Color.web(archer.getFill()));
        polygon.setStroke(Color.web(archer.getStroke()));
        polygon.setLayoutX(archer.getLayoutX());
        polygon.setLayoutY(archer.getLayoutY());

        return polygon;
    }

    public Score getScore() {
        return score;
    }
    public void setScore(int points, int shotCount) {
        score.setPoints(points);
        score.setShotCount(shotCount);
    }
    public void setScore(Score score) {
        this.score.setPoints(score.getPoints());
        this.score.setShotCount(score.getShotCount());
    }
    public void resetScore() {
        score.setPoints(0);
        score.setShotCount(0);
    }
    public double getLayoutX() {
        return archer.getLayoutX();
    }
    public double getLayoutY() {
        return archer.getLayoutY();
    }
    public void setColor(Color color) {
        archer.setColor(color);
    }
    public String getColor() {
        return archer.getFill();
    }
    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
    public void cleanup() {
        arrows.clear();
        resetScore();
        setWinner(false);
        isReady = false;
    }

    @Override
    public String toString() {
        return "Archer{" +
                "arrows=" + arrows +
                ", score=" + score +
                ", archer=" + archer +
                ", nickName='" + nickName + '\'' +
                ", isReady=" + isReady +
                ", isWinner=" + isWinner +
                '}';
    }
}