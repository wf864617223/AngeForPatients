package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * Created by rimi on 2016/6/16.
 */
public class ResMedicalVoucherInfo {
    /**
     * message : Success
     * result : {"list":[{"amount":1,"createTime":1467800327000,"data":"{\"user_name\":\"测试-mxdf\",\"user_telephone\":\"15928414675\",\"department_name\":\"产科\",\"doctor_name\":\"李宁\",\"note\":\"\",\"ref_regtype\":\"5\",\"date\":\"2016-07-07\",\"department\":\"0133\",\"doctor\":\"lining\",\"is_vip\":\"是\"}","id":"68387156862648468ad7e9effb29ddc5","name":"医生挂号-李宁","payOrder":{"createTime":1467800327000,"details":[{"$ref":"$.result.list[0]"}],"id":"3a92077602c54c9190fcbfb47802b19f","name":"医生挂号-李宁","payTime":1467800587000,"payWay":"微信JS","price":0.01,"status":"已付款"},"price":0.01,"type":"医生挂号"},{"amount":1,"createTime":1467798691000,"data":"{\"user_name\":\"测试-mxdf\",\"user_telephone\":\"15928414675\",\"appointment\":\"023000061\",\"appointment_name\":\"NT一般产前超声检查\",\"note\":\"\",\"date\":\"2016-07-08\",\"is_vip\":\"是\"}","id":"a12c847108bb49ef9100a68990b53b51","name":"预约检查-NT一般产前超声检查","payOrder":{"createTime":1467798691000,"details":[{"$ref":"$.result.list[1]"}],"id":"36d85acba30b4fdeb7cf954a33bf1e3f","name":"预约检查-NT一般产前超声检查","payTime":1467800748000,"payWay":"微信JS","price":0.01,"status":"已付款"},"price":0.01,"type":"检查预约"},{"amount":1,"createTime":1467798372000,"data":"{\"user_name\":\"测试-mxdf\",\"user_telephone\":\"15928414675\",\"department_name\":\"妇科\",\"doctor_name\":\"李宁\",\"note\":\"\",\"ref_regtype\":\"5\",\"date\":\"2016-07-07\",\"department\":\"0132\",\"doctor\":\"lining\",\"is_vip\":\"是\"}","id":"3ad086873973418da8998258c73d8d40","name":"医生挂号-李宁","payOrder":{"createTime":1467798372000,"details":[{"$ref":"$.result.list[2]"}],"id":"33933b816bf6473d8d94c18a59664bab","name":"医生挂号-李宁","payTime":1467800435000,"payWay":"微信JS","price":0.01,"status":"已付款"},"price":0.01,"type":"医生挂号"},{"amount":1,"createTime":1467787276000,"data":"{\"department\":\"0132\",\"department_name\":\"妇科\",\"is_vip\":\"\",\"doctor\":\"lining\",\"doctor_name\":\"李宁\",\"date\":\"2016-07-07\",\"ref_regtype\":\"5\",\"note\":\"\",\"user_name\":\"测试-mxdf\",\"user_telephone\":\"15928414675\"}","id":"9c4af058ae824b2fb748eb6881b81622","name":"医生挂号","payOrder":{"createTime":1467787276000,"details":[{"$ref":"$.result.list[3]"}],"id":"fc9bf288bedd4e7d9dffbb60032b05c1","name":"医生挂号","payTime":1467787290000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"医生挂号"},{"amount":1,"createTime":1467780101000,"data":"{\"his_order\":\"0011607061300000298\"}","id":"f6b17713b20b4e0db2e657817fb3c827","name":"缴费支付","payOrder":{"createTime":1467780101000,"details":[{"$ref":"$.result.list[4]"}],"id":"219252a1ffdf4d6e84a90c1f08d66929","name":"缴费支付","payTime":1467780118000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"门诊缴费"},{"amount":1,"createTime":1467779948000,"data":"{\"his_order\":\"0011607061300000297\"}","id":"3a64f4ac6c67479e9a0d0a47388c67d5","name":"缴费支付","payOrder":{"createTime":1467779948000,"details":[{"$ref":"$.result.list[5]"}],"id":"bce676e6494d4bbf8a3671a9d6df65fb","name":"缴费支付","payTime":1467779961000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"门诊缴费"},{"amount":1,"createTime":1467779810000,"data":"{\"his_order\":\"0011607061300000296\"}","id":"02fcbd259a7d4413a625a8da174dd1a9","name":"缴费支付","payOrder":{"createTime":1467779810000,"details":[{"$ref":"$.result.list[6]"}],"id":"6e597811b5074564be28a174f86ad2b1","name":"缴费支付","payTime":1467779823000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"门诊缴费"},{"amount":1,"createTime":1467779653000,"data":"{\"his_order\":\"0011607061200000295\"}","id":"f5ad1f9cf2074ccd9ce20113dc032c8a","name":"缴费支付","payOrder":{"createTime":1467779653000,"details":[{"$ref":"$.result.list[7]"}],"id":"d794fb49ff434c118288e047b9a87ade","name":"缴费支付","payTime":1467779675000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"门诊缴费"},{"amount":1,"createTime":1467779458000,"data":"{\"his_order\":\"0011607061200000294\"}","id":"c0a408cae6764582988793a6c259a3f2","name":"缴费支付","payOrder":{"createTime":1467779458000,"details":[{"$ref":"$.result.list[8]"}],"id":"172d70f6dcca475ba8c3c9b7a4e55ea6","name":"缴费支付","payTime":1467779472000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"门诊缴费"},{"amount":1,"createTime":1467779341000,"data":"{\"his_order\":\"0011607061200000293\"}","id":"2e90462323d949709feaccaf02f9203f","name":"缴费支付","payOrder":{"createTime":1467779341000,"details":[{"$ref":"$.result.list[9]"}],"id":"038fa045ff1f49f88df17e1687eabeeb","name":"缴费支付","payTime":1467779355000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"门诊缴费"}],"pageCount":7,"pageNumber":1,"pageSize":10,"totalCount":69}
     * status : 0
     */

