package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * 医生一周预约信息
 * Created by rimi on 2016/6/16.
 */
public class ResAppointmentTime {


    /**
     * status : 0
     * message : success
     * result : [{}]
     */

    private int status;
    private String message;
    private List<AppionmentTime> result;

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

    public List<AppionmentTime> getResult() {
        return result;
    }

    public void setResult(List<AppionmentTime> result) {
        this.result = result;
    }


}
