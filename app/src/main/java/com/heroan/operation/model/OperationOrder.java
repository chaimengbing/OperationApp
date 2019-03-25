package com.heroan.operation.model;

import zuo.biao.library.base.BaseModel;

public class OperationOrder extends BaseModel {

    /**
     * {
     * order_id: "27",
     * company_id: "2716958899",
     * project_id: "7384994995",
     * typename: "1",
     * order_name: "vevee",
     * order_summary: "测试成功",
     * sch_same: "1",
     * finish_time: null,
     * create_time: "2019-03-25 19:12:18.0"
     * }
     */
    private String order_id;
    private String company_id;
    private String project_id;
    private String typename;
    private String order_name;
    private String order_summary;
    private String sch_same;
    private String finish_time;
    private String create_time;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getOrder_summary() {
        return order_summary;
    }

    public void setOrder_summary(String order_summary) {
        this.order_summary = order_summary;
    }

    public String getSch_same() {
        return sch_same;
    }

    public void setSch_same(String sch_same) {
        this.sch_same = sch_same;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    protected boolean isCorrect() {
        return false;
    }
}
