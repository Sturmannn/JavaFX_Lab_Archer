package org.archer.elements;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Score {
    private VBox scorePane;
    private Label scoreLabel;
    private Label shotCountLabel;
    public Score(VBox scorePane, Label scoreLabel, Label shotCountLabel) {
        this.scorePane = scorePane;
        this.scoreLabel = scoreLabel;
        this.shotCountLabel = shotCountLabel;
    }
    public Label getScoreLabel() {
        return scoreLabel;
    }
    public int getScore() {return Integer.parseInt(scoreLabel.getText());}
    public Label getShotCountLabel() {
        return shotCountLabel;
    }
    public int getShotCount() {
        return Integer.parseInt(shotCountLabel.getText());
    }
    public void setScoreLabel(Label scoreLabel) {
        this.scoreLabel = scoreLabel;
    }
    public void setScore(int score) {
        this.scoreLabel.setText(String.valueOf(score));
    }
    public void setShotCountLabel(Label shotCountLabel) {
        this.shotCountLabel = shotCountLabel;
    }
    public void setShotCount(int shotCount) {
        this.shotCountLabel.setText(String.valueOf(shotCount));
    }

    public VBox getScorePane() {
        return scorePane;
    }
}
