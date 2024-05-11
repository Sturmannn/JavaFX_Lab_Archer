package org.archer.elements;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class Serializable_Line {
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private String stroke;
    private double layoutX;
    private double layoutY;

    public Serializable_Line(double startX, double startY, double endX, double endY, String stroke) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.stroke = stroke;
    }
    public Serializable_Line(Line line) {
        this.startX = line.getStartX();
        this.startY = line.getStartY();
        this.endX = line.getEndX();
        this.endY = line.getEndY();
        this.stroke = line.getStroke().toString();
//        this.layoutX = line.getLayoutX();
//        this.layoutY = line.getLayoutY();
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public double getEndX() {
        return endX;
    }
    public void setEndX(double endX) {
        this.endX = endX;
    }
    public double getEndY() {
        return endY;
    }

    public String getStroke() {
        return stroke;
    }
    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public double getLayoutX() {
        return layoutX;
    }

    public void setLayoutX(double layoutX) {
        this.layoutX = layoutX;
    }

    public double getLayoutY() {
        return layoutY;
    }

    public void setLayoutY(double layoutY) {
        this.layoutY = layoutY;
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