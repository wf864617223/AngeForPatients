package com.hb.rimi.angel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hp on 2016/7/13.
 */
public class PayOrderedBean{

    /**
     * message : Success
     * result : {"list":[{"createTime":1468399340000,"details":[{"amount":2,"createTime":1468399340000,"data":"{\"product_list\":[ {\"product_id\":\"10\",\"amount\":\"2\"}]}","id":"011c3c8a237b4b1ba9fe56db13e598ec","image":"/wechat/img/content/stores/img-3@2x.png","name":"白开水","payOrder":{"$ref":"$.result.list[0]"},"price":0.01,"productId":10,"productName":"白开水","productType":"一瓶","type":"商城订单"}],"id":"21a6a2bbeee34546afbfc304d8526c55","name":"白开水","payWay":"支付宝","price":0.02,"status":"未付款","type":"商城订单"},{"createTime":1468399153000,"details":[{"amount":1,"createTime":1468399153000,"data":"{\"product_list\":[ {\"product_id\":\"8\",\"amount\":\"1\"}]}","id":"3af4abbd68e54f7b8beb055b227b5a87","image":"/wechat/img/content/stores/img-1@2x.png","name":"睿峰工作牌","payOrder":{"$ref":"$.result.list[1]"},"price":0.01,"productId":8,"productName":"睿峰工作牌","productType":"se100","type":"商城订单"}],"id":"674814e0a6b94c8bb0d49a74f91893c3","name":"睿峰工作牌","payWay":"支付宝","price":0.01,"status":"未付款","type":"商城订单"}],"pageCount":1,"pageNumber":1,"pageSize":10,"totalCount":2}
     * status : 0
     */

    private String message;
    /**
     * list : [{"createTime":1468399340000,"details":[{"amount":2,"createTime":1468399340000,"data":"{\"product_list\":[ {\"product_id\":\"10\",\"amount\":\"2\"}]}","id":"011c3c8a237b4b1ba9fe56db13e598ec","image":"/wechat/img/content/stores/img-3@2x.png","name":"白开水","payOrder":{"$ref":"$.result.list[0]"},"price":0.01,"productId":10,"productName":"白开水","productType":"一瓶","type":"商城订单"}],"id":"21a6a2bbeee34546afbfc304d8526c55","name":"白开水","payWay":"支付宝","price":0.02,"status":"未付款","type":"商城订单"},{"createTime":1468399153000,"details":[{"amount":1,"createTime":1468399153000,"data":"{\"product_list\":[ {\"product_id\":\"8\",\"amount\":\"1\"}]}","id":"3af4abbd68e54f7b8beb055b227b5a87","image":"/wechat/img/content/stores/img-1@2x.png","name":"睿峰工作牌","payOrder":{"$ref":"$.result.list[1]"},"price":0.01,"productId":8,"productName":"睿峰工作牌","productType":"se100","type":"商城订单"}],"id":"674814e0a6b94c8bb0d49a74f91893c3","name":"睿峰工作牌","payWay":"支付宝","price":0.01,"status":"未付款","type":"商城订单"}]
     * pageCount : 1
     * pageNumber : 1
     * pageSize : 10
     * totalCount : 2
     */

    private ResultBean result;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ResultBean {
        private int pageCount;
        private int pageNumber;
        private int pageSize;
        private int totalCount;
        /**
         * createTime : 1468399340000
         * details : [{"amount":2,"createTime":1468399340000,"data":"{\"product_list\":[ {\"product_id\":\"10\",\"amount\":\"2\"}]}","id":"011c3c8a237b4b1ba9fe56db13e598ec","image":"/wechat/img/content/stores/img-3@2x.png","name":"白开水","payOrder":{"$ref":"$.result.list[0]"},"price":0.01,"productId":10,"productName":"白开水","productType":"一瓶","type":"商城订单"}]
         * id : 21a6a2bbeee34546afbfc304d8526c55
         * name : 白开水
         * payWay : 支付宝
         * price : 0.02
         * status : 未付款
         * type : 商城订单
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

        public static class ListBean  implements Serializable{
            private long createTime;
            private String id;
            private String name;
            private String payWay;
            private double price;
            private String status;
            private String type;
            /**
             * amount : 2
             * createTime : 1468399340000
             * data : {"product_list":[ {"product_id":"10","amount":"2"}]}
             * id : 011c3c8a237b4b1ba9fe56db13e598ec
             * image : /wechat/img/content/stores/img-3@2x.png
             * name : 白开水
             * payOrder : {"$ref":"$.result.list[0]"}
             * price : 0.01
             * productId : 10
             * productName : 白开水
             * productType : 一瓶
             * type : 商城订单
             */

            private List<DetailsBean> details;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPayWay() {
                return payWay;
            }

            public void setPayWay(String payWay) {
                this.payWay = payWay;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<DetailsBean> getDetails() {
                return details;
            }

            public void setDetails(List<DetailsBean> details) {
                this.details = details;
            }

            public static class DetailsBean{
                private int amount;
                private long createTime;
                private String data;
                private String id;
                private String image;
                private String name;
                /**
                 * $ref : $.result.list[0]
                 */

                private PayOrderBean payOrder;
                private double price;
                private int productId;
                private String productName;
                private String productType;
                private String type;

                public int getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public String getData() {
                    return data;
                }

                public void setData(String data) {
                    this.data = data;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public PayOrderBean getPayOrder() {
                    return payOrder;
                }

                public void setPayOrder(PayOrderBean payOrder) {
                    this.payOrder = payOrder;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getProductType() {
                    return productType;
                }

                public void setProductType(String productType) {
                    this.productType = productType;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public static class PayOrderBean {
                    private String $ref;

                    public String get$ref() {
                        return $ref;
                    }

                    public void set$ref(String $ref) {
                        this.$ref = $ref;
                    }
                }
            }
        }
    }
}
