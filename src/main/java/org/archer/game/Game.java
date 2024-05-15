package org.archer.game;

import org.archer.elements.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Model model;
    private static Thread movingThread;
    private static final Thread_manager threadManager = new Thread_manager();
    private static final byte ARROW_SPEED = 5;
    private static final byte ARROW_LENGTH = 60;

    public Game(final Model model) {
        this.model = model;
        model.setGameStatus(GameStatus.Stopped);
    }

    public void StartGame() {
        if (model.getGameStatus() != GameStatus.Stopped) {
            model.getArchers().forEach((k, v) -> v.resetScore());
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
                SetStartData();
                model.notifyObservers();
            }
            });
            movingThread.setDaemon(true);
            movingThread.start();
        }
    }

    private void MovingGame() {
        synchronized(threadManager) {
            model.getArchers().values().forEach(archer -> {
            if (!archer.getArrows().isEmpty()) {
                checkHit(archer);
            }
            });
        }
        model.getTargets().moving();
    }

    public void StopGame() {
        if (model.getGameStatus() != GameStatus.Stopped) {
            synchronized(threadManager) {
                model.getArchers().forEach((k, v) -> v.cleanup());
            }
            threadManager.do_notify();
            model.setGameStatus(GameStatus.Stopped);
            if (movingThread != null)
                movingThread.interrupt();
            movingThread = null;
        }
    }

    public void Pause() {
        if (model.getGameStatus() == GameStatus.Started) model.setGameStatus(GameStatus.Paused);
        else if (model.getGameStatus() == GameStatus.Paused) {
            threadManager.do_notify();
            synchronized(threadManager) {
                model.setGameStatus(GameStatus.Started);
            }
        }
    }

    public void Shot(final String nickName) {
        if (model.getGameStatus() != GameStatus.Started) return;
        synchronized(threadManager) {
            model.getArcher(nickName).getScore().setShotCount(model.getArcher(nickName).getScore().getShotCount() + 1);
        }

        double y_arrow = model.getArcher(nickName).getLayoutY();
        Arrow arrow = new Arrow(ARROW_LENGTH, y_arrow, "black");
        synchronized(threadManager) {
            model.getArcher(nickName).addArrow(arrow);
        }
    }

    private void checkHit(final Archer archer) {
        List < Arrow > arrowsToRemove = new ArrayList < > ();
        for (Arrow arrow: archer.getArrows()) {
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

            if (archer.getScore().getPoints() >= 6) {
                StopGame();
                synchronized(threadManager) {
                    model.setWinner(archer.getNickName());
                }
                return;
            }
        }
        synchronized(threadManager) {
            archer.getArrows().removeAll(arrowsToRemove);
        }
    }

    private double GetCircleDistance(final Serializable_Circle circle, final Arrow arrow) {
        double circleCenterX = circle.getLayoutX();
        double circleCenterY = circle.getLayoutY();
        double arrowEndX = arrow.getArrowShaft().getEndX();
        double arrowEndY = arrow.getArrowShaft().getEndY();

        return Math.sqrt(Math.pow(circleCenterX - arrowEndX, 2) + Math.pow(circleCenterY - arrowEndY, 2));
    }

    private void SetStartData() {
        model.getTargets().getNearCircleDirection()[0] = true;
        model.getTargets().getDistantCircleDirection()[0] = false;

        model.getTargets().getNearTargetCircle().setLayoutX(model.getTargets().getNearTargetLine().getLayoutX());
        model.getTargets().getNearTargetCircle().setLayoutY(model.getMainFieldSize().getY() / 2);
        model.getTargets().getDistantTargetCircle().setLayoutX(model.getTargets().getDistantTargetLine().getLayoutX());
        model.getTargets().getDistantTargetCircle().setLayoutY(model.getMainFieldSize().getY() / 2);

        model.getArchers().forEach((k, v) -> v.resetScore());

        for (Archer shooter: model.getArchers().values()) {
            if (shooter != null) {
                shooter.setPosition(model.getMainFieldSize().getY() / 2);
                shooter.setWinner(false);
            }
        }
        model.setGameStatus(GameStatus.Stopped);
    }

    public void ShooterChangePosition(final Archer archer, final String nickName) {
        if (model.getGameStatus() != GameStatus.Started) return;
        double y = archer.getLayoutY();
        if (y <= model.getMainFieldSize().getY() - 30 && y >= 30)
            model.getArcher(nickName).setPosition(y);
        else if (y < 30)
            model.getArcher(nickName).setPosition(30);
        else
            model.getArcher(nickName).setPosition(model.getMainFieldSize().getY() - 30);
    }
}