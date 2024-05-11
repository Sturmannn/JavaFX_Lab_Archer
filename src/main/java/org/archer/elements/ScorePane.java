package org.archer.elements;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ScorePane {
    VBox scorePane;
    public ScorePane(Label Nick, Label points, Label ShotCount) {
        Nick.setTextFill(Color.BLACK);
        this.scorePane = new VBox();
        this.scorePane.getChildren().add(Nick);
        this.scorePane.getChildren().add(points);
        this.scorePane.getChildren().add(ShotCount);
        scorePane.setStyle(
                "-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
    }
    public VBox getScorePane() {
        return scorePane;
    }
}
