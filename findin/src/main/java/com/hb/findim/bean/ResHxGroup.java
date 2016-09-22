package com.hb.findim.bean;

import java.util.List;

/**
 * Created by rimi on 2016/6/23.
 */
public class ResHxGroup {

    /**
     * status : 0
     * message : success
     * result : [{"img":"","groupid":"208747136047645120","affiliations":"3","description":"","groupname":"育儿经验"},{"img":"","groupid":"208747252649296304","affiliations":"4","description":"","groupname":"母乳喂养"}]
     */

    private int status;
    private String message;
    /**
     * img :
     * groupid : 208747136047645120
     * affiliations : 3
     * description :
     * groupname : 育儿经验
     */

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

    public static class ResultBean {
        private String img;
        private String groupid;
        private String affiliations;
        private String description;
        private String groupname;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getAffiliations() {
            return affiliations;
        }

        public void setAffiliations(String affiliations) {
            this.affiliations = affiliations;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }
    }
}
