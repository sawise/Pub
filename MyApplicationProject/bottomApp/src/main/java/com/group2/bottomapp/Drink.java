package com.group2.bottomapp;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Drink extends Fragment implements View.OnClickListener {

    private String drinkName;
    private String drinkIngredients;
    private String drinkInstructions;

    private TextView tvDrinkName;
    private TextView tvDrinkIngredients;
    private TextView tvDrinkInstructions;
    private ImageView ivDrinkImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.drink, container, false);


        ivDrinkImage = (ImageView) rootView.findViewById(R.id.ivDrinkImage);

        tvDrinkName = (TextView) rootView.findViewById(R.id.tvDrinkName);
        tvDrinkIngredients = (TextView) rootView.findViewById(R.id.tvDrinkIngredients);
        tvDrinkInstructions = (TextView) rootView.findViewById(R.id.tvDrinkInstructions);

        initDrink();

        return rootView;
    }


    @Override
    public void onClick(View v) {

    }


    public void initDrink(){
        Cocktail cocktail = JsonParser.getCocktails().get(0);
        //TODO: Kraschar här... vene varför
       /*ArrayList<String> ingredientsFromAPI = new ArrayList<String>();
        ingredientsFromAPI.add("Orange Juice");
        ingredientsFromAPI.add("Vodka");

        drinkIngredients = "";

        for(String s : ingredientsFromAPI){
            drinkIngredients += s + "\n";
        }

        drinkName = "Screwdriver";
        drinkInstructions = "Served in a highball glass.\n";
        drinkInstructions += "Mix 50ml Vodka (1 part) with 100ml Orange Juice (2 parts)\n\n";
        drinkInstructions += "The most common variation of the Screwdriver is one part vodka, one part orange juice and one part orange soda";
        */

        Drawable image = getResources().getDrawable(R.drawable.ic_launcher);
        ivDrinkImage.setImageDrawable(image);

        tvDrinkName.setText(cocktail.getName());
        tvDrinkInstructions.setText(cocktail.getDescription());
        tvDrinkIngredients.setText(cocktail.getIngredientString());
    }
}