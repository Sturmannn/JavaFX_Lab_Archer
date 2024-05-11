package org.archer.elements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

public class Arrow {
    private Serializable_Line arrowShaft;
    private Serializable_Polygon arrowHead;

    MyPair coordinates; // x, y (координаты начала стрелы)

    public Arrow(double endX, double endY, double layoutY, String strokeColor) {
//        coordinates = new Pair(endX - size, endY);
//        arrowShaft = new Serializable_Line(coordinates.getKey(), coordinates.getValue(), endX, endY, strokeColor);
        coordinates = new MyPair(endX, endY);
//        arrowShaft = new Serializable_Line(coordinates.getX(), coordinates.getY(), endX, endY, strokeColor);
        arrowShaft = new Serializable_Line(0, coordinates.getY(), endX, endY, strokeColor);
        List<Double> points = Arrays.asList(
                arrowShaft.getEndX() - 5, arrowShaft.getEndY() - 5,
                arrowShaft.getEndX(), arrowShaft.getEndY(),
                arrowShaft.getEndX() - 5, arrowShaft.getEndY() + 5
        );
        arrowHead = new Serializable_Polygon(points, strokeColor);
//        arrowShaft.setLayoutY(0);
//        arrowHead.setLayoutY(0);
//        arrowHead.setLayoutY(layoutY);
    }

    public Serializable_Line getArrowShaft() {
        return arrowShaft;
    }

    public Serializable_Polygon getArrowHead() {
        return arrowHead;
    }
    public Line getArrowShaftFX() {
        return arrowShaft.toLineFX();
    }
    public Polygon getArrowHeadFX() {
        return arrowHead.toPolygonFX();
    }

    public void moving(int speed) {
//        arrowShaft = new Serializable_Line(
//                arrowShaft.getStartX() + speed,
//                arrowShaft.getStartY(),
//                arrowShaft.getEndX() + speed,
//                arrowShaft.getEndY(),
//                arrowShaft.getStroke()
//        );
//
//        List<Double> points = arrowHead.getPoints();
//        for (int i = 0; i < points.size(); i += 2) {
//            points.set(i, points.get(i) + speed);
//        }
//        arrowHead = new Serializable_Polygon(points, arrowHead.getFill());

//        arrowShaft.setLayoutX(arrowShaft.getLayoutX() + speed);
//        arrowHead.setLayoutX(arrowHead.getLayoutX() + speed);
        arrowShaft.setStartX(arrowShaft.getStartX() + speed);
        arrowShaft.setEndX(arrowShaft.getEndX() + speed);
        List<Double> points = arrowHead.getPoints();
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