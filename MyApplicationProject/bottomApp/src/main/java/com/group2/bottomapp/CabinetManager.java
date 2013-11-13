package com.group2.bottomapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Hugo on 2013-11-13.
 */

public class CabinetManager {

    private static ArrayList<String> ingredients = new ArrayList<String>();

    private static Application callBack;

    public static void Initiate(Application cb){
        callBack = cb;
        loadList();
    }

    public static void AddIngredient(String ing){
        if(!doesIngredientExistAlready(ing)){
            ingredients.add(ing);
        }

        saveList();
    }

    public static void RemoveIngredient(String ing){
        int ingredientId = getIngredientId(ing);
        if(ingredientId != -1){
            ingredients.remove(ingredientId);
        }

        saveList();
    }

    private static boolean doesIngredientExistAlready(String ing){
        for(String s : ingredients){
            if(s.equals(ing)){
               return true;
            }
        }
        return false;
    }

    private static int getIngredientId(String ing){
        for(String s : ingredients){
            if(s.equals(ing)){
                return ingredients.indexOf(s);
            }
        }
        return -1;
    }

    private static void saveList(){
        String fileString = "";
        for(String s : ingredients){
            fileString += ingredients + ";";
        }

        writeToFile(fileString);
    }

    private static void loadList(){
        String fileString = readFromFile();

        String[] ings = fileString.split(";");

        ingredients.clear();
        for(String s : ings){
            ingredients.add(s);
        }
    }


    private static void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(callBack.openFileOutput("cabinet.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = callBack.openFileInput("cabinet.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

}
