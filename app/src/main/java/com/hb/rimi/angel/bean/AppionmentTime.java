package com.hb.rimi.angel.bean;

/**
 * Created by rimi on 2016/6/16.
 */
public class AppionmentTime {

    /**
     * ID : Id
     * DoctorName : 医生名字
     * DoctorCode : 编码
     * Date : 时间
     * Count : 22
     * SuccessCount : 20
     */

    private String ID;
    private String DoctorName;
    private String DoctorCode;
    private String Date;
    private int Count;
    private int SuccessCount;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String DoctorName) {
        this.DoctorName = DoctorName;
    }

    public String getDoctorCode() {
        return DoctorCode;
    }

    public void setDoctorCode(String DoctorCode) {
        this.DoctorCode = DoctorCode;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public int getSuccessCount() {
        return SuccessCount;
    }

    public void setSuccessCount(int SuccessCount) {
        this.SuccessCount = SuccessCount;
    }
}
