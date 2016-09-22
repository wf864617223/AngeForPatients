package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * 医生挂号费用列表 需要根据医生id和名字进行比较
 * Created by rimi on 2016/6/20.
 */
public class ResDoctorCost {

    /**
     * status : 0
     * message : success
     * result : [{"DOCTOR_ID":"maq","NAME":"马琴","TELEPHONE":null,"POST":null,"DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"2","PRICE":30,"REMARK":"初诊"},{"DOCTOR_ID":"lixiaox","NAME":"李晓霞","TELEPHONE":null,"POST":"主治医师","DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"2","PRICE":30,"REMARK":"初诊"},{"DOCTOR_ID":"lixue","NAME":"李雪","TELEPHONE":null,"POST":"主治医师","DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"2","PRICE":30,"REMARK":"初诊"},{"DOCTOR_ID":"lining","NAME":"李宁","TELEPHONE":null,"POST":"副主任医师","DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"5","PRICE":40,"REMARK":"初诊"},{"DOCTOR_ID":"djfang","NAME":"邓金芳","TELEPHONE":null,"POST":null,"DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"2","PRICE":30,"REMARK":"初诊"},{"DOCTOR_ID":"pingli","NAME":"李萍","TELEPHONE":null,"POST":"主任医师","DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"8","PRICE":50,"REMARK":"初诊"},{"DOCTOR_ID":"malan","NAME":"马澜","TELEPHONE":null,"POST":null,"DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"5","PRICE":40,"REMARK":"初诊"},{"DOCTOR_ID":"sxling","NAME":"苏小玲","TELEPHONE":null,"POST":null,"DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"2","PRICE":30,"REMARK":"初诊"},{"DOCTOR_ID":"suzl","NAME":"苏祖连","TELEPHONE":null,"POST":null,"DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"2","PRICE":30,"REMARK":"初诊"},{"DOCTOR_ID":"hzyan","NAME":"韩字研","TELEPHONE":null,"POST":"主任医师","DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"11","PRICE":280,"REMARK":"初诊"},{"DOCTOR_ID":"jsxun","NAME":"金善循","TELEPHONE":null,"POST":"主治医师","DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"8","PRICE":50,"REMARK":"初诊"},{"DOCTOR_ID":"youhong","NAME":"游虹","TELEPHONE":null,"POST":null,"DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"2","PRICE":30,"REMARK":"初诊"},{"DOCTOR_ID":"zhaojie","NAME":"赵洁","TELEPHONE":null,"POST":null,"DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"2","PRICE":30,"REMARK":"初诊"},{"DOCTOR_ID":"xbing","NAME":"肖兵","TELEPHONE":null,"POST":"主任医师","DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"23","PRICE":40,"REMARK":"初诊"},{"DOCTOR_ID":"wanghm","NAME":"王红梅","TELEPHONE":null,"POST":null,"DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"2","PRICE":30,"REMARK":"初诊"},{"DOCTOR_ID":"lshua","NAME":"刘绍华","TELEPHONE":null,"POST":null,"DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"5","PRICE":40,"REMARK":"初诊"},{"DOCTOR_ID":"lpeng","NAME":"彭丽","TELEPHONE":null,"POST":"医师","DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"2","PRICE":30,"REMARK":"初诊"},{"DOCTOR_ID":"nsling","NAME":"倪寿菱","TELEPHONE":null,"POST":"副主任医师","DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"8","PRICE":50,"REMARK":"初诊"},{"DOCTOR_ID":"pmdan","NAME":"彭明丹","TELEPHONE":null,"POST":"主治医师","DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"2","PRICE":30,"REMARK":"初诊"},{"DOCTOR_ID":"yxqian","NAME":"苑小倩","TELEPHONE":null,"POST":null,"DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"5","PRICE":40,"REMARK":"初诊"},{"DOCTOR_ID":"zhaozhan","NAME":"赵展","TELEPHONE":null,"POST":null,"DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"5","PRICE":40,"REMARK":"初诊"},{"DOCTOR_ID":"zaning","NAME":"朱爱宁","TELEPHONE":null,"POST":null,"DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"2","PRICE":30,"REMARK":"初诊"},{"DOCTOR_ID":"yuansh","NAME":"袁守红","TELEPHONE":null,"POST":"主治医师","DEPT_ID":"0132","DEPT_NAME":"妇科","REGISTER_TYPE":"2","PRICE":30,"REMARK":"初诊"}]
     */

    private int status;
    private String message;
    /**
     * DOCTOR_ID : maq
     * NAME : 马琴
     * TELEPHONE : null
     * POST : null
     * DEPT_ID : 0132
     * DEPT_NAME : 妇科
     * REGISTER_TYPE : 2
     * PRICE : 30
     * REMARK : 初诊
     */

    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String DOCTOR_ID;
        private String NAME;
        private Object TELEPHONE;
        private Object POST;
        private String DEPT_ID;
        private String DEPT_NAME;
        private String REGISTER_TYPE;
        private double PRICE;
        private String REMARK;

        public String getDOCTOR_ID() {
            return DOCTOR_ID;
        }

        public void setDOCTOR_ID(String DOCTOR_ID) {
            this.DOCTOR_ID = DOCTOR_ID;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public Object getTELEPHONE() {
            return TELEPHONE;
        }

        public void setTELEPHONE(Object TELEPHONE) {
            this.TELEPHONE = TELEPHONE;
        }

        public Object getPOST() {
            return POST;
        }

        public void setPOST(Object POST) {
            this.POST = POST;
        }

        public String getDEPT_ID() {
            return DEPT_ID;
        }

        public void setDEPT_ID(String DEPT_ID) {
            this.DEPT_ID = DEPT_ID;
        }

        public String getDEPT_NAME() {
            return DEPT_NAME;
        }

        public void setDEPT_NAME(String DEPT_NAME) {
            this.DEPT_NAME = DEPT_NAME;
        }

        public String getREGISTER_TYPE() {
            return REGISTER_TYPE;
        }

        public void setREGISTER_TYPE(String REGISTER_TYPE) {
            this.REGISTER_TYPE = REGISTER_TYPE;
        }

        public double getPRICE() {
            return PRICE;
        }

        public void setPRICE(double PRICE) {
            this.PRICE = PRICE;
        }

        public String getREMARK() {
            return REMARK;
        }

        public void setREMARK(String REMARK) {
            this.REMARK = REMARK;
        }
    }
}
