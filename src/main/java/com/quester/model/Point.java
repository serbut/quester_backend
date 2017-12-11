package com.quester.model;

import java.util.UUID;

/**
 * Created by sergeybutorin on 02/11/2017.
 */
public class Point {
    private int id;
    private UUID uuid;
    private double latitude;
    private double longitude;

    @SuppressWarnings("unused")
    private Point() {
    }

    public Point(int id, UUID uuid, double latitude, double longitude) {
        this.id = id;
        this.uuid = uuid;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Point(UUID uuid, double latitude, double longitude) {
        this.uuid = uuid;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
