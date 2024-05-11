package org.archer.elements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Serializable_Polygon {
    private List<Double> points;
    private String fill = "black";
    private String stroke = "black";
    private double layoutX;
    private double layoutY;

//    public Serializable_Polygon() {}

    public Serializable_Polygon(List<Double> points, String fill) {
        this.points = points;
        this.fill = fill;
    }
    public Serializable_Polygon(List<Double> points, Color fill) {
        this.points = points;
        this.fill = fill.toString();
    }
    public Serializable_Polygon(Polygon polygon) {
        this.points = polygon.getPoints();
        this.fill = polygon.getFill().toString();
        this.stroke = polygon.getStroke().toString();
        this.layoutX = polygon.getLayoutX();
        this.layoutY = polygon.getLayoutY();
    }
    public List<Double> getPoints() {
        return points;
    }

    public String getFill() {
        return fill;
    }
    public void setFill(Color fill) {
        this.fill = fill.toString();
    }
    public String getStroke() {
        return stroke;
    }
    public void setStroke(Color stroke) {
        this.stroke = stroke.toString();
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

    public Polygon toPolygonFX() {
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(points);
        polygon.setFill(Color.web(fill));
        polygon.setStroke(Color.web(stroke));
        polygon.setLayoutX(layoutX);
        polygon.setLayoutY(layoutY);
        return polygon;
    }

    public void setColor(Color color) {
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