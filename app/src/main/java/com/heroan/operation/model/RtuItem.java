package com.heroan.operation.model;

import zuo.biao.library.base.BaseModel;

public class RtuItem extends BaseModel {

    private String station_id;
    private String station_addr;

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getStation_addr() {
        return station_addr;
    }

    public void setStation_addr(String station_addr) {
        this.station_addr = station_addr;
    }

    @Override
    protected boolean isCorrect() {
        return false;
    }
}
