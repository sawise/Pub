package com.group2.bottomapp;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Hugo on 2013-11-19.
 */
public class APIManager {

    public static List<Cocktail> getAllCocktails(){
        //TODO: get all cocktails, not just available
        return getAllAvailableCocktails();
    }

    public static List<Cocktail> getAllAvailableCocktails(){
        String json = "[{\"id\": 1,\"name\": \"Redbull Vodka\",\"description\": \"Add vodka and ice then mix with Redbull.\",\"ingredients\": [{\"id\": 1,\"name\": \"Vodka\",\"measurement\": \"6 cl\"},{\"id\": 3,\"name\": \"Redbull\",\"measurement\": \"1\"},{\"id\": 11,\"name\": \"Ice\",\"measurement\": \"3 pieces\"}],\"ratingUp\": 2,\"ratingDown\": 1,\"ingredientsSize\": 3},{\"id\": 2,\"name\": \"Vodka Orange Juice\",\"description\": \"Mix ice, vodka and orange juice.\",\"ingredients\": [{\"id\": 1,\"name\": \"Vodka\",\"measurement\": \"6 cl\"},{\"id\": 2,\"name\": \"Orange Juice\",\"measurement\": \"1\"},{\"id\": 11,\"name\": \"Ice\",\"measurement\": \"3 pieces\"}],\"ratingUp\": 2,\"ratingDown\": 1,\"ingredientsSize\": 3},{\"id\": 3,\"name\": \"The Ultimate Margarita\",\"description\": \"Shake together with ice and strain into a margarita glass. Optional: rub the cut side of a lime on the glass rim and dip it intp salt; and add a lime wedge to the rim of the glass as a garnish.\",\"ingredients\": [{\"id\": 4,\"name\": \"Orange Liqueur\",\"measurement\": \"0.5 oz\"},{\"id\": 5,\"name\": \"White Tequila\",\"measurement\": \"2 oz\"},{\"id\": 6,\"name\": \"Lemon Juice\",\"measurement\": \"1 oz\"},{\"id\": 7,\"name\": \"Lime Juice\",\"measurement\": \"1 oz\"},{\"id\": 8,\"name\": \"Syrup\",\"measurement\": \"1 oz\"}],\"ratingUp\": 0,\"ratingDown\": 0,\"ingredientsSize\": 5},{\"id\": 4,\"name\": \"Tropical Smoothie\",\"description\": \"Combine all ingredients into a blender and puree until smooth. Serve in tall stylish glasses.\",\"ingredients\": [{\"id\": 9,\"name\": \"Strawberr\",\"measurement\": \"1 qt hulled\"},{\"id\": 10,\"name\": \"Banana\",\"measurement\": \"1 chunked\"},{\"id\": 16,\"name\": \"Peach\",\"measurement\": \"2\"},{\"id\": 12,\"name\": \"Orange Peach-Mango Juice\",\"measurement\": \"1 cup\"},{\"id\": 11,\"name\": \"Ice\",\"measurement\": \"2 cup\"}],\"ratingUp\": 0,\"ratingDown\": 0,\"ingredientsSize\": 5},{\"id\": 5,\"name\": \"Monkey's Dessert\",\"description\": \"Shake Kahlua and banana liqueur in a cocktail shaker with ice. Pour into an old-fashioned glass and fill with chocolate milk.\",\"ingredients\": [{\"id\": 13,\"name\": \"Banana Liqueur\",\"measurement\": \"1 oz\"},{\"id\": 14,\"name\": \"Kahlua Coffe Liqueur\",\"measurement\": \"1 oz\"},{\"id\": 15,\"name\": \"Chocolate Milk\",\"measurement\": \"4 oz\"}],\"ratingUp\": 0,\"ratingDown\": 0,\"ingredientsSize\": 3},{\"id\": 6,\"name\": \"Screwdriver\",\"description\": \"Served in a highball glass.\\nMix 50ml Vodka (1 part) with 100ml Orange Juice (2 parts)\\n\\nThe most common variation of the Screwdriver is one part vodka, one part orange juice and one part orange soda\",\"ingredients\": [{\"id\": 1,\"name\": \"Orange Juice\",\"measurement\": \"100 ml\"},{\"id\": 1,\"name\": \"Vodka\",\"measurement\": \"50 ml\"}],\"ratingUp\": 1,\"ratingDown\": 0,\"ingredientsSize\": 2}]";

        //Parsar ett objekt
        try {

            JSONArray cocktailArr = new JSONArray(json);

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
