package com.group2.bottomapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Hugo on 2013-11-19.
 */
public class APIManager {

    public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static List<Cocktail> getAllCocktails(){
        //TODO: get all cocktails, not just available
        return getAllAvailableCocktails();
    }

    public static List<Cocktail> getAllAvailableCocktails(){
        //Parsar ett objekt
        if(!hazInternetz()) return new ArrayList<Cocktail>();
        try {

            JSONArray cocktailArr = readJsonFromUrl("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/drinks/all");

            ArrayList<Cocktail> listToReturn = new ArrayList<Cocktail>();

            for(int i = 0; i < cocktailArr.length(); i++){
                JSONObject cocktailObj = cocktailArr.getJSONObject(i);

                int cocktailId = cocktailObj.getInt("id");
                String cocktailName = cocktailObj.getString("name");
                String cocktailDesc = cocktailObj.getString("description");
                int upvotes = cocktailObj.getInt("ratingUp");
                int downvotes = cocktailObj.getInt("ratingDown");

                List<Ingredient> ingredients = new ArrayList<Ingredient>();
                JSONArray jsonIngredients = cocktailObj.getJSONArray("ingredients");
                for (int j = 0; j < jsonIngredients.length(); j++)
                {
                    JSONObject jsonIngredientObj = jsonIngredients.getJSONObject(j);
                    int ingId = jsonIngredientObj.getInt("id");
                    String ingName = jsonIngredientObj.getString("name");
                    String ingMeasurement = jsonIngredientObj.getString("measurement");
                    ingredients.add(new Ingredient(ingId, ingName, ingMeasurement));
                }

                Cocktail cocktailToAdd = new Cocktail(cocktailId, cocktailName, cocktailDesc, upvotes, downvotes);
                cocktailToAdd.setIngredients(ingredients);

                listToReturn.add(cocktailToAdd);
            }

            return listToReturn;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Categories> getCategories(){
        //Parsar ett objekt

        try {
            JSONArray CategoriesArr = readJsonFromUrl("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/categories/all");
            ArrayList<Categories> listToReturn = new ArrayList<Categories>();

            for(int i = 0; i < CategoriesArr.length(); i++){
                JSONObject cocktailObj = CategoriesArr.getJSONObject(i);

                int categoryId = cocktailObj.getInt("id");
                String categoryName = cocktailObj.getString("name");
                Log.i("JSONN category", categoryId+" - "+ categoryName);

                ArrayList<Categories> categories = new ArrayList<Categories>();
                categories.add(new Categories(categoryId, categoryName));
                Categories categoriesToAdd = new Categories();
                categoriesToAdd.setCategoryList(categories);
                listToReturn.add(categoriesToAdd);

            }
            return listToReturn;

        } catch (Exception e) {
            Log.i("ERRROR", e+"");
            e.printStackTrace();
        }

        return null;
    }


    public static ArrayList<Ingredient> getIngridient(int catId){
        //Parsar ett objekt
        try {
            JSONArray ingredientArr;

            if(catId > 0){
                ingredientArr = readJsonFromUrl("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/category/"+catId);
                Log.i("JSONN", "get from category");
            } else {
                ingredientArr = readJsonFromUrl("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/ingredients/all");
                Log.i("JSONN", "get all");
            }
            ArrayList<Ingredient> listToReturn = new ArrayList<Ingredient>();

            for(int i = 0; i < ingredientArr.length(); i++){
                JSONObject cocktailObj = ingredientArr.getJSONObject(i);
                int ingredientId = cocktailObj.getInt("id");
                String ingredientName = cocktailObj.getString("name");
                JSONObject ingredientCat = cocktailObj.getJSONObject("category");
                String ingMeasurement = cocktailObj.getString("measurement");

                ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
                int categoryID = ingredientCat.getInt("id");
                String categoryName = ingredientCat.getString("name");
                ingredients.add(new Ingredient(ingredientId, ingredientName, ingMeasurement, categoryName));
                Log.i("JSONN ing in cat:"+categoryID, ingredientId+" - "+ ingredientName);

                Ingredient ingredientToAdd = new Ingredient();
                ingredientToAdd.setIngredientList(ingredients);

                listToReturn.add(ingredientToAdd);
            }

            return listToReturn;

        } catch (Exception e) {
            Log.i("ERRROR", e+"");
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Ingredient> getIngridientinCategory(int catId){
        //Parsar ett objekt
        try {

            JSONArray ingredientArr = readJsonFromUrl("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/category/");
            ArrayList<Ingredient> listToReturn = new ArrayList<Ingredient>();

            for(int i = 0; i < ingredientArr.length(); i++){
                JSONObject cocktailObj = ingredientArr.getJSONObject(i);
                int ingredientId = cocktailObj.getInt("id");
                String ingredientName = cocktailObj.getString("name");
                JSONObject ingredientCat = cocktailObj.getJSONObject("category");
                String ingMeasurement = cocktailObj.getString("measurement");

                ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
                int categoryID = ingredientCat.getInt("id");
                String categoryName = ingredientCat.getString("name");
                ingredients.add(new Ingredient(ingredientId, categoryName, ingMeasurement));
                Log.i("JSONN ing", ingredientId+" - "+ ingredientName);
                Log.i("JSONN cat", categoryID+ " - " + categoryName);

                Ingredient ingredientToAdd = new Ingredient();
                ingredientToAdd.setIngredientList(ingredients);

                listToReturn.add(ingredientToAdd);
            }

            return listToReturn;

        } catch (Exception e) {
            Log.i("ERRROR", e+"");
            e.printStackTrace();
        }

        return null;
    }


    public static Cocktail getRandomDrink(){
        List<Cocktail> list = getAllAvailableCocktails();
        if(list.isEmpty()){
            return null;
        }
        Random rnd = new Random();

        int rndNo = rnd.nextInt(list.size());

        return list.get(rndNo);
    }

    public static Cocktail getDrinkWithID(int id){
        List<Cocktail> cocktails = getAllCocktails();
        if(cocktails.isEmpty()){
            return null;
        }
        for(Cocktail c : cocktails){
            if(c.getId() == id){
                return c;
            }
        }

        return null;
    }



    private static boolean hazInternetz(){
        ConnectivityManager connMgr = (ConnectivityManager) MainActivity.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
