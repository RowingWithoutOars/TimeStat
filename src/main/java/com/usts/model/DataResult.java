package com.usts.model;

public class DataResult {
    private Object data = null;
    private String msg = "";
    private Integer code;
    private String error ="";
    public DataResult() {
    }

    public DataResult(Object data, String msg, Integer code) {
        this.data = data;
        this.msg = msg;
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
