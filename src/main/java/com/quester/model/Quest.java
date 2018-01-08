package com.quester.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Created by sergeybutorin on 02/11/2017.
 */
public class Quest extends QuestBase {
    private String title;
    private String description;
    private int userId;
    private List<Point> points;

    @SuppressWarnings("unused")
    private Quest() {
    }

    public Quest(UUID uuid, int version, String title, String description, int userId) {
        super(uuid, version);
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.points = null;
    }

    public Quest(UUID uuid, int version, String title, String description, int userId, @NotNull List<Point> points) {
        super(uuid, version);
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.points = points;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
