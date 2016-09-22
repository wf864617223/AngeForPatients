package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * Created by hp on 2016/6/13.
 */
public class ReportPacs {

    /**
     * status : 0
     * message : success
     * result : {"PatName":"李春梅","techNo":"XC00880","ApplyDeptName":"产科","Sex":2,"Age":29,"CardNo":"201481571","BedNo":"","ItemResult":"","ReportDoctorName":"景卫","ReportWriterName":"景卫","ReportTime":"2014/7/22 12:01:26","ExecTime":"2014/7/22 0:00:00","ItemResult1":"末","rsList":[{"imageUrl":"http://61.139.124.246:89/XC/1408/36352.jpg"},{"imageUrl":"http://61.139.124.246:89/XC/1408/36309.jpg"},{"imageUrl":"http://61.139.124.246:89/XC/1408/36310.jpg"},{"imageUrl":"http://61.139.124.246:89/XC/1408/36323.jpg"}]}
     */

    private int status;
    private String message;
    /**
     * PatName : 李春梅
     * techNo : XC00880
     * ApplyDeptName : 产科
     * Sex : 2
     * Age : 29.0
     * CardNo : 201481571
     * BedNo :
     * ItemResult :
     * ReportDoctorName : 景卫
     * ReportWriterName : 景卫
     * ReportTime : 2014/7/22 12:01:26
     * ExecTime : 2014/7/22 0:00:00
     * ItemResult1 : 末
     * rsList : [{"imageUrl":"http://61.139.124.246:89/XC/1408/36352.jpg"},{"imageUrl":"http://61.139.124.246:89/XC/1408/36309.jpg"},{"imageUrl":"http://61.139.124.246:89/XC/1408/36310.jpg"},{"imageUrl":"http://61.139.124.246:89/XC/1408/36323.jpg"}]
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
        private String PatName;
        private String techNo;
        private String ApplyDeptName;
        private int Sex;
        private double Age;
        private String CardNo;
        private String BedNo;
        private String ItemResult;
        private String ReportDoctorName;
        private String ReportWriterName;
        private String ReportTime;
        private String ExecTime;
        private String ItemResult1;

        public String getInstrument() {
            return Instrument;
        }

        public void setInstrument(String instrument) {
            Instrument = instrument;
        }

        public String getExamination() {
            return Examination;
        }

        public void setExamination(String examination) {
            Examination = examination;
        }

        private String Instrument;
        private String Examination;
        /**
         * imageUrl : http://61.139.124.246:89/XC/1408/36352.jpg
         */

        private List<ListBean> list;

        public String getPatName() {
            return PatName;
        }

        public void setPatName(String PatName) {
            this.PatName = PatName;
        }

        public String getTechNo() {
            return techNo;
        }

        public void setTechNo(String techNo) {
            this.techNo = techNo;
        }

        public String getApplyDeptName() {
            return ApplyDeptName;
        }

        public void setApplyDeptName(String ApplyDeptName) {
            this.ApplyDeptName = ApplyDeptName;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int Sex) {
            this.Sex = Sex;
        }

        public double getAge() {
            return Age;
        }

        public void setAge(double Age) {
            this.Age = Age;
        }

        public String getCardNo() {
            return CardNo;
        }

        public void setCardNo(String CardNo) {
            this.CardNo = CardNo;
        }

        public String getBedNo() {
            return BedNo;
        }

        public void setBedNo(String BedNo) {
            this.BedNo = BedNo;
        }

        public String getItemResult() {
            return ItemResult;
        }

        public void setItemResult(String ItemResult) {
            this.ItemResult = ItemResult;
        }

        public String getReportDoctorName() {
            return ReportDoctorName;
        }

        public void setReportDoctorName(String ReportDoctorName) {
            this.ReportDoctorName = ReportDoctorName;
        }

        public String getReportWriterName() {
            return ReportWriterName;
        }

        public void setReportWriterName(String ReportWriterName) {
            this.ReportWriterName = ReportWriterName;
        }

        public String getReportTime() {
            return ReportTime;
        }

        public void setReportTime(String ReportTime) {
            this.ReportTime = ReportTime;
        }

        public String getExecTime() {
            return ExecTime;
        }

        public void setExecTime(String ExecTime) {
            this.ExecTime = ExecTime;
        }

        public String getItemResult1() {
            return ItemResult1;
        }

        public void setItemResult1(String ItemResult1) {
            this.ItemResult1 = ItemResult1;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String imageUrl;

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }
        }
    }
}
