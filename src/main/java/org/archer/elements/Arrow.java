package org.archer.elements;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;

import java.util.ArrayList;

public class Arrow {
    private final Line arrowShaft;
    private final Polygon arrowHead;

    Pair<Double, Double> coordinates; // x, y (координаты начала стрелы)

    public Arrow(double endX, double endY, int size) {
        coordinates = new Pair<>(endX - size, endY);
        arrowShaft = new Line(coordinates.getKey(), coordinates.getValue(), endX, endY); // Сначала Х.У начальной точки, потом конечной
        arrowHead = new Polygon();

        arrowHead.getPoints().addAll(
                arrowShaft.getEndX() - 5, arrowShaft.getEndY() - 5,
                arrowShaft.getEndX(), arrowShaft.getEndY(),
                arrowShaft.getEndX() - 5, arrowShaft.getEndY() + 5
        );
    }

    public Line getArrowShaft() {
        return arrowShaft;
    }
    public Polygon getArrowHead() {
        return arrowHead;
    }

    public void moving(int speed) {
        arrowShaft.setStartX(arrowShaft.getStartX() + speed);
        arrowShaft.setEndX(arrowShaft.getEndX() + speed);

        arrowHead.getPoints().set(0, arrowHead.getPoints().get(0) + speed);
        arrowHead.getPoints().set(2, arrowHead.getPoints().get(2) + speed);
        arrowHead.getPoints().set(4, arrowHead.getPoints().get(4) + speed);
    }

}