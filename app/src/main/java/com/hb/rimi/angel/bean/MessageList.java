package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * Created by hp on 2016/6/30.
 */
public class MessageList {

    /**
     * status : 0
     * message : success
     * result : {"pageCount":1,"pageNumber":1,"pageSize":10,"totalCount":2,"list":[{"content":"content","expand":"1","id":1,"sendTime":1465714496000,"readTime":1465714496000,"title":"title","type":"提示"}]}
     */

    private int status;
    private String message;
    /**
     * pageCount : 1
     * pageNumber : 1
     * pageSize : 10
     * totalCount : 2
     * list : [{"content":"content","expand":"1","id":1,"sendTime":1465714496000,"readTime":1465714496000,"title":"title","type":"提示"}]
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
        private int pageCount;
        private int pageNumber;
        private int pageSize;
        private int totalCount;
        /**
         * content : content
         * expand : 1
         * id : 1
         * sendTime : 1465714496000
         * readTime : 1465714496000
         * title : title
         * type : 提示
         */

        private List<ListBean> list;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String content;
            private String expand;
            private int id;
            private long sendTime;
            private long readTime;
            private String title;
            private String type;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getExpand() {
                return expand;
            }

            public void setExpand(String expand) {
                this.expand = expand;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getSendTime() {
                return sendTime;
            }

            public void setSendTime(long sendTime) {
                this.sendTime = sendTime;
            }

            public long getReadTime() {
                return readTime;
            }

            public void setReadTime(long readTime) {
                this.readTime = readTime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
