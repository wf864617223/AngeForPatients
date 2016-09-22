package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * Created by hp on 2016/7/4.
 */
public class BusListBean {

    /**
     * status : 0
     * message : success
     * result : {"pageCount":1,"pageNumber":1,"pageSize":10,"totalCount":4,"list":[{"image":"/upload/1467954968273WeChat_1467950130.png","price":100,"name":"睿峰工作牌","tradePrice":50,"sales":0},{"image":"/upload/1467955014796huwang-1@3x.png","price":210,"name":"日本花王妙而舒婴儿纸尿裤s82片*2包","tradePrice":100,"sales":0},{"image":"/upload/1467954720388WeChat_1467956072.png","price":20,"name":"白开水","tradePrice":10,"sales":0},{"image":"","price":50,"name":"中国成都第一茶，茶叶精选 龙泉水泡制，无任何添加剂","tradePrice":40,"sales":0}]}
     */

    private int status;
    private String message;
    /**
     * pageCount : 1
     * pageNumber : 1
     * pageSize : 10
     * totalCount : 4
     * list : [{"image":"/upload/1467954968273WeChat_1467950130.png","price":100,"name":"睿峰工作牌","tradePrice":50,"sales":0},{"image":"/upload/1467955014796huwang-1@3x.png","price":210,"name":"日本花王妙而舒婴儿纸尿裤s82片*2包","tradePrice":100,"sales":0},{"image":"/upload/1467954720388WeChat_1467956072.png","price":20,"name":"白开水","tradePrice":10,"sales":0},{"image":"","price":50,"name":"中国成都第一茶，茶叶精选 龙泉水泡制，无任何添加剂","tradePrice":40,"sales":0}]
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
         * image : /upload/1467954968273WeChat_1467950130.png
         * price : 100.0
         * name : 睿峰工作牌
         * tradePrice : 50.0
         * sales : 0
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
            private String image;
            private double price;
            private String name;
            private double tradePrice;
            private int sales;
            private int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getTradePrice() {
                return tradePrice;
            }

            public void setTradePrice(double tradePrice) {
                this.tradePrice = tradePrice;
            }

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }
        }
    }
}
