package com.quester.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by sergeybutorin on 02/11/2017.
 */
public class Quest {
    private int id;
    private String title;
    private int userId;
    private List<Point> points;
    @JsonProperty("token")
    private String userToken;

    @SuppressWarnings("unused")
    private Quest() {
    }

    public Quest(int id, String title, int userId) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.points = null;
    }

    public Quest(int id, String title, String userToken) {
        this.id = id;
        this.title = title;
        this.userToken = userToken;
        this.points = null;
    }

    public Quest(int id, String title, int userId, @NotNull List<Point> points) {
        this.id = id;
        this.title = title;
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

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
