package com.websystique.springmvc.dto;

import com.websystique.springmvc.model.Status;

import java.io.Serializable;

public class NewStatusDto implements Serializable {
    private int entityId;
    private Status entityStatus;

    public NewStatusDto(int entityId, Status entityStatus) {
        this.entityId = entityId;
        this.entityStatus = entityStatus;
    }

    public NewStatusDto() {
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Status getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(Status entityStatus) {
        this.entityStatus = entityStatus;
    }
}