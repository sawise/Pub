package com.group2.bottomapp;

import java.util.ArrayList;

/**
 * Created by FilipFransson on 2013-11-19.
 */
public class Ingredient {
    private int id;
    private String name;
    private String categoryID;
    private String categoryName;
    private String measurement;
    private ArrayList<Ingredient> ingredientList;


    public Ingredient(int id, String name, String measurement){
        this.id = id;
        this.name = name;
        this.measurement = measurement;
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


}
