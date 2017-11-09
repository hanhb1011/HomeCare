package org.androidtown.homecare.Models;

/**
 * Created by hanhb on 2017-11-08.
 */

public class User {

    private String name = ""; //temp ""
    private Object birthday;
    private double star; //평점


    public User() {
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

    public void setStar(double star) {
        this.star = star;
    }
}
