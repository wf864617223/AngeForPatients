package com.hb.rimi.angel.bean;

/**
 * Created by hp on 2016/7/5.
 */
public class BusInfoBean {

    /**
     * status : 0
     * message : success
     * result : {"id":8,"name":"日本Kao花王妙而舒婴儿纸尿裤 S82*2","image":"/upload/1467708052539Koala.jpg","price":368,"tradePrice":269,"introduction":"日本畅销第一的纸尿裤品牌，用的安全放心的纸尿裤，聪明妈妈的选择...","isNew":true,"isHot":true,"sales":0,"productName":"日本花王纸尿裤S82片*2","productType":"S82*2","brand":"花王","countryOrigin":"日本","suitableCrowd":"6-11kg宝宝","category":{"id":24,"name":"护理用品","sortIndex":"2"}}
     */

    private int status;
    private String message;
    /**
     * id : 8
     * name : 日本Kao花王妙而舒婴儿纸尿裤 S82*2
     * image : /upload/1467708052539Koala.jpg
     * price : 368.0
     * tradePrice : 269.0
     * introduction : 日本畅销第一的纸尿裤品牌，用的安全放心的纸尿裤，聪明妈妈的选择...
     * isNew : true
     * isHot : true
     * sales : 0
     * productName : 日本花王纸尿裤S82片*2
     * productType : S82*2
     * brand : 花王
     * countryOrigin : 日本
     * suitableCrowd : 6-11kg宝宝
     * category : {"id":24,"name":"护理用品","sortIndex":"2"}
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
        private int id;
        private String name;
        private String image;
        private double price;
        private double tradePrice;
        private String introduction;
        private boolean isNew;
        private boolean isHot;
        private int sales;
        private String productName;
        private String productType;
        private String brand;
        private String countryOrigin;
        private String suitableCrowd;
        /**
         * id : 24
         * name : 护理用品
         * sortIndex : 2
         */

        private CategoryBean category;

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

        public double getTradePrice() {
            return tradePrice;
        }

        public void setTradePrice(double tradePrice) {
            this.tradePrice = tradePrice;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public boolean isIsNew() {
            return isNew;
        }

        public void setIsNew(boolean isNew) {
            this.isNew = isNew;
        }

        public boolean isIsHot() {
            return isHot;
        }

        public void setIsHot(boolean isHot) {
            this.isHot = isHot;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
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

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCountryOrigin() {
            return countryOrigin;
        }

        public void setCountryOrigin(String countryOrigin) {
            this.countryOrigin = countryOrigin;
        }

        public String getSuitableCrowd() {
            return suitableCrowd;
        }

        public void setSuitableCrowd(String suitableCrowd) {
            this.suitableCrowd = suitableCrowd;
        }

        public CategoryBean getCategory() {
            return category;
        }

        public void setCategory(CategoryBean category) {
            this.category = category;
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
