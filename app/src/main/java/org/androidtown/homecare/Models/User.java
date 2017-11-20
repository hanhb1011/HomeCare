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
    private String phoneNumber;
    private Integer newMessages;
    private Integer money; //가상화폐
    private Boolean isOnline;
    private String gender;
    private String personality;


    public User() {
    }

    public User(String uid) {
        this.uid = uid;
        star = 0.0d;
        homecareCount = 0;
        newMessages = 0;
        money = 0;
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


    public String getCurrent_homecare() {
        return current_homecare;
    }

    public void setCurrent_homecare(String current_homecare) {
        this.current_homecare = current_homecare;
    }

    public Integer getNewMessages() {
        return newMessages;
    }

    public void setNewMessages(Integer newMessages) {
        this.newMessages = newMessages;
    }

    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getHomecareCount() {
        return homecareCount;
    }

    public void setHomecareCount(Integer homecareCount) {
        this.homecareCount = homecareCount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }
}
