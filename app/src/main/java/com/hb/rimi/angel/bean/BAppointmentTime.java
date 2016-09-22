package com.hb.rimi.angel.bean;

import java.io.Serializable;

/**
 * Created by rimi on 2016/6/16.
 */
public class BAppointmentTime implements Serializable {

    private int ID;//主键
    private int count;//数量
    private String date;//时间
    private int SuccessCount;//成功数量

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSuccessCount() {
        return SuccessCount;
    }

    public void setSuccessCount(int successCount) {
        SuccessCount = successCount;
    }
}
