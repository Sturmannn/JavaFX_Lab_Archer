package org.archer.elements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.List;

public class Serializable_Polygon {
    private final List < Double > points;
    private String fill;
    private String stroke = "black";
    private double layoutX;
    private double layoutY;

    public Serializable_Polygon(final List < Double > points, final String fill) {
        this.points = points;
        this.fill = fill;
    }
    public Serializable_Polygon(final List < Double > points, final Color fill) {
        this.points = points;
        this.fill = fill.toString();
    }
    public Serializable_Polygon(final Polygon polygon) {
        this.points = polygon.getPoints();
        this.fill = polygon.getFill().toString();
        this.stroke = polygon.getStroke().toString();
        this.layoutX = polygon.getLayoutX();
        this.layoutY = polygon.getLayoutY();
    }
    public List < Double > getPoints() {
        return points;
    }

    public String getFill() {
        return fill;
    }
    public String getStroke() {
        return stroke;
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

    public Polygon toPolygonFX() {
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(points);
        polygon.setFill(Color.web(fill));
        polygon.setStroke(Color.web(stroke));
        polygon.setLayoutX(layoutX);
        polygon.setLayoutY(layoutY);
        return polygon;
    }

    public void setColor(final Color color) {
        fill = color.toString();
        stroke = color.toString();
    }

    @Override
    public String toString() {
        return "Serializable_Polygon{" +
                "points=" + points +
                ", fill='" + fill + '\'' +
                ", stroke='" + stroke + '\'' +
                ", layoutX=" + layoutX +
                ", layoutY=" + layoutY +
                '}';
    }
}