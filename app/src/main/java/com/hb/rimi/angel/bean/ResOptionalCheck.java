package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * Created by rimi on 2016/6/12.
 */
public class ResOptionalCheck {

    /**
     * status : 0
     * message : success
     * result : [{}]
     */

    private int status;
    private String message;
    private List<OptionalCheck> result;

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

    public List<OptionalCheck> getResult() {
        return result;
    }

    public void setResult(List<OptionalCheck> result) {
        this.result = result;
    }


}
