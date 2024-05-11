package org.archer.game;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import org.archer.elements.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {
    private final Model model;
    private static Thread movingThread;
    private static Thread_manager threadManager;
    SocketServerWrapper socketServer;
    final public byte ARROW_SPEED = 5; // на сколько пикселей сдвинется стрела в единицу времени
    final public byte ARROW_LENGTH = 60; // длина стрелы в пикселях

    public Game(Model model, SocketServerWrapper socketServer) {
        this.model = model;
        model.setGameStatus(GameStatus.Stopped);
        threadManager = new Thread_manager();
        this.socketServer = socketServer;
    }
    public void initialize(AnchorPane mainWindow, AnchorPane mainField, AnchorPane shooterPane, Line nearTargetLine, Line distantTargetLine, Targets targets, Score score) {
        nearTargetLine.endYProperty().bind(mainField.heightProperty()); // Привязка конечной координаты Y к высоте AnchorPane
        distantTargetLine.endYProperty().bind(mainField.heightProperty());
//        this.score = score;
//        this.targets = targets;
//        this.shooterPane = shooterPane;
//        this.mainField = mainField;
//        this.mainWindow = mainWindow;
    }
    public Game getGame() {
        return this;
    }
    public void StartGame(){
        if (model.getGameStatus() != GameStatus.Stopped) {
            model.getArchers().forEach(Archer::resetScore);
        } else {
            model.setGameStatus(GameStatus.Started);
            if (movingThread != null) return;
            movingThread = new Thread(() -> {
               try {
                   while (model.getGameStatus() != GameStatus.Stopped) {
                       if (model.getGameStatus() == GameStatus.Paused) {
                           threadManager.do_wait();
                           model.setGameStatus(GameStatus.Started);
                       }
                       MovingGame();

                       model.notifyObservers();

                       Thread.sleep(10);
                   }
               } catch (InterruptedException e) {
                   // Игра остановлена, значит нужно поставить на стартовые позиции мишени
                   SetStartData();
               }
            });
            movingThread.setDaemon(true);
            movingThread.start();
        }
    }

    // Перемещение мишеней за единицу времени
    private void MovingGame() {
        synchronized (threadManager) {
            model.getArchers().forEach(archer -> {
                if (!archer.getArrows().isEmpty()) {
                    checkHit(archer);
                }
            });
        }
        model.getTargets().moving();
    }
    // Остановка игры
    public void StopGame() {
        if (model.getGameStatus() != GameStatus.Stopped) {
            threadManager.do_notify();
            model.getArchers().forEach(Archer::cleanup);
            if (movingThread != null)
                movingThread.interrupt();
            movingThread = null;
            SetStartData();
        }
    }
    // Пауза игры
    public void Pause() {
        if (model.getGameStatus() == GameStatus.Started) model.setGameStatus(GameStatus.Paused);
        else if (model.getGameStatus() == GameStatus.Paused) {
            threadManager.do_notify();
            model.setGameStatus(GameStatus.Started);
        }
    }
    // Выстрел
    public void Shot(int index) {
        if (model.getGameStatus() != GameStatus.Started) return;
        synchronized (model.getArchers()){
            model.getArcher(index).getScore().setShotCount(model.getArcher(index).getScore().getShotCount() + 1);
        }

        double y_arrow = model.getArcher(index).getLayoutY();

//        if (model.getArcher(index) != null)
//            y_arrow = model.getArcher(index).getLayoutY();
        Arrow arrow = new Arrow(ARROW_LENGTH, y_arrow, model.getArcher(0).getLayoutY(), "black");
        synchronized (model.getArcher(index).getArrows()) {
            model.getArcher(index).addArrow(arrow);
        }
    }
    private void checkHit(Archer archer) {
        List<Arrow> arrowsToRemove = new ArrayList<>();
        synchronized (threadManager) {
            for (Arrow arrow : archer.getArrows()) {
                arrow.moving(ARROW_SPEED);
                double nearCircleDistance = GetCircleDistance(model.getTargets().getNearTargetCircle(), arrow);
                double farthestCircleDistance = GetCircleDistance(model.getTargets().getDistantTargetCircle(), arrow);

                if (nearCircleDistance < farthestCircleDistance && nearCircleDistance <= model.getTargets().getNearTargetCircle().getRadius()) {
                    archer.getScore().setPoints(archer.getScore().getPoints() + 1);
                    arrowsToRemove.add(arrow);
                }
                if (farthestCircleDistance <= model.getTargets().getDistantTargetCircle().getRadius()) {
                    archer.getScore().setPoints(archer.getScore().getPoints() + 2);
                    arrowsToRemove.add(arrow);
                }
                if (arrow.getArrowShaft().getStartX() > model.getMainFieldSize().getX())
                    arrowsToRemove.add(arrow);

                if (archer.getScore().getPoints() >= 2) {
                    System.out.println(socketServer.getId() + " win");
//                    synchronized (archer.getArrows()) {
//                        archer.getArrows().removeAll(arrowsToRemove);
//                    }
                    synchronized (threadManager) {
                        StopGame();
                    }
                    model.setWinner(socketServer.getId());
//                    model.setWinner(0);
                    return;
                }
            }
        }
//        synchronized (archer.getArrows()) {
        synchronized (threadManager) {
            archer.getArrows().removeAll(arrowsToRemove);
        }
    }

    // Расстояние от конца стрелы до мишени
    private double GetCircleDistance(Serializable_Circle circle, Arrow arrow) {
        double circleCenterX = circle.getLayoutX();
        double circleCenterY = circle.getLayoutY();
        double arrowEndX = arrow.getArrowShaft().getEndX();
        double arrowEndY = arrow.getArrowShaft().getEndY();

        return Math.sqrt(Math.pow(circleCenterX - arrowEndX, 2) + Math.pow(circleCenterY - arrowEndY, 2));
    }

    // Установка начальных данных, например, при остановке игры
    private void SetStartData() {
        model.getTargets().getNearCircleDirection()[0] = true; // down
        model.getTargets().getDistantCircleDirection()[0] = false; // up;

        model.getTargets().getNearTargetCircle().setLayoutX(model.getTargets().getNearTargetLine().getLayoutX());
        model.getTargets().getNearTargetCircle().setLayoutY(model.getMainFieldSize().getY() / 2);
        model.getTargets().getDistantTargetCircle().setLayoutX(model.getTargets().getDistantTargetLine().getLayoutX());
        model.getTargets().getDistantTargetCircle().setLayoutY(model.getMainFieldSize().getY() / 2);

        model.getArchers().forEach(Archer::resetScore);

        for (Archer shooter : model.getArchers()) {
            if (shooter != null) {
                shooter.setPosition(model.getMainFieldSize().getY() / 2);
                shooter.setWinner(false);
            }
        }
        model.setGameStatus(GameStatus.Stopped);
    }

    // Изменение положения стрелка
    public void ShooterChangePosition(Archer archer, int id) {
        if (model.getGameStatus() != GameStatus.Started) return;
        double y = archer.getLayoutY();
        if (y <= model.getMainFieldSize().getY() - 30 && y >= 30)
            model.getArcher(id).setPosition(y);
        else if (y < 30)
            model.getArcher(id).setPosition(30);
        else
            model.getArcher(id).setPosition(model.getMainFieldSize().getY() - 30);
    }
}
