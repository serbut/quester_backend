package com.quester.model;

import java.util.UUID;

/**
 * Created by sergeybutorin on 27/11/2017.
 */
public class QuestBase {
    private UUID uuid;
    private int version;

    @SuppressWarnings("unused")
    public QuestBase() {
    }

    public QuestBase(UUID uuid, int version) {
        this.uuid = uuid;
        this.version = version;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
