package com.group2.bottomapp;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo on 2013-11-19.
 */
public class APIManager {

    public static List<Cocktail> getAllAvailableCocktails(){
        String json = "{\"cocktails\": [{\"id\": 1,\"name\": \"redbull-vodka\",\"description\": \"Good\",\"ingredients\": [{\"id\": 1,\"name\": \"Vodka\",\"measurement\": \"6 cl\"},{\"id\": 1,\"name\": \"RedBull\",\"measurement\": \"1\"},{\"id\": 1,\"name\": \"Ice\",\"measurement\": \"3 pieces\"}],\"ratingUp\": 1,\"ratingDown\": 0,\"ingredientsSize\": 3},{\"id\": 2,\"name\": \"Screwdriver\",\"description\": \"Served in a highball glass.\\nMix 50ml Vodka (1 part) with 100ml Orange Juice (2 parts)\\n\\nThe most common variation of the Screwdriver is one part vodka, one part orange juice and one part orange soda\",\"ingredients\": [{\"id\": 1,\"name\": \"Orange Juice\",\"measurement\": \"100 ml\"},{\"id\": 1,\"name\": \"Vodka\",\"measurement\": \"50 ml\"}],\"ratingUp\": 1,\"ratingDown\": 0,\"ingredientsSize\": 2}]}";

        //Parsar ett objekt
        try {

            JSONObject obj = new JSONObject(json);

            JSONArray cocktailArr = obj.getJSONArray("cocktails");

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
}
