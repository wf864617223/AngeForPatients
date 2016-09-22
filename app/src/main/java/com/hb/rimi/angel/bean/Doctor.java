package com.hb.rimi.angel.bean;

/**
 * 医生信息
 * Created by rimi on 2016/6/3.
 */
public class Doctor {
    /**
     * Name : 游虹
     * code : youhong
     * Position : 主任
     * subject : 儿科,产科
     * come : 成都
     * introduction : 毕业于。。。。
     * Qualifications : 高级妇科
     * url : http://61.139.124.246:81/upload/men.jpg
     * Specialty : 妇科
     */

    private String Name;//名称
    private String code;//代码
    private String Position;//职位
    private String subject;//科室
    private String come;//来自
    private String introduction;//简介
    private String Qualifications;//资格
    private String url;//头像
    private String Specialty;//专长


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public void setPosition(String position) {
        Position = position;
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

    public void setQualifications(String qualifications) {
        Qualifications = qualifications;
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

    public void setSpecialty(String specialty) {
        Specialty = specialty;
    }
}
