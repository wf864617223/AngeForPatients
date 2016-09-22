package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * 项目一周预约信息
 * Created by rimi on 2016/6/16.
 */
public class ResBAppointmentTime {
    private int status;
    private String message;
    private List<BAppointmentTime> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BAppointmentTime> getResult() {
        return result;
    }

    public void setResult(List<BAppointmentTime> result) {
        this.result = result;
    }
}
