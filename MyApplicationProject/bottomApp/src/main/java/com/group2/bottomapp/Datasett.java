package com.group2.bottomapp;

/**
 * Created by sam on 12/4/13.
 */

import android.database.MatrixCursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Datasett {

    public  LinkedHashMap<String, ArrayList<Ingredient>> sections = new LinkedHashMap<String, ArrayList<Ingredient>>();
    public  LinkedHashMap<Integer, String> itemsInSection = new LinkedHashMap<Integer, String>();
    public  Object[] listObjects;
    public  int itemId;
    public  int categoryId;
    public  String item;
    public  String category;

    public Datasett(int itemId, int categoryId, String item, String category){
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.category = category;
        this.item = item;
    }
    public Datasett(){

    }


    public  LinkedHashMap<String, ArrayList<Ingredient>> getSections() {
        return sections;
    }

    public void setSections(LinkedHashMap<String, ArrayList<Ingredient>> sections) {
        this.sections = sections;
    }
    public  void addSection(String sectionName, ArrayList<Ingredient> sectionItem) {
        sections.put(sectionName, sectionItem);
    }

    public  LinkedHashMap<Integer, String> getItemsInsections() {
        return itemsInSection;
    }

    public void setItemsInsection(LinkedHashMap<Integer, String> itemsInsections) {
        this.itemsInSection = itemsInsections;
    }

    public  Object[] getListObjects() {
        return listObjects;
    }

    public void setListObjects(Object[] listObjects) {
        this.listObjects = listObjects;
    }

    public  int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public  int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public  String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public  String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



}
