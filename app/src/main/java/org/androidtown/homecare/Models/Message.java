package org.androidtown.homecare.Models;

/**
 * Created by hanhb on 2017-11-12.
 */

public class Message {

    String uid;
    String content;
    Object timestamp;


    public Message() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
