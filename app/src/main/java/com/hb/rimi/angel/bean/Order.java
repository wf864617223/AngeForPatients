package com.hb.rimi.angel.bean;

/**
 * 未付费订单列表
 * Created by rimi on 2016/6/12.
 */
public class Order {

    /**
     * ORDER_ID : 0011606061390000003
     * ITEM_NAME : 生长发育监测(套外)
     * ITEM_ID : 982
     * ITEM_UNIT : 次
     * ITEM_SPEC : 次
     * AMOUNT : 3
     * REPETITION : 1
     * ITEM_PRICE : 30
     * COSTS : 90
     * IC_CODE : 62011097
     * PRES_CODE : 160603-heping0003
     * PRES_ID : 2207789
     * STATUS : 0
     * PRES_TYPE_CODE : 06
     * INPUT_DATE : 2016-06-03 16:52:05
     * PATIENT_NAME : 汤悦龄
     * DEPT_ID : 0103
     * DOCTOR_ID : heping
     * PERFORMED_DEPT_ID : 0103
     * APPOINTMENT : null
     * COMBINATION_ID : null
     * CLASS_ON_RCPT : 06
     * SUBJ_CODE : 07
     * CLASS_ON_RECKONING : 07
     */

    private String ORDER_ID;//订单号
    private String ITEM_NAME;//名称
    private int ITEM_ID;//ID
    private String ITEM_UNIT;//单位
    private String ITEM_SPEC;//规格
    private int AMOUNT;//数量
    private int REPETITION;//数量
    private double ITEM_PRICE;//单价
    private double COSTS;//金额
    private String IC_CODE;//会员卡号
    private String PRES_CODE;//处方号
    private int PRES_ID;//处方ID
    private String STATUS;//处方状态
    private String PRES_TYPE_CODE;//处方费用类型
    private String INPUT_DATE;
    private String PATIENT_NAME;
    private String DEPT_ID;
    private String DOCTOR_ID;
    private String PERFORMED_DEPT_ID;//执行科室
    private String APPOINTMENT;//预约时间
    private String COMBINATION_ID;//组合项目，套餐ID
    private String CLASS_ON_RCPT;
    private String SUBJ_CODE;
    private String CLASS_ON_RECKONING;

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public void setORDER_ID(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public int getITEM_ID() {
        return ITEM_ID;
    }

    public void setITEM_ID(int ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }

    public String getITEM_UNIT() {
        return ITEM_UNIT;
    }

    public void setITEM_UNIT(String ITEM_UNIT) {
        this.ITEM_UNIT = ITEM_UNIT;
    }

    public String getITEM_SPEC() {
        return ITEM_SPEC;
    }

    public void setITEM_SPEC(String ITEM_SPEC) {
        this.ITEM_SPEC = ITEM_SPEC;
    }

    public int getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(int AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public int getREPETITION() {
        return REPETITION;
    }

    public void setREPETITION(int REPETITION) {
        this.REPETITION = REPETITION;
    }

    public double getITEM_PRICE() {
        return ITEM_PRICE;
    }

    public void setITEM_PRICE(double ITEM_PRICE) {
        this.ITEM_PRICE = ITEM_PRICE;
    }

    public double getCOSTS() {
        return COSTS;
    }

    public void setCOSTS(double COSTS) {
        this.COSTS = COSTS;
    }

    public String getIC_CODE() {
        return IC_CODE;
    }

    public void setIC_CODE(String IC_CODE) {
        this.IC_CODE = IC_CODE;
    }

    public String getPRES_CODE() {
        return PRES_CODE;
    }

    public void setPRES_CODE(String PRES_CODE) {
        this.PRES_CODE = PRES_CODE;
    }

    public int getPRES_ID() {
        return PRES_ID;
    }

    public void setPRES_ID(int PRES_ID) {
        this.PRES_ID = PRES_ID;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getPRES_TYPE_CODE() {
        return PRES_TYPE_CODE;
    }

    public void setPRES_TYPE_CODE(String PRES_TYPE_CODE) {
        this.PRES_TYPE_CODE = PRES_TYPE_CODE;
    }

    public String getINPUT_DATE() {
        return INPUT_DATE;
    }

    public void setINPUT_DATE(String INPUT_DATE) {
        this.INPUT_DATE = INPUT_DATE;
    }

    public String getPATIENT_NAME() {
        return PATIENT_NAME;
    }

    public void setPATIENT_NAME(String PATIENT_NAME) {
        this.PATIENT_NAME = PATIENT_NAME;
    }

    public String getDEPT_ID() {
        return DEPT_ID;
    }

    public void setDEPT_ID(String DEPT_ID) {
        this.DEPT_ID = DEPT_ID;
    }

    public String getDOCTOR_ID() {
        return DOCTOR_ID;
    }

    public void setDOCTOR_ID(String DOCTOR_ID) {
        this.DOCTOR_ID = DOCTOR_ID;
    }

    public String getPERFORMED_DEPT_ID() {
        return PERFORMED_DEPT_ID;
    }

    public void setPERFORMED_DEPT_ID(String PERFORMED_DEPT_ID) {
        this.PERFORMED_DEPT_ID = PERFORMED_DEPT_ID;
    }

    public String getAPPOINTMENT() {
        return APPOINTMENT;
    }

    public void setAPPOINTMENT(String APPOINTMENT) {
        this.APPOINTMENT = APPOINTMENT;
    }

    public String getCOMBINATION_ID() {
        return COMBINATION_ID;
    }

    public void setCOMBINATION_ID(String COMBINATION_ID) {
        this.COMBINATION_ID = COMBINATION_ID;
    }

    public String getCLASS_ON_RCPT() {
        return CLASS_ON_RCPT;
    }

    public void setCLASS_ON_RCPT(String CLASS_ON_RCPT) {
        this.CLASS_ON_RCPT = CLASS_ON_RCPT;
    }

    public String getSUBJ_CODE() {
        return SUBJ_CODE;
    }

    public void setSUBJ_CODE(String SUBJ_CODE) {
        this.SUBJ_CODE = SUBJ_CODE;
    }

    public String getCLASS_ON_RECKONING() {
        return CLASS_ON_RECKONING;
    }

    public void setCLASS_ON_RECKONING(String CLASS_ON_RECKONING) {
        this.CLASS_ON_RECKONING = CLASS_ON_RECKONING;
    }
}
