package org.archer.elements;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class Serializable_Line {
    private double startX;
    private final double startY;
    private double endX;
    private final double endY;
    private final String stroke;
    private double layoutX;
    private double layoutY;

    public Serializable_Line(final double startX, final double startY, final double endX, final double endY, final String stroke) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.stroke = stroke;
    }
    public Serializable_Line(final Line line) {
        this.startX = line.getStartX();
        this.startY = line.getStartY();
        this.endX = line.getEndX();
        this.endY = line.getEndY();
        this.stroke = line.getStroke().toString();
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartX(final double startX) {
        this.startX = startX;
    }

    public double getEndX() {
        return endX;
    }
    public void setEndX(final double endX) {
        this.endX = endX;
    }
    public double getEndY() {
        return endY;
    }

    public double getLayoutX() {
        return layoutX;
    }

    public void setLayoutX(final double layoutX) {
        this.layoutX = layoutX;
    }

    public Line toLineFX() {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Paint.valueOf(stroke));
        line.setLayoutX(layoutX);
        line.setLayoutY(layoutY);
        return line;
    }

    @Override
    public String toString() {
        return "Serializable_Line{" +
                "startX=" + startX +
                ", startY=" + startY +
                ", endX=" + endX +
                ", endY=" + endY +
                ", stroke='" + stroke + '\'' +
                ", layoutX=" + layoutX +
                ", layoutY=" + layoutY +
                '}';
    }
}