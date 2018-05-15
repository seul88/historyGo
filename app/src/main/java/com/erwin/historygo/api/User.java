package com.erwin.historygo.api;

import android.support.annotation.NonNull;

public class User {
    private String name;
    private int points;

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public User(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public User() {
    }


}
