package org.androidtown.homecare.Models;

/**
 * Created by hanhb on 2017-11-07.
 */

public class Content {
    private String title;
    private String StringContent;
    private Object timestamp; //게시일
    private String uid;

    public Content(){
        //timestamp = ServerValue.TIMESTAMP; //파이어베이스 서버에서 시간 불러오기
    }

    public Content(String stringContent) {
        StringContent = stringContent;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStringContent() {
        return StringContent;
    }

    public void setStringContent(String stringContent) {
        StringContent = stringContent;
    }
}