    private String message;
    /**
     * list : [{"amount":1,"createTime":1467800327000,"data":"{\"user_name\":\"测试-mxdf\",\"user_telephone\":\"15928414675\",\"department_name\":\"产科\",\"doctor_name\":\"李宁\",\"note\":\"\",\"ref_regtype\":\"5\",\"date\":\"2016-07-07\",\"department\":\"0133\",\"doctor\":\"lining\",\"is_vip\":\"是\"}","id":"68387156862648468ad7e9effb29ddc5","name":"医生挂号-李宁","payOrder":{"createTime":1467800327000,"details":[{"$ref":"$.result.list[0]"}],"id":"3a92077602c54c9190fcbfb47802b19f","name":"医生挂号-李宁","payTime":1467800587000,"payWay":"微信JS","price":0.01,"status":"已付款"},"price":0.01,"type":"医生挂号"},{"amount":1,"createTime":1467798691000,"data":"{\"user_name\":\"测试-mxdf\",\"user_telephone\":\"15928414675\",\"appointment\":\"023000061\",\"appointment_name\":\"NT一般产前超声检查\",\"note\":\"\",\"date\":\"2016-07-08\",\"is_vip\":\"是\"}","id":"a12c847108bb49ef9100a68990b53b51","name":"预约检查-NT一般产前超声检查","payOrder":{"createTime":1467798691000,"details":[{"$ref":"$.result.list[1]"}],"id":"36d85acba30b4fdeb7cf954a33bf1e3f","name":"预约检查-NT一般产前超声检查","payTime":1467800748000,"payWay":"微信JS","price":0.01,"status":"已付款"},"price":0.01,"type":"检查预约"},{"amount":1,"createTime":1467798372000,"data":"{\"user_name\":\"测试-mxdf\",\"user_telephone\":\"15928414675\",\"department_name\":\"妇科\",\"doctor_name\":\"李宁\",\"note\":\"\",\"ref_regtype\":\"5\",\"date\":\"2016-07-07\",\"department\":\"0132\",\"doctor\":\"lining\",\"is_vip\":\"是\"}","id":"3ad086873973418da8998258c73d8d40","name":"医生挂号-李宁","payOrder":{"createTime":1467798372000,"details":[{"$ref":"$.result.list[2]"}],"id":"33933b816bf6473d8d94c18a59664bab","name":"医生挂号-李宁","payTime":1467800435000,"payWay":"微信JS","price":0.01,"status":"已付款"},"price":0.01,"type":"医生挂号"},{"amount":1,"createTime":1467787276000,"data":"{\"department\":\"0132\",\"department_name\":\"妇科\",\"is_vip\":\"\",\"doctor\":\"lining\",\"doctor_name\":\"李宁\",\"date\":\"2016-07-07\",\"ref_regtype\":\"5\",\"note\":\"\",\"user_name\":\"测试-mxdf\",\"user_telephone\":\"15928414675\"}","id":"9c4af058ae824b2fb748eb6881b81622","name":"医生挂号","payOrder":{"createTime":1467787276000,"details":[{"$ref":"$.result.list[3]"}],"id":"fc9bf288bedd4e7d9dffbb60032b05c1","name":"医生挂号","payTime":1467787290000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"医生挂号"},{"amount":1,"createTime":1467780101000,"data":"{\"his_order\":\"0011607061300000298\"}","id":"f6b17713b20b4e0db2e657817fb3c827","name":"缴费支付","payOrder":{"createTime":1467780101000,"details":[{"$ref":"$.result.list[4]"}],"id":"219252a1ffdf4d6e84a90c1f08d66929","name":"缴费支付","payTime":1467780118000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"门诊缴费"},{"amount":1,"createTime":1467779948000,"data":"{\"his_order\":\"0011607061300000297\"}","id":"3a64f4ac6c67479e9a0d0a47388c67d5","name":"缴费支付","payOrder":{"createTime":1467779948000,"details":[{"$ref":"$.result.list[5]"}],"id":"bce676e6494d4bbf8a3671a9d6df65fb","name":"缴费支付","payTime":1467779961000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"门诊缴费"},{"amount":1,"createTime":1467779810000,"data":"{\"his_order\":\"0011607061300000296\"}","id":"02fcbd259a7d4413a625a8da174dd1a9","name":"缴费支付","payOrder":{"createTime":1467779810000,"details":[{"$ref":"$.result.list[6]"}],"id":"6e597811b5074564be28a174f86ad2b1","name":"缴费支付","payTime":1467779823000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"门诊缴费"},{"amount":1,"createTime":1467779653000,"data":"{\"his_order\":\"0011607061200000295\"}","id":"f5ad1f9cf2074ccd9ce20113dc032c8a","name":"缴费支付","payOrder":{"createTime":1467779653000,"details":[{"$ref":"$.result.list[7]"}],"id":"d794fb49ff434c118288e047b9a87ade","name":"缴费支付","payTime":1467779675000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"门诊缴费"},{"amount":1,"createTime":1467779458000,"data":"{\"his_order\":\"0011607061200000294\"}","id":"c0a408cae6764582988793a6c259a3f2","name":"缴费支付","payOrder":{"createTime":1467779458000,"details":[{"$ref":"$.result.list[8]"}],"id":"172d70f6dcca475ba8c3c9b7a4e55ea6","name":"缴费支付","payTime":1467779472000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"门诊缴费"},{"amount":1,"createTime":1467779341000,"data":"{\"his_order\":\"0011607061200000293\"}","id":"2e90462323d949709feaccaf02f9203f","name":"缴费支付","payOrder":{"createTime":1467779341000,"details":[{"$ref":"$.result.list[9]"}],"id":"038fa045ff1f49f88df17e1687eabeeb","name":"缴费支付","payTime":1467779355000,"payWay":"支付宝","price":0.01,"status":"已付款"},"price":0.01,"type":"门诊缴费"}]
     * pageCount : 7
     * pageNumber : 1
     * pageSize : 10
     * totalCount : 69
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
         * amount : 1
         * createTime : 1467800327000
         * data : {"user_name":"测试-mxdf","user_telephone":"15928414675","department_name":"产科","doctor_name":"李宁","note":"","ref_regtype":"5","date":"2016-07-07","department":"0133","doctor":"lining","is_vip":"是"}
         * id : 68387156862648468ad7e9effb29ddc5
         * name : 医生挂号-李宁
         * payOrder : {"createTime":1467800327000,"details":[{"$ref":"$.result.list[0]"}],"id":"3a92077602c54c9190fcbfb47802b19f","name":"医生挂号-李宁","payTime":1467800587000,"payWay":"微信JS","price":0.01,"status":"已付款"}
         * price : 0.01
         * type : 医生挂号
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
            private int amount;
            private long createTime;
            private String data;
            private String id;
            private String name;
            /**
             * createTime : 1467800327000
             * details : [{"$ref":"$.result.list[0]"}]
             * id : 3a92077602c54c9190fcbfb47802b19f
             * name : 医生挂号-李宁
             * payTime : 1467800587000
             * payWay : 微信JS
             * price : 0.01
             * status : 已付款
             */

