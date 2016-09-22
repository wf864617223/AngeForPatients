package com.hb.rimi.angel.bean;

import java.util.List;

/**
 * Created by hp on 2016/6/17.
 */
public class HomeTopPic {

    /**
     * message : Success
     * result : {"app_banner":{"enable":true,"folder":[{"path":"static/topic_1/"},{"path":"static/topic_2/"},{"path":"static/topic_3/"}],"images":{"hdpi":"hdpi.jpg","iPhone4":"iPhone4.jpg","iPhone5":"iPhone5.jpg","iPhone6":"iPhone6.jpg","iPhone6Plus":"iPhone6Plus.jpg","idpi":"idpi.jpg","mdpi":"mdpi.jpg","xhdpi":"xhdpi.jpg","xxhdpi":"xxhdpi.jpg"}}}
     * status : 0
     */

    private String message;
    /**
     * app_banner : {"enable":true,"folder":[{"path":"static/topic_1/"},{"path":"static/topic_2/"},{"path":"static/topic_3/"}],"images":{"hdpi":"hdpi.jpg","iPhone4":"iPhone4.jpg","iPhone5":"iPhone5.jpg","iPhone6":"iPhone6.jpg","iPhone6Plus":"iPhone6Plus.jpg","idpi":"idpi.jpg","mdpi":"mdpi.jpg","xhdpi":"xhdpi.jpg","xxhdpi":"xxhdpi.jpg"}}
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
         * enable : true
         * folder : [{"path":"static/topic_1/"},{"path":"static/topic_2/"},{"path":"static/topic_3/"}]
         * images : {"hdpi":"hdpi.jpg","iPhone4":"iPhone4.jpg","iPhone5":"iPhone5.jpg","iPhone6":"iPhone6.jpg","iPhone6Plus":"iPhone6Plus.jpg","idpi":"idpi.jpg","mdpi":"mdpi.jpg","xhdpi":"xhdpi.jpg","xxhdpi":"xxhdpi.jpg"}
         */

        private AppBannerBean app_banner;

        public AppBannerBean getApp_banner() {
            return app_banner;
        }

        public void setApp_banner(AppBannerBean app_banner) {
            this.app_banner = app_banner;
        }

        public static class AppBannerBean {
            private boolean enable;
            /**
             * hdpi : hdpi.jpg
             * iPhone4 : iPhone4.jpg
             * iPhone5 : iPhone5.jpg
             * iPhone6 : iPhone6.jpg
             * iPhone6Plus : iPhone6Plus.jpg
             * idpi : idpi.jpg
             * mdpi : mdpi.jpg
             * xhdpi : xhdpi.jpg
             * xxhdpi : xxhdpi.jpg
             */

            private ImagesBean images;
            /**
             * path : static/topic_1/
             */

            private List<FolderBean> folder;

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }

            public ImagesBean getImages() {
                return images;
            }

            public void setImages(ImagesBean images) {
                this.images = images;
            }

            public List<FolderBean> getFolder() {
                return folder;
            }

            public void setFolder(List<FolderBean> folder) {
                this.folder = folder;
            }

            public static class ImagesBean {
                private String hdpi;
                private String iPhone4;
                private String iPhone5;
                private String iPhone6;
                private String iPhone6Plus;
                private String idpi;
                private String mdpi;
                private String xhdpi;
                private String xxhdpi;

                public String getHdpi() {
                    return hdpi;
                }

                public void setHdpi(String hdpi) {
                    this.hdpi = hdpi;
                }

                public String getIPhone4() {
                    return iPhone4;
                }

                public void setIPhone4(String iPhone4) {
                    this.iPhone4 = iPhone4;
                }

                public String getIPhone5() {
                    return iPhone5;
                }

                public void setIPhone5(String iPhone5) {
                    this.iPhone5 = iPhone5;
                }

                public String getIPhone6() {
                    return iPhone6;
                }

                public void setIPhone6(String iPhone6) {
                    this.iPhone6 = iPhone6;
                }

                public String getIPhone6Plus() {
                    return iPhone6Plus;
                }

                public void setIPhone6Plus(String iPhone6Plus) {
                    this.iPhone6Plus = iPhone6Plus;
                }

                public String getIdpi() {
                    return idpi;
                }

                public void setIdpi(String idpi) {
                    this.idpi = idpi;
                }

                public String getMdpi() {
                    return mdpi;
                }

                public void setMdpi(String mdpi) {
                    this.mdpi = mdpi;
                }

                public String getXhdpi() {
                    return xhdpi;
                }

                public void setXhdpi(String xhdpi) {
                    this.xhdpi = xhdpi;
                }

                public String getXxhdpi() {
                    return xxhdpi;
                }

                public void setXxhdpi(String xxhdpi) {
                    this.xxhdpi = xxhdpi;
                }
            }

            public static class FolderBean {
                private String path;

                public String getPath() {
                    return path;
                }

                public void setPath(String path) {
                    this.path = path;
                }
            }
        }
    }
}
