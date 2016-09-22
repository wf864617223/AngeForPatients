package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * Created by rimi on 2016/6/15.
 */
public class ResVisitList {
    private int status;
    private String message;
    private List<VisitList> result;

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

    public List<VisitList> getResult() {
        return result;
    }

    public void setResult(List<VisitList> result) {
        this.result = result;
    }
}
