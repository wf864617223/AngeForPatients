package com.hb.rimi.angel.bean;

/**
 * 检查项
 * Created by rimi on 2016/6/12.
 */
public class OptionalCheck {


    /**
     * ITEM_CODE : 023000001
     * ITEM_NAME : 中晚期产前超声二级检查
     * ITEM_ID : 5.0
     * CHENCK_OBJECT : 腹部
     */

    private String ITEM_CODE;//代码
    private String ITEM_NAME;//项目名
    private double ITEM_ID;//主键
    private String CHENCK_OBJECT;//部位
    private double ITEM_PRICE;//价格
    /**
     * DEPT_CODE : 0230
     * DETP_NAME : B超室
     * ITEM_TYPE : 1
     * FEE_TYPE_MASK : null
     * CLASS_ON_OUTP_RCPT : 06
     * DESCRIBE : null
     * CLASS_ON_RECKONING : 27
     * SUBJ_CODE : 27
     * DESCRIBE_DETAIL : (1) 胎儿头颅:颅骨强回声环，脑中线，侧脑室，后颅窝池 (2) 胎儿脊柱:排列，连续性 (3) 胎儿心脏:显示并观察四腔心切面 (4) 胎儿腹部:观察腹壁，脐带腹壁入口，肝，胃，双肾，膀胱 (5) 胎儿四肢:显示一般股骨并测量股骨长度，显示一侧肱骨并测量肱骨长度。 四维产前超声三级筛查（系统筛查），时间22周--26周
     */

    private String DEPT_CODE;
    private String DETP_NAME;
    private String ITEM_TYPE;
    private Object FEE_TYPE_MASK;
    private String CLASS_ON_OUTP_RCPT;
    private String DESCRIBE;
    private String CLASS_ON_RECKONING;
    private String SUBJ_CODE;
    private String DESCRIBE_DETAIL;


    public String getITEM_CODE() {
        return ITEM_CODE;
    }

    public void setITEM_CODE(String ITEM_CODE) {
        this.ITEM_CODE = ITEM_CODE;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public double getITEM_ID() {
        return ITEM_ID;
    }

    public void setITEM_ID(double ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }

    public String getCHENCK_OBJECT() {
        return CHENCK_OBJECT;
    }

    public void setCHENCK_OBJECT(String CHENCK_OBJECT) {
        this.CHENCK_OBJECT = CHENCK_OBJECT;
    }

    public double getITEM_PRICE() {
        return ITEM_PRICE;
    }

    public void setITEM_PRICE(double ITEM_PRICE) {
        this.ITEM_PRICE = ITEM_PRICE;
    }

    public String getDEPT_CODE() {
        return DEPT_CODE;
    }

    public void setDEPT_CODE(String DEPT_CODE) {
        this.DEPT_CODE = DEPT_CODE;
    }

    public String getDETP_NAME() {
        return DETP_NAME;
    }

    public void setDETP_NAME(String DETP_NAME) {
        this.DETP_NAME = DETP_NAME;
    }

    public String getITEM_TYPE() {
        return ITEM_TYPE;
    }

    public void setITEM_TYPE(String ITEM_TYPE) {
        this.ITEM_TYPE = ITEM_TYPE;
    }

    public Object getFEE_TYPE_MASK() {
        return FEE_TYPE_MASK;
    }

    public void setFEE_TYPE_MASK(Object FEE_TYPE_MASK) {
        this.FEE_TYPE_MASK = FEE_TYPE_MASK;
    }

    public String getCLASS_ON_OUTP_RCPT() {
        return CLASS_ON_OUTP_RCPT;
    }

    public void setCLASS_ON_OUTP_RCPT(String CLASS_ON_OUTP_RCPT) {
        this.CLASS_ON_OUTP_RCPT = CLASS_ON_OUTP_RCPT;
    }

    public String getDESCRIBE() {
        return DESCRIBE;
    }

    public void setDESCRIBE(String DESCRIBE) {
        this.DESCRIBE = DESCRIBE;
    }

    public String getCLASS_ON_RECKONING() {
        return CLASS_ON_RECKONING;
    }

    public void setCLASS_ON_RECKONING(String CLASS_ON_RECKONING) {
        this.CLASS_ON_RECKONING = CLASS_ON_RECKONING;
    }

    public String getSUBJ_CODE() {
        return SUBJ_CODE;
    }

    public void setSUBJ_CODE(String SUBJ_CODE) {
        this.SUBJ_CODE = SUBJ_CODE;
    }

    public String getDESCRIBE_DETAIL() {
        return DESCRIBE_DETAIL;
    }

    public void setDESCRIBE_DETAIL(String DESCRIBE_DETAIL) {
        this.DESCRIBE_DETAIL = DESCRIBE_DETAIL;
    }
}
