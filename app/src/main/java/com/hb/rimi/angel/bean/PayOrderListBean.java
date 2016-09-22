package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * 我的订单界面——未支付
 * Created by hp on 2016/6/28.
 */
public class PayOrderListBean {

    /**
     * message : Success
     * result : {"list":[{"createTime":1468395462000,"details":[{"amount":1,"createTime":1468395462000,"data":"{\"product_list\":[ {\"product_id\":\"9\",\"amount\":\"1\"}]}","id":"5ee7d618c7b941499150a8770fb783d4","name":"日本花王妙而舒婴儿纸尿裤s82片*2包","payOrder":{"$ref":"$.result.list[0]"},"price":0.01,"product":{"brand":"花王","category":{"id":24,"name":"护理用品","sortIndex":"1"},"countryOrigin":"日本","id":9,"image":"/wechat/img/content/stores/img-2@2x.png","introduction":"爱UI很多事都拉上科技大厦地阿森纳大师课就对啦斯柯达卡洛斯的会计师的控件事都算定","isHot":false,"isNew":true,"name":"日本花王妙而舒婴儿纸尿裤s82片*2包","price":210,"productName":"日本花王","productType":"S82*2","sales":0,"suitableCrowd":"日本人","tradePrice":0.01},"type":"商城订单"}],"id":"8c1b877848f149bebd8a93689210754a","name":"日本花王妙而舒婴儿纸尿裤s82片*2包","payWay":"支付宝","price":0.01,"status":"未付款","type":"商城订单"},{"createTime":1468395187000,"details":[{"amount":1,"createTime":1468395187000,"data":"{\"product_list\":[ {\"product_id\":\"8\",\"amount\":\"1\"}]}","id":"3efb63e5c78448fc97cf8b989f872542","name":"睿峰工作牌","payOrder":{"$ref":"$.result.list[1]"},"price":0.01,"product":{"brand":"睿峰","category":{"id":23,"name":"保健品","sortIndex":"0"},"countryOrigin":"中国成都","id":8,"image":"/wechat/img/content/stores/img-1@2x.png","introduction":"睿峰工作牌 用于身份识别。此卡为身份地位的象征，掉了赔款200","isHot":true,"isNew":false,"name":"睿峰工作牌","price":100,"productName":"睿峰工作牌","productType":"se100","sales":0,"suitableCrowd":"公司员工","tradePrice":0.01},"type":"商城订单"}],"id":"d7cd952f2fa640e38efd1c72abe4d0b7","name":"睿峰工作牌","payWay":"支付宝","price":0.01,"status":"未付款","type":"商城订单"}],"pageCount":1,"pageNumber":1,"pageSize":10,"totalCount":2}
     * status : 0
     */

    private String message;
    /**
     * list : [{"createTime":1468395462000,"details":[{"amount":1,"createTime":1468395462000,"data":"{\"product_list\":[ {\"product_id\":\"9\",\"amount\":\"1\"}]}","id":"5ee7d618c7b941499150a8770fb783d4","name":"日本花王妙而舒婴儿纸尿裤s82片*2包","payOrder":{"$ref":"$.result.list[0]"},"price":0.01,"product":{"brand":"花王","category":{"id":24,"name":"护理用品","sortIndex":"1"},"countryOrigin":"日本","id":9,"image":"/wechat/img/content/stores/img-2@2x.png","introduction":"爱UI很多事都拉上科技大厦地阿森纳大师课就对啦斯柯达卡洛斯的会计师的控件事都算定","isHot":false,"isNew":true,"name":"日本花王妙而舒婴儿纸尿裤s82片*2包","price":210,"productName":"日本花王","productType":"S82*2","sales":0,"suitableCrowd":"日本人","tradePrice":0.01},"type":"商城订单"}],"id":"8c1b877848f149bebd8a93689210754a","name":"日本花王妙而舒婴儿纸尿裤s82片*2包","payWay":"支付宝","price":0.01,"status":"未付款","type":"商城订单"},{"createTime":1468395187000,"details":[{"amount":1,"createTime":1468395187000,"data":"{\"product_list\":[ {\"product_id\":\"8\",\"amount\":\"1\"}]}","id":"3efb63e5c78448fc97cf8b989f872542","name":"睿峰工作牌","payOrder":{"$ref":"$.result.list[1]"},"price":0.01,"product":{"brand":"睿峰","category":{"id":23,"name":"保健品","sortIndex":"0"},"countryOrigin":"中国成都","id":8,"image":"/wechat/img/content/stores/img-1@2x.png","introduction":"睿峰工作牌 用于身份识别。此卡为身份地位的象征，掉了赔款200","isHot":true,"isNew":false,"name":"睿峰工作牌","price":100,"productName":"睿峰工作牌","productType":"se100","sales":0,"suitableCrowd":"公司员工","tradePrice":0.01},"type":"商城订单"}],"id":"d7cd952f2fa640e38efd1c72abe4d0b7","name":"睿峰工作牌","payWay":"支付宝","price":0.01,"status":"未付款","type":"商城订单"}]
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
         * createTime : 1468395462000
         * details : [{"amount":1,"createTime":1468395462000,"data":"{\"product_list\":[ {\"product_id\":\"9\",\"amount\":\"1\"}]}","id":"5ee7d618c7b941499150a8770fb783d4","name":"日本花王妙而舒婴儿纸尿裤s82片*2包","payOrder":{"$ref":"$.result.list[0]"},"price":0.01,"product":{"brand":"花王","category":{"id":24,"name":"护理用品","sortIndex":"1"},"countryOrigin":"日本","id":9,"image":"/wechat/img/content/stores/img-2@2x.png","introduction":"爱UI很多事都拉上科技大厦地阿森纳大师课就对啦斯柯达卡洛斯的会计师的控件事都算定","isHot":false,"isNew":true,"name":"日本花王妙而舒婴儿纸尿裤s82片*2包","price":210,"productName":"日本花王","productType":"S82*2","sales":0,"suitableCrowd":"日本人","tradePrice":0.01},"type":"商城订单"}]
         * id : 8c1b877848f149bebd8a93689210754a
         * name : 日本花王妙而舒婴儿纸尿裤s82片*2包
         * payWay : 支付宝
         * price : 0.01
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

        public static class ListBean {
            private long createTime;
            private String id;
            private String name;
            private String payWay;
            private double price;
            private String status;
            private String type;
            /**
             * amount : 1
             * createTime : 1468395462000
             * data : {"product_list":[ {"product_id":"9","amount":"1"}]}
             * id : 5ee7d618c7b941499150a8770fb783d4
             * name : 日本花王妙而舒婴儿纸尿裤s82片*2包
             * payOrder : {"$ref":"$.result.list[0]"}
             * price : 0.01
             * product : {"brand":"花王","category":{"id":24,"name":"护理用品","sortIndex":"1"},"countryOrigin":"日本","id":9,"image":"/wechat/img/content/stores/img-2@2x.png","introduction":"爱UI很多事都拉上科技大厦地阿森纳大师课就对啦斯柯达卡洛斯的会计师的控件事都算定","isHot":false,"isNew":true,"name":"日本花王妙而舒婴儿纸尿裤s82片*2包","price":210,"productName":"日本花王","productType":"S82*2","sales":0,"suitableCrowd":"日本人","tradePrice":0.01}
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

            public static class DetailsBean {
                private int amount;
                private long createTime;
                private String data;
                private String id;
                private String name;
                /**
                 * $ref : $.result.list[0]
                 */

