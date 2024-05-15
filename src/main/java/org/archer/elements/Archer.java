package org.archer.elements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Archer {
    private final ArrayList < Arrow > arrows = new ArrayList < > ();
    private final Score score = new Score();
    private final Serializable_Polygon archer;
    private String nickName = null;
    private boolean isReady = false;
    private boolean isWinner = false;

    public Archer(final double X, final double Y, final String nickName) {
        this.nickName = nickName;
        final List < Double > position = Arrays.asList(
                0.0, 30.0,
                20.0, 0.0,
                0.0, -30.0
        );
        archer = new Serializable_Polygon(position, "black");

        archer.setLayoutX(X);
        archer.setLayoutY(Y);
    }

    public Archer(final Serializable_Polygon archer) {
        this.archer = archer;
    }

    public String getNickName() {
        return nickName;
    }

    public void setPosition(final double y) {
        archer.setLayoutY(y);
    }

    public Serializable_Polygon getArcher() {
        return archer;
    }

    public void addArrow(final Arrow arrow) {
        arrows.add(arrow);
    }

    public ArrayList < Arrow > getArrows() {
        return arrows;
    }

    public Polygon getArcherFX() {
        final Polygon polygon = new Polygon();
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

    public void resetScore() {
        score.setPoints(0);
        score.setShotCount(0);
    }

    public double getLayoutY() {
        return archer.getLayoutY();
    }

    public void setColor(final Color color) {
        archer.setColor(color);
    }

    public String getColor() {
        return archer.getFill();
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(final boolean winner) {
        isWinner = winner;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(final boolean ready) {
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