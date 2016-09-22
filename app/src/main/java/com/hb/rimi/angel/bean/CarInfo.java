package com.hb.rimi.angel.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 购物车需要的对象
 * Created by rimi on 2016/7/13.
 */
@Table(name = "carInfo")
public class CarInfo {
    @Column(name = "id", isId = true, autoGen = true)
    private long id;//id
    @Column(name = "product_id")
    private long product_id;//pid
    @Column(name = "goodName")
    private String goodName;//名称
    @Column(name = "price")
    private double price;//价格
    @Column(name = "count")
    private int count;//数量
    @Column(name = "specifications")
    private String specifications;//规格

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "=======> goodName=" + goodName + " id=" + id;
    }
}
