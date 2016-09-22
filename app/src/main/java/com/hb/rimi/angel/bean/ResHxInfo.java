package com.hb.rimi.angel.bean;

/**
 * 查询环信账号
 * Created by rimi on 2016/6/22.
 */
public class ResHxInfo {


    /**
     * status : 0
     * message : success
     * result : {"easemob_token":"xxxxxxxxxxxx"}
     */

    private int status;
    private String message;
    /**
     * easemob_token : xxxxxxxxxxxx
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
        private String easemob_token;

        public String getEasemob_token() {
            return easemob_token;
        }

        public void setEasemob_token(String easemob_token) {
            this.easemob_token = easemob_token;
        }
    }
}
