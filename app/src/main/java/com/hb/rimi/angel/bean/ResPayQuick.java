package com.hb.rimi.angel.bean;

/**
 * Created by rimi on 2016/6/21.
 */
public class ResPayQuick {

    /**
     * message : Success
     * result : {"alipay":{"_input_charset":"\"utf-8\"","body":"\"挂号-孔医生\"","it_b_pay":"\"30m\"","notify_url":"\"pay/alipay/callback\"","out_trade_no":"\"3d4097cd81b84702abcb94cfba945d01\"","partner":"\"2088221885977122\"","payment_type":"\"1\"","seller_id":"\"cdxqangel@angel-hospital.com\"","service":"\"mobile.securitypay.pay\"","sign":"\"WIkjmb9ZcHFqAy6mTpVbn4tm4C5LQB2uQxqJAjK0Ow4u54BOANG8D%2BwzH0sVxcAOUZIdpujODMubRh5M3fbwJjhF6xbYaEbtTp%2BF%2FUUJv4BRlRx3s9TwlFKb7AryLXMGnWTP4Schl%2BZ%2FwebnNf%2BfP9NSSkOlKO%2FgmBaCu9DuIO5R8vloEDLZaBmOhAkQMHEp3zSo1YpTUl63%2FB1Z9GQ%2F7EAcjEXr%2Bp13wVlXTkOcWwydsNfy4z6A5KDdNI3dkNAiiIPfYW%2F06CKt0PC1Qu8eShWPrEtqwWbzrO5DqzFuWfLSaCFyX4SBalaSoWWikCPjxPplugx%2BeVoDCjVPHs1d3T4cFi%2B8dzNkMBzQfpoxUryN%2FIH6LtqjE61ej5%2FOu6O3dcCwHoZKvySQvt%2B3F6EDXLOoEM0sWWdZHNSAaQBbNJwim1zGxxOhfLpnTsok2zsEJL3yQH0P8rZf7cP5vE%2FCmpJFv2SRYI36u7uhQgPPE9e32ZEKswgfgc1%2Fr9v7SUpg\"","sign_type":"\"RSA\"","subject":"\"挂号-孔医生\"","total_fee":"\"100.00\""}}
     * status : 0
     */

