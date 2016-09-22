package com.hb.rimi.angel.bean;

/**
 * Created by hp on 2016/6/3.
 */
public class UserInfo {


    /**
     * status : 0
     * message : success
     * result : {"Name":"罗真菊","Mobile":"15123309125","Birthday":"1966-02-04","Menses":"","MWeek":"28","EDC":"","IC_NO":"2015115997","ic_type":"家属卡","ic_fee":0}
     */

    private int status;
    private String message;
    /**
     * Name : 罗真菊
     * Mobile : 15123309125
     * Birthday : 1966-02-04
     * Menses :
     * MWeek : 28
     * EDC :
     * IC_NO : 2015115997
     * ic_type : 家属卡
     * ic_fee : 0
     */

    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String Name;
        private String Mobile;
        private String Birthday;
        private String Menses;
        private String MWeek;
        private String EDC;
        private String IC_NO;
        private String ic_type;
        private String ic_fee;
        private String sex;
        private String rebateType;

        public String getCustomerManagerMobile() {
            return CustomerManagerMobile;
        }

        public void setCustomerManagerMobile(String customerManagerMobile) {
            CustomerManagerMobile = customerManagerMobile;
        }

        private String CustomerManagerMobile;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getRebateType() {
            return rebateType;
        }

        public void setRebateType(String rebateType) {
            this.rebateType = rebateType;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String Birthday) {
            this.Birthday = Birthday;
        }

        public String getMenses() {
            return Menses;
        }

        public void setMenses(String Menses) {
            this.Menses = Menses;
        }

        public String getMWeek() {
            return MWeek;
        }

        public void setMWeek(String MWeek) {
            this.MWeek = MWeek;
        }

        public String getEDC() {
            return EDC;
        }

        public void setEDC(String EDC) {
            this.EDC = EDC;
        }

        public String getIC_NO() {
            return IC_NO;
        }

        public void setIC_NO(String IC_NO) {
            this.IC_NO = IC_NO;
        }

        public String getIc_type() {
            return ic_type;
        }

        public void setIc_type(String ic_type) {
            this.ic_type = ic_type;
        }

        public String getIc_fee() {
            return ic_fee;
        }

        public void setIc_fee(String ic_fee) {
            this.ic_fee = ic_fee;
        }
    }
}
