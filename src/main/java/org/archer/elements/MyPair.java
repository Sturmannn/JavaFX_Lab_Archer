package org.archer.elements;

public class MyPair {
    private final double x;
    private final double y;

    public MyPair(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "MyPair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}