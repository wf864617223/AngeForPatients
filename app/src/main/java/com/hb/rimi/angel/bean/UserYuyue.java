package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * Created by hp on 2016/6/16.
 */
public class UserYuyue {

    /**
     * status : 0
     * message : success
     * result : [{"OUTP_NO":"62010622","PATIENT_NAME":"高思琪","PHONE":"13518160943","DOCTOR_NAME":"李宁","ITEM_NAME":"唐氏筛查(套餐内打折项)","BESPOKE_TIME":"2015-12-25 00:00:00","IC_TYPE":"蓝贝贝"},{"OUTP_NO":"62010622","PATIENT_NAME":"高思琪","PHONE":"13518160943","DOCTOR_NAME":"李宁","ITEM_NAME":"采血费","BESPOKE_TIME":"2015-12-25 00:00:00","IC_TYPE":"蓝贝贝"},{"OUTP_NO":"62010622","PATIENT_NAME":"高思琪","PHONE":"13518160943","DOCTOR_NAME":"李宁","ITEM_NAME":"多普勒胎心监测","BESPOKE_TIME":"2015-12-25 00:00:00","IC_TYPE":"蓝贝贝"},{"OUTP_NO":"62010622","PATIENT_NAME":"高思琪","PHONE":"13518160943","DOCTOR_NAME":"李宁","ITEM_NAME":"产科诊疗费(套餐打折项)","BESPOKE_TIME":"2015-12-25 00:00:00","IC_TYPE":"蓝贝贝"}]
     */

    private int status;
    private String message;
    /**
     * OUTP_NO : 62010622
     * PATIENT_NAME : 高思琪
     * PHONE : 13518160943
     * DOCTOR_NAME : 李宁
     * ITEM_NAME : 唐氏筛查(套餐内打折项)
     * BESPOKE_TIME : 2015-12-25 00:00:00
     * IC_TYPE : 蓝贝贝
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
        private String OUTP_NO;
        private String PATIENT_NAME;
        private String PHONE;
        private String DOCTOR_NAME;
        private String ITEM_NAME;
        private String BESPOKE_TIME;
        private String IC_TYPE;
        private List<UserYuyue.ResultBean> resultBeans;
        public String getOUTP_NO() {
            return OUTP_NO;
        }

        public void setOUTP_NO(String OUTP_NO) {
            this.OUTP_NO = OUTP_NO;
        }

        public String getPATIENT_NAME() {
            return PATIENT_NAME;
        }

        public void setPATIENT_NAME(String PATIENT_NAME) {
            this.PATIENT_NAME = PATIENT_NAME;
        }

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }

        public String getDOCTOR_NAME() {
            return DOCTOR_NAME;
        }

        public void setDOCTOR_NAME(String DOCTOR_NAME) {
            this.DOCTOR_NAME = DOCTOR_NAME;
        }

        public String getITEM_NAME() {
            return ITEM_NAME;
        }

        public void setITEM_NAME(String ITEM_NAME) {
            this.ITEM_NAME = ITEM_NAME;
        }

        public String getBESPOKE_TIME() {
            return BESPOKE_TIME;
        }

        public void setBESPOKE_TIME(String BESPOKE_TIME) {
            this.BESPOKE_TIME = BESPOKE_TIME;
        }

        public String getIC_TYPE() {
            return IC_TYPE;
        }

        public void setIC_TYPE(String IC_TYPE) {
            this.IC_TYPE = IC_TYPE;
        }

        public List<ResultBean> getResultBeans() {
            return resultBeans;
        }

        public void setResultBeans(List<ResultBean> resultBeans) {
            this.resultBeans = resultBeans;
        }
    }
}
