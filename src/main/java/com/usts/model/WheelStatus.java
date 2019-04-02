package com.usts.model;

public class WheelStatus {
    private Integer status;// 工作状态
    private Integer all_work;// 总的工作时间
    private Integer current_day_work;// 当天工作时间
    private Integer current_hour_work;// 当前小时工作时间

    public WheelStatus() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAll_work() {
        return all_work;
    }

    public void setAll_work(Integer all_work) {
        this.all_work = all_work;
    }

    public Integer getCurrent_day_work() {
        return current_day_work;
    }

    public void setCurrent_day_work(Integer current_day_work) {
        this.current_day_work = current_day_work;
    }

    public Integer getCurrent_hour_work() {
        return current_hour_work;
    }

    public void setCurrent_hour_work(Integer current_hour_work) {
        this.current_hour_work = current_hour_work;
    }
}
