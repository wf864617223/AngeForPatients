package com.hb.rimi.angel.bean;

/**
 * 就诊列表信息
 * Created by rimi on 2016/6/15.
 */
public class VisitList {

    /**
     * DOCTOR_CODE : lining
     * DOCTOR_NAME : 李宁
     * VISIT_NO : 160608-linng004
     * VISIT_DATE : 2016-06-08 15:38:43
     * IC_CODE : 123456789123
     * PATIENT_NAME : 测试
     * DETP_CODE : 0133
     * DEPT_NAME : 产科
     */

    private String DOCTOR_CODE;
    private String DOCTOR_NAME;
    private String VISIT_NO;
    private String VISIT_DATE;
    private String IC_CODE;
    private String PATIENT_NAME;
    private String DETP_CODE;
    private String DEPT_NAME;

    private double SUMCOSTS;//总金额


    private long ID;
    private String CustomerName;
    private String type;
    private String DateCreated;
    private String DateEnd;
    private String ContactType;

    public String getSimpleDsc() {
        return simpleDsc;
    }

    public void setSimpleDsc(String simpleDsc) {
        this.simpleDsc = simpleDsc;
    }

    public String getDetailDsc() {
        return detailDsc;
    }

    public void setDetailDsc(String detailDsc) {
        this.detailDsc = detailDsc;
    }

    private String simpleDsc;//简单描述
    private String detailDsc;//详细描述

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(String dateEnd) {
        DateEnd = dateEnd;
    }

    public String getContactType() {
        return ContactType;
    }

    public void setContactType(String contactType) {
        ContactType = contactType;
    }

    private double price;//价格
    private String proName;//项目名称

    public String getDOCTOR_CODE() {
        return DOCTOR_CODE;
    }

    public void setDOCTOR_CODE(String DOCTOR_CODE) {
        this.DOCTOR_CODE = DOCTOR_CODE;
    }

    public String getDOCTOR_NAME() {
        return DOCTOR_NAME;
    }

    public void setDOCTOR_NAME(String DOCTOR_NAME) {
        this.DOCTOR_NAME = DOCTOR_NAME;
    }

    public String getVISIT_NO() {
        return VISIT_NO;
    }

    public void setVISIT_NO(String VISIT_NO) {
        this.VISIT_NO = VISIT_NO;
    }

    public String getVISIT_DATE() {
        return VISIT_DATE;
    }

    public void setVISIT_DATE(String VISIT_DATE) {
        this.VISIT_DATE = VISIT_DATE;
    }

    public String getIC_CODE() {
        return IC_CODE;
    }

    public void setIC_CODE(String IC_CODE) {
        this.IC_CODE = IC_CODE;
    }

    public String getPATIENT_NAME() {
        return PATIENT_NAME;
    }

    public void setPATIENT_NAME(String PATIENT_NAME) {
        this.PATIENT_NAME = PATIENT_NAME;
    }

    public String getDETP_CODE() {
        return DETP_CODE;
    }

    public void setDETP_CODE(String DETP_CODE) {
        this.DETP_CODE = DETP_CODE;
    }

    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    public void setDEPT_NAME(String DEPT_NAME) {
        this.DEPT_NAME = DEPT_NAME;
    }

    public double getSUMCOSTS() {
        return SUMCOSTS;
    }

    public void setSUMCOSTS(double SUMCOSTS) {
        this.SUMCOSTS = SUMCOSTS;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }
}
