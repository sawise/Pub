package com.group2.bottomapp;

import java.util.ArrayList;

/**
 * Created by sam on 12/10/13.
 */
public class Categories {
    private int id;
    private String name;
    private ArrayList<Categories> categoryList;


    public Categories(int id, String name, ArrayList<Categories> categoryList){
        this.id = id;
        this.name = name;
        this.categoryList = categoryList;
    }

    public Categories(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Categories(){
    }

    public ArrayList<Categories> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<Categories> categoryList) {
        this.categoryList = categoryList;
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
}
