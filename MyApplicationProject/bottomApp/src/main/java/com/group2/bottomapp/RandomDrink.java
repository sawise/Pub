package com.group2.bottomapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class RandomDrink extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.random, container, false);

        final Cocktail cocktail = APIManager.getRandomDrink();

        final ImageView ivDrinkImage = (ImageView) rootView.findViewById(R.id.ivDrinkImage);
        final TextView tvDrinkName = (TextView) rootView.findViewById(R.id.tvDrinkName);
        final Button btnNewRandom = (Button) rootView.findViewById(R.id.btnNewRandom);

        Drawable image = getResources().getDrawable(R.drawable.ic_launcher);
        ivDrinkImage.setImageDrawable(image);

        tvDrinkName.setText(cocktail.getName());

        btnNewRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cocktail newCocktail = APIManager.getRandomDrink();
                while(newCocktail.equals(cocktail)){
                    newCocktail = APIManager.getRandomDrink();
                }
                tvDrinkName.setText(newCocktail.getName());
            }
        });

        return rootView;
    }


    @Override
    public void onClick(View v) {

    }

}