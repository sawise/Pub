package com.group2.bottomapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.group2.bottomapp.JsonDownloaders.JsonAddFavorite;
import com.group2.bottomapp.JsonDownloaders.JsonAddIngredient;
import com.group2.bottomapp.JsonDownloaders.JsonDownloadAllCocktails;
import com.group2.bottomapp.JsonDownloaders.JsonDownloadAvailableCocktails;
import com.group2.bottomapp.JsonDownloaders.JsonDownloadCategories;
import com.group2.bottomapp.JsonDownloaders.JsonDownloadIngredients;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class APIManager {

    public static ArrayList<Cocktail> allCocktails = new ArrayList<Cocktail>();
    public static ArrayList<Cocktail> availableCocktails = new ArrayList<Cocktail>();
    public static ArrayList<Categories> categories = new ArrayList<Categories>();
    public static ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    public static void updateEverything() {
        try {
            updateAllCocktails();
            updateAvailableCocktails();
            updateIngredients();
            updateCategories();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateAllCocktails() throws IOException, JSONException {
        JsonDownloadAllCocktails task = new JsonDownloadAllCocktails();
        task.execute("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/drinks/all");
    }

    public static void updateAvailableCocktails() throws IOException, JSONException {
        JsonDownloadAvailableCocktails task = new JsonDownloadAvailableCocktails();
        task.execute("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/" + HelperClass.User.userId);
    }

    public static void updateCategories() throws IOException, JSONException {
        JsonDownloadCategories task = new JsonDownloadCategories();
        task.execute("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/categories/all");
    }

    public static void updateIngredients() throws IOException, JSONException {
        JsonDownloadIngredients task = new JsonDownloadIngredients();
        task.execute("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/ingredients/all");
    }

    public static List<Cocktail> getAllCocktails() {
        //if(!hazInternetz()) return new ArrayList<Cocktail>();
        try {
            updateAllCocktails();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allCocktails;
    }

    public static List<Cocktail> getAllAvailableCocktails() {
        //if(!hazInternetz()) return new ArrayList<Cocktail>();
        try {
            updateAvailableCocktails();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableCocktails;
    }

    public static ArrayList<Categories> getCategories() {
        //if(!hazInternetz()) return new ArrayList<Cocktail>();
        try {
            updateCategories();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    public static ArrayList<Ingredient> getAllIngredients() {
        try {
            updateIngredients();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    public static ArrayList<Ingredient> getIngredientsByCategory(int category) {
        ArrayList<Ingredient> listToReturn = new ArrayList<Ingredient>();

        try {
            updateIngredients();

            String catName = "";

            for (Categories c : categories) {
                if (c.getId() == category) {
                    catName = c.getName();
                }
            }

            for (Ingredient i : getAllIngredients()) {
                Log.i("ingg", i.getName() + "<->" + i.getCategoryName());
                if (i.getCategoryName().equals(catName)) {
                    Log.i("ingg", i.getCategoryName());
                    listToReturn.add(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("JSONN", "" + e);
        }

        return listToReturn;
    }

    public static Cocktail getRandomDrink() {
        List<Cocktail> list = getAllAvailableCocktails();
        if (list.isEmpty()) {
            return null;
        }
        Random rnd = new Random();

        int rndNo = rnd.nextInt(list.size());

        return list.get(rndNo);
    }

    public static Cocktail getDrinkWithID(int id) {
        try {
            updateAllCocktails();
            List<Cocktail> cocktails = getAllCocktails();
            if (cocktails.isEmpty()) {
                return null;
            }
            for (Cocktail c : cocktails) {
                if (c.getId() == id) {
                    return c;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Cocktail> getCocktailsFromJson(String json) {
        try {

            JSONArray cocktailArr = new JSONArray(json);

            ArrayList<Cocktail> listToReturn = new ArrayList<Cocktail>();

            for (int i = 0; i < cocktailArr.length(); i++) {
                JSONObject cocktailObj = cocktailArr.getJSONObject(i);

                int cocktailId = cocktailObj.getInt("id");
                String cocktailName = cocktailObj.getString("name");
                String cocktailDesc = cocktailObj.getString("description");
                int upvotes = cocktailObj.getInt("ratingUp");
                int downvotes = cocktailObj.getInt("ratingDown");

                List<Ingredient> ingredients = new ArrayList<Ingredient>();
                JSONArray jsonIngredients = cocktailObj.getJSONArray("ingredients");
                for (int j = 0; j < jsonIngredients.length(); j++) {
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

    public static ArrayList<Categories> getCategoriesFromJson(String json) {
        try {

            JSONArray CategoriesArr = new JSONArray(json);
            ArrayList<Categories> listToReturn = new ArrayList<Categories>();

            for (int i = 0; i < CategoriesArr.length(); i++) {
                JSONObject cocktailObj = CategoriesArr.getJSONObject(i);

                int categoryId = cocktailObj.getInt("id");
                String categoryName = cocktailObj.getString("name");
                listToReturn.add(new Categories(categoryId, categoryName));
                ;
            }
            return listToReturn;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Ingredient> getIngredientsFromJson(String json) {
        ArrayList<Ingredient> listToReturn = new ArrayList<Ingredient>();

        try {
            JSONArray ingredientArr = new JSONArray(json);

            for (int i = 0; i < ingredientArr.length(); i++) {
                JSONObject cocktailObj = ingredientArr.getJSONObject(i);
                int ingredientId = cocktailObj.getInt("id");
                String ingredientName = cocktailObj.getString("name");
                JSONObject ingredientCat = cocktailObj.getJSONObject("category");
                String ingMeasurement = cocktailObj.getString("measurement");

                int categoryID = ingredientCat.getInt("id");
                String categoryName = ingredientCat.getString("name");

                Ingredient ingredientToAdd = new Ingredient();
                ingredientToAdd.setId(ingredientId);
                ingredientToAdd.setName(ingredientName);
                ingredientToAdd.setMeasurement(ingMeasurement);
                ingredientToAdd.setCategoryName(categoryName);
                ingredientToAdd.setCategoryID(categoryID);
                ingredientToAdd.setIngredientList(ingredients);

                listToReturn.add(ingredientToAdd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listToReturn;
    }

    //Uploads
    public static void addIngredientToAccount(int ingId) {
        Log.d("tjafsmannen", "försöker lägga till " + ingId + " till konto");
        JsonAddIngredient jsonAddIngredient = new JsonAddIngredient();
        jsonAddIngredient.execute("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/add/ingredient/" + ingId);
    }

    public static void addFavoriteToAccount(int ingId) {
        JsonAddFavorite jsonAddFavorite = new JsonAddFavorite();
        jsonAddFavorite.execute("http://dev2-vyh.softwerk.se:8080/bottomAppServer/json/users/add/favorite/" + ingId);
    }

    private static boolean hazInternetz() {
        ConnectivityManager connMgr = (ConnectivityManager) MainActivity.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