            private PayOrderBean payOrder;
            private double price;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public static class PayOrderBean {
                private long createTime;
                private String id;
                private String name;
                private long payTime;
                private String payWay;
                private double price;
                private String status;
                /**
                 * $ref : $.result.list[0]
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

                public long getPayTime() {
                    return payTime;
                }

                public void setPayTime(long payTime) {
                    this.payTime = payTime;
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

                public List<DetailsBean> getDetails() {
                    return details;
                }

                public void setDetails(List<DetailsBean> details) {
                    this.details = details;
                }

                public static class DetailsBean {
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


//
//
//
//    /**
//     * status : 0
//     * message : success
//     * result : {"pageCount":1,"pageNumber":1,"pageSize":10,"totalCount":2,"rsList":[{"amount":1,"createTime":1465724754000,"data":"data","id":"xxxxxxxxxx","name":"商品订单-医生挂号1","price":99,"type":"医生挂号"}]}
//     */
//
//    private int status;
//    private String message;
//    /**
//     * pageCount : 1
//     * pageNumber : 1
//     * pageSize : 10
//     * totalCount : 2
//     * rsList : [{"amount":1,"createTime":1465724754000,"data":"data","id":"xxxxxxxxxx","name":"商品订单-医生挂号1","price":99,"type":"医生挂号"}]
//     */
//
//    private ResultBean result;
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public ResultBean getResult() {
//        return result;
//    }
//
//    public void setResult(ResultBean result) {
//        this.result = result;
//    }
//
//    public static class ResultBean {
//        private int pageCount;
//        private int pageNumber;
//        private int pageSize;
//        private int totalCount;
//        /**
//         * amount : 1
//         * createTime : 1465724754000
//         * data : data
//         * id : xxxxxxxxxx
//         * name : 商品订单-医生挂号1
//         * price : 99
//         * type : 医生挂号
//         */
//
//        private List<ListBean> list;
//
//        public int getPageCount() {
//            return pageCount;
//        }
//
//        public void setPageCount(int pageCount) {
//            this.pageCount = pageCount;
//        }
//
//        public int getPageNumber() {
//            return pageNumber;
//        }
//
//        public void setPageNumber(int pageNumber) {
//            this.pageNumber = pageNumber;
//        }
//
//        public int getPageSize() {
//            return pageSize;
//        }
//
//        public void setPageSize(int pageSize) {
//            this.pageSize = pageSize;
//        }
//
//        public int getTotalCount() {
//            return totalCount;
//        }
//
//        public void setTotalCount(int totalCount) {
//            this.totalCount = totalCount;
//        }
//
//        public List<ListBean> getList() {
//            return list;
//        }
//
//        public void setList(List<ListBean> list) {
//            this.list = list;
//        }
//
//        public static class ListBean {
//            private int amount;
//            private long createTime;
//            private String data;
//            private String id;
//            private String name;
//            private double price;
//            private String type;
//
//            public int getAmount() {
//                return amount;
//            }
//
//            public void setAmount(int amount) {
//                this.amount = amount;
//            }
//
//            public long getCreateTime() {
//                return createTime;
//            }
//
//            public void setCreateTime(long createTime) {
//                this.createTime = createTime;
//            }
//
//            public String getData() {
//                return data;
//            }
//
//            public void setData(String data) {
//                this.data = data;
//            }
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public double getPrice() {
//                return price;
//            }
//
//            public void setPrice(double price) {
//                this.price = price;
//            }
//
//            public String getType() {
//                return type;
//            }
//
//            public void setType(String type) {
//                this.type = type;
//            }
//        }
//    }
}
