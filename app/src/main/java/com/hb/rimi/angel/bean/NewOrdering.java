package com.hb.rimi.angel.bean;

/**
 * Created by hp on 2016/6/30.
 */
public class NewOrdering {

    /**
     * status : 0
     * message : success
     * result : {"pay_order_id":"xxxxxxxxxxxxxxxxxxxxx","name":"xxxxxxxxxx","price":199}
     */

    private int status;
    private String message;
    /**
     * pay_order_id : xxxxxxxxxxxxxxxxxxxxx
     * name : xxxxxxxxxx
     * price : 199
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
        private String pay_order_id;
        private String name;
        private int price;

        public String getPay_order_id() {
            return pay_order_id;
        }

        public void setPay_order_id(String pay_order_id) {
            this.pay_order_id = pay_order_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
