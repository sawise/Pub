package com.group2.bottomapp;

/**
 * Created by FilipFransson on 2013-11-19.
 */
public class Ingredient {
    private int id;
    private String name;
    private String measurement;

    public Ingredient(int id, String name, String measurement){
        this.id = id;
        this.name = name;
        this.measurement = measurement;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