                private PayOrderBean payOrder;
                private double price;
                /**
                 * brand : 花王
                 * category : {"id":24,"name":"护理用品","sortIndex":"1"}
                 * countryOrigin : 日本
                 * id : 9
                 * image : /wechat/img/content/stores/img-2@2x.png
                 * introduction : 爱UI很多事都拉上科技大厦地阿森纳大师课就对啦斯柯达卡洛斯的会计师的控件事都算定
                 * isHot : false
                 * isNew : true
                 * name : 日本花王妙而舒婴儿纸尿裤s82片*2包
                 * price : 210
                 * productName : 日本花王
                 * productType : S82*2
                 * sales : 0
                 * suitableCrowd : 日本人
                 * tradePrice : 0.01
                 */

                private ProductBean product;
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

                public ProductBean getProduct() {
                    return product;
                }

                public void setProduct(ProductBean product) {
                    this.product = product;
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

                public static class ProductBean {
                    private String brand;
                    /**
                     * id : 24
                     * name : 护理用品
                     * sortIndex : 1
                     */

                    private CategoryBean category;
                    private String countryOrigin;
                    private int id;
                    private String image;
                    private String introduction;
                    private boolean isHot;
                    private boolean isNew;
                    private String name;
                    private int price;
                    private String productName;
                    private String productType;
                    private int sales;
                    private String suitableCrowd;
                    private double tradePrice;

                    public String getBrand() {
                        return brand;
                    }

                    public void setBrand(String brand) {
                        this.brand = brand;
                    }

                    public CategoryBean getCategory() {
                        return category;
                    }

                    public void setCategory(CategoryBean category) {
                        this.category = category;
                    }

                    public String getCountryOrigin() {
                        return countryOrigin;
                    }

                    public void setCountryOrigin(String countryOrigin) {
                        this.countryOrigin = countryOrigin;
                    }

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

                    public String getIntroduction() {
                        return introduction;
                    }

                    public void setIntroduction(String introduction) {
                        this.introduction = introduction;
                    }

                    public boolean isIsHot() {
                        return isHot;
                    }

                    public void setIsHot(boolean isHot) {
                        this.isHot = isHot;
                    }

                    public boolean isIsNew() {
                        return isNew;
                    }

                    public void setIsNew(boolean isNew) {
                        this.isNew = isNew;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getPrice() {
                        return price;
                    }

                    public void setPrice(int price) {
                        this.price = price;
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

                    public int getSales() {
                        return sales;
                    }

                    public void setSales(int sales) {
                        this.sales = sales;
                    }

                    public String getSuitableCrowd() {
                        return suitableCrowd;
                    }

                    public void setSuitableCrowd(String suitableCrowd) {
                        this.suitableCrowd = suitableCrowd;
                    }

                    public double getTradePrice() {
                        return tradePrice;
                    }

                    public void setTradePrice(double tradePrice) {
                        this.tradePrice = tradePrice;
                    }

                    public static class CategoryBean {
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
            }
        }
    }
}
