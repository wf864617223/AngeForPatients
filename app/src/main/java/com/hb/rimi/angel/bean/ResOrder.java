package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * 未付费订单列表结果
 * Created by rimi on 2016/6/12.
 */
public class ResOrder {

    /**
     * status : 0
     * message : success
     * result : [{}]
     */

    private int status;
    private String message;
    private List<Order> result;

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

    public List<Order> getResult() {
        return result;
    }

    public void setResult(List<Order> result) {
        this.result = result;
    }

}
