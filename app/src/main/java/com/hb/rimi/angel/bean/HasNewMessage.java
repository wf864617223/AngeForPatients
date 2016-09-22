package com.hb.rimi.angel.bean;

/**
 * Created by hp on 2016/6/30.
 */
public class HasNewMessage {

    /**
     * status : 0
     * message : success
     * result : {"has_new":true}
     */

    private int status;
    private String message;
    /**
     * has_new : true
     */

    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private boolean has_new;

        public boolean isHas_new() {
            return has_new;
        }

        public void setHas_new(boolean has_new) {
            this.has_new = has_new;
        }
    }
}
