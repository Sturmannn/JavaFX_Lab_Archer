package org.archer.elements;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ScorePane {
    VBox scorePane;
    final String playerColor;

    public ScorePane(final Label Nick, final Label points, final Label ShotCount, final String playerColor) {
        this.playerColor = playerColor;
        Rectangle colorRect = new Rectangle(13, 13);
        colorRect.setFill(Color.web(playerColor));
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(Nick, colorRect);
        this.scorePane = new VBox();
        this.scorePane.getChildren().add(hBox);
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