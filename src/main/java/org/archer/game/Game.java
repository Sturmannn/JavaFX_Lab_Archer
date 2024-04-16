package org.archer.game;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import org.archer.elements.Arrow;
import org.archer.elements.GameStatus;
import org.archer.elements.Score;
import org.archer.elements.Targets;

import java.util.ArrayList;
import java.util.Iterator;

public class Game {
    private GameStatus gameStatus;
    private Score score; // счёт игры
    private Targets targets;
    private static Thread movingThread;
    final private Thread_manager threadManager;

    private AnchorPane mainWindow; // Основное окно
    private AnchorPane mainField; // Поле, где летают стрелы и мишени
    private AnchorPane shooterPane; // Pane со стрелками
    private Polygon shooter;

    private final ArrayList<Arrow> arrows;
    final public byte ARROW_SPEED = 5; // на сколько пикселей сдвинется стрела в единицу времени
    final public byte ARROW_LENGTH = 60; // длина стрелы в пикселях

    public Game() {
        gameStatus = GameStatus.Stopped;
        threadManager = new Thread_manager();
        arrows = new ArrayList<>(5);
    }
    public void initialize(AnchorPane mainWindow, AnchorPane mainField, AnchorPane shooterPane, Line nearTargetLine, Line distantTargetLine, Targets targets, Score score) {
        nearTargetLine.endYProperty().bind(mainField.heightProperty()); // Привязка конечной координаты Y к высоте AnchorPane
        distantTargetLine.endYProperty().bind(mainField.heightProperty());
        this.score = score;
        this.targets = targets;
        this.shooterPane = shooterPane;
        this.mainField = mainField;
        this.mainWindow = mainWindow;
    }

    public void StartGame(){
        if (gameStatus != GameStatus.Stopped) {
            score.setScore(0);
            score.setShotCount(0);
        } else {
            gameStatus = GameStatus.Started;
            if (movingThread != null) return;
            movingThread = new Thread(() -> {
               try {
                   while (gameStatus != GameStatus.Stopped) {
                       if (gameStatus == GameStatus.Paused) {
                           threadManager.do_wait();
                           gameStatus = GameStatus.Started;
                       }
                       MovingGame();
                       Thread.sleep(10);
                   }
               } catch (InterruptedException e) {
//                   throw new RuntimeException(e);
                   // Игра остановлена, значит нужно поставить на стартовые позиции мишени
                   Platform.runLater(this::SetStartData);
               }
            });
            movingThread.setDaemon(true);
            movingThread.start();
        }
    }
    private void MovingGame() {
        Platform.runLater(() -> {
            if (!arrows.isEmpty()) {
                ArrowMovement();
            }
            targets.moving();
        });
    }
    public void StopGame() {
        if (gameStatus != GameStatus.Stopped) {
            threadManager.do_notify();
                arrows.forEach(arrow -> mainWindow.getChildren().removeAll(arrow.getArrowShaft(), arrow.getArrowHead()));
                arrows.clear();
            if (movingThread != null)
                movingThread.interrupt();
            movingThread = null;
            Platform.runLater(this::SetStartData);
        }
    }

    public void Pause() {
        if (gameStatus == GameStatus.Started) gameStatus = GameStatus.Paused;
        else if (gameStatus == GameStatus.Paused) {
            threadManager.do_notify();
            gameStatus = GameStatus.Started;
        }
    }
    public void Shot() {
        if (gameStatus != GameStatus.Started) return;
        score.setShotCount(score.getShotCount() + 1);

        double y_arrow = mainField.getHeight() / 2; // Положение стрелы на оси ОУ
        if (shooter != null)
            y_arrow = shooter.getLayoutY();
        Arrow arrow = new Arrow(shooterPane.getWidth() + ARROW_LENGTH, y_arrow, ARROW_LENGTH);
        arrows.add(arrow);

        mainWindow.getChildren().addAll(arrow.getArrowShaft(), arrow.getArrowHead()); // Отображение стрелы
    }

    private void ArrowMovement() {
        Iterator< Arrow > iterator = arrows.iterator();

        while (iterator.hasNext()) {
            Arrow arrow = iterator.next();

            arrow.moving(ARROW_SPEED);
            double nearCircleDistance = GetCircleDistance(targets.getNearTargetCircle(), arrow);
            double farthestCircleDistance = GetCircleDistance(targets.getDistantTargetCircle(), arrow);
            if (nearCircleDistance <= targets.getNearTargetCircle().getRadius() || farthestCircleDistance <= targets.getDistantTargetCircle().getRadius() || arrow.getArrowShaft().getStartX() > score.getScorePane().getLayoutX()) {
                mainWindow.getChildren().removeAll(arrow.getArrowShaft(), arrow.getArrowHead());
                if (nearCircleDistance < farthestCircleDistance && nearCircleDistance <= targets.getNearTargetCircle().getRadius())
                    score.setScore(score.getScore() + 1);
                else if (farthestCircleDistance <= targets.getDistantTargetCircle().getRadius())
                    score.setScore(score.getScore() + 2);
                iterator.remove();
            } else if (arrow.getArrowShaft().getEndX() >= score.getScorePane().getLayoutX()) {
                arrow.getArrowShaft().setEndX(arrow.getArrowShaft().getEndX() - ARROW_SPEED);
                arrow.getArrowHead().setStroke(Color.TRANSPARENT); // Прозрачность контура
                arrow.getArrowHead().setFill(Color.TRANSPARENT); // Прозрачность заливки
            }
        }
    }

    private double GetCircleDistance(Circle circle, Arrow arrow) {
        return Math.sqrt(Math.pow(circle.getLayoutX() + mainField.getLayoutX() - arrow.getArrowShaft().getEndX(), 2) + Math.pow(circle.getLayoutY() + mainField.getLayoutY() - arrow.getArrowShaft().getEndY(), 2));
    }

    private void SetStartData() {
        (targets.getNearCircleDirection())[0] = true; // down
        (targets.getDistantCircleDirection())[0] = false; // up;

        targets.getNearTargetCircle().setLayoutY(mainField.getHeight() / 2);
        targets.getDistantTargetCircle().setLayoutY(mainField.getHeight() / 2);

        score.setShotCount(0);
        score.setScore(0);

        if (shooter != null)
            shooter.setLayoutY(shooterPane.getHeight() / 2);

        gameStatus = GameStatus.Stopped;
    }

    public void ShooterChangePosition(double y, Polygon shooter) {
        if (this.shooter == null) this.shooter = shooter;
        double shooterHeight = shooter.getPoints().get(1) - shooter.getPoints().get(5);
        if (y <= shooterPane.getHeight() - shooterHeight && y >= shooterHeight)
            shooter.setLayoutY(y);
        else if (y < shooterHeight)
            shooter.setLayoutY(shooterHeight / 2);
        else
            shooter.setLayoutY(shooterPane.getHeight() - shooterHeight / 2);
    }
}
