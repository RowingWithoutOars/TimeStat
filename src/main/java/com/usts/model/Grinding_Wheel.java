package com.usts.model;

import java.util.Date;

public class Grinding_Wheel {
    private int did;
    private Date dtime;
    private int dnum;
    private int dhour;
    private int dworkinghour;
    private int dstating=1;// 默认在线

    public Grinding_Wheel() {
    }

    public Grinding_Wheel(int did, Date dtime, int dnum, int dhour, int dworkinghour, int dstating) {
        this.did = did;
        this.dtime = dtime;
        this.dnum = dnum;
        this.dhour = dhour;
        this.dworkinghour = dworkinghour;
        this.dstating = dstating;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public Date getDtime() {
        return dtime;
    }

    public void setDtime(Date dtime) {
        this.dtime = dtime;
    }

    public int getDnum() {
        return dnum;
    }

    public void setDnum(int dnum) {
        this.dnum = dnum;
    }

    public int getDhour() {
        return dhour;
    }

    public void setDhour(int dhour) {
        this.dhour = dhour;
    }

    public int getDworkinghour() {
        return dworkinghour;
    }

    public void setDworkinghour(int dworkinghour) {
        this.dworkinghour = dworkinghour;
    }

    public int getDstating() {
        return dstating;
    }

    public void setDstating(int dstating) {
        this.dstating = dstating;
    }

    @Override
    public String toString() {
        return "Grinding_Wheel{" +
                "did=" + did +
                ", dtime=" + dtime +
                ", dnum=" + dnum +
                ", dhour=" + dhour +
                ", dworkinghour=" + dworkinghour +
                ", dstating=" + dstating +
                '}';
    }
}
