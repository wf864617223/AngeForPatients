package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * Created by rimi on 2016/6/12.
 */
public class ResDepartment {
    private int status;
    private String message;
    private List<Department> result;

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

    public List<Department> getResult() {
        return result;
    }

    public void setResult(List<Department> result) {
        this.result = result;
    }
}
