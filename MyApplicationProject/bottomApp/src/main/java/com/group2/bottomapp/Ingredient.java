package com.group2.bottomapp;

import java.util.ArrayList;

public class Ingredient {

    public int imageResourceId;

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

        int test = MainActivity.getAppContext().getResources().getIdentifier(name.replace(" ", "_").replace("/", "").toLowerCase(),"drawable",MainActivity.getAppContext().getPackageName());
        if (test != 0) {
            //Personlig bild finns
            imageResourceId = MainActivity.getAppContext().getResources().getIdentifier(name.replace(" ", "_").replace("/","").toLowerCase(),"drawable",MainActivity.getAppContext().getPackageName());
        } else {
            imageResourceId = R.drawable.ic_launcher;
        }
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
