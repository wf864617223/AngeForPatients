package com.hb.rimi.angel.bean;

/**
 * Created by hp on 2016/6/13.
 */
public class ReportImg {

    /**
     * status : 0
     * message : success
     * result : {"imgUrl":"http:\\61.139.124.246:85\\his\\20159\\IMG_2015_09_13_14_48_33_941.jpg"}
     */

    private int status;
    private String message;
    /**
     * imgUrl : http:\61.139.124.246:85\his\20159\IMG_2015_09_13_14_48_33_941.jpg
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
        private String imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
