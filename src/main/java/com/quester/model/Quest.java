package com.quester.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by sergeybutorin on 02/11/2017.
 */
public class Quest {
    private int id;
    private int version;
    private String title;
    private String description;
    private int userId;
    private List<Point> points;

    @SuppressWarnings("unused")
    private Quest() {
    }

    public Quest(int id, int version, String title, String description, int userId) {
        this.id = id;
        this.version = version;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.points = null;
    }

    public Quest(int id, int version, String title, String description, int userId, @NotNull List<Point> points) {
        this.id = id;
        this.version = version;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(@Nullable List<Point> points) {
        this.points = points;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
