package com.hb.rimi.angel.bean;


import java.util.List;

/**
 * crm电话预约
 * 请求返回数据Bean
 * 适用格式：
 * {
 * status:0, //成功:0，失败:1
 * message:”success” //成功:success, 失败:error或错误信息
 * result:{
 * token:”xxxxxxxxxxxxxxxxxxxxxxxxx”
 * }
 * }
 * <p/>
 * Created by rimi on 2016/6/2.
 */
public class ResCrmPhone {

    private int status;
    private String message;
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

//    ID 主键ID,CustomerName 客户名字,[type] 项目ID,DateCreated 创建时间,DateEnd 到院时间,ContactType 客户电话

    public static class ResultBean {
        private long ID;
        private String CustomerName;
        private String type;
        private String DateCreated;
        private String DateEnd;
        private String ContactType;

        private double price;//价格
        private String proName;//项目名称

        public String getSimpleDsc() {
            return simpleDsc;
        }

        public void setSimpleDsc(String simpleDsc) {
            this.simpleDsc = simpleDsc;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getDetailDsc() {
            return detailDsc;
        }

        public void setDetailDsc(String detailDsc) {
            this.detailDsc = detailDsc;
        }

        private String simpleDsc;//简单描述
        private String detailDsc;//详细描述
        public long getID() {
            return ID;
        }

        public void setID(long ID) {
            this.ID = ID;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String customerName) {
            CustomerName = customerName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDateCreated() {
            return DateCreated;
        }

        public void setDateCreated(String dateCreated) {
            DateCreated = dateCreated;
        }

        public String getDateEnd() {
            return DateEnd;
        }

        public void setDateEnd(String dateEnd) {
            DateEnd = dateEnd;
        }

        public String getContactType() {
            return ContactType;
        }

        public void setContactType(String contactType) {
            ContactType = contactType;
        }
    }
}
