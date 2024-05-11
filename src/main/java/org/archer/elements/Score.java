package org.archer.elements;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

//public class Score {
//    private VBox scorePane;
//    private Label pointsLabel;
//    private Label shotCountLabel;
//    public Score() {}
//    public Score(VBox scorePane, Label scoreLabel, Label shotCountLabel) {
//        this.scorePane = scorePane;
//        this.pointsLabel = scoreLabel;
//        this.shotCountLabel = shotCountLabel;
//    }
//    public Label getPointsLabel() {
//        return pointsLabel;
//    }
//    public int getPoints() {return Integer.parseInt(pointsLabel.getText());}
//    public Label getShotCountLabel() {
//        return shotCountLabel;
//    }
//    public int getShotCount() {
//        return Integer.parseInt(shotCountLabel.getText());
//    }
//    public void setPointsLabel(Label pointsLabel) {
//        this.pointsLabel = pointsLabel;
//    }
//    public void setPoints(int points) {
//        this.pointsLabel.setText(String.valueOf(points));
//    }
//    public void setShotCountLabel(Label shotCountLabel) {
//        this.shotCountLabel = shotCountLabel;
//    }
//    public void setShotCount(int shotCount) {
//        this.shotCountLabel.setText(String.valueOf(shotCount));
//    }
//
//    public VBox getScorePane() {
//        return scorePane;
//    }
//
//    @Override
//    public String toString() {
//        return "Score{" +
//                "scorePane=" + scorePane +
//                ", pointsLabel=" + pointsLabel +
//                ", shotCountLabel=" + shotCountLabel +
//                '}';
//    }
//}

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

    public void setPoints(int points) {
        this.points = points;
    }

    public int getShotCount() {
        return shotCount;
    }

    public void setShotCount(int shotCount) {
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