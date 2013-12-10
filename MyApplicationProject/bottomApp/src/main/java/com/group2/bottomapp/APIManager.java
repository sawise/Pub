package com.group2.bottomapp;

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

    public static ArrayList<Ingredient> getIngridient(){
        //Parsar ett objekt
        try {

            JSONArray ingredientArr = readJsonFromUrl("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/ingredients/all");

            ArrayList<Ingredient> listToReturn = new ArrayList<Ingredient>();

            for(int i = 0; i < ingredientArr.length(); i++){
                JSONObject cocktailObj = ingredientArr.getJSONObject(i);
                int ingredientId = cocktailObj.getInt("id");
                String ingredientName = cocktailObj.getString("name");
                String ingredientCategory = cocktailObj.getString("description");
                Log.i("JSONN", ingredientId+" - "+ ingredientName+" - "+ ingredientCategory);

                ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
                JSONArray jsonIngredients = cocktailObj.getJSONArray("ingredients");
                for (int j = 0; j < jsonIngredients.length(); j++)
                {
                    JSONObject jsonIngredientObj = jsonIngredients.getJSONObject(j);
                    int ingId = jsonIngredientObj.getInt("id");
                    String ingName = jsonIngredientObj.getString("name");
                    String ingMeasurement = jsonIngredientObj.getString("measurement");

                    ingredients.add(new Ingredient(ingId, ingName, ingMeasurement));
                }

                Ingredient ingredientToAdd = new Ingredient();
                ingredientToAdd.setIngredientList(ingredients);

                listToReturn.add(ingredientToAdd);
            }

            return listToReturn;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Cocktail getRandomDrink(){
        List<Cocktail> list = getAllAvailableCocktails();
        Random rnd = new Random();

        int rndNo = rnd.nextInt(list.size());

        return list.get(rndNo);
    }

    public static Cocktail getDrinkWithID(int id){
        List<Cocktail> cocktails = getAllCocktails();

        for(Cocktail c : cocktails){
            if(c.getId() == id){
                return c;
            }
        }

        return null;
    }
}
