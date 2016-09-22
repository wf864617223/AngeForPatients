package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * Created by hp on 2016/6/12.
 */
public class ListReport {

    /**
     * status : 0
     * message : success
     * result : [{"id":202524,"check_time":"2016/5/29 0:00:00","report_time":"2016/5/29 16:05:56","type":1},{"id":202525,"check_time":"2016/5/29 0:00:00","report_time":"2016/5/29 16:07:27","type":1},{"id":202531,"check_time":"2016/5/29 0:00:00","report_time":"2016/5/29 16:27:07","type":1},{"id":202532,"check_time":"2016/5/29 0:00:00","report_time":"2016/5/29 16:38:03","type":1},{"id":202533,"check_time":"2016/5/29 0:00:00","report_time":"2016/5/29 16:46:41","type":1},{"id":202534,"check_time":"2016/5/29 0:00:00","report_time":"2016/5/29 21:10:05","type":1},{"id":9121,"check_time":"2016/5/29 15:21:34","report_time":"2016/5/29 15:21:34","type":3},{"id":9123,"check_time":"2016/5/29 16:46:45","report_time":"2016/5/29 16:46:45","type":3},{"id":9124,"check_time":"2016/5/29 16:46:53","report_time":"2016/5/29 16:46:53","type":3}]
     */

    private int status;
    private String message;
    /**
     * id : 202524
     * check_time : 2016/5/29 0:00:00
     * report_time : 2016/5/29 16:05:56
     * type : 1
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
        private int id;
        private String check_time;
        private String report_time;
        private String report_Name;

        public String getReport_Name() {
            return report_Name;
        }

        public void setReport_Name(String report_Name) {
            this.report_Name = report_Name;
        }

        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCheck_time() {
            return check_time;
        }

        public void setCheck_time(String check_time) {
            this.check_time = check_time;
        }

        public String getReport_time() {
            return report_time;
        }

        public void setReport_time(String report_time) {
            this.report_time = report_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
