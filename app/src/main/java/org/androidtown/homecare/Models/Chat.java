package org.androidtown.homecare.Models;

/**
 * Created by hanhb on 2017-11-12.
 */

public class Chat {

    String key;
    String uid;
    String uidOfCareTaker;


    public Chat() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUidOfCareTaker() {
        return uidOfCareTaker;
    }

    public void setUidOfCareTaker(String uidOfCareTaker) {
        this.uidOfCareTaker = uidOfCareTaker;
    }
}
