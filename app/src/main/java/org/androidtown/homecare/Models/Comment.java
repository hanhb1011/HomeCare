package org.androidtown.homecare.Models;

/**
 * Created by hanhb on 2017-11-08.
 */

public class Comment {

    //Comment : 댓글 model

    private String uid;
    private String commentString;
    private Object timestamp;

    public Comment() {
        //timestamp = ServerValue.TIMESTAMP;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCommentString() {
        return commentString;
    }

    public void setCommentString(String commentString) {
        this.commentString = commentString;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
