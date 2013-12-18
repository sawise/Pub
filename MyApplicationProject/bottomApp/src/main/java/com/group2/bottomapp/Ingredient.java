package com.group2.bottomapp;

import java.util.ArrayList;

public class Ingredient {
    private int id;
    private String name;
    private int categoryID;
    private String categoryName;
    private String measurement;
    private ArrayList<Ingredient> ingredientList;

    public Ingredient(int id, String name, String measurement){
        this.id = id;
        this.name = name;
        this.measurement = measurement;
    }

    public Ingredient(int id, String name, String measurement, String categoryName){
        this.id = id;
        this.name = name;
        this.measurement = measurement;
        this.categoryName = categoryName;
    }
    public Ingredient(){

    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
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


    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return name;
    }

}