    private String message;
    /**
     * alipay : {"_input_charset":"\"utf-8\"","body":"\"挂号-孔医生\"","it_b_pay":"\"30m\"","notify_url":"\"pay/alipay/callback\"","out_trade_no":"\"3d4097cd81b84702abcb94cfba945d01\"","partner":"\"2088221885977122\"","payment_type":"\"1\"","seller_id":"\"cdxqangel@angel-hospital.com\"","service":"\"mobile.securitypay.pay\"","sign":"\"WIkjmb9ZcHFqAy6mTpVbn4tm4C5LQB2uQxqJAjK0Ow4u54BOANG8D%2BwzH0sVxcAOUZIdpujODMubRh5M3fbwJjhF6xbYaEbtTp%2BF%2FUUJv4BRlRx3s9TwlFKb7AryLXMGnWTP4Schl%2BZ%2FwebnNf%2BfP9NSSkOlKO%2FgmBaCu9DuIO5R8vloEDLZaBmOhAkQMHEp3zSo1YpTUl63%2FB1Z9GQ%2F7EAcjEXr%2Bp13wVlXTkOcWwydsNfy4z6A5KDdNI3dkNAiiIPfYW%2F06CKt0PC1Qu8eShWPrEtqwWbzrO5DqzFuWfLSaCFyX4SBalaSoWWikCPjxPplugx%2BeVoDCjVPHs1d3T4cFi%2B8dzNkMBzQfpoxUryN%2FIH6LtqjE61ej5%2FOu6O3dcCwHoZKvySQvt%2B3F6EDXLOoEM0sWWdZHNSAaQBbNJwim1zGxxOhfLpnTsok2zsEJL3yQH0P8rZf7cP5vE%2FCmpJFv2SRYI36u7uhQgPPE9e32ZEKswgfgc1%2Fr9v7SUpg\"","sign_type":"\"RSA\"","subject":"\"挂号-孔医生\"","total_fee":"\"100.00\""}
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
        /**
         * _input_charset : "utf-8"
         * body : "挂号-孔医生"
         * it_b_pay : "30m"
         * notify_url : "pay/alipay/callback"
         * out_trade_no : "3d4097cd81b84702abcb94cfba945d01"
         * partner : "2088221885977122"
         * payment_type : "1"
         * seller_id : "cdxqangel@angel-hospital.com"
         * service : "mobile.securitypay.pay"
         * sign : "WIkjmb9ZcHFqAy6mTpVbn4tm4C5LQB2uQxqJAjK0Ow4u54BOANG8D%2BwzH0sVxcAOUZIdpujODMubRh5M3fbwJjhF6xbYaEbtTp%2BF%2FUUJv4BRlRx3s9TwlFKb7AryLXMGnWTP4Schl%2BZ%2FwebnNf%2BfP9NSSkOlKO%2FgmBaCu9DuIO5R8vloEDLZaBmOhAkQMHEp3zSo1YpTUl63%2FB1Z9GQ%2F7EAcjEXr%2Bp13wVlXTkOcWwydsNfy4z6A5KDdNI3dkNAiiIPfYW%2F06CKt0PC1Qu8eShWPrEtqwWbzrO5DqzFuWfLSaCFyX4SBalaSoWWikCPjxPplugx%2BeVoDCjVPHs1d3T4cFi%2B8dzNkMBzQfpoxUryN%2FIH6LtqjE61ej5%2FOu6O3dcCwHoZKvySQvt%2B3F6EDXLOoEM0sWWdZHNSAaQBbNJwim1zGxxOhfLpnTsok2zsEJL3yQH0P8rZf7cP5vE%2FCmpJFv2SRYI36u7uhQgPPE9e32ZEKswgfgc1%2Fr9v7SUpg"
         * sign_type : "RSA"
         * subject : "挂号-孔医生"
         * total_fee : "100.00"
         */

        private String alipayContruct;//支付宝参数
        private AlipayBean alipay;

        public AlipayBean getAlipay() {
            return alipay;
        }

        public void setAlipay(AlipayBean alipay) {
            this.alipay = alipay;
        }

        public String getAlipayContruct() {
            return alipayContruct;
        }

        public void setAlipayContruct(String alipayContruct) {
            this.alipayContruct = alipayContruct;
        }

        public static class AlipayBean {
            private String _input_charset;
            private String body;
            private String it_b_pay;
            private String notify_url;
            private String out_trade_no;
            private String partner;
            private String payment_type;
            private String seller_id;
            private String service;
            private String sign;
            private String sign_type;
            private String subject;
            private String total_fee;

            public String get_input_charset() {
                return _input_charset;
            }

            public void set_input_charset(String _input_charset) {
                this._input_charset = _input_charset;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public String getIt_b_pay() {
                return it_b_pay;
            }

            public void setIt_b_pay(String it_b_pay) {
                this.it_b_pay = it_b_pay;
            }

            public String getNotify_url() {
                return notify_url;
            }

            public void setNotify_url(String notify_url) {
                this.notify_url = notify_url;
            }

            public String getOut_trade_no() {
                return out_trade_no;
            }

            public void setOut_trade_no(String out_trade_no) {
                this.out_trade_no = out_trade_no;
            }

            public String getPartner() {
                return partner;
            }

            public void setPartner(String partner) {
                this.partner = partner;
            }

            public String getPayment_type() {
                return payment_type;
            }

            public void setPayment_type(String payment_type) {
                this.payment_type = payment_type;
            }

            public String getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(String seller_id) {
                this.seller_id = seller_id;
            }

            public String getService() {
                return service;
            }

            public void setService(String service) {
                this.service = service;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getSign_type() {
                return sign_type;
            }

            public void setSign_type(String sign_type) {
                this.sign_type = sign_type;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getTotal_fee() {
                return total_fee;
            }

            public void setTotal_fee(String total_fee) {
                this.total_fee = total_fee;
            }
        }
    }
}
