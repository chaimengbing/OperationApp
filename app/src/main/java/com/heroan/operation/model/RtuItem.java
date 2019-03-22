package com.heroan.operation.model;

import zuo.biao.library.base.BaseModel;

public class RtuItem extends BaseModel {

    /**
     * {"projectId":"119361810131214","stationId":"120001808140027","projectAlias":"龙慧德鸿}
     */

    private String projectId;
    private String stationId;
    private String projectAlias;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getProjectAlias() {
        return projectAlias;
    }

    public void setProjectAlias(String projectAlias) {
        this.projectAlias = projectAlias;
    }

    @Override
    protected boolean isCorrect() {
        return false;
    }
}
