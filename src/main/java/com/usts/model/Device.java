package com.usts.model;

public class Device {
    private int deviceid;
    private String device_name;
    private String device_table;
    private int device_num;
    private int device_status;

    public Device() {
    }

    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_table() {
        return device_table;
    }

    public void setDevice_table(String device_table) {
        this.device_table = device_table;
    }

    public int getDevice_num() {
        return device_num;
    }

    public void setDevice_num(int device_num) {
        this.device_num = device_num;
    }

    public int getDevice_status() {
        return device_status;
    }

    public void setDevice_status(int device_status) {
        this.device_status = device_status;
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceid=" + deviceid +
                ", device_name='" + device_name + '\'' +
                ", device_table='" + device_table + '\'' +
                ", device_num=" + device_num +
                ", device_status=" + device_status +
                '}';
    }
}
