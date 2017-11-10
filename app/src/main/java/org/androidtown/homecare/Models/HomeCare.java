package org.androidtown.homecare.Models;

import com.google.firebase.database.ServerValue;

/**
 * Created by hanhb on 2017-11-07.
 */


public class HomeCare {
    private String key;
    private String title;
    private Object timestamp; //게시일
    private String uid;
    private Integer pay;
    private String location;
    private String comment;
    private String careType;
    private Long startPeriod;
    private Long endPeriod;
    private String uidOfCareTaker;


    public HomeCare(){
    }

    public String getUidOfCareTaker() {
        return uidOfCareTaker;
    }

    public void setUidOfCareTaker(String uidOfCareTaker) {
        this.uidOfCareTaker = uidOfCareTaker;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(){
        timestamp = ServerValue.TIMESTAMP;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCareType() {
        return careType;
    }

    public void setCareType(String careType) {
        this.careType = careType;
    }

    public Long getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Long startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Long getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(Long endPeriod) {
        this.endPeriod = endPeriod;
    }
}
