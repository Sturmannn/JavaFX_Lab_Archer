package org.archer.elements;

public class Score {
    private int points;
    private int shotCount;

    public Score() {
        this.points = 0;
        this.shotCount = 0;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(final int points) {
        this.points = points;
    }

    public int getShotCount() {
        return shotCount;
    }

    public void setShotCount(final int shotCount) {
        this.shotCount = shotCount;
    }

    @Override
    public String toString() {
        return "Score{" +
                "points=" + points +
                ", shotCount=" + shotCount +
                '}';
    }
}