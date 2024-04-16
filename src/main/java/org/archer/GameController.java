package org.archer;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import org.archer.elements.*;
import org.archer.game.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class GameController {

    @FXML
    private AnchorPane mainWindow;
    @FXML
    private Label shotCountLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Circle nearTargetCircle;
    @FXML
    private Circle distantTargetCircle;
    @FXML
    private Line nearTargetLine;
    @FXML
    private Line distantTargetLine;
    @FXML
    private AnchorPane shooterPane;
    @FXML
    private Polygon shooter;
    @FXML
    private AnchorPane mainField;
    @FXML
    private VBox scorePane;

    private Game game;

    @FXML
    private void initialize() {
        game = new Game();
        Score score = new Score(scorePane, scoreLabel, shotCountLabel);
        Targets targets = new Targets(nearTargetCircle, distantTargetCircle, nearTargetLine, distantTargetLine);
        game.initialize(mainWindow, mainField, shooterPane, nearTargetLine, distantTargetLine, targets, score);
    }

    @FXML
    protected void onStartGameButtonClick() {
        game.StartGame();
    }

    @FXML
    protected void onStopGameButtonClick() {
        game.StopGame();
    }

    @FXML
    protected void onPauseGameButtonClick() {
        game.Pause();
    }

    @FXML
    protected void onShotButtonClick() {
        game.Shot();
    }

    @FXML
    protected void shooterChangePosition(MouseEvent event) {
        double y = event.getY();
        game.ShooterChangePosition(y, shooter);
    }
}