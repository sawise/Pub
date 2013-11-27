package com.group2.bottomapp;


import android.util.Log;

import java.util.List;

/**
 * Created by FilipFransson on 2013-11-19.
 */

public class Cocktail {
    private Convert convert;
    private int id;

    private int imageId;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    private String name;
    private String description;
    private  int ratingUp;
    private int ratingDown;
    private List<Ingredient> ingredients;

    public Cocktail(int id, String name, String description, int ratingUp, int ratingDown){
        this.id = id;
        this.name = name;
        this.description = description;
        this.ratingUp = ratingUp;
        this.ratingDown = ratingDown;
    }
    public Cocktail(){

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

    public List<Ingredient> getIngredients(){
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    public String getIngredientString(String format) {
        convert = new Convert();
        String result = "";
        for(Ingredient i : getIngredients()){
            String mesurementStr = i.getMeasurement();
            if(mesurementStr.contains("cl") || mesurementStr.contains("oz") || mesurementStr.contains("ml")|| mesurementStr.contains("ml")){
                String[] mesurement = mesurementStr.split(" ");
                double mesurementInt = Double.valueOf(mesurement[0]);
                result += i.getName() + ", ";
                if(mesurementStr.contains("oz")){
                    if(format.equals("cl")){
                        mesurementInt = convert.ozTocl(mesurementInt);
                        result +=  mesurementInt + " cl\n";
                    } else if(format.equals("ml")){
                        mesurementInt = convert.ozToml(mesurementInt);
                        result +=  mesurementInt + " ml \n";
                    } else {
                        result +=  mesurementInt + " fl oz \n";
                    }
                }else if(mesurementStr.contains("cl")){
                    if(format.equals("oz")){
                        mesurementInt = convert.clTooz(mesurementInt);
                        result +=  mesurementInt + " fl oz \n";
                    } else if(format.equals("ml")){
                        mesurementInt = convert.clToml(mesurementInt);
                        result +=  mesurementInt + " ml \n";
                    } else {
                        result +=  mesurementInt + " cl \n";
                    }
                }else if(mesurementStr.contains("ml")){
                    if(format.equals("oz")){
                        mesurementInt = convert.mlTooz(mesurementInt);
                        result +=  mesurementInt + " fl oz \n";
                    } else if(format.equals("cl")){
                        mesurementInt = convert.mlTocl(mesurementInt);
                        result +=  mesurementInt + " ml \n";
                    } else {
                        result +=  mesurementInt + " ml\n";
                    }
                } else{
                    result +=  mesurementInt + " cl\n";
                }
                Log.i("mesurement", i.getMeasurement());
            }else {
                result += i.getName() + ", " + mesurementStr + "\n";
            }

        }

        return result;
    }
}

