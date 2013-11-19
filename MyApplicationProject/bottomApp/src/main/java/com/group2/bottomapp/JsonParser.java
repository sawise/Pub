package com.group2.bottomapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo on 2013-11-19.
 */
public class JsonParser {
    private static String json = "{\"id\": 1,\"name\": \"redbull-vodka\",\"description\": \"Good\",\"ingredients\": [{\"id\": 1,\"name\": \"Vodka\",\"measurement\": \"6 cl\"},{\"id\": 1,\"name\": \"RedBull\",\"measurement\": \"1\"},{\"id\": 1,\"name\": \"Ice\",\"measurement\": \"3 pieces\"}],\"ratingUp\": 1,\"ratingDown\": 0,\"ingredientsSize\": 3}";

    public static List<Cocktail> getCocktails(){

        //Parsar ett objekt
        //TODO: När JSON-filen innehåller en lista av cocktails måste denna parsern ändras
        try {
            JSONObject obj = new JSONObject(json);

            int cocktailId = obj.getInt("id");
            String cocktailName = obj.getString("name");
            String cocktailDesc = obj.getString("description");
            int upvotes = obj.getInt("ratingUp");
            int downvotes = obj.getInt("ratingDown");

            List<Ingredient> ingredients = new ArrayList<Ingredient>();
            JSONArray jsonIngredients = obj.getJSONArray("ingredients");
            for (int i = 0; i < jsonIngredients.length(); i++)
            {
                JSONObject jsonIngredientObj = jsonIngredients.getJSONObject(i);
                int ingId = jsonIngredientObj.getInt("id");
                String ingName = jsonIngredientObj.getString("name");
                String ingMeasurement = jsonIngredientObj.getString("measurement");

                ingredients.add(new Ingredient(ingId, ingName, ingMeasurement));
            }


            ArrayList<Cocktail> listToReturn = new ArrayList<Cocktail>();

            listToReturn.add(new Cocktail(cocktailId, cocktailName, cocktailDesc, upvotes, downvotes));

            return listToReturn;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
