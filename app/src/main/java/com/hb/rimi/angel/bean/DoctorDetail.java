package com.hb.rimi.angel.bean;

/**
 * Created by rimi on 2016/6/8.
 */
public class DoctorDetail {

    /**
     * Name : 李萍
     * code : a123
     * Position : 主任
     * subject : 妇科,产科
     * come : 成都
     * introduction : 毕业于。。。。
     * Qualifications : 高级妇科
     * url : http://61.139.124.246:81/upload/men.jpg
     * Specialty : 妇科
     */

    private String Name;
    private String code;
    private String Position;
    private String subject;
    private String come;
    private String introduction;
    private String Qualifications;
    private String url;
    private String Specialty;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCome() {
        return come;
    }

    public void setCome(String come) {
        this.come = come;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getQualifications() {
        return Qualifications;
    }

    public void setQualifications(String Qualifications) {
        this.Qualifications = Qualifications;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSpecialty() {
        return Specialty;
    }

    public void setSpecialty(String Specialty) {
        this.Specialty = Specialty;
    }
}
