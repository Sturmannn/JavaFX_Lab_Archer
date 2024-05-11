package org.archer.elements;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import java.util.Arrays;

public class Targets {
    private final Serializable_Line nearTargetLine;
    private final Serializable_Line distantTargetLine;
    private final Serializable_Circle nearTargetCircle;
    private final Serializable_Circle distantTargetCircle;
    final private byte NEAR_TARGET_SPEED = 1;
    final private byte DISTANT_TARGET_SPEED = NEAR_TARGET_SPEED * 2;
    private final boolean[] nearCircleDirection = {
            true // down
    };

    private final boolean[] distantCircleDirection = {
            false // up
    };

    public Targets(MyPair mainFieldSize) {
        double nearTargetDistance = 300;
        double distantTargetDistance = 400;
//        nearTargetLine = new Serializable_Line(0, 0, 0, mainFieldSize.getY(), "black");
//        distantTargetLine = new Serializable_Line(0, 0, 0, mainFieldSize.getY(), "black");
//        nearTargetCircle = new Serializable_Circle(nearTargetDistance, mainFieldSize.getY() / 2, 30, "blue");
//        distantTargetCircle = new Serializable_Circle(distantTargetDistance, mainFieldSize.getY() / 2, 15, "red");

        nearTargetLine = new Serializable_Line(0, 0, 0, mainFieldSize.getY(), "black");
        distantTargetLine = new Serializable_Line(0, 0, 0, mainFieldSize.getY(), "black");
        nearTargetCircle = new Serializable_Circle(0, 0, 30, nearTargetDistance, mainFieldSize.getY() / 2, "blue");
        distantTargetCircle = new Serializable_Circle(0, 0, 15, distantTargetDistance, mainFieldSize.getY() / 2, "red");

        nearTargetLine.setLayoutX(nearTargetDistance);
        distantTargetLine.setLayoutX(distantTargetDistance);

//        nearTargetCircle.setLayoutX(nearTargetDistance);
//        distantTargetCircle.setLayoutX(distantTargetDistance);
//        nearTargetCircle.setLayoutY(mainFieldSize.getY() / 2);
//        distantTargetCircle.setLayoutY(mainFieldSize.getY() / 2 );


//        nearTargetCircle.setLayoutY(0);
//        distantTargetCircle.setLayoutY(0);

//        System.out.println(nearTargetCircle.toString());
//        System.out.println(nearTargetLine.toString());
    }

    public Targets(Serializable_Circle nearTargetCircle, Serializable_Circle distantTargetCircle, Serializable_Line nearTargetLine, Serializable_Line distantTargetLine)
    {
        this.nearTargetCircle = nearTargetCircle;
        this.distantTargetCircle = distantTargetCircle;
        this.nearTargetLine = nearTargetLine;
        this.distantTargetLine = distantTargetLine;
    }
    public Targets(Circle nearTargetCircle, Circle distantTargetCircle, javafx.scene.shape.Line nearTargetLine, javafx.scene.shape.Line distantTargetLine) {
        this.nearTargetCircle = new Serializable_Circle(nearTargetCircle);
        this.distantTargetCircle = new Serializable_Circle(distantTargetCircle);
        this.nearTargetLine = new Serializable_Line(nearTargetLine);
        this.distantTargetLine = new Serializable_Line(distantTargetLine);
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
    public Serializable_Line getNearTargetLine() {
        return nearTargetLine;
    }
    public Line getNearTargetLineFX() {
        return nearTargetLine.toLineFX();
    }

    public Serializable_Line getDistantTargetLine() {
        return distantTargetLine;
    }
    public Line getDistantTargetLineFX() {
        return distantTargetLine.toLineFX();
    }

    public Serializable_Circle getNearTargetCircle() {
        return nearTargetCircle;
    }
    public Circle getNearTargetCircleFX() {
        return nearTargetCircle.toCircleFX();
    }

    public Serializable_Circle getDistantTargetCircle() {
        return distantTargetCircle;
    }
    public Circle getDistantTargetCircleFX() {
        return distantTargetCircle.toCircleFX();
    }
    public boolean[] getNearCircleDirection() {
        return nearCircleDirection;
    }

    public boolean[] getDistantCircleDirection() {
        return distantCircleDirection;
    }

    @Override
    public String toString() {
        return "Targets{" +
                "nearTargetLine=" + nearTargetLine +
                ", distantTargetLine=" + distantTargetLine +
                ", nearTargetCircle=" + nearTargetCircle +
                ", distantTargetCircle=" + distantTargetCircle +
                ", NEAR_TARGET_SPEED=" + NEAR_TARGET_SPEED +
                ", DISTANT_TARGET_SPEED=" + DISTANT_TARGET_SPEED +
                ", nearCircleDirection=" + Arrays.toString(nearCircleDirection) +
                ", distantCircleDirection=" + Arrays.toString(distantCircleDirection) +
                '}';
    }
}