package com.hb.rimi.angel.bean;

/**
 * Created by rimi on 2016/6/23.
 */
public class ResHisOrderId {

    /**
     * status : 0
     * message : success
     * result : {"order_id":"0011606231700000028"}
     */

    private int status;
    private String message;
    /**
     * order_id : 0011606231700000028
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
        private String order_id;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }
    }
}
