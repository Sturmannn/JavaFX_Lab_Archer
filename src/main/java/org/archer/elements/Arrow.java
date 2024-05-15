package org.archer.elements;

import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.Arrays;
import java.util.List;

public class Arrow {
    private final Serializable_Line arrowShaft;
    private final Serializable_Polygon arrowHead;

    MyPair coordinates; // x, y (координаты начала стрелы)

    public Arrow(final double endX, final double endY, final String strokeColor) {
        coordinates = new MyPair(endX, endY);
        arrowShaft = new Serializable_Line(0, coordinates.getY(), endX, endY, strokeColor);
        final List < Double > points = Arrays.asList(
                arrowShaft.getEndX() - 5, arrowShaft.getEndY() - 5,
                arrowShaft.getEndX(), arrowShaft.getEndY(),
                arrowShaft.getEndX() - 5, arrowShaft.getEndY() + 5
        );
        arrowHead = new Serializable_Polygon(points, strokeColor);
    }

    public Serializable_Line getArrowShaft() {
        return arrowShaft;
    }

    public Line getArrowShaftFX() {
        return arrowShaft.toLineFX();
    }
    public Polygon getArrowHeadFX() {
        return arrowHead.toPolygonFX();
    }

    public void moving(final int speed) {
        arrowShaft.setStartX(arrowShaft.getStartX() + speed);
        arrowShaft.setEndX(arrowShaft.getEndX() + speed);
        final List < Double > points = arrowHead.getPoints();
        for (int i = 0; i < points.size(); i += 2) {
            points.set(i, points.get(i) + speed);
        }
    }

    @Override
    public String toString() {
        return "Arrow{" +
                "arrowShaft=" + arrowShaft +
                ", arrowHead=" + arrowHead +
                ", coordinates=" + coordinates +
                '}';
    }
}