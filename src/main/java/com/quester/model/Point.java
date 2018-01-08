package com.quester.model;

import java.util.UUID;

/**
 * Created by sergeybutorin on 02/11/2017.
 */
public class Point {
    private int id;
    private UUID uuid;
    private LatLng coordinates;

    @SuppressWarnings("unused")
    private Point() {
    }

    public Point(int id, UUID uuid, double latitude, double longitude) {
        this.id = id;
        this.uuid = uuid;
        this.coordinates = new LatLng(latitude, longitude);
    }

    public Point(UUID uuid, double latitude, double longitude) {
        this.uuid = uuid;
        this.coordinates = new LatLng(latitude, longitude);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }
}
