package com.group2.bottomapp;


/**
 * Created by FilipFransson on 2013-11-19.
 */

public class Cocktail {
    private int id;
    private String name;
    private String description;
    private  int ratingUp;
    private int ratingDown;

    public Cocktail(int id, String name, String description, int ratingUp, int ratingDown){
        this.id = id;
        this.name = name;
        this.description = description;
        this.ratingUp = ratingUp;
        this.ratingDown = ratingDown;
    }

//Getters and setters
    public int getRatingUp() {
        return ratingUp;
    }

    public void setRatingUp(int ratingUp) {
        this.ratingUp = ratingUp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRatingDown() {
        return ratingDown;
    }

    public void setRatingDown(int ratingDown) {
        this.ratingDown = ratingDown;
    }

}

