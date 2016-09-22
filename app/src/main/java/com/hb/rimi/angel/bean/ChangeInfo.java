package com.hb.rimi.angel.bean;

/**
 * Created by hp on 2016/6/23.
 */
public class ChangeInfo {

    /**
     * status : 0
     * message : success
     * result : {"token":""}
     */

    private int status;
    private String message;
    /**
     * token :
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
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
