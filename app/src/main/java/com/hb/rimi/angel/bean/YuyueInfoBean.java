package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * Created by hp on 2016/6/16.
 */
public class YuyueInfoBean {

    /**
     * status : 0
     * message : success
     * result : [{"PATIENT_NAME":"卢婷","INPUT_DATE":"2015-09-20 10:26:58","REGISTER_NO":"150920-01153076","IC_CODE":"900001552","DOC_CODE":"pingli","DOC_NAME":"李萍","DEPT_CODE":"0133","DEPT_NAME":"产科","ENSURE_FLAG":"1","OPER_DATE":null}]
     */

    private int status;
    private String message;
    /**
     * PATIENT_NAME : 卢婷
     * INPUT_DATE : 2015-09-20 10:26:58
     * REGISTER_NO : 150920-01153076
     * IC_CODE : 900001552
     * DOC_CODE : pingli
     * DOC_NAME : 李萍
     * DEPT_CODE : 0133
     * DEPT_NAME : 产科
     * ENSURE_FLAG : 1
     * OPER_DATE : null
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
        private String PATIENT_NAME;
        private String INPUT_DATE;
        private String REGISTER_NO;
        private String IC_CODE;
        private String DOC_CODE;
        private String DOC_NAME;
        private String DEPT_CODE;
        private String DEPT_NAME;
        private String ENSURE_FLAG;
        private String OPER_DATE;

        public String getPATIENT_NAME() {
            return PATIENT_NAME;
        }

        public void setPATIENT_NAME(String PATIENT_NAME) {
            this.PATIENT_NAME = PATIENT_NAME;
        }

        public String getINPUT_DATE() {
            return INPUT_DATE;
        }

        public void setINPUT_DATE(String INPUT_DATE) {
            this.INPUT_DATE = INPUT_DATE;
        }

        public String getREGISTER_NO() {
            return REGISTER_NO;
        }

        public void setREGISTER_NO(String REGISTER_NO) {
            this.REGISTER_NO = REGISTER_NO;
        }

        public String getIC_CODE() {
            return IC_CODE;
        }

        public void setIC_CODE(String IC_CODE) {
            this.IC_CODE = IC_CODE;
        }

        public String getDOC_CODE() {
            return DOC_CODE;
        }

        public void setDOC_CODE(String DOC_CODE) {
            this.DOC_CODE = DOC_CODE;
        }

        public String getDOC_NAME() {
            return DOC_NAME;
        }

        public void setDOC_NAME(String DOC_NAME) {
            this.DOC_NAME = DOC_NAME;
        }

        public String getDEPT_CODE() {
            return DEPT_CODE;
        }

        public void setDEPT_CODE(String DEPT_CODE) {
            this.DEPT_CODE = DEPT_CODE;
        }

        public String getDEPT_NAME() {
            return DEPT_NAME;
        }

        public void setDEPT_NAME(String DEPT_NAME) {
            this.DEPT_NAME = DEPT_NAME;
        }

        public String getENSURE_FLAG() {
            return ENSURE_FLAG;
        }

        public void setENSURE_FLAG(String ENSURE_FLAG) {
            this.ENSURE_FLAG = ENSURE_FLAG;
        }

        public String getOPER_DATE() {
            return OPER_DATE;
        }

        public void setOPER_DATE(String OPER_DATE) {
            this.OPER_DATE = OPER_DATE;
        }
    }
}
