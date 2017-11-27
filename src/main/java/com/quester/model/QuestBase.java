package com.quester.model;

/**
 * Created by sergeybutorin on 27/11/2017.
 */
public class QuestBase {
    private int id;
    private int version;

    public QuestBase(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
