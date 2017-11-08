package org.androidtown.homecare.Models;

/**
 * Created by hanhb on 2017-11-07.
 */


public class Content {
    private String title;
    private Object timestamp; //게시일
    private String uid;
    private Integer pay;
    private String location;
    private String comment;
    private String careType;
    private Object startPeriod;
    private Object endPeriod;


    public Content(){
        //timestamp = ServerValue.TIMESTAMP; //파이어베이스 서버에서 시간 불러오기
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

    public Object getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Object startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Object getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(Object endPeriod) {
        this.endPeriod = endPeriod;
    }
}
