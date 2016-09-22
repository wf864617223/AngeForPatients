package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * 构造订单id和数量 json数组
 * Created by rimi on 2016/7/14.
 */
public class OrderIdCount {


    /**
     * product_id : xxxxxxxxxxxxxx
     * amount : xx
     */

    private List<ProductListBean> product_list;

    public List<ProductListBean> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<ProductListBean> product_list) {
        this.product_list = product_list;
    }

    public static class ProductListBean {
        private String product_id;
        private String amount;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
