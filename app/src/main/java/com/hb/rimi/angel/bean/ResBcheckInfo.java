package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * 就诊提醒-B超检查信息
 * Created by rimi on 2016/6/20.
 */
public class ResBcheckInfo {

    /**
     * status : 0
     * message : success
     * result : [{"appProject":"B超","sort":8,"AppTime":"2016-06-17 00:00:00"}]
     */

    private int status;
    private String message;
    /**
     * appProject : B超
     * sort : 8
     * AppTime : 2016-06-17 00:00:00
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
        private String appProject;
        private int sort;
        private String AppTime;
        private String ComeTime;//到达时间

        public String getAppProject() {
            return appProject;
        }

        public void setAppProject(String appProject) {
            this.appProject = appProject;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getAppTime() {
            return AppTime;
        }

        public void setAppTime(String AppTime) {
            this.AppTime = AppTime;
        }

        public String getComeTime() {
            return ComeTime;
        }

        public void setComeTime(String comeTime) {
            ComeTime = comeTime;
        }
    }
}
