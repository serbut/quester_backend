package com.quester.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sergeybutorin on 02/11/2017.
 */
public class Point {
    private int id;
    @JsonProperty("order")
    private short orderNumber;
    private double x;
    private double y;

    @SuppressWarnings("unused")
    private Point() {
    }

    public Point(int id, short orderNumber, double x, double y) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(short orderNumber) {
        this.orderNumber = orderNumber;
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
