package com.group2.bottomapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Drink extends Fragment implements View.OnClickListener {

    private String drinkName = "Screwdriver";
    private String drinkIngredients = "OJ, Vodka";
    private String drinkInstructions = "Mix OJ with the Vodka!";

    private TextView tvDrinkName;
    private TextView tvDrinkIngrediants;
    private TextView tvDrinkInstructions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.drink, container, false);

        initDrink();

        tvDrinkName = (TextView) rootView.findViewById(R.id.tvDrinkName);
        tvDrinkIngrediants = (TextView) rootView.findViewById(R.id.tvDrinkIngredients);
        tvDrinkInstructions = (TextView) rootView.findViewById(R.id.tvDrinkInstructions);

        tvDrinkName.setText(drinkName);
        tvDrinkInstructions.setText(drinkInstructions);
        tvDrinkIngrediants.setText(drinkIngredients);

        return rootView;
    }


    @Override
    public void onClick(View v) {

    }


    public void initDrink(){
        drinkName = "Screwdriver";
        drinkIngredients = "OJ, Vodka";
        drinkInstructions = "Mix OJ with the Vodka!";
    }
}