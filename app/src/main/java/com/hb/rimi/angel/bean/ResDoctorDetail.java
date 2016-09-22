package com.hb.rimi.angel.bean;


/**
 * 请求返回数据Bean
 * 适用格式：
 * {
 * status:0, //成功:0，失败:1
 * message:”success” //成功:success, 失败:error或错误信息
 * result:{
 * token:”xxxxxxxxxxxxxxxxxxxxxxxxx”
 * }
 * }
 * <p/>
 * Created by rimi on 2016/6/2.
 */
public class ResDoctorDetail {

    private int status;
    private String message;
    private DoctorDetail result;

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

    public DoctorDetail getResult() {
        return result;
    }

    public void setResult(DoctorDetail result) {
        this.result = result;
    }


}
