package org.archer.elements;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Serializable_Circle {
    private double centerX;
    private double centerY;
    private double radius;
    private String fill;
    private double layoutX;
    private double layoutY;

    public Serializable_Circle(double centerX, double centerY, double radius, double layoutX, double layoutY, String fill) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.fill = fill;
    }
    public Serializable_Circle(Circle circle) {
        this.centerX = circle.getCenterX();
        this.centerY = circle.getCenterY();
        this.radius = circle.getRadius();
        this.fill = circle.getFill().toString();
        this.layoutX = circle.getLayoutX();
        this.layoutY = circle.getLayoutY();
    }
    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }

    public String getFill() {
        return fill;
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
    public Circle toCircleFX() {
        Circle circle = new Circle(centerX, centerY, radius);
        circle.setFill(Paint.valueOf(fill));
        circle.setLayoutX(layoutX);
        circle.setLayoutY(layoutY);
        return circle;
    }

    @Override
    public String toString() {
        return "Serializable_Circle{" +
                "centerX=" + centerX +
                ", centerY=" + centerY +
                ", radius=" + radius +
                ", fill='" + fill + '\'' +
                ", layoutX=" + layoutX +
                ", layoutY=" + layoutY +
                '}';
    }
}