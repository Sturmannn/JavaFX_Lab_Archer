package org.archer.elements;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Targets {
    private final Line nearTargetLine;
    private final Line distantTargetLine;
    private final Circle nearTargetCircle;
    private final Circle distantTargetCircle;
    final private byte NEAR_TARGET_SPEED = 1;
    final private byte DISTANT_TARGET_SPEED = NEAR_TARGET_SPEED * 2;
    private final boolean[] nearCircleDirection = {
            true // down
    };

    private final boolean[] distantCircleDirection = {
            false // up
    };

    public Targets(Circle nearTargetCircle, Circle distantTargetCircle, Line nearTargetLine, Line distantTargetLine)
    {
        this.nearTargetCircle = nearTargetCircle;
        this.distantTargetCircle = distantTargetCircle;
        this.nearTargetLine = nearTargetLine;
        this.distantTargetLine = distantTargetLine;
    }

    public void moving() {
        // Условие движения ближней мишени
        if (nearCircleDirection[0] == true && nearTargetCircle.getLayoutY() + nearTargetCircle.getRadius() < nearTargetLine.getEndY())
            nearTargetCircle.setLayoutY(nearTargetCircle.getLayoutY() + NEAR_TARGET_SPEED);
        else {
            nearCircleDirection[0] = false;
            nearTargetCircle.setLayoutY(nearTargetCircle.getLayoutY() - NEAR_TARGET_SPEED);
            if (nearTargetCircle.getLayoutY() - nearTargetCircle.getRadius() <= nearTargetLine.getStartY())
                nearCircleDirection[0] = true;
        }

        // Условие движения дальней мишени
        if (distantCircleDirection[0] == true && distantTargetCircle.getLayoutY() + distantTargetCircle.getRadius() < distantTargetLine.getEndY())
            distantTargetCircle.setLayoutY(distantTargetCircle.getLayoutY() + DISTANT_TARGET_SPEED);
        else {
            distantCircleDirection[0] = false;
            distantTargetCircle.setLayoutY(distantTargetCircle.getLayoutY() - DISTANT_TARGET_SPEED);
            if (distantTargetCircle.getLayoutY() - distantTargetCircle.getRadius() <= distantTargetLine.getStartY())
                distantCircleDirection[0] = true;
        }
    }
    public Line getNearTargetLine() {
        return nearTargetLine;
    }

    public Line getDistantTargetLine() {
        return distantTargetLine;
    }

    public Circle getNearTargetCircle() {
        return nearTargetCircle;
    }

    public Circle getDistantTargetCircle() {
        return distantTargetCircle;
    }
    public boolean[] getNearCircleDirection() {
        return nearCircleDirection;
    }

    public boolean[] getDistantCircleDirection() {
        return distantCircleDirection;
    }
}
