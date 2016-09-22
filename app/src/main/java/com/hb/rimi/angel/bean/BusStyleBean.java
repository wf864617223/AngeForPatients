package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * Created by hp on 2016/7/4.
 */
public class BusStyleBean {

    /**
     * status : 0
     * message : success
     * result : [{"id":1,"name":"one","sortIndex":"1"},{"id":2,"name":"twoo","sortIndex":"23"},{"id":3,"name":"threee","sortIndex":"5"},{"id":17,"name":"dfdsf","sortIndex":"4"},{"id":18,"name":"dddd","sortIndex":"5"},{"id":19,"name":"rrrr","sortIndex":"6"},{"id":20,"name":"gggg","sortIndex":"7"},{"id":21,"name":"tttt","sortIndex":"8"},{"id":22,"name":"fsff","sortIndex":"9"}]
     */

    private int status;
    private String message;
    /**
     * id : 1
     * name : one
     * sortIndex : 1
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
        private String name;
        private String sortIndex;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSortIndex() {
            return sortIndex;
        }

        public void setSortIndex(String sortIndex) {
            this.sortIndex = sortIndex;
        }
    }
}
