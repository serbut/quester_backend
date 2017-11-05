package com.quester.model;

/**
 * Created by sergeybutorin on 02/11/2017.
 */
public class Point {
    private int id;
    private double x;
    private double y;

    @SuppressWarnings("unused")
    private Point() {
    }

    public Point(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
