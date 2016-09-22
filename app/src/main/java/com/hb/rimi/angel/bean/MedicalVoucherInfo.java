package com.hb.rimi.angel.bean;

/**
 * 就诊凭证
 * Created by rimi on 2016/6/8.
 */
public class MedicalVoucherInfo {
    private String voucherType;//就诊类型
    private String voucherPerson;//就诊人
    private String voucherIcNo;//就诊卡号
    private String voucherDepartment;//就诊科室
    private String doctor;//医生
    private String date;//就诊时间






    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherPerson() {
        return voucherPerson;
    }

    public void setVoucherPerson(String voucherPerson) {
        this.voucherPerson = voucherPerson;
    }

    public String getVoucherIcNo() {
        return voucherIcNo;
    }

    public void setVoucherIcNo(String voucherIcNo) {
        this.voucherIcNo = voucherIcNo;
    }

    public String getVoucherDepartment() {
        return voucherDepartment;
    }

    public void setVoucherDepartment(String voucherDepartment) {
        this.voucherDepartment = voucherDepartment;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
