package com.hb.rimi.angel.bean;

/**
 * Created by hp on 2016/6/6.
 */
public class CustomPhone{

    /**
     * message : Success
     * result : {"contact_telephone":{"show_telephone":"028-68718666","telephone":"028-68718666"}}
     * status : 0
     */

    private String message;
    /**
     * contact_telephone : {"show_telephone":"028-68718666","telephone":"028-68718666"}
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
         * show_telephone : 028-68718666
         * telephone : 028-68718666
         */

        private ContactTelephoneBean contact_telephone;

        public ContactTelephoneBean getContact_telephone() {
            return contact_telephone;
        }

        public void setContact_telephone(ContactTelephoneBean contact_telephone) {
            this.contact_telephone = contact_telephone;
        }

        public static class ContactTelephoneBean {
            private String show_telephone;
            private String telephone;

            public String getShow_telephone() {
                return show_telephone;
            }

            public void setShow_telephone(String show_telephone) {
                this.show_telephone = show_telephone;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }
        }
    }
}