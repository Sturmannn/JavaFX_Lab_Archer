package org.example.lab_1_archer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.util.ArrayList;
import java.util.Iterator;

public class HelloController {

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
    private VBox shooterPane;
    @FXML
    private AnchorPane mainField;
    @FXML
    private VBox scorePane;

    private int pointsNumber;
    private int shotsCount;
    private enum GameStatus {
        Started,
        Stoped
    };
    private GameStatus gameStatus = GameStatus.Stoped;
    private boolean isShooting = false;
    private boolean isPaused = false;

    private Thread movingThread;

    private final boolean[] nearCircleDirection = {
            true
    };
    private final boolean[] distantCircleDirection = {
            false
    };

    private ArrayList < Arrow > arrows;

    @FXML
    private void initialize() {
        nearTargetLine.endYProperty().bind(mainField.heightProperty()); // Привязка конечной координаты Y к высоте AnchorPane
        distantTargetLine.endYProperty().bind(mainField.heightProperty());
    }

    @FXML
    protected void onStartGameButtonClick() {
        isPaused = false;
        if (gameStatus == GameStatus.Started) {
            shotsCount = pointsNumber = 0;
            scoreLabel.setText(String.valueOf(pointsNumber));
            shotCountLabel.setText(String.valueOf(shotsCount));
        } else {
            gameStatus = GameStatus.Started;

            if (movingThread != null) return;
            movingThread = new Thread(() -> {
                try {
                    while (gameStatus == GameStatus.Started) {
                        if (isPaused) {
                            synchronized(this) {
                                try {
                                    this.wait();
                                } catch (InterruptedException e) {
                                    //  e.printStackTrace();
                                }
                            }
                            isPaused = false;
                        }
                        Platform.runLater(() -> {

                            // Условие проверки, произведён ли был выстрел
                            if (isShooting) shot();

                            // Условие движения ближней мишени
                            if (nearCircleDirection[0] == true && nearTargetCircle.getLayoutY() + nearTargetCircle.getRadius() < nearTargetLine.getEndY())
                                nearTargetCircle.setLayoutY(nearTargetCircle.getLayoutY() + 1);
                            else {
                                nearCircleDirection[0] = false;
                                nearTargetCircle.setLayoutY(nearTargetCircle.getLayoutY() - 1);
                                if (nearTargetCircle.getLayoutY() - nearTargetCircle.getRadius() <= nearTargetLine.getStartY())
                                    nearCircleDirection[0] = true;
                            }

                            // Условие движения дальней мишени
                            if (distantCircleDirection[0] == true && distantTargetCircle.getLayoutY() + distantTargetCircle.getRadius() < distantTargetLine.getEndY())
                                distantTargetCircle.setLayoutY(distantTargetCircle.getLayoutY() + 2);
                            else {
                                distantCircleDirection[0] = false;
                                distantTargetCircle.setLayoutY(distantTargetCircle.getLayoutY() - 2);
                                if (distantTargetCircle.getLayoutY() - distantTargetCircle.getRadius() <= distantTargetLine.getStartY())
                                    distantCircleDirection[0] = true;
                            }
                        });

                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    //                        throw new RuntimeException(e);
                    nearTargetCircle.setLayoutY(mainField.getHeight() / 2);
                    distantTargetCircle.setLayoutY(mainField.getHeight() / 2);
                }
            });
            movingThread.setDaemon(true);
            movingThread.start();
        }
    }

    @FXML
    protected void onStopGameButtonClick() {
        if (movingThread == null) return;
        if (isPaused && gameStatus == GameStatus.Started) synchronized(this) {
            this.notifyAll();
        }
        if (arrows != null) {
            arrows.forEach(arrow -> {
                mainWindow.getChildren().removeAll(arrow.getArrowShaft(), arrow.getArrowHead());
            });
        }
        if (arrows != null) arrows.clear();

        gameStatus = GameStatus.Stoped;
        movingThread.interrupt();
        movingThread = null;

        nearCircleDirection[0] = true;
        distantCircleDirection[0] = false;

        shotsCount = pointsNumber = 0;
        shotCountLabel.setText(String.valueOf(shotsCount));
        scoreLabel.setText(String.valueOf(pointsNumber));
    }

    @FXML
    protected void onPauseGameButtonClick() {
        if (!isPaused) isPaused = true;
        else {
            synchronized(this) {
                this.notifyAll();
            }
            isPaused = false;
        }
    }

    @FXML
    protected void onShotButtonClick() {
        if (gameStatus == GameStatus.Stoped || isPaused) return;
        isShooting = true;
        shotsCount++;
        shotCountLabel.setText(String.valueOf(shotsCount));

        Arrow arrow = new Arrow(shooterPane.getWidth() + 60, mainField.getHeight() / 2, 60); // 60 - arrow's length

        if (arrows == null) arrows = new ArrayList < > (5);

        arrows.add(arrow);

        mainWindow.getChildren().addAll(arrow.getArrowShaft(), arrow.getArrowHead());
    }
    protected void shot() {
        Iterator < Arrow > iterator = arrows.iterator();
        byte arrowSpeed = 5;
        while (iterator.hasNext()) {
            Arrow arrow = iterator.next();

            arrow.movement(arrowSpeed);

            double nearCircleDistance = Math.sqrt(Math.pow(nearTargetCircle.getLayoutX() + mainField.getLayoutX() - arrow.getArrowShaft().getEndX(), 2) + Math.pow(nearTargetCircle.getLayoutY() + mainField.getLayoutY() - arrow.getArrowShaft().getEndY(), 2));
            double farthestCircleDistance = Math.sqrt(Math.pow(distantTargetCircle.getLayoutX() + mainField.getLayoutX() - arrow.getArrowShaft().getEndX(), 2) + Math.pow(distantTargetCircle.getLayoutY() + mainField.getLayoutY() - arrow.getArrowShaft().getEndY(), 2));
            if (nearCircleDistance <= nearTargetCircle.getRadius() || farthestCircleDistance <= distantTargetCircle.getRadius() || arrow.getArrowShaft().getStartX() > scorePane.getLayoutX()) {
                mainWindow.getChildren().removeAll(arrow.getArrowShaft(), arrow.getArrowHead());
                if (nearCircleDistance < farthestCircleDistance && nearCircleDistance <= nearTargetCircle.getRadius()) pointsNumber++;
                else if (farthestCircleDistance <= distantTargetCircle.getRadius()) pointsNumber += 2;
                scoreLabel.setText(String.valueOf(pointsNumber));
                iterator.remove();
            } else if (arrow.getArrowShaft().getEndX() >= scorePane.getLayoutX()) {
                arrow.getArrowShaft().setEndX(arrow.getArrowShaft().getEndX() - arrowSpeed);
                arrow.getArrowHead().setStroke(Color.TRANSPARENT); // Прозрачность контура
                arrow.getArrowHead().setFill(Color.TRANSPARENT); // Прозрачность заливки
            }
        }
    }
}