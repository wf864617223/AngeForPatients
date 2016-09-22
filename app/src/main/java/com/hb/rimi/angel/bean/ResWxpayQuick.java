package com.hb.rimi.angel.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 微信支付结构
 * Created by Administrator on 2016/6/27.
 */
public class ResWxpayQuick {

    /**
     * message : Success
     * result : {"wechat":{"appid":"wx19b0c821e604e397","noncestr":"690b75f60a404389b7b8acf73e73762d","package":"Sign=WXPay","partnerid":"1359307702","prepayid":"wx20160627154127e16371222b0408497316","sign":"26024CC8FD6B51CB0F822868C12FE751","timestamp":"1467013400023"},"wechatContruct":"appid=wx19b0c821e604e397&noncestr=690b75f60a404389b7b8acf73e73762d&package=Sign=WXPay&partnerid=1359307702&prepayid=wx20160627154127e16371222b0408497316&timestamp=1467013400023&sign=26024CC8FD6B51CB0F822868C12FE751"}
     * status : 0
     */

    private String message;
    /**
     * wechat : {"appid":"wx19b0c821e604e397","noncestr":"690b75f60a404389b7b8acf73e73762d","package":"Sign=WXPay","partnerid":"1359307702","prepayid":"wx20160627154127e16371222b0408497316","sign":"26024CC8FD6B51CB0F822868C12FE751","timestamp":"1467013400023"}
     * wechatContruct : appid=wx19b0c821e604e397&noncestr=690b75f60a404389b7b8acf73e73762d&package=Sign=WXPay&partnerid=1359307702&prepayid=wx20160627154127e16371222b0408497316&timestamp=1467013400023&sign=26024CC8FD6B51CB0F822868C12FE751
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
         * appid : wx19b0c821e604e397
         * noncestr : 690b75f60a404389b7b8acf73e73762d
         * package : Sign=WXPay
         * partnerid : 1359307702
         * prepayid : wx20160627154127e16371222b0408497316
         * sign : 26024CC8FD6B51CB0F822868C12FE751
         * timestamp : 1467013400023
         */

        private WechatBean wechat;
        private String wechatContruct;

        public WechatBean getWechat() {
            return wechat;
        }

        public void setWechat(WechatBean wechat) {
            this.wechat = wechat;
        }

        public String getWechatContruct() {
            return wechatContruct;
        }

        public void setWechatContruct(String wechatContruct) {
            this.wechatContruct = wechatContruct;
        }

        public static class WechatBean {
            private String appid;
            private String noncestr;
            @SerializedName("package")
            private String packageX;
            private String partnerid;
            private String prepayid;
            private String sign;
            private String timestamp;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
        }
    }
}
