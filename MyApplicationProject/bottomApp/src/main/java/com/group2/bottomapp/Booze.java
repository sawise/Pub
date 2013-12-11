package com.group2.bottomapp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lukas on 2013-12-11.
 */
public class Booze {
    private int id;
    private String name;
    private String categoryID;
    private String categoryName;
    private HashMap<String, ArrayList<Booze>> boozeList;


    public Booze(int id, String name){
        this.id = id;
        this.name = name;
    }
    public Booze(){

    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public HashMap<String, ArrayList<Booze>> getIngredientList() {
        return boozeList;
    }

    public void setIngredientList(HashMap<String, ArrayList<Booze>> boozeList) {
        this.boozeList = boozeList;
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

