package com.erwin.historygo.api;

public class PlaceModel {

    private String id;
    private String name;
    private int points;
    private double rating;
    private String description;
    private int year;
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PlaceModel(String name) {
        this.name = name;
    }

    public PlaceModel(){

    }


    public PlaceModel(String name, int points, String description, int year) {

        this.name = name;
        this.points = points;
        this.description = description;
        this.year = year;
    }

    public PlaceModel(String id, String name, int points, double rating, String description, int year) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.rating = rating;
        this.description = description;
        this.year = year;
    }

    public PlaceModel(String id, String name, int points, double rating, String description, int year, String image) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.rating = rating;
        this.description = description;
        this.year = year;
        this.image = image;
    }
}
