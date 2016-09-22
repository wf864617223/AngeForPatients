package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * Created by hp on 2016/6/13.
 */
public class ResponseLis {


    /**
     * status : 0
     * message : success
     * result : {"ApplyNo":2017,"techNo":"7","PatName":"乔静梅","Sex":2,"Age":30,"CardNo":"400009117","ApplyDeptName":"门诊部","BedNo":"","slavename":"血","SampleTime":"2014/6/16 11:43:14","ReceiveTime":"2014/6/16 11:43:14","ClinicDesc":"","toDocName":"赵展","ExecDocName":"倪孝君","ExecTime":"2014/6/16 0:00:00","ReportTime":"2014/6/16 12:03:56","VerifierName":"倪孝君","PubDateTime":"2014/6/16 13:23:32","list":[{"ItemName":"纤维蛋白原","RESULT":"3.38","REFERENCERANGE":"1.80～3.50","UNIT":"g/l                 "},{"ItemName":"凝血酶原时间","RESULT":"10.50","REFERENCERANGE":"9.80～12.10","UNIT":"s                   "},{"ItemName":"国际标准化比值","RESULT":"0.90","REFERENCERANGE":"0.85～1.25","UNIT":"                    "},{"ItemName":"凝血酶时间","RESULT":"19.90","REFERENCERANGE":"14.00～21.00","UNIT":"s                   "}]}
     */

    private int status;
    private String message;
    /**
     * ApplyNo : 2017
     * techNo : 7
     * PatName : 乔静梅
     * Sex : 2
     * Age : 30
     * CardNo : 400009117
     * ApplyDeptName : 门诊部
     * BedNo :
     * slavename : 血
     * SampleTime : 2014/6/16 11:43:14
     * ReceiveTime : 2014/6/16 11:43:14
     * ClinicDesc :
     * toDocName : 赵展
     * ExecDocName : 倪孝君
     * ExecTime : 2014/6/16 0:00:00
     * ReportTime : 2014/6/16 12:03:56
     * VerifierName : 倪孝君
     * PubDateTime : 2014/6/16 13:23:32
     * list : [{"ItemName":"纤维蛋白原","RESULT":"3.38","REFERENCERANGE":"1.80～3.50","UNIT":"g/l                 "},{"ItemName":"凝血酶原时间","RESULT":"10.50","REFERENCERANGE":"9.80～12.10","UNIT":"s                   "},{"ItemName":"国际标准化比值","RESULT":"0.90","REFERENCERANGE":"0.85～1.25","UNIT":"                    "},{"ItemName":"凝血酶时间","RESULT":"19.90","REFERENCERANGE":"14.00～21.00","UNIT":"s                   "}]
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
        private long ApplyNo;
        private String techNo;
        private String PatName;
        private int Sex;
        private int Age;
        private String CardNo;
        private String ApplyDeptName;
        private String BedNo;
        private String slavename;
        private String SampleTime;
        private String ReceiveTime;
        private String ClinicDesc;
        private String toDocName;
        private String ExecDocName;
        private String ExecTime;
        private String ReportTime;
        private String VerifierName;
        private String PubDateTime;
        /**
         * ItemName : 纤维蛋白原
         * RESULT : 3.38
         * REFERENCERANGE : 1.80～3.50
         * UNIT : g/l
         */

        private List<ListBean> list;

        public long getApplyNo() {
            return ApplyNo;
        }

        public void setApplyNo(long ApplyNo) {
            this.ApplyNo = ApplyNo;
        }

        public String getTechNo() {
            return techNo;
        }

        public void setTechNo(String techNo) {
            this.techNo = techNo;
        }

        public String getPatName() {
            return PatName;
        }

        public void setPatName(String PatName) {
            this.PatName = PatName;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int Sex) {
            this.Sex = Sex;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int Age) {
            this.Age = Age;
        }

        public String getCardNo() {
            return CardNo;
        }

        public void setCardNo(String CardNo) {
            this.CardNo = CardNo;
        }

        public String getApplyDeptName() {
            return ApplyDeptName;
        }

        public void setApplyDeptName(String ApplyDeptName) {
            this.ApplyDeptName = ApplyDeptName;
        }

        public String getBedNo() {
            return BedNo;
        }

        public void setBedNo(String BedNo) {
            this.BedNo = BedNo;
        }

        public String getSlavename() {
            return slavename;
        }

        public void setSlavename(String slavename) {
            this.slavename = slavename;
        }

        public String getSampleTime() {
            return SampleTime;
        }

        public void setSampleTime(String SampleTime) {
            this.SampleTime = SampleTime;
        }

        public String getReceiveTime() {
            return ReceiveTime;
        }

        public void setReceiveTime(String ReceiveTime) {
            this.ReceiveTime = ReceiveTime;
        }

        public String getClinicDesc() {
            return ClinicDesc;
        }

        public void setClinicDesc(String ClinicDesc) {
            this.ClinicDesc = ClinicDesc;
        }

        public String getToDocName() {
            return toDocName;
        }

        public void setToDocName(String toDocName) {
            this.toDocName = toDocName;
        }

        public String getExecDocName() {
            return ExecDocName;
        }

        public void setExecDocName(String ExecDocName) {
            this.ExecDocName = ExecDocName;
        }

        public String getExecTime() {
            return ExecTime;
        }

        public void setExecTime(String ExecTime) {
            this.ExecTime = ExecTime;
        }

        public String getReportTime() {
            return ReportTime;
        }

        public void setReportTime(String ReportTime) {
            this.ReportTime = ReportTime;
        }

        public String getVerifierName() {
            return VerifierName;
        }

        public void setVerifierName(String VerifierName) {
            this.VerifierName = VerifierName;
        }

        public String getPubDateTime() {
            return PubDateTime;
        }

        public void setPubDateTime(String PubDateTime) {
            this.PubDateTime = PubDateTime;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String ItemName;
            private String RESULT;
            private String REFERENCERANGE;
            private String UNIT;

            public String getItemName() {
                return ItemName;
            }

            public void setItemName(String ItemName) {
                this.ItemName = ItemName;
            }

            public String getRESULT() {
                return RESULT;
            }

            public void setRESULT(String RESULT) {
                this.RESULT = RESULT;
            }

            public String getREFERENCERANGE() {
                return REFERENCERANGE;
            }

            public void setREFERENCERANGE(String REFERENCERANGE) {
                this.REFERENCERANGE = REFERENCERANGE;
            }

            public String getUNIT() {
                return UNIT;
            }

            public void setUNIT(String UNIT) {
                this.UNIT = UNIT;
            }
        }
    }
}
