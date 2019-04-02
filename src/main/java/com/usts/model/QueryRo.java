package com.usts.model;

import java.util.Date;

public class QueryRo {
    // 表名
    private String table_name;
    // 开始时间
    private String startTime;
    // 截止时间
    private String endTime;

    public QueryRo() {
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
