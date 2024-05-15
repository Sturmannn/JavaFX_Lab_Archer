package org.archer.elements;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Serializable_Circle {
    private final double centerX;
    private final double centerY;
    private final double radius;
    private final String fill;
    private double layoutX;
    private double layoutY;

    public Serializable_Circle(final double centerX, final double centerY, final double radius, final double layoutX, final double layoutY, final String fill) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.fill = fill;
    }
    public Serializable_Circle(final Circle circle) {
        this.centerX = circle.getCenterX();
        this.centerY = circle.getCenterY();
        this.radius = circle.getRadius();
        this.fill = circle.getFill().toString();
        this.layoutX = circle.getLayoutX();
        this.layoutY = circle.getLayoutY();
    }

    public double getRadius() {
        return radius;
    }

    public double getLayoutX() {
        return layoutX;
    }

    public void setLayoutX(final double layoutX) {
        this.layoutX = layoutX;
    }

    public double getLayoutY() {
        return layoutY;
    }

    public void setLayoutY(final double layoutY) {
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