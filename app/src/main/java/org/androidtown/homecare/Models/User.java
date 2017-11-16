package org.androidtown.homecare.Models;

/**
 * Created by hanhb on 2017-11-08.
 */

public class User {

    private String name = ""; //temp ""
    private Object birthday;
    private String uid;
    private String current_homecare; //현재 진행중인, 또는 등록한 홈케어의 key
    private Double star; //평점
    private Integer homecareCount;
    private Integer money; //가상화폐


    public User() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
        this.birthday = birthday;
    }

    public double getStar() {
        return star;
    }


    public String getCurrent_homecare() {
        return current_homecare;
    }

    public void setCurrent_homecare(String current_homecare) {
        this.current_homecare = current_homecare;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public Integer getHomecareCount() {
        return homecareCount;
    }

    public void setHomecareCount(Integer homecareCount) {
        this.homecareCount = homecareCount;
    }
}
