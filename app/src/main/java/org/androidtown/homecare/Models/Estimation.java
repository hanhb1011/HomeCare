package org.androidtown.homecare.Models;

/**
 * Created by hanhb on 2017-11-16.
 */

public class Estimation {

    private String comment, key;
    private float kindness, wellness, faithfulness; //친절함, 업무숙련도, 성실함

    public Estimation() {

    }

    public Estimation(String comment, String key, float kindness, float wellness, float faithfulness) {
        this.comment = comment;
        this.kindness = kindness;
        this.wellness = wellness;
        this.faithfulness = faithfulness;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getKindness() {
        return kindness;
    }

    public void setKindness(float kindness) {
        this.kindness = kindness;
    }

    public float getWellness() {
        return wellness;
    }

    public void setWellness(float wellness) {
        this.wellness = wellness;
    }

    public float getFaithfulness() {
        return faithfulness;
    }

    public void setFaithfulness(float faithfulness) {
        this.faithfulness = faithfulness;
    }
}
