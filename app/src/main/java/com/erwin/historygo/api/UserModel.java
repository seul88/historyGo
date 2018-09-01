package com.erwin.historygo.api;

import android.support.annotation.NonNull;

public class UserModel {
    private String name;
    private int points;
    private String email;
    private String country;
    private int id;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserModel(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public UserModel() {
    }

    public UserModel(String name, int points, String email, String country, int id) {
        this.name = name;
        this.points = points;
        this.email = email;
        this.country = country;
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", points=" + points +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", id=" + id +
                '}';
    }
}
